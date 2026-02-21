package me.andreasmelone.pinkfont.color;

import me.andreasmelone.pinkfont.gradient.ColorGradient;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.awt.Color;

public class ColorUtil {
    public static float brightness(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        int max = Math.max(r, Math.max(g, b));

        return max / 255.0f;
    }

    public static int setBrightness(int argb, float brightness) {
        brightness = Math.max(0f, Math.min(1f, brightness));

        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        float[] hsb = Color.RGBtoHSB(r, g, b, null);

        hsb[2] = brightness;

        int rgb =  Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

        return (a << 24) | (rgb & 0x00FFFFFF);
    }

    public static Component rainbowify(Component component, int factor) {
        ColorGradient gradient = new ColorGradient(
                factor,
                0xFFFF0000, 0xFFFF5E00, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0077FF, 0xFF0000FF, 0xFF8000FF, 0xFFFF00FF
        );

        MutableComponent mutable = Component.empty();

        for (Component c : component.toFlatList()) {
            String content = c.getString();
            for(int i = 0; i < content.length(); i++) {
                int color = gradient.getCurrentColor(i);
                mutable.append(Component.literal(content.charAt(i) + "").withStyle(c.getStyle()).withColor(color));
            }

        }

        return mutable;
    }
}
