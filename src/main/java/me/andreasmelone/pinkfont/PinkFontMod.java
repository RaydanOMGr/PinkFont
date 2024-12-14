package me.andreasmelone.pinkfont;

import me.andreasmelone.pinkfont.config.PinkFontConfig;
import me.andreasmelone.pinkfont.platform.Platform;
import me.andreasmelone.pinkfont.presets.PresetManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
//? if neoforge {
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
//? if >=1.21.4 {
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
//?} else {
/*import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
*///?}
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//?} else if fabric {
/*import net.fabricmc.api.ClientModInitializer;
//? if >=1.21.9 {
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
//?} else {
/^import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
^///?}
*///?} else if forge {
/*import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*///?}
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

//? if !fabric {
    @Mod(value = PinkFontMod.MOD_ID)
    public class PinkFontMod {
//?} else {
    /*public class PinkFontMod implements ClientModInitializer {
*///?}
    public static final String MOD_ID = "pinkfont";
    public static final String MOD_NAME = "PinkFont";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ResourceLocation PRESET_LOCATION = rl("presets.json");
    public static final ResourceLocation PRESET_MANAGER_RELOAD_LISTENER_ID = rl("preset_manager_reloader");

    public static void init() {
        PinkFontConfig.HANDLER.load();
    }

    public static CompletableFuture<Void> onReloadListener(PreparableReloadListener.PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture.<Void>supplyAsync(() -> {
            LOGGER.info("Reloading presets!");
            PresetManager.INSTANCE.reload(manager, PinkFontMod.PRESET_LOCATION);
            return null;
        }, backgroundExecutor).thenCompose(barrier::wait).thenAcceptAsync((s) -> {});
    }

    public static ResourceLocation webp(String path) {
        return rl("webp/" + path + ".webp");
    }

    public static ResourceLocation rl(String path) {
        return Platform.newResourceLocation(MOD_ID, path);
    }

    //? if fabric {
    /*@Override
    public void onInitializeClient() {
        init();

        //? if >= 1.21.9 {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(PRESET_MANAGER_RELOAD_LISTENER_ID, new PresetManagerReloadListener());
        //?} else {
//        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new PresetManagerReloadListener());
        //?}
    }


    public static class PresetManagerReloadListener
        //? if >=1.21.9 {
            implements PreparableReloadListener {
        //?} else {
//            implements IdentifiableResourceReloadListener {
//        @Override
        //?}
        public ResourceLocation getFabricId() {
            return PRESET_MANAGER_RELOAD_LISTENER_ID;
        }

        @Override
        @NotNull
        //? if >=1.21.9 {
        public CompletableFuture<Void> reload(SharedState sharedState, Executor backgroundExecutor, PreparationBarrier barrier, Executor gameExecutor) {
            ResourceManager manager = sharedState.resourceManager();
        //?} else if >=1.21.4 {
        //public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, Executor backgroundExecutor, Executor gameExecutor) {
        //?} else {
        /^public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, ProfilerFiller backgroundFiller, ProfilerFiller gameFiller,
                                              Executor backgroundExecutor, Executor gameExecutor) {
        ^///?}
            return onReloadListener(barrier, manager, backgroundExecutor, gameExecutor);
        }
    }
    *///?} else {
    //? if neoforge {
    public PinkFontMod(IEventBus eventBus, ModContainer modContainer) {
    //?} else {
    /*public PinkFontMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    *///?}
        PinkFontMod.init();
        //? if >=1.21.1 {
        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (container, parent) -> PinkFontConfig.create().generateScreen(parent)
        );
        //?} else {
        /*ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> PinkFontConfig.create().generateScreen(parent))
        );
        *///?}
        eventBus.addListener(this::onAddClientReloadListeners);
    }

    //? if >=1.21.4 {
    public void onAddClientReloadListeners(AddClientReloadListenersEvent event) {
        //? if >=1.21.9 {
        event.addListener(PRESET_MANAGER_RELOAD_LISTENER_ID, (sharedState, backgroundExecutor, barrier, gameExecutor) -> {
            return onReloadListener(barrier, sharedState.resourceManager(), backgroundExecutor, gameExecutor);
        });
        //? } else {
//        event.addListener(PinkFontMod.PRESET_MANAGER_RELOAD_LISTENER_ID, PinkFontMod::onReloadListener);
        //?}
    }
    //?} else {
        /*public void onAddClientReloadListeners(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener((barrier, manager, backgroundFiller, gameFiller,
                                          backgroundExecutor, gameExecutor) -> {
                return PinkFontMod.onReloadListener(barrier, manager, backgroundExecutor, gameExecutor);
            });
        }
    *///?}
    //?}
}