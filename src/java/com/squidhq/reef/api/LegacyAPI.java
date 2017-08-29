package com.squidhq.reef.api;

import com.squidhq.plugin.API;
import com.squidhq.reef.Reef;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class LegacyAPI implements API {

    private final Reef reefPlugin;

    public LegacyAPI(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    /**
     * @deprecated Use {@link ReefAPI#isUsingSquid(Player)} instead.
     */
    @Override
    public boolean isRunning(Player player) {
        return reefPlugin.getSquidPlayerList().contains(player.getUniqueId());
    }

    /**
     * @deprecated Use {@link ReefAPI#isUsingSquid(UUID)} instead.
     */
    @Override
    public boolean isRunning(UUID uuid) {
        return reefPlugin.getSquidPlayerList().contains(uuid);
    }

    /**
     * @deprecated Use {@link ReefAPI#getSquidPlayers()} instead.
     */
    @Override
    public Set<UUID> getPlayers() {
        return Collections.unmodifiableSet(reefPlugin.getSquidPlayerSet());
    }
}
