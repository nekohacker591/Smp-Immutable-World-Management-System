package com.smp.iwms.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smp.iwms.player.PlayerInstanceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class JsonPersistenceLayer implements PersistenceLayer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPersistenceLayer.class);

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path playerDirectory;

    public JsonPersistenceLayer(Path rootDirectory) {
        this.playerDirectory = rootDirectory.resolve("players");
        ensureDirectories();
    }

    @Override
    public Optional<PlayerInstanceData> loadPlayer(UUID playerUuid) {
        Path playerFile = playerFile(playerUuid);
        if (!Files.exists(playerFile)) {
            return Optional.empty();
        }

        try (Reader reader = Files.newBufferedReader(playerFile, StandardCharsets.UTF_8)) {
            return Optional.ofNullable(gson.fromJson(reader, PlayerInstanceData.class));
        } catch (IOException ex) {
            LOGGER.error("Failed to read player data for {}", playerUuid, ex);
            return Optional.empty();
        }
    }

    @Override
    public void savePlayer(PlayerInstanceData data) {
        Path playerFile = playerFile(data.getPlayerUuid());
        try {
            Files.createDirectories(playerFile.getParent());
            try (Writer writer = Files.newBufferedWriter(playerFile, StandardCharsets.UTF_8)) {
                gson.toJson(data, writer);
            }
        } catch (IOException ex) {
            LOGGER.error("Failed to write player data for {}", data.getPlayerUuid(), ex);
        }
    }

    private Path playerFile(UUID playerUuid) {
        return playerDirectory.resolve(playerUuid + ".json");
    }

    private void ensureDirectories() {
        try {
            Files.createDirectories(playerDirectory);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not initialize persistence directory: " + playerDirectory, ex);
        }
    }
}
