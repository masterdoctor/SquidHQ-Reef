package com.squidhq.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class CommandStats implements CommandExecutor {

    private Plugin plugin;

    public CommandStats(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Set<UUID> players = new HashSet<UUID>();
        players.addAll(this.plugin.getPlayers());
        Iterator<UUID> playersIt = players.iterator();
        while (playersIt.hasNext()) {
            if (this.plugin.getServer().getPlayer(playersIt.next()) != null) {
                continue;
            }
            playersIt.remove();
        }
        sender.sendMessage(ChatColor.YELLOW + "Running SquidHQ: " + ChatColor.WHITE + players.size() + "/" + this.plugin.getServer().getOnlinePlayers().size());
        return true;
    }

}
