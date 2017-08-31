package com.squidhq.reef;

import com.squidhq.reef.api.placeholders.util.StockPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;

class MessagingListener implements PluginMessageListener {

    private final Reef reefPlugin;

    public MessagingListener(Reef reefPlugin){
        this.reefPlugin = reefPlugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes){
        // Attempt to read the plugin message using UTF-8 encoding.
        String input;
        try {
            input = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            reefPlugin.getLogger().log(Level.WARNING, "Unable to read plugin message sent by " + player.getName());
            return;
        }

        // Check the player hasn't already been identified
        if(reefPlugin.isIdentified(player.getUniqueId())){
            return;
        }

        // Mark that the player has been identified to prevent people abusing SquidHQ rewards.
        reefPlugin.markIdentifiedPlayer(player.getUniqueId());

        // The SquidHQ launcher appends 'squidhq' to the brand string.
        boolean hasSquid = input.endsWith("squidhq");

        // Load the commands and messages to be displayed to the player.
        List<String> messages;
        List<String> commands;

        if(hasSquid){
            // Set the messages and commands
            messages = reefPlugin.getConfig().getStringList("login.squid-client.messages");
            commands = reefPlugin.getConfig().getStringList("login.squid-client.commands");
            // Mark the player as a SquidHQ user
            reefPlugin.setSquidPlayer(player.getUniqueId());
        }else{
            // Set the messages and commands
            messages = reefPlugin.getConfig().getStringList("login.vanilla-client.messages");
            commands = reefPlugin.getConfig().getStringList("login.vanilla-client.commands");
        }

        // If there are any messages loaded, display them.
        if(messages != null){
            for(String message : messages){
                player.sendMessage(StockPlaceholders.replace(message, player));
            }
        }

        // If there are any commands loaded, execute them.
        if(commands != null){
            for(String command : commands){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), StockPlaceholders.replace(command, player, false));
            }
        }
    }

}
