package me.andreasmelone.pinkfont.presets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.OptionDescription;
import me.andreasmelone.pinkfont.PinkFontMod;
import me.andreasmelone.pinkfont.locations.Text;
import me.andreasmelone.pinkfont.color.AwtColorHexSerializer;
import me.andreasmelone.pinkfont.config.PinkFontConfig;
import me.andreasmelone.pinkfont.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class PresetManager {
    public static final PresetManager INSTANCE = new PresetManager();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Color.class, new AwtColorHexSerializer())
            .create();

    private ResourceLocation presetLocation = null;
    private Map<String, Preset> loadedPresets = new LinkedHashMap<>();

    private PresetManager() {
    }

    public void reload(ResourceManager resourceManager, ResourceLocation presetLocation) {
        this.presetLocation = presetLocation;

        this.load(resourceManager);
    }

    private void load(ResourceManager resourceManager) {
        loadedPresets = new LinkedHashMap<>();

        // the original namespace is always the first to be loaded
        loadFromResourceLocation(resourceManager, presetLocation.getNamespace(), presetLocation);

        for (String namespace : resourceManager.getNamespaces()) {
            if(namespace.equalsIgnoreCase("pinkfont")) continue;
            ResourceLocation location = Platform.newResourceLocation(namespace, presetLocation.getPath());
            loadFromResourceLocation(resourceManager, namespace, location);
        }
    }

    private void loadFromResourceLocation(ResourceManager resourceManager, String namespace, ResourceLocation location) {
        Type type = new TypeToken<LinkedHashMap<String, Preset>>(){}.getType();

        Resource presetResource = resourceManager.getResource(location).orElse(null);
        if(presetResource == null) return;
        PinkFontMod.LOGGER.info("Loading presets from namespace {}", namespace);
        try(BufferedReader reader = presetResource.openAsReader()) {
            loadedPresets.putAll(GSON.fromJson(reader, type));
        } catch (IOException e) {
            PinkFontMod.LOGGER.error("Failed to read presets in namespace {}! Not loading.", namespace, e);
        }
    }

    public ConfigCategory.Builder populatePresetCategory(Consumer<Float> durationSetter, Consumer<List<Color>> colorListSetter, ConfigCategory.Builder builder) {
        this.loadedPresets.forEach((name, preset) -> {
            builder.option(ButtonOption.createBuilder()
                    .name(preset.effect().transformName(Component.translatable("config_button.pinkfont.preset_" + name)))
                    .text(Text.GUI_APPLY.copy().withStyle(style -> style.withBold(true)))
                    .action((screen, option) -> {
                        durationSetter.accept(preset.duration());
                        colorListSetter.accept(preset.colors());

                        PinkFontConfig.HANDLER.save();
                    })
                    .description(OptionDescription.createBuilder()
                            .text(preset.effect().transformDescription(Component.translatable("config_description.pinkfont.preset_" + name)))
                            .build())
                    .build());
        });
        return builder;
    }
}
