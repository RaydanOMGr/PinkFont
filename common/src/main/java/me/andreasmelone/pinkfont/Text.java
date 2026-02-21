package me.andreasmelone.pinkfont;

import net.minecraft.network.chat.Component;

public class Text {
    public static final Component CATEGORY_COLORS = Component.translatable("config_category.pinkfont.colors");

    public static final Component OPTION_COLOR_MODE = Component.translatable("config_option.pinkfont.color_mode");
    public static final Component OPTION_COLOR_MODE_DESCRIPTION = Component.translatable("config_description.pinkfont.color_mode");

    public static final Component OPTION_BRIGHTEN_DARK = Component.translatable("config_option.pinkfont.brighten_dark");
    public static final Component OPTION_BRIGHTEN_DARK_DESCRIPTION = Component.translatable("config_description.pinkfont.brighten_dark");

    public static final Component OPTION_MIN_BRIGHTNESS = Component.translatable("config_option.pinkfont.min_brightness");
    public static final Component OPTION_MIN_BRIGHTNESS_DESCRIPTION = Component.translatable("config_description.pinkfont.min_brightness");

    public static final Component OPTION_GRADIENT_DURATION = Component.translatable("config_option.pinkfont.gradient_duration");
    public static final Component OPTION_GRADIENT_DURATION_DESCRIPTION = Component.translatable("config_description.pinkfont.gradient_duration");

    public static final Component OPTION_GRADIENT_COLORS = Component.translatable("config_option.pinkfont.gradient_colors");
    public static final Component OPTION_GRADIENT_COLORS_DESCRIPTION = Component.translatable("config_description.pinkfont.gradient_colors");

    public static final Component CATEGORY_PRESETS = Component.translatable("config_category.pinkfont.presets");

    public static final Component BUTTON_RAINBOW = Component.translatable("config_button.pinkfont.rainbow");
    public static final Component BUTTON_RAINBOW_DESCRIPTION = Component.translatable("config_description.pinkfont.rainbow");

    public static final Component GUI_APPLY = Component.translatable("gui.pinkfont.apply");

    public static Component requires(Component dependency) {
        return Component.translatable("config_description.pinkfont.dependency", dependency).withStyle(style -> style.withBold(true));
    }
}
