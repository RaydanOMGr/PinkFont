package me.andreasmelone.pinkfont.fabric;

import me.andreasmelone.pinkfont.PinkFontCommon;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PinkFontFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PinkFontCommon.init();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new PresetManagerReloadListener());
    }

    public static class PresetManagerReloadListener implements IdentifiableResourceReloadListener {
        @Override
        public ResourceLocation getFabricId() {
            return PinkFontCommon.PRESET_MANAGER_RELOAD_LISTENER_ID;
        }

        @Override
        @NotNull
        public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
            return PinkFontCommon.onReloadListener(barrier, manager, backgroundExecutor, gameExecutor);
        }
    }
}
