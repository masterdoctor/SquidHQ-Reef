package com.squidhq.plugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Plugin extends JavaPlugin implements API, Listener, PluginMessageListener {

	private Set<UUID> players = new HashSet<UUID>();
	private Set<UUID> branded = new HashSet<UUID>();

	@Override
	public void onLoad() {
		super.getConfig().options().copyDefaults(true);
		super.saveConfig();
		super.reloadConfig();
	}

	@Override
	public void onEnable() {
		APISingleton._api = this;
		super.getServer().getPluginManager().registerEvents(this, this);
		super.getServer().getMessenger().registerIncomingPluginChannel(this, "MC|Brand", this);
		super.getCommand("sqcheck").setExecutor(new CommandCheck(this));
		super.getCommand("sqstats").setExecutor(new CommandStats(this));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		this.players.remove(uuid);
		this.branded.remove(uuid);
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		String string;
		try {
			string = new String(bytes, "UTF-8");
		} catch(UnsupportedEncodingException exception) {
			return;
		}
		if (this.branded.contains(player.getUniqueId())) {
			return;
		}
		boolean hasSquid = string.endsWith("squidhq");
		List<String> messages;
		List<String> commands;
		this.branded.add(player.getUniqueId());
		if (hasSquid) {
			messages = super.getConfig().getStringList("login.with-squid.messages");
			commands = super.getConfig().getStringList("login.with-squid.commands");
			this.players.add(player.getUniqueId());
		} else {
			messages = super.getConfig().getStringList("login.without-squid.messages");
			commands = super.getConfig().getStringList("login.without-squid.commands");
		}
		if (messages != null && !messages.isEmpty()) {
			for (String message : messages) {
				message = ChatColor.translateAlternateColorCodes('&', message);
				player.sendMessage(message.replace("{player}", player.getName()));
			}
		}
		if (commands != null && !commands.isEmpty()) {
			for (String command : commands) {
				super.getServer().dispatchCommand(super.getServer().getConsoleSender(), command.replace("{player}", player.getName()));
			}
		}
	}

	public boolean isRunning(Player player) {
		return this.players.contains(player.getUniqueId());
	}

	public boolean isRunning(UUID uuid) {
		return this.players.contains(uuid);
	}

	public Set<UUID> getPlayers() {
		return Collections.unmodifiableSet(this.players);
	}

}
