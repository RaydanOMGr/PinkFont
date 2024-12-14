package me.andreasmelone.pinkfont.mixin;

//? if !forge {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//?} else {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
*///?}
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.andreasmelone.pinkfont.color.ColorUtil;
import me.andreasmelone.pinkfont.config.PinkFontConfig;
import me.andreasmelone.pinkfont.gradient.ColorGradient;
import me.andreasmelone.pinkfont.platform.Platform;
import net.minecraft.client.Minecraft;
//? if >=1.21.9 {
import net.minecraft.client.gui.font.glyphs.BakedSheetGlyph;
//?}
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
//? if >=1.21.4 {
import net.minecraft.util.ARGB;
//?} else {
/*import me.andreasmelone.pinkfont.color.ARGB;
*///?}
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Coerce;

import java.awt.*;

//? if >=1.21.9 {
@Mixin(BakedSheetGlyph.class)
//?} else {
//@Mixin(BakedGlyph.class)
//?}
public class BakedGlyphMixin {
    //? if >=1.21.9 {
    @WrapMethod(
            method = "render(ZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;IZI)V"
    )
    public void wrapRender(boolean bl, float f, float g, float h, Matrix4f matrix4f, VertexConsumer vertexConsumer, int color, boolean bl2, int j, Operation<Void> original) {
        int changedColor = pinkFont$processColor(color);
        original.call(bl, f, g, h, matrix4f, vertexConsumer, changedColor, bl2, j);
    }

    @WrapMethod(
            method = "buildEffect"
    )
    public void wrapEffectBuilder(@Coerce Object effect, float shadowOffset, float depthOffset, int shadowColor, VertexConsumer consumer, int packedLight, Matrix4f pose, Operation<Void> original) {
        int changedColor = pinkFont$processColor(shadowColor);
        original.call(effect, shadowOffset, depthOffset, changedColor, consumer, packedLight, pose);
    }
    //?} else if <1.21.9 && >=1.21.4 {
//    @WrapMethod(
//            method = "render(ZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;IZI)V"
//    )
//    public void wrapRender(boolean bl, float f, float g, float h, Matrix4f matrix4f, VertexConsumer vertexConsumer, int color, boolean bl2, int j, Operation<Void> original) {
//        int changedColor = pinkFont$processColor(color);
//        original.call(bl, f, g, h, matrix4f, vertexConsumer, changedColor, bl2, j);
//    }
//
//    @WrapMethod(
//            method = "buildEffect"
//    )
//    public void wrapEffectBuilder(BakedGlyph.Effect effect, float f, float g, int color, VertexConsumer vertexConsumer, int j, Matrix4f matrix4f, Operation<Void> original) {
//        int changedColor = pinkFont$processColor(color);
//        original.call(effect, f, g, changedColor, vertexConsumer, j, matrix4f);
//    }

    //?} else <1.21.4 {
    /*//? if !forge {
    @WrapMethod(
            method = "render"
    )
    public void wrapRender(boolean bl, float f, float j, Matrix4f matrix4f, VertexConsumer vertexConsumer, float r, float g, float b, float a, int l, Operation<Void> original) {
        int changedColor = pinkFont$processColor(ColorUtil.floatToARGB(a, r, g, b));
        float[] argb = ColorUtil.argbToFloat(changedColor);
        original.call(bl, f, j, matrix4f, vertexConsumer, argb[1], argb[2], argb[3], argb[0], l);
    }

    @WrapMethod(
            method = "renderEffect"
    )
    public void wrapRenderEffect(BakedGlyph.Effect effect, Matrix4f matrix4f, VertexConsumer vertexConsumer, int i, Operation<Void> original) {
        BakedGlyphEffectAccessor effectAccessor = (BakedGlyphEffectAccessor) effect;
        int changedColor = pinkFont$processColor(ColorUtil.floatToARGB(effectAccessor.getA(), effectAccessor.getR(), effectAccessor.getG(), effectAccessor.getB()));
        float[] argb = ColorUtil.argbToFloat(changedColor);
        BakedGlyph.Effect newEffect = new BakedGlyph.Effect(
                effectAccessor.getX0(), effectAccessor.getY0(),
                effectAccessor.getX1(), effectAccessor.getY1(),
                effectAccessor.getDepth(),
                argb[1], argb[2], argb[3], argb[0]
        );
        original.call(newEffect, matrix4f, vertexConsumer, i);
    }
    //?} else {
    /^@Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    public VertexConsumer changeMainColor(VertexConsumer instance, float r, float g, float b, float a) {
        int resultColor = pinkFont$processColor(ColorUtil.floatToARGB(a, r, g, b));
        float[] argb = ColorUtil.argbToFloat(resultColor);
        return instance.color(argb[1], argb[2], argb[3], argb[0]);
    }

    @Redirect(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    public VertexConsumer changeEffectColor(VertexConsumer instance, float r, float g, float b, float a) {
        int resultColor = pinkFont$processColor(ColorUtil.floatToARGB(a, r, g, b));
        float[] argb = ColorUtil.argbToFloat(resultColor);
        return instance.color(argb[1], argb[2], argb[3], argb[0]);
    }
    ^///?}
    *///?}

    @Unique
    private static int pinkFont$processColor(int rgbColor) {
        Minecraft minecraft = Minecraft.getInstance();
        PinkFontConfig config = PinkFontConfig.getInstance();
        ColorGradient gradient = config.getColorGradient();

        if(!gradient.hasColors()) return rgbColor;

        //? if >=1.21.4 {
        float deltaFrameTime = minecraft.getDeltaTracker().getRealtimeDeltaTicks();
        //?} else if 1.21.1 {
        /*float deltaFrameTime = minecraft.getTimer().getRealtimeDeltaTicks();
        *///?} else {
        /*float deltaFrameTime = minecraft.getDeltaFrameTime();
        *///?}

        int currentColor = gradient.getCurrentColor(Platform.getTickCount() + deltaFrameTime);

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
