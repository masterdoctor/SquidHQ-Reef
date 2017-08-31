package com.squidhq.reef.api.placeholders;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class StockPlaceholders {

    public static String replace(String text, Player player){
        return replace(text, player, true);
    }

    public static String replace(String text, Player player, boolean colorize){
        if(colorize){
            text = ChatColor.translateAlternateColorCodes('&', text);
        }

        text = text.replaceAll(Pattern.quote("{player}"), player.getName());
        text = text.replaceAll(Pattern.quote("{displayname}"), player.getDisplayName());
        text = text.replaceAll(Pattern.quote("{uuid}"), player.getUniqueId().toString());

        return text;
    }

}
