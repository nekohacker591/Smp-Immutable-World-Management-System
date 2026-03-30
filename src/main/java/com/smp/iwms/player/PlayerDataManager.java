package com.smp.iwms.player;

import com.smp.iwms.instance.InstanceManager;
import com.smp.iwms.persistence.PersistenceLayer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private final PersistenceLayer persistenceLayer;
    private final InstanceManager instanceManager;
    private final Map<UUID, PlayerInstanceData> onlinePlayers = new ConcurrentHashMap<>();

    public PlayerDataManager(PersistenceLayer persistenceLayer, InstanceManager instanceManager) {
        this.persistenceLayer = persistenceLayer;
        this.instanceManager = instanceManager;
    }

    public PlayerInstanceData onJoin(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        PlayerInstanceData data = persistenceLayer.loadPlayer(uuid).orElseGet(() -> instanceManager.bootstrapData(uuid));
        data.setLastSeenEpochMs(System.currentTimeMillis());

        instanceManager.markLoaded(data);
        onlinePlayers.put(uuid, data);
        return data;
    }

    public void onLeave(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        PlayerInstanceData data = onlinePlayers.remove(uuid);
        if (data == null) {
            data = persistenceLayer.loadPlayer(uuid).orElseGet(() -> instanceManager.bootstrapData(uuid));
        }

        data.setLastSeenEpochMs(System.currentTimeMillis());
        persistenceLayer.savePlayer(data);
        instanceManager.unload(uuid);
    }
}
