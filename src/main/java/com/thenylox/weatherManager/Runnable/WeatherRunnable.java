package com.thenylox.weatherManager.Runnable;

import com.thenylox.weatherManager.Model.Enum.WeatherStatus;
import com.thenylox.weatherManager.Model.WeatherConfiguration;
import com.thenylox.weatherManager.WeatherManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import static com.thenylox.weatherManager.Model.Enum.WeatherStatus.*;

public class WeatherRunnable implements Runnable {
    private final WeatherConfiguration WEATHER_CONFIGURATION;
    private final WeatherManager PLUGIN;

    public WeatherRunnable(WeatherConfiguration weatherConfiguration, WeatherManager PLUGIN) {
        this.WEATHER_CONFIGURATION = weatherConfiguration;
        this.PLUGIN = PLUGIN;
    }

    @Override
    public void run() {
        if(ChangeWeatherRoll(WEATHER_CONFIGURATION.WeatherStatus)){
            ChangeWeatherToRoll(WEATHER_CONFIGURATION.WeatherStatus);
        };
    }

    private boolean ChangeWeatherRoll(WeatherStatus weatherStatus) {
        int random = (int) (Math.random() * 100);

        switch(weatherStatus){
            case CLEAR:
                return random <= WEATHER_CONFIGURATION.ClearChangeChance ? true : false;
            case RAIN:
                return random <= WEATHER_CONFIGURATION.RainChangeChance ? true : false;
            case THUNDER:
                return random <= WEATHER_CONFIGURATION.ThunderChangeChance ? true : false;
            default:
                return false;
        }
    }

    private void ChangeWeatherToRoll(WeatherStatus weatherStatus){
        WeatherStatus changeTo = null;
        int random = (int) (Math.random() * 100);

        switch(weatherStatus){
            case CLEAR:
                changeTo = random <= WEATHER_CONFIGURATION.ClearToRainChance ? RAIN : THUNDER;
                break;
            case RAIN:
                changeTo = random <= WEATHER_CONFIGURATION.RainToClearChance ? CLEAR : THUNDER;
                break;
            case THUNDER:
                changeTo = random <= WEATHER_CONFIGURATION.ThunderToClearChance ? CLEAR : RAIN;
                break;
        }
        ChangeWeather(changeTo);
    }

    private void ChangeWeather(WeatherStatus type) {
        boolean setThundering = false;
        boolean setStorm = false;
        WEATHER_CONFIGURATION.WeatherStatus = type;

        switch (type) {
            case CLEAR:
                setThundering = false;
                setStorm = false;
                break;

            case RAIN:
                setThundering = false;
                setStorm = true;
                break;

            case THUNDER:
                setThundering = true;
                setStorm = true;
                break;
        }

        final boolean setThunderingFinal = setThundering;
        final boolean setStormFinal = setStorm;

        //Apply to all worlds
        Bukkit.getScheduler().runTask(PLUGIN, () -> {
            for (World WORLD : WEATHER_CONFIGURATION.Worlds) {
                WORLD.setThundering(setThunderingFinal);
                WORLD.setStorm(setStormFinal);
            }
        });

        if (WEATHER_CONFIGURATION.IsBroadcastEnabled) {
            SendWeatherChangeBroadcast();
        }
    }

    private void SendWeatherChangeBroadcast() {
        Bukkit.broadcastMessage(WEATHER_CONFIGURATION.BroadcastMessage.replace("%weather%",WEATHER_CONFIGURATION.WeatherStatus.toString().toLowerCase()));
    }
}

