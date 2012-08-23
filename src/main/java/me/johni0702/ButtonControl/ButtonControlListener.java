package me.johni0702.ButtonControl;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ButtonControlListener implements Listener
{
	ButtonControl plugin;
	
	ButtonControlListener(ButtonControl plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (e.getBlock().getTypeId() != Material.STONE_BUTTON.getId() && e.getBlock().getTypeId() != Material.STONE_PLATE.getId())
			return;
		Block b = e.getBlock();
		
		ControlButton button = null;
		for (ControlButton cb : plugin.buttons)
		{
			if (cb == null)
				continue;
			if (cb.isAt(b))
			{
				button = cb;
			}
		}
		if (button != null)
		{
			if (!ButtonControlPermissions.canDestroyButtons(e.getPlayer()))
				e.setCancelled(true);
			else
			{
				plugin.buttons.remove(button);
				plugin.saveFile();
			}
		}
	}
	
	@EventHandler
	public  void onPlayerInteract(PlayerInteractEvent e)
	{
		if (!e.hasBlock()) 
			return;
		if (e.getClickedBlock().getTypeId() != Material.STONE_BUTTON.getId() && e.getClickedBlock().getTypeId() != Material.STONE_PLATE.getId()) 
			return;

		Block b = e.getClickedBlock();
		for (ControlButton cb : plugin.buttons)
		{
			if (cb == null)
				continue;
			if (cb.isAt(b))
				cb.onPressed(e.getPlayer());
		}
		
		
		if (plugin.onCreation.containsKey(e.getPlayer()))
		{
			ControlButton cb = plugin.onCreation.get(e.getPlayer());
			plugin.onCreation.remove(e.getPlayer());
			cb.setBlock(e.getClickedBlock());
			plugin.buttons.add(cb);
			plugin.saveFile();
			plugin.loadFile();
			switch (cb.getType())
			{
			case RAIN:
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[6]);
				break;
			case DAY:
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[9]);
				break;
			case NIGHT:
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[10]);
				break;
			case SUN:
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[8]);
				break;
			case THUNDER:
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[7]);
				break;
			}
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e)
	{
		
		if (!ControlButton.canWeatherChange())
			e.setCancelled(true);
	}
}
