package me.andreasmelone.pinkfont.neoforge;

import me.andreasmelone.pinkfont.PinkFontCommon;
import me.andreasmelone.pinkfont.config.PinkFontConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = PinkFontCommon.MOD_ID, dist = Dist.CLIENT)
public class PinkFontNeoForge {
    public PinkFontNeoForge(IEventBus eventBus, ModContainer modContainer) {
        PinkFontCommon.init();
        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (container, parent) -> PinkFontConfig.create().generateScreen(parent)
        );
        eventBus.addListener(this::onAddClientReloadListeners);
    }

    public void onAddClientReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(PinkFontCommon.PRESET_MANAGER_RELOAD_LISTENER_ID, PinkFontCommon::onReloadListener);
    }
}