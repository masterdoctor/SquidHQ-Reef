package com.squidhq.reef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

class StatisticCommand implements CommandExecutor {

    private final Reef reefPlugin;

    public StatisticCommand(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        List<UUID> squidPlayers = reefPlugin.getSquidPlayerList();
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSquidHQ  &e" + Math.round(squidPlayers.size() / Bukkit.getOnlinePlayers().size() * 100) + "% (" + squidPlayers.size() + "/" + Bukkit.getOnlinePlayers().size() + ") of the players online are currently using SquidHQ."));
        }else {
            sender.sendMessage(Math.round(squidPlayers.size() / Bukkit.getOnlinePlayers().size() * 100) + "% (" + squidPlayers.size() + "/" + Bukkit.getOnlinePlayers().size() + ") of the players online are currently using SquidHQ.");
        }

        return true;
    }

}
