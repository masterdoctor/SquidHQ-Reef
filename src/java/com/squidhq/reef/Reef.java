package com.squidhq.reef;

import com.squidhq.plugin.APISingleton;
import com.squidhq.reef.api.LegacyAPI;
import com.squidhq.reef.api.ReefAPI;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

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

        // Register all the listeners and commands
        getLogger().log(Level.INFO, "Registering commands and listeners.");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginCommand("sqcheck").setExecutor(new CheckCommand(this));
        getServer().getPluginCommand("sqstats").setExecutor(new StatisticCommand(this));
        getServer().getPluginCommand("sqreload").setExecutor(new ReloadCommand(this));

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
