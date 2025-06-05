package com.github.thenylox.weathermanager.model;

import com.github.thenylox.weathermanager.model.enums.WeatherStatus;
import org.bukkit.World;

import java.util.ArrayList;

public class WeatherConfiguration {
    public String defaultWorldName;
    public WeatherStatus weatherStatus;
    public int weatherRollTimer;
    public boolean isBroadcastEnabled;
    public String broadcastMessage;
    public ArrayList<World> worlds;
    public int clearChangeChance;
    public int rainChangeChance;
    public int thunderChangeChance;
    public int clearToRainChance;
    public int clearToThunderChance;
    public int rainToClearChance;
    public int rainToThunderChance;
    public int thunderToClearChance;
    public int thunderToRainChance;

    public WeatherConfiguration() {
        this.defaultWorldName = null;
        this.weatherStatus = null;
        this.weatherRollTimer = 0;
        this.isBroadcastEnabled = false;
        this.broadcastMessage = "";
        this.clearChangeChance = 0;
        this.rainChangeChance = 0;
        this.thunderChangeChance = 0;
        this.clearToRainChance = 0;
        this.clearToThunderChance = 0;
        this.rainToClearChance = 0;
        this.rainToThunderChance = 0;
        this.thunderToClearChance = 0;
        this.thunderToRainChance = 0;
    }
}
