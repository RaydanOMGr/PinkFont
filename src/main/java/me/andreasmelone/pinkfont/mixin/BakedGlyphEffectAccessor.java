package me.andreasmelone.pinkfont.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if <=1.21.1 {
/*import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BakedGlyph.Effect.class)
public interface BakedGlyphEffectAccessor {
    @Accessor("x0")
    float getX0();

    @Accessor("y0")
    float getY0();

    @Accessor("x1")
    float getX1();

    @Accessor("y1")
    float getY1();

    @Accessor("depth")
    float getDepth();

    @Accessor(value = "r")
    float getR();

    @Accessor(value = "g")
    float getG();

    @Accessor(value = "b")
    float getB();

    @Accessor(value = "a")
    float getA();
}*/
//?} else {
// just whatever so it doesn't complain about the class not existing
@Mixin(Minecraft.class)
public interface BakedGlyphEffectAccessor {
}
//?}
