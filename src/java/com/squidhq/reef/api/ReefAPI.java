package com.squidhq.reef.api;

import com.squidhq.reef.Reef;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class ReefAPI {

    private final Reef reefPlugin;

    public ReefAPI(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    /**
     * Gets a list of players on the server that are using the SquidHQ launcher.
     * @return The list of players using SquidHQ.
     */
    public Set<UUID> getSquidPlayers(){
        return Collections.unmodifiableSet(reefPlugin.getSquidPlayerSet());
    }

    /**
     * Checks if a player is using SquidHQ.
     * @param player The player that you want to check.
     * @return Whether or not the player is using SquidHQ.
     */
    public boolean isUsingSquid(Player player){
        return getSquidPlayers().contains(player.getUniqueId());
    }

    /**
     * Checks if a player is using SquidHQ by their UUID.
     * @param uuid The UUID of the player that you want to check.
     * @return Whether or not the player is using SquidHQ.
     */
    public boolean isUsingSquid(UUID uuid){
        return getSquidPlayers().contains(uuid);
    }

}
