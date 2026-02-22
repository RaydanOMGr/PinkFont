package me.andreasmelone.pinkfont.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.ValueFormatters;
import me.andreasmelone.pinkfont.PinkFontCommon;
import me.andreasmelone.pinkfont.locations.Text;
import me.andreasmelone.pinkfont.color.AwtColorHexSerializer;
import me.andreasmelone.pinkfont.gradient.ColorGradient;
import me.andreasmelone.pinkfont.platform.PlatformHelper;
import me.andreasmelone.pinkfont.presets.PresetManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PinkFontConfig {
    private static final List<Color> DEFAULT_COLORS = List.of(new Color(0xFFFFCCFF));
    private static final List<Color> RAINBOW_COLORS = List.of(
            new Color(0xFFFF0000),
            new Color(0xFFFF5E00),
            new Color(0xFFFFFF00),
            new Color(0xFF00FF00),
            new Color(0xFF00FFFF),
            new Color(0xFF0077FF),
            new Color(0xFF0000FF),
            new Color(0xFF8000FF),
            new Color(0xFFFF00FF)
    );
    private static final List<Color> TRANS_COLORS = List.of(
            new Color(0xFF2DC6DE),
            new Color(0xFFFDB6FF),
            new Color(0xFFFFFFFF),
            new Color(0xFFFDB6FF),
            new Color(0xFF2DC6DE)
    );

    public static ConfigClassHandler<PinkFontConfig> HANDLER = ConfigClassHandler.createBuilder(PinkFontConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(PinkFontCommon.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PlatformHelper.Instance.getConfigDir().resolve(PinkFontCommon.MOD_ID + ".json5"))
                            .appendGsonBuilder((builder) -> builder
                                    .setPrettyPrinting()
                                    .registerTypeAdapter(Color.class, new AwtColorHexSerializer()))
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry(comment = "The color mode defines how the colors get applied. Possible modes: SUBTRACT, SET, SET_KEEP_BRIGHTNESS")
    private ColorMode colorMode = ColorMode.SUBTRACT;
    @SerialEntry(comment = "Whether dark colors should be made brighter so applied colors can be seen")
    private boolean brightenDark = false;
    @SerialEntry(comment = "If the previous option is toggled, this is the brightness value that text will have at least (range: 0.0 - 1.0, values above 0.3 may affect shadow colors)")
    private float minBrightness = 0.2f;
    @SerialEntry(comment = "The amount of time it takes to go from one color to another")
    private float duration = 0;
    @SerialEntry(comment = "A list of colors to smoothly swap between in the format \"#RRGGBBAA\"")
    private List<Color> colors = new ArrayList<>(DEFAULT_COLORS);

    private boolean gradientDirty = false;
    private ColorGradient gradient;

    public static YetAnotherConfigLib create() {
        Option<Float> durationOption;
        ListOption<Color> colorListOption;
        // I try to avoid initializing options in variables, as that hurts readability
        // but here I don't think I can do it in any other way without introducing additional complexity
        Option<Float> minBrightnessOption = Option.<Float>createBuilder()
                .name(Text.OPTION_MIN_BRIGHTNESS)
                .description(OptionDescription.createBuilder()
                        .text(Text.requires(Text.OPTION_BRIGHTEN_DARK))
                        .text(Component.empty())
                        .text(Text.OPTION_MIN_BRIGHTNESS_DESCRIPTION)
                        .build())
                .binding(0.2f, () -> getInstance().minBrightness, minBrightness -> getInstance().minBrightness = minBrightness)
                .controller(opt -> FloatSliderControllerBuilder.create(opt)
                        .range(0.0f, 1.0f)
                        .step(0.01f)
                        .formatValue(ValueFormatters.percent(0)))
                .available(getInstance().brightenDark)
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.empty())
                .category(ConfigCategory.createBuilder()
                        .name(Text.CATEGORY_COLORS)
                        .option(Option.<ColorMode>createBuilder()
                                .name(Text.OPTION_COLOR_MODE)
                                .description(OptionDescription.createBuilder()
                                        .webpImage(PinkFontCommon.webp("colormode_preview"))
                                        .text(Text.OPTION_COLOR_MODE_DESCRIPTION)
                                        .build())
                                .binding(ColorMode.SUBTRACT, () -> getInstance().colorMode, colorMode -> getInstance().colorMode = colorMode)
                                .controller(opt -> EnumControllerBuilder.create(opt)
                                        .enumClass(ColorMode.class)
                                        .formatValue((mode) -> Component.translatable(mode.getTranslationKey())))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.OPTION_BRIGHTEN_DARK)
                                .description(OptionDescription.createBuilder()
                                        .text(Text.OPTION_BRIGHTEN_DARK_DESCRIPTION)
                                        .build())
                                .binding(false, () -> getInstance().brightenDark, brightenDark -> {
                                    getInstance().brightenDark = brightenDark;
                                    minBrightnessOption.setAvailable(brightenDark);
                                })
                                .addListener((option, event) -> minBrightnessOption.setAvailable(option.pendingValue()))
                                .controller(opt -> BooleanControllerBuilder.create(opt).onOffFormatter())
                                .build())
                        .option(minBrightnessOption)
                        .option(durationOption = Option.<Float>createBuilder()
                                .name(Text.OPTION_GRADIENT_DURATION)
                                .description(OptionDescription.createBuilder()
                                        .text(Text.OPTION_GRADIENT_DURATION_DESCRIPTION)
                                        .build())
                                .binding(0.0f, () -> getInstance().duration, duration -> {
                                    getInstance().duration = duration;
                                    getInstance().gradientDirty = true;
                                })
                                .controller(opt -> FloatFieldControllerBuilder.create(opt).min(0.0f))
                                .build())
                        .group(colorListOption = ListOption.<Color>createBuilder()
                                .name(Text.OPTION_GRADIENT_COLORS)
                                .description(OptionDescription.createBuilder()
                                        .text(Text.OPTION_GRADIENT_COLORS_DESCRIPTION)
                                        .build())
                                .initial(() -> {
                                    if(getInstance().colors.isEmpty()) return DEFAULT_COLORS.getFirst();
                                    return getInstance().colors.getLast();
                                })
                                .binding(DEFAULT_COLORS, () -> getInstance().colors, colors -> {
                                    getInstance().colors = colors;
                                    getInstance().gradientDirty = true;
                                })
                                .collapsed(false)
                                .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(true))
                                .insertEntriesAtEnd(true)
                                .build())
                        .build()
                )
                .category(PresetManager.INSTANCE.populatePresetCategory((duration) -> { getInstance().setDuration(duration); durationOption.requestSet(duration); },
                                (colorList) -> { getInstance().setColors(colorList); colorListOption.requestSet(colorList); },
                        ConfigCategory.createBuilder()
                        .name(Text.CATEGORY_PRESETS))
                        .build())
                .save(HANDLER::save)
                .build();
    }

    @NotNull
    public ColorMode getColorMode() {
        if(this.colorMode == null) this.colorMode = ColorMode.SUBTRACT;
        return this.colorMode;
    }

    public void setColorMode(@NotNull ColorMode colorMode) {
        this.colorMode = colorMode;
    }

    public boolean isBrightenDark() {
        return brightenDark;
    }

    public void setBrightenDark(boolean brightenDark) {
        this.brightenDark = brightenDark;
    }

    public float getMinBrightness() {
        return minBrightness;
    }

    public void setMinBrightness(float minBrightness) {
        this.minBrightness = minBrightness;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
        this.gradientDirty = true;
    }

    @NotNull
    public List<Color> getColors() {
        if(this.colors == null) this.colors = new ArrayList<>(DEFAULT_COLORS);
        return this.colors;
    }

    public void setColors(@NotNull List<Color> colors) {
        this.colors = colors;
        this.gradientDirty = true;
    }

    @NotNull
    public ColorGradient getColorGradient() {
        if(this.gradientDirty || this.gradient == null) {
            this.gradient = new ColorGradient(
                    this.duration,
                    this.colors.stream().mapToInt(Color::getRGB).toArray()
            );
            this.gradientDirty = false;
        }
        return this.gradient;
    }

    public static PinkFontConfig getInstance() {
        return HANDLER.instance();
    }
}
