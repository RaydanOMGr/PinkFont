package me.andreasmelone.pinkfont;

//? if fabric {
/*import net.fabricmc.loader.api.FabricLoader;
*///?} else if neoforge {
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.LoadingModList;
//?} else if forge {
/*import net.minecraftforge.fml.loading.LoadingModList;
*///?}
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class PinkFontMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] splitPackage = mixinClassName.split("\\.");
        String[] splitWords = splitPackage[splitPackage.length - 1].split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
        if ("compat".equalsIgnoreCase(splitPackage[splitPackage.length - 2])) {
            return isModLoaded(splitWords[0].toLowerCase());
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    private static boolean isModLoaded(String modId) {
        //? if fabric {
        /*return FabricLoader.getInstance().isModLoaded(modId);
        *///?} else {
        //? if >=1.21.9 {
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(modId) != null;
        //?} else {
//        return LoadingModList.get().getModFileById(modId) != null;
        //?}
        //?}
    }
}
