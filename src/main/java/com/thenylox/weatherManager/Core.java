package com.thenylox.weatherManager;

import com.thenylox.weatherManager.Model.Enum.WeatherStatus;
import com.thenylox.weatherManager.Model.WeatherConfiguration;
import com.thenylox.weatherManager.Runnable.WeatherRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.thenylox.weatherManager.Model.Enum.WeatherStatus.*;

public class Core {
    private final WeatherManager plugin;
    private final ArrayList<World> worlds = new ArrayList<>();
    private final YamlConfiguration config;
    private final WeatherConfiguration weatherConfiguration = new WeatherConfiguration();
    private final World defaultWorld;

    public Core(WeatherManager plugin, File configFile) {
        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.weatherConfiguration.defaultWorldName = config.getString("default_world");
        loadWorldsFromConfig();
        this.defaultWorld = Bukkit.getWorld(weatherConfiguration.defaultWorldName);
        this.weatherConfiguration.worlds = worlds;
    }

    public void systemStart(){
        igniteCheck();
        populateWeatherConfiguration();
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
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new WeatherRunnable(weatherConfiguration, plugin), 20L, 20L * 60 * weatherConfiguration.weatherRollTimer);
    }

    private void loadWorldsFromConfig() {
        List<String> worldNames = config.getStringList("worlds");
        for (String worldName : worldNames) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                worlds.add(world);
            } else {
                plugin.getLogger().warning("[WeatherManager] World not found: " + worldName);
            }
        }
    }
    private void igniteCheck(){
        if (worlds.isEmpty()) {
            System.out.println(
                    Component.text("[RainMutiplier] No worlds found in the configuration file.", NamedTextColor.RED)
            );
        }

        if (defaultWorld == null) {
            System.out.println(
                    Component.text("[RainMutiplier] Default world", NamedTextColor.RED)
            );
        }
    }
    private void populateWeatherConfiguration() {
        weatherConfiguration.weatherStatus = getCurrentWeatherStatus();
        weatherConfiguration.weatherRollTimer = config.getInt("weather_roll_timer");
        weatherConfiguration.isBroadcastEnabled = config.getBoolean("broadcast_enabled");
        weatherConfiguration.broadcastMessage = config.getString("broadcast_message");
        weatherConfiguration.clearChangeChance = config.getInt("clear_change_chance");
        weatherConfiguration.rainChangeChance = config.getInt("rain_change_chance");
        weatherConfiguration.thunderChangeChance = config.getInt("thunder_change_chance");
        weatherConfiguration.clearToRainChance = config.getInt("clear_to_rain_chance");
        weatherConfiguration.clearToThunderChance = config.getInt("clear_to_thunder_chance");
        weatherConfiguration.rainToClearChance = config.getInt("rain_to_clear_chance");
        weatherConfiguration.rainToThunderChance = config.getInt("rain_to_thunder_chance");
        weatherConfiguration.thunderToClearChance = config.getInt("thunder_to_clear_chance");
        weatherConfiguration.thunderToRainChance = config.getInt("thunder_to_rain_chance");
    }
}