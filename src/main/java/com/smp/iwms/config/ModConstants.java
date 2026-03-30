package com.smp.iwms.config;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class ModConstants {
    public static final String MOD_ID = "iwms";
    public static final String MOD_NAME = "Immutable World Management System";

    private ModConstants() {
    }

    public static Path dataDirectory() {
        return FabricLoader.getInstance().getConfigDir().resolve(MOD_ID);
    }
}
