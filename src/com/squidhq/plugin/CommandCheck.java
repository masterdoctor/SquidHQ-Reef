package com.squidhq.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandCheck implements CommandExecutor {

    private Plugin plugin;

    public CommandCheck(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/sqcheck <player/uuid>");
            return true;
        }
        Player player;
        try {
            player = this.plugin.getServer().getPlayer(UUID.fromString(args[0]));
        } catch (Exception exception) {
            player = this.plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online");
                return true;
            }
        }
        sender.sendMessage(ChatColor.YELLOW + "Player " + player.getName() + " is " + ChatColor.GREEN +
                (this.plugin.isRunning(player) ? "now" : "not") + ChatColor.YELLOW + " running SquidHQ");
        return true;
    }

}
