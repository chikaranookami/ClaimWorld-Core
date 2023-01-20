package claimworld.net.supporter.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class WeatherChangeEvent implements Listener {

    @EventHandler
    public void weatherChangeEvent(org.bukkit.event.weather.WeatherChangeEvent event) {
        if (!event.toWeatherState()) return;
        if (!new Random().nextBoolean()) return;

        event.setCancelled(true);
    }
}
