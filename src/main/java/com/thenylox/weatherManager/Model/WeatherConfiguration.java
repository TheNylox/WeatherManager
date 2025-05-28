package com.thenylox.weatherManager.Model;

import com.thenylox.weatherManager.Model.Enum.WeatherStatus;
import org.bukkit.World;

import java.util.ArrayList;

public class WeatherConfiguration {
    public String DefaultWorldName;
    public WeatherStatus WeatherStatus;
    public int WeatherRollTimer;
    public boolean IsBroadcastEnabled;
    public String BroadcastMessage;
    public ArrayList<World> Worlds;
    public int ClearChangeChance;
    public int RainChangeChance;
    public int ThunderChangeChance;
    public int ClearToRainChance;
    public int ClearToThunderChance;
    public int RainToClearChance;
    public int RainToThunderChance;
    public int ThunderToClearChance;
    public int ThunderToRainChance;

    public WeatherConfiguration() {
        this.DefaultWorldName = null;
        this.WeatherStatus = null;
        this.WeatherRollTimer = 0;
        this.IsBroadcastEnabled = false;
        this.BroadcastMessage = "";
        this.ClearChangeChance = 0;
        this.RainChangeChance = 0;
        this.ThunderChangeChance = 0;
        this.ClearToRainChance = 0;
        this.ClearToThunderChance = 0;
        this.RainToClearChance = 0;
        this.RainToThunderChance = 0;
        this.ThunderToClearChance = 0;
        this.ThunderToRainChance = 0;
    }
}
