package me.andreasmelone.pinkfont.gradient;

import com.google.gson.*;
import net.minecraft.util.ARGB;

public class ColorGradient {
    public static final ColorGradient RAINBOW = new ColorGradient(
            10,
            0xFFFF0000, 0xFFFF5E00, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0077FF, 0xFF0000FF, 0xFF8000FF, 0xFFFF00FF
    );

    private final float duration;
    private final int[] colorList;

    public ColorGradient(float duration, int... colorList) {
        this.duration = duration;
        this.colorList = colorList;
    }

    public int getCurrentColor(float tick) {
        if(this.colorList.length == 1) return this.colorList[0];

        float maxDuration = duration * colorList.length;
        float currentTick = tick % maxDuration;

        int currentColor = (int)Math.floor(currentTick / duration);

        float segmentTime = currentTick % duration;
        float progress = segmentTime / duration;

        int nextColor = (currentColor + 1) % colorList.length;

        return ARGB.lerp(progress, this.colorList[currentColor], this.colorList[nextColor]);
    }

    public boolean hasColors() {
        return this.colorList.length > 0;
    }

    public float getDuration() {
        return duration;
    }

    public int[] getColorList() {
        return colorList.clone();
    }
}
