package com.smp.iwms.persistence;

import com.smp.iwms.player.PlayerInstanceData;

import java.util.Optional;
import java.util.UUID;

public interface PersistenceLayer {
    Optional<PlayerInstanceData> loadPlayer(UUID playerUuid);

    void savePlayer(PlayerInstanceData data);
}
