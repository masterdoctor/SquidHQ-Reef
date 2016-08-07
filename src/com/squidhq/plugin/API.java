package com.squidhq.plugin;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface API {

    boolean isRunning(Player player);

    boolean isRunning(UUID uuid);

    Set<UUID> getPlayers();

}
