package com.squidhq.reef.api.placeholders.integration;

import com.squidhq.reef.Reef;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Pattern;

public class PlaceholderAPI extends EZPlaceholderHook {

    private Reef reefPlugin;

    public PlaceholderAPI(Reef reefPlugin){
        super(reefPlugin, "squidhq");
        this.reefPlugin = reefPlugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if(identifier.equals("players_using_launcher")){
            return String.valueOf(reefPlugin.getSquidPlayerList().size());
        }

        if(identifier.equals("players_using_launcher_percentage")){
            return String.valueOf(Math.round((reefPlugin.getSquidPlayerList().size() / Bukkit.getOnlinePlayers().size()) * 100));
        }

        if(identifier.contains("is_player_") && identifier.contains("_using_launcher")){
            // identifier should be is_player_{PLAYER}_using_launcher
            String playerName = identifier.split(Pattern.quote("_"))[2];
            if(Bukkit.getPlayerExact(playerName) != null){
                UUID uuid = Bukkit.getPlayerExact(playerName).getUniqueId();
                return String.valueOf(reefPlugin.getSquidPlayerList().contains(uuid));
            }else{
                return "";
            }
        }

        if(player == null){
            return "";
        }

        if(identifier.equals("is_using_launcher")){
            return String.valueOf(reefPlugin.getSquidPlayerList().contains(player.getUniqueId()));
        }

        return null;
    }
}
