package me.andreasmelone.pinkfont.platform;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface PlatformHelper {
    Path getConfigDir();

    class Instance {
        private static final PlatformHelper INSTANCE = ServiceLoader.load(PlatformHelper.class)
                .findFirst()
                .orElseThrow();

        public static Path getConfigDir() {
            return INSTANCE.getConfigDir();
        }
    }
}
