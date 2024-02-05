package dev.dhyces.charmofreturn.services;

import java.util.ServiceLoader;

public class Services {

    public static final PlatformHelper PLATFORM_HELPER = loadService(PlatformHelper.class);
    public static final ConfigHelper CONFIG_HELPER = loadService(ConfigHelper.class);

    private static <T> T loadService(Class<T> clazz) {
        ServiceLoader<T> loader = ServiceLoader.load(clazz);

        return loader.findFirst().orElseThrow(() -> new IllegalStateException("Service unavailable"));
    }
}
