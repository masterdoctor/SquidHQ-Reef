package com.squidhq.reef;

import com.squidhq.plugin.APISingleton;
import com.squidhq.reef.api.LegacyAPI;
import com.squidhq.reef.api.ReefAPI;
import com.squidhq.reef.api.placeholders.integration.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Reef extends JavaPlugin {

    private List<String> identifiedPlayers;
    private List<String> squidPlayers;

    @Override
    public void onEnable(){
        // Save the default configuration (if we do it this way, we can preserve the comments in the configuration)
        getLogger().log(Level.INFO, "Loading configuration...");
        saveDefaultConfig();

        // Instantiate the arrays used to store the players
        identifiedPlayers = new ArrayList<>();
        squidPlayers = new ArrayList<>();

        // Check if players have already been initialised
        FileConfiguration dat = new YamlConfiguration();
        try {
            dat.load(new File(getDataFolder() + File.separator + "dat.yml"));
            for(String uuid : dat.getKeys(false)){
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                if(player != null && player.isOnline()){
                    // load player
                    identifiedPlayers.add(uuid);
                    if(dat.getBoolean(uuid)){
                        squidPlayers.add(uuid);
                    }
                }
            }

            new File(getDataFolder() + File.separator + "dat.yml").delete();
        }catch(Exception ex){
            getLogger().log(Level.INFO, "Unable to restore previous player set.");
        }

        // Register all the listeners and commands
        getLogger().log(Level.INFO, "Registering commands and listeners.");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginCommand("sqcheck").setExecutor(new CheckCommand(this));
        getServer().getPluginCommand("sqstats").setExecutor(new StatisticCommand(this));
        getServer().getPluginCommand("sqreload").setExecutor(new ReloadCommand(this));

        // Integrate with PlaceholderAPI
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().log(Level.INFO, "Attempting PlaceholderAPI integration...");
            new PlaceholderAPI(this).hook();
        }

        getLogger().log(Level.INFO, "Registering messaging channel.");
        // Plugin messaging (for identifying SquidHQ clients)
        getServer().getMessenger().registerIncomingPluginChannel(this, "MC|Brand", new MessagingListener(this));

        getLogger().log(Level.INFO, "Starting API.");
        getServer().getServicesManager().register(ReefAPI.class, new ReefAPI(this), this, ServicePriority.Normal);
        // Start the legacy API
        APISingleton._api = new LegacyAPI(this);

        getLogger().log(Level.INFO, "SquidHQ plugin active. Thanks for supporting SquidHQ!");
    }

    @Override
    public void onDisable(){
        FileConfiguration dat = new YamlConfiguration();
        for(Player player : getServer().getOnlinePlayers()){
            dat.set(player.getUniqueId().toString(), squidPlayers.contains(player.getUniqueId().toString()));
        }

        try {
            dat.save(new File(getDataFolder() + File.separator + "dat.yml"));
        }catch(IOException ex){
            getLogger().log(Level.SEVERE, "Unable to save data. Please delete " + getDataFolder().getName() + "/dat.yml");
        }

        getLogger().log(Level.INFO, "Plugin deactivated.");
        getLogger().log(Level.INFO, "Thanks for supporting SquidHQ!");
    }


    /* INTERNAL PLUGIN METHODS */

    void setSquidPlayer(UUID uuid){
        squidPlayers.add(uuid.toString());
    }

    void unsetSquidPlayer(UUID uuid){
        squidPlayers.remove(uuid.toString());
    }

    void markIdentifiedPlayer(UUID uuid){
        identifiedPlayers.add(uuid.toString());
    }

    void unmarkPlayer(UUID uuid){
        identifiedPlayers.remove(uuid.toString());
    }

    boolean isIdentified(UUID uuid){
        return identifiedPlayers.contains(uuid.toString());
    }

    boolean isSquidPlayer(UUID uuid) {
        return squidPlayers.contains(uuid.toString());
    }

    public Set<UUID> getSquidPlayerSet(){
        Set<UUID> result = new HashSet<>();
        for(String squidPlayer : squidPlayers){
            result.add(UUID.fromString(squidPlayer));
        }

        return result;
    }

    public List<UUID> getSquidPlayerList(){
        List<UUID> result = new ArrayList<>();
        for(String squidPlayer : squidPlayers){
            result.add(UUID.fromString(squidPlayer));
        }

        return result;
    }

}
