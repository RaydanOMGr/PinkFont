package me.andreasmelone.pinkfont.config;

import me.andreasmelone.pinkfont.PinkFontCommon;
import me.andreasmelone.pinkfont.color.ColorUtil;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public enum ColorMode {
    SUBTRACT {
        @Override
        public int processColor(int colorInput, int modifierColor) {
            int r = ARGB.red(colorInput);
            int g = ARGB.green(colorInput);
            int b = ARGB.blue(colorInput);
            int a = ARGB.alpha(colorInput);

            int r2 = 255 - ARGB.red(modifierColor);
            int g2 = 255 - ARGB.green(modifierColor);
            int b2 = 255 - ARGB.blue(modifierColor);
            int a2 = 255 - ARGB.alpha(modifierColor);

            return ARGB.color(
                    Mth.clamp(a - a2, 0, 255),
                    Mth.clamp(r - r2, 0, 255),
                    Mth.clamp(g - g2, 0, 255),
                    Mth.clamp(b - b2, 0, 255)
            );
        }
    },
    SET {
        @Override
        public int processColor(int colorInput, int modifierColor) {
            return modifierColor;
        }
    },
    SET_KEEP_BRIGHTNESS {
        @Override
        public int processColor(int colorInput, int modifierColor) {
            float brightness = ColorUtil.brightness(colorInput);
            return ColorUtil.setBrightness(modifierColor, brightness);
        }
    };

    public String getTranslationKey() {
        return "color_mode." + PinkFontCommon.MOD_ID + "." + this.name().toLowerCase();
    }

    public abstract int processColor(int colorInput, int modifierColor);
}
