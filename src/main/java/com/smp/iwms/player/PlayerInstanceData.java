package com.smp.iwms.player;

import java.util.UUID;

public class PlayerInstanceData {
    private UUID playerUuid;
    private String instanceId;
    private long baseSeed;
    private long lastSeenEpochMs;

    public PlayerInstanceData() {
    }

    public PlayerInstanceData(UUID playerUuid, String instanceId, long baseSeed, long lastSeenEpochMs) {
        this.playerUuid = playerUuid;
        this.instanceId = instanceId;
        this.baseSeed = baseSeed;
        this.lastSeenEpochMs = lastSeenEpochMs;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public long getBaseSeed() {
        return baseSeed;
    }

    public void setBaseSeed(long baseSeed) {
        this.baseSeed = baseSeed;
    }

    public long getLastSeenEpochMs() {
        return lastSeenEpochMs;
    }

    public void setLastSeenEpochMs(long lastSeenEpochMs) {
        this.lastSeenEpochMs = lastSeenEpochMs;
    }
}
