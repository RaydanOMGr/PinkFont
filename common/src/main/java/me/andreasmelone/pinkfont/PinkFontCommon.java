package me.andreasmelone.pinkfont;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinkFontCommon {
    public static final String MOD_ID = "pinkfont";
    public static final String MOD_NAME = "PinkFont";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        PinkFontConfig.HANDLER.load();
    }

    public static ResourceLocation webp(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "webp/" + path + ".webp");
    }
}