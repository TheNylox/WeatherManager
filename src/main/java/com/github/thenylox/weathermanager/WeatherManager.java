package com.github.thenylox.weathermanager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class WeatherManager extends JavaPlugin {

    private File configFile;
    private Core core;

    // ANSI Color Codes
    private final Logger log = getLogger();
    private static final String RESET = "\u001B[0m";
    private static final String BRIGHTGREEN = "\u001B[92m";


    @Override
    public void onEnable() {
        printTitle();
        loadConfig();
        instanceClasses();
        core.systemStart();
        log.info(BRIGHTGREEN + "Plugin Startup Done" + RESET);
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig(){
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        log.info(BRIGHTGREEN + "× Config File Loaded"+ RESET);
    }
    private void instanceClasses(){
        instanceCore();
        log.info(BRIGHTGREEN + "× Classes instance Enabled"+ RESET);

    }
    private void instanceCore() {
        core = new Core(this, configFile);
    }
    private void printTitle(){
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";

        log.info(
   "\n"+ BRIGHTGREEN + " __        __         _   _               " + cyan + "  __  __                                   "+
        "\n"+ BRIGHTGREEN + " \\ \\      / /__  __ _| |_| |__   ___ _ __ " + cyan + " |  \\/  | __ _ _ __   __ _  __ _  ___ _ __ "+
        "\n"+ BRIGHTGREEN + "  \\ \\ /\\ / / _ \\/ _` | __| '_ \\ / _ \\ '__|" + cyan + " | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|"+
        "\n"+ BRIGHTGREEN + "   \\ V  V /  __/ (_| | |_| | | |  __/ |   " + cyan + " | |  | | (_| | | | | (_| | (_| |  __/ |   "+
        "\n"+ BRIGHTGREEN + "    \\_/\\_/ \\___|\\__,_|\\__|_| |_|\\___|_|   " + cyan + " |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   "+
        "\n"+ BRIGHTGREEN + "                                          " + cyan + "                           |___/           "+
        "\n"+ yellow +"                                                                                  v 1.0"+ RESET);
    }
}
