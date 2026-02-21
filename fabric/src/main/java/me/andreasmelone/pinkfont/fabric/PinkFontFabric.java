package me.andreasmelone.pinkfont.fabric;

import me.andreasmelone.pinkfont.PinkFontCommon;
import net.fabricmc.api.ModInitializer;

public class PinkFontFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PinkFontCommon.init();
    }
}
