package com.thenylox.weatherManager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class WeatherManager extends JavaPlugin {

    private File configFile;
    private Core core;

    // ANSI Color Codes
    private final Logger log = getLogger();
    private final String reset = "\u001B[0m";
    private final String cyan = "\u001B[36m";
    private final String brightGreen = "\u001B[92m";
    private final String yellow = "\u001B[33m";


    @Override
    public void onEnable() {
        PrintTitle();
        LoadConfig();
        InstanceClasses();
        core.systemStart();
        log.info(brightGreen + "Plugin Startup Done" + reset);
    }

    @Override
    public void onDisable() {
    }

    private void LoadConfig(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        log.info(brightGreen + "× Config File Loaded"+reset);
    }
    private void InstanceClasses(){
        InstanceCore();
        log.info(brightGreen + "× Classes instance Enabled"+reset);

    }
    private void InstanceCore() {
        core = new Core(this, configFile);
    }
    private void PrintTitle(){
        log.info(
   "\n"+ brightGreen + " __        __         _   _               " + cyan + "  __  __                                   "+
        "\n"+ brightGreen + " \\ \\      / /__  __ _| |_| |__   ___ _ __ " + cyan + " |  \\/  | __ _ _ __   __ _  __ _  ___ _ __ "+
        "\n"+ brightGreen + "  \\ \\ /\\ / / _ \\/ _` | __| '_ \\ / _ \\ '__|" + cyan + " | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|"+
        "\n"+ brightGreen + "   \\ V  V /  __/ (_| | |_| | | |  __/ |   " + cyan + " | |  | | (_| | | | | (_| | (_| |  __/ |   "+
        "\n"+ brightGreen + "    \\_/\\_/ \\___|\\__,_|\\__|_| |_|\\___|_|   " + cyan + " |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   "+
        "\n"+ brightGreen + "                                          " + cyan + "                           |___/           "+
        "\n"+ yellow +"                                                                                  v 1.0"+reset);
    }
}
