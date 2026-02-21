package me.andreasmelone.pinkfont;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinkFontCommon {
    public static final String MOD_ID = "pinkfont";
    public static final String MOD_NAME = "PinkFont";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        PinkFontConfig.HANDLER.load();
    }
}