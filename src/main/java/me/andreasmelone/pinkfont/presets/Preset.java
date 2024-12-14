package me.andreasmelone.pinkfont.presets;

import me.andreasmelone.pinkfont.color.ColorUtil;
import net.minecraft.network.chat.MutableComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public record Preset(Effect effect, float duration, List<Color> colors) {
    @Override
    public List<Color> colors() {
        return new ArrayList<>(colors);
    }

    public enum Effect {
        NONE,
        RAINBOW {
            @Override
            public MutableComponent transformName(MutableComponent component) {
                return ColorUtil.rainbowify(component, 3);
            }

            @Override
            public MutableComponent transformDescription(MutableComponent component) {
                return ColorUtil.rainbowify(component, 10);
            }
        },
        TRANS {
            @Override
            public MutableComponent transformName(MutableComponent component) {
                return ColorUtil.forcefem(component);
            }
        };

        public MutableComponent transformName(MutableComponent component) {
            return component;
        }

        public MutableComponent transformDescription(MutableComponent component) {
            return component;
        }
    }
}
