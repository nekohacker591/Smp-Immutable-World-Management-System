package com.smp.iwms;

import com.smp.iwms.config.ModConstants;
import com.smp.iwms.instance.InstanceManager;
import com.smp.iwms.persistence.JsonPersistenceLayer;
import com.smp.iwms.persistence.PersistenceLayer;
import com.smp.iwms.player.PlayerDataManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmutableWorldManagementMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ModConstants.MOD_ID);

    private PlayerDataManager playerDataManager;

    @Override
    public void onInitialize() {
        long defaultSeed = 884422113355L;

        PersistenceLayer persistenceLayer = new JsonPersistenceLayer(ModConstants.dataDirectory());
        InstanceManager instanceManager = new InstanceManager(defaultSeed);
        this.playerDataManager = new PlayerDataManager(persistenceLayer, instanceManager);

        registerLifecycleHooks();
        LOGGER.info("{} initialized", ModConstants.MOD_NAME);
    }

    private void registerLifecycleHooks() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            var player = handler.getPlayer();
            var data = playerDataManager.onJoin(player);
            LOGGER.info("Loaded instance {} for {}", data.getInstanceId(), player.getGameProfile().getName());
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            var player = handler.getPlayer();
            playerDataManager.onLeave(player);
            LOGGER.info("Saved player data for {}", player.getGameProfile().getName());
        });
    }
}
