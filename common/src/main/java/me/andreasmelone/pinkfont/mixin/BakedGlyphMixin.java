package me.andreasmelone.pinkfont.mixin;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import me.andreasmelone.pinkfont.gradient.ColorGradient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;

@Mixin(BakedGlyph.class)
public class BakedGlyphMixin {
    @ModifyArgs(
            method = "render(ZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;IZI)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    public void changeMainColor(Args args) {
        args.set(0, pinkFont$processColor(args.get(0)));
    }

    @ModifyArgs(
            method = "buildEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(I)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    public void changeEffectColor(Args args) {
        args.set(0, pinkFont$processColor(args.get(0)));
    }

    @Unique
    private static int pinkFont$processColor(int rgbColor) {
        PinkFontConfig config = PinkFontConfig.getInstance();
        ColorGradient gradient = config.getColorGradient();

        if(!gradient.hasColors()) return rgbColor;

        long tickCount = ((MinecraftAccessor)Minecraft.getInstance()).getClientTickCount();
        int currentColor = gradient.getCurrentColor(tickCount + Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks());

        if(config.isBrightenDark()) {
            float[] hsbVals = Color.RGBtoHSB(ARGB.red(rgbColor), ARGB.green(rgbColor), ARGB.blue(rgbColor), null);
            if(hsbVals[2] < config.getMinBrightness()) {
                hsbVals[2] = config.getMinBrightness();
                rgbColor = Color.HSBtoRGB(hsbVals[0], hsbVals[1], hsbVals[2]);
            }
        }

        return config.getColorMode().processColor(rgbColor, currentColor);
    }
}
