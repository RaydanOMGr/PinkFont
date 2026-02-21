package me.andreasmelone.pinkfont.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.andreasmelone.pinkfont.config.PinkFontConfig;

public class ModMenuAdapter implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> PinkFontConfig.create().generateScreen(parent);
    }
}