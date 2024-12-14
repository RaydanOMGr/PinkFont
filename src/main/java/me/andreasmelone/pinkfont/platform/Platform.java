package me.andreasmelone.pinkfont.platform;

import net.minecraft.resources.ResourceLocation;
//? if fabric {
/*import net.fabricmc.loader.api.FabricLoader;
*///?} else if neoforge {
import net.neoforged.fml.loading.FMLPaths;
//?} else {
/*import net.minecraftforge.fml.loading.FMLPaths;
*///?}

import java.nio.file.Path;

public class Platform {
    public static long clientTickCount = 0;

    public static Path getConfigDir() {
        //? if fabric {
        /*return FabricLoader.getInstance().getConfigDir();
        *///?} else {
        return FMLPaths.CONFIGDIR.get();
        //?}
    }

    public static long getTickCount() {
        return clientTickCount;
    }

    public static ResourceLocation newResourceLocation(String namespace, String path) {
        //? if >=1.21.1 {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //?} else {
        /*return new ResourceLocation(namespace, path);
        *///?}
    }
}
