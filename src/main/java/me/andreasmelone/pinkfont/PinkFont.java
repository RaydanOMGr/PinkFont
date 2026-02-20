package me.andreasmelone.pinkfont;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import net.fabricmc.api.ClientModInitializer;

public class PinkFont implements ClientModInitializer {
    public static final String MOD_ID = "pinkfont";

    @Override
    public void onInitializeClient() {
        PinkFontConfig.HANDLER.load();
    }
}
