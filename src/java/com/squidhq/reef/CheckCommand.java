package com.squidhq.reef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

class CheckCommand implements CommandExecutor {

    private final Reef reefPlugin;

    public CheckCommand(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lSquidHQ  &c&oInvalid usage! &cUse: /sqcheck <player/uuid>"));
            return true;
        }

        Player player;

        if(args[0].contains("-")){
            // Is a UUID because a Minecraft username cannot contain a hyphen (-).
            try {
                player = Bukkit.getServer().getPlayer(UUID.fromString(args[0]));
            }catch(Exception ex){
                // Invalid input
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSquidHQ  &c&oInvalid UUID! &cPlease double-check the UUID or try entering the player name instead."));
                return true;
            }
        }else{
            // Treat as a username
            try {
                player = Bukkit.getServer().getPlayer(args[0]);
            }catch(Exception ex){
                // Invalid input
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSquidHQ  &c&oInvalid username! &cPlease double-check the player name."));
                return true;
            }
        }

        if(player != null){
            // Return whether or not the player is a SquidHQ player
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSquidHQ  &eChecking &7[&a" + player.getName() + "&7]&e..."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSquidHQ  &e" + player.getName() + " using SquidHQ: " + (reefPlugin.isSquidPlayer(player.getUniqueId()) ? "&a\u2714" : "&c&l\u2718")));
        }else{
            // Player offline or not found
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSquidHQ  &c&oPlayer not found! &cPerhaps the player is offline?"));
        }

        return true;
    }

}
