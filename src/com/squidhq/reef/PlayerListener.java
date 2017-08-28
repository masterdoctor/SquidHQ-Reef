package com.squidhq.reef;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private Reef reefPlugin;

    public PlayerListener(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        reefPlugin.unmarkPlayer(event.getPlayer().getUniqueId());
        reefPlugin.unsetSquidPlayer(event.getPlayer().getUniqueId());
    }

}
