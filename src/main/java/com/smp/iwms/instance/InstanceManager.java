package com.smp.iwms.instance;

import com.smp.iwms.player.PlayerInstanceData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceManager {
    private final Map<UUID, String> loadedInstances = new ConcurrentHashMap<>();
    private final long baseSeed;

    public InstanceManager(long baseSeed) {
        this.baseSeed = baseSeed;
    }

    public PlayerInstanceData bootstrapData(UUID playerUuid) {
        String instanceId = loadedInstances.computeIfAbsent(playerUuid, uuid -> "instance-" + uuid.toString().substring(0, 8));
        return new PlayerInstanceData(playerUuid, instanceId, baseSeed, System.currentTimeMillis());
    }

    public void markLoaded(PlayerInstanceData data) {
        loadedInstances.put(data.getPlayerUuid(), data.getInstanceId());
    }

    public void unload(UUID playerUuid) {
        loadedInstances.remove(playerUuid);
    }
}
