package me.andreasmelone.pinkfont.mixin;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BakedGlyph.class)
public class BakedGlyphMixin {
    @ModifyArgs(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    public void changeColor(Args args) {
        float r = args.get(0);
        float g = args.get(1);
        float b = args.get(2);

        args.set(0, Mth.clamp(r - PinkFontConfig.getInstance().getCyan(), 0.0f, 1.0f));
        args.set(1, Mth.clamp(g - PinkFontConfig.getInstance().getMagenta(), 0.0f, 1.0f));
        args.set(2, Mth.clamp(b - PinkFontConfig.getInstance().getYellow(), 0.0f, 1.0f));
    }
}
