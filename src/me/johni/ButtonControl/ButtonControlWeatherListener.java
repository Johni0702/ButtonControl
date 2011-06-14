package me.johni.ButtonControl;

import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

public class ButtonControlWeatherListener extends WeatherListener{
	//Variablen
	ButtonControl plugin;
	
	//Methoden
	//Konstructor
	ButtonControlWeatherListener(ButtonControl plugin)
	{
		this.plugin = plugin;
	}
	
	//onWeatherChange
	public void onWeatherChange(WeatherChangeEvent e)
	{
		
		if (System.currentTimeMillis() - plugin.sstart <  plugin.sd*60000 ||
				System.currentTimeMillis() - plugin.sstart <  plugin.sd*60000 ||
				System.currentTimeMillis() - plugin.sstart <  plugin.sd*60000)
			e.setCancelled(true);
	}
}
