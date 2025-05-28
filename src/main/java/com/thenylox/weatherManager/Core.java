package com.thenylox.weatherManager;

import com.thenylox.weatherManager.Model.Enum.WeatherStatus;
import com.thenylox.weatherManager.Model.WeatherConfiguration;
import com.thenylox.weatherManager.Runnable.WeatherRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.thenylox.weatherManager.Model.Enum.WeatherStatus.*;

public class Core {
    private WeatherManager PLUGIN;
    private ArrayList<World> worlds = new ArrayList<>();
    private YamlConfiguration config;
    private WeatherConfiguration weatherConfiguration = new WeatherConfiguration();
    private World defaultWorld;

    public Core(WeatherManager PLUGIN, File configFile) {
        this.PLUGIN = PLUGIN;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.weatherConfiguration.DefaultWorldName = config.getString("default_world");
        loadWorldsFromConfig();
        this.defaultWorld = Bukkit.getWorld(weatherConfiguration.DefaultWorldName);
        this.weatherConfiguration.Worlds = worlds;
    }

    public void systemStart(){
        IgniteCheck();
        PopulateWeatherConfiguration();
        startWeatherRunnable();
    }

    private WeatherStatus getCurrentWeatherStatus(){
        if (defaultWorld.hasStorm()) {
            if (defaultWorld.isThundering()) {
                return THUNDER;
            } else {
                return RAIN;
            }
        } else {
            return CLEAR;
        }
    }

    private void startWeatherRunnable(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, new WeatherRunnable(weatherConfiguration, PLUGIN), 20L, 20L * 60 * weatherConfiguration.WeatherRollTimer);
    }

    private void loadWorldsFromConfig() {
        List<String> worldNames = config.getStringList("worlds");
        for (String worldName : worldNames) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                worlds.add(world);
            } else {
                PLUGIN.getLogger().warning("[WeatherManager] World not found: " + worldName);
            }
        }
    }
    private void IgniteCheck(){
        if (worlds.size() == 0) {
            System.out.println(ChatColor.RED + "[RainMutiplier] No worlds found in the configuration file.");
        }

        if(defaultWorld == null){
            System.out.println(ChatColor.RED + "[RainMutiplier] Default world");
        }
    }
    private void PopulateWeatherConfiguration() {
        weatherConfiguration.WeatherStatus = getCurrentWeatherStatus();
        weatherConfiguration.WeatherRollTimer = config.getInt("weather_roll_timer");
        weatherConfiguration.IsBroadcastEnabled = config.getBoolean("broadcast_enabled");
        weatherConfiguration.BroadcastMessage = config.getString("broadcast_message");
        weatherConfiguration.ClearChangeChance = config.getInt("clear_change_chance");
        weatherConfiguration.RainChangeChance = config.getInt("rain_change_chance");
        weatherConfiguration.ThunderChangeChance = config.getInt("thunder_change_chance");
        weatherConfiguration.ClearToRainChance = config.getInt("clear_to_rain_chance");
        weatherConfiguration.ClearToThunderChance = config.getInt("clear_to_thunder_chance");
        weatherConfiguration.RainToClearChance = config.getInt("rain_to_clear_chance");
        weatherConfiguration.RainToThunderChance = config.getInt("rain_to_thunder_chance");
        weatherConfiguration.ThunderToClearChance = config.getInt("thunder_to_clear_chance");
        weatherConfiguration.ThunderToRainChance = config.getInt("thunder_to_rain_chance");
    }
}