package me.andreasmelone.pinkfont.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.andreasmelone.pinkfont.PinkFont;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PinkFontConfig {
    public static ConfigClassHandler<PinkFontConfig> HANDLER = ConfigClassHandler.createBuilder(PinkFontConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(PinkFont.MOD_ID, "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve(PinkFont.MOD_ID + ".json5"))
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry
    private float cyan = 0.0f;
    @SerialEntry
    private float magenta = 0.2f;
    @SerialEntry
    private float yellow = 0.0f;

    public static YetAnotherConfigLib create() {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.empty())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config_category.pinkfont.colors"))
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("config_option.pinkfont.cyan"))
                                .binding(0.0f, () -> getInstance().cyan, cyan -> getInstance().cyan = cyan)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f, 1.0f).step(0.1f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("config_option.pinkfont.magenta"))
                                .binding(0.2f, () -> getInstance().magenta, magenta -> getInstance().magenta = magenta)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f, 1.0f).step(0.1f))
                                .build())
                        .option(Option.<Float>createBuilder()
                                .name(Component.translatable("config_option.pinkfont.yellow"))
                                .binding(0.0f, () -> getInstance().yellow, yellow -> getInstance().yellow = yellow)
                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.0f, 1.0f).step(0.1f))
                                .build())
                        .build()
                )
                .save(HANDLER::save)
                .build();
    }

    public float getCyan() {
        return cyan;
    }

    public void setCyan(float cyan) {
        this.cyan = cyan;
    }

    public float getMagenta() {
        return magenta;
    }

    public void setMagenta(float magenta) {
        this.magenta = magenta;
    }

    public float getYellow() {
        return yellow;
    }

    public void setYellow(float yellow) {
        this.yellow = yellow;
    }

    public static PinkFontConfig getInstance() {
        return HANDLER.instance();
    }
}
