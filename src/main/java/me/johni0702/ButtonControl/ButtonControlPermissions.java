package me.johni0702.ButtonControl;


import org.bukkit.entity.Player;

public class ButtonControlPermissions 
{

	public static boolean permission(Player player, String permission) 
	{
		return player.hasPermission(permission);
	}

	public static boolean canSetButtons(Player player) {
		return permission(player, "buttoncontrol.set");
	}

	public static boolean canDestroyButtons(Player player) {
		return permission(player, "buttoncontrol.destroy");
	}

	public static boolean day(Player player) {
		return permission(player, "buttoncontrol.time.day");
	}

	public static boolean night(Player player) {
		return permission(player, "buttoncontrol.time.night");
	}

	public static boolean sunny(Player player) {
		return permission(player, "buttoncontrol.weather.sunny");
	}

	public static boolean rain(Player player) {
		return permission(player, "buttoncontrol.weather.rain");
	}

	public static boolean thunder(Player player) {
		return permission(player, "buttoncontrol.weather.thunder");
	}
}
