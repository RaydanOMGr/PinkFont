package me.andreasmelone.pinkfont.color;

import net.minecraft.util.Mth;

public class ARGB {
    public static int alpha(int i) {
        return i >>> 24;
    }

    public static int red(int i) {
        return i >> 16 & 255;
    }

    public static int green(int i) {
        return i >> 8 & 255;
    }

    public static int blue(int i) {
        return i & 255;
    }

    public static int color(int i, int j, int k, int l) {
        return i << 24 | j << 16 | k << 8 | l;
    }

    public static int color(int i, int j, int k) {
        return color(255, i, j, k);
    }

    public static int lerp(float f, int i, int j) {
        int k = Mth.lerpInt(f, alpha(i), alpha(j));
        int l = Mth.lerpInt(f, red(i), red(j));
        int m = Mth.lerpInt(f, green(i), green(j));
        int n = Mth.lerpInt(f, blue(i), blue(j));
        return color(k, l, m, n);
    }
}
