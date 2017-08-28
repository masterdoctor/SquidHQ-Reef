package com.squidhq.reef;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private Reef reefPlugin;

    public ReloadCommand(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        reefPlugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lSquidHQ  &eThe SquidHQ plugin configuration has been reloaded."));
        return true;
    }

}
