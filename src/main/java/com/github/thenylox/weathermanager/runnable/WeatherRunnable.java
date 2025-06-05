package com.github.thenylox.weathermanager.runnable;

import com.github.thenylox.weathermanager.model.enums.WeatherStatus;
import com.github.thenylox.weathermanager.model.WeatherConfiguration;
import com.github.thenylox.weathermanager.WeatherManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;

import static com.github.thenylox.weathermanager.model.enums.WeatherStatus.*;

public class WeatherRunnable implements Runnable {
    private final WeatherConfiguration weatherConfiguration;
    private final WeatherManager plugin;

    public WeatherRunnable(WeatherConfiguration weatherConfiguration, WeatherManager plugin) {
        this.weatherConfiguration = weatherConfiguration;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(changeWeatherRoll(weatherConfiguration.weatherStatus)){
            changeWeatherToRoll(weatherConfiguration.weatherStatus);
        }
    }

    private boolean changeWeatherRoll(WeatherStatus weatherStatus) {
        int random = (int) (Math.random() * 100);

        return switch (weatherStatus) {
            case CLEAR -> random <= weatherConfiguration.clearChangeChance;
            case RAIN -> random <= weatherConfiguration.rainChangeChance;
            case THUNDER -> random <= weatherConfiguration.thunderChangeChance;
        };
    }

    private void changeWeatherToRoll(WeatherStatus weatherStatus){
        int random = (int) (Math.random() * 100);

        WeatherStatus changeTo = switch (weatherStatus) {
            case CLEAR -> random <= weatherConfiguration.clearToRainChance ? RAIN : THUNDER;
            case RAIN -> random <= weatherConfiguration.rainToClearChance ? CLEAR : THUNDER;
            case THUNDER -> random <= weatherConfiguration.thunderToClearChance ? CLEAR : RAIN;
        };
        changeWeather(changeTo);
    }

    private void changeWeather(WeatherStatus type) {
        record WeatherState(boolean thundering, boolean storm) {}
        weatherConfiguration.weatherStatus = type;

        WeatherState state = switch (type) {
            case CLEAR   -> new WeatherState(false, false);
            case RAIN    -> new WeatherState(false, true);
            case THUNDER -> new WeatherState(true, true);
        };

        final boolean setThunderingFinal = state.thundering();
        final boolean setStormFinal = state.storm();


        //Apply to all worlds
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (World world : weatherConfiguration.worlds) {
                world.setThundering(setThunderingFinal);
                world.setStorm(setStormFinal);
            }
        });

        if (weatherConfiguration.isBroadcastEnabled) {
            sendWeatherChangeBroadcast();
        }
    }

    private void sendWeatherChangeBroadcast() {
        String message = weatherConfiguration.broadcastMessage
                .replace("%weather%", weatherConfiguration.weatherStatus.toString().toLowerCase());
        Component component = Component.text(message);
        Bukkit.broadcast(component);
    }
}

