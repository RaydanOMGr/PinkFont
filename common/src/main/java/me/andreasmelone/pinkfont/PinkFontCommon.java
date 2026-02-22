package me.andreasmelone.pinkfont;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import me.andreasmelone.pinkfont.presets.PresetManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PinkFontCommon {
    public static final String MOD_ID = "pinkfont";
    public static final String MOD_NAME = "PinkFont";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceLocation PRESET_LOCATION = ResourceLocation.fromNamespaceAndPath(MOD_ID, "presets.json");
    public static final ResourceLocation PRESET_MANAGER_RELOAD_LISTENER_ID = ResourceLocation.fromNamespaceAndPath(PinkFontCommon.MOD_ID, "preset_manager_reloader");

    public static void init() {
        PinkFontConfig.HANDLER.load();
    }

    public static CompletableFuture<Void> onReloadListener(PreparableReloadListener.PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture.<Void>supplyAsync(() -> {
            LOGGER.info("Reloading presets!");
            PresetManager.INSTANCE.reload(manager, PinkFontCommon.PRESET_LOCATION);
            return null;
        }, backgroundExecutor).thenCompose(barrier::wait).thenAcceptAsync((s) -> {});
    }

    public static ResourceLocation webp(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "webp/" + path + ".webp");
    }
}