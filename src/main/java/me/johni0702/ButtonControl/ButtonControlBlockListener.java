package me.johni0702.ButtonControl;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class ButtonControlBlockListener extends BlockListener
{
	ButtonControl plugin;
	
	ButtonControlBlockListener(ButtonControl plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent e)
	{
		if (e.getBlock().getTypeId() != 77 && e.getBlock().getTypeId() != 70)
			return;
		Block b = e.getBlock();
		int i = 0;
		while (i < plugin.rain.length-1)
		{
			if (b.getX() == plugin.rain[i].getX() &&
				b.getY() == plugin.rain[i].getY() &&
				b.getZ() == plugin.rain[i].getZ())
			{
				if (!e.getPlayer().isOp())
				{
					e.setCancelled(true);
					return;
				}
				while (i < plugin.rain.length-1)
				{
					ButtonControl.config.setProperty("Buttons.Rain.X."+i, ButtonControl.config.getInt("Buttons.Rain.X."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Rain.Y."+i, ButtonControl.config.getInt("Buttons.Rain.Y."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Rain.Z."+i, ButtonControl.config.getInt("Buttons.Rain.Z."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Rain.world."+i, ButtonControl.config.getString("Buttons.Rain.world."+(i+1)));
					i++;
				}
				ButtonControl.config.setProperty("Buttons.RainAnzahl", ButtonControl.config.getInt("Buttons.RainAnzahl", 0)-1);
				ButtonControl.config.save();
				plugin.loadFile();
			}
			i++;
		}
		i = 0;
		while (i < plugin.stop.length-1)
		{
			if (b.getX() == plugin.stop[i].getX() &&
				b.getY() == plugin.stop[i].getY() &&
				b.getZ() == plugin.stop[i].getZ())
			{
				if (!e.getPlayer().isOp())
				{
					e.setCancelled(true);
					return;
				}
				while (i < plugin.stop.length-1)
				{
					ButtonControl.config.setProperty("Buttons.Stop.X."+i, ButtonControl.config.getInt("Buttons.Stop.X."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Stop.Y."+i, ButtonControl.config.getInt("Buttons.Stop.Y."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Stop.Z."+i, ButtonControl.config.getInt("Buttons.Stop.Z."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Stop.world."+i, ButtonControl.config.getString("Buttons.Stop.world."+(i+1)));
					i++;
				}
				ButtonControl.config.setProperty("Buttons.StopAnzahl", ButtonControl.config.getInt("Buttons.StopAnzahl", 0)-1);
				ButtonControl.config.save();
				plugin.loadFile();
			}
			i++;
		}
		i = 0;
		while (i < plugin.thunder.length-1)
		{
			if (b.getX() == plugin.thunder[i].getX() &&
				b.getY() == plugin.thunder[i].getY() &&
				b.getZ() == plugin.thunder[i].getZ())
			{
				if (!e.getPlayer().isOp())
				{
					e.setCancelled(true);
					return;
				}
				while (i < plugin.thunder.length-1)
				{
					ButtonControl.config.setProperty("Buttons.Storm.X."+i, ButtonControl.config.getInt("Buttons.Storm.X."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Storm.Y."+i, ButtonControl.config.getInt("Buttons.Storm.Y."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Storm.Z."+i, ButtonControl.config.getInt("Buttons.Storm.Z."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.Storm.world."+i, ButtonControl.config.getString("Buttons.Storm.world."+(i+1)));
					i++;
				}
				ButtonControl.config.setProperty("Buttons.StormAnzahl", ButtonControl.config.getInt("Buttons.StormAnzahl", 0)-1);
				ButtonControl.config.save();
				plugin.loadFile();
			}
			i++;
		}
		i = 0;
		while (i < plugin.day.length-1)
		{
			if (b.getX() == plugin.day[i].getX() &&
				b.getY() == plugin.day[i].getY() &&
				b.getZ() == plugin.day[i].getZ())
			{
				if (!e.getPlayer().isOp())
				{
					e.setCancelled(true);
					return;
				}
				while (i < plugin.day.length-1)
				{
					ButtonControl.config.setProperty("Buttons.day.X."+i, ButtonControl.config.getInt("Buttons.day.X."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.day.Y."+i, ButtonControl.config.getInt("Buttons.day.Y."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.day.Z."+i, ButtonControl.config.getInt("Buttons.day.Z."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.day.world."+i, ButtonControl.config.getString("Buttons.day.world."+(i+1)));
					i++;
				}
				ButtonControl.config.setProperty("Buttons.DayAnzahl", ButtonControl.config.getInt("Buttons.DayAnzahl", 0)-1);
				ButtonControl.config.save();
				plugin.loadFile();
			}
			i++;
		}
		i = 0;
		while (i < plugin.night.length-1)
		{
			if (b.getX() == plugin.night[i].getX() &&
				b.getY() == plugin.night[i].getY() &&
				b.getZ() == plugin.night[i].getZ())
			{
				if (!e.getPlayer().isOp())
				{
					e.setCancelled(true);
					return;
				}
				while (i < plugin.night.length-1)
				{
					ButtonControl.config.setProperty("Buttons.night.X."+i, ButtonControl.config.getInt("Buttons.night.X."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.night.Y."+i, ButtonControl.config.getInt("Buttons.night.Y."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.night.Z."+i, ButtonControl.config.getInt("Buttons.night.Z."+(i+1), 0));
					ButtonControl.config.setProperty("Buttons.night.world."+i, ButtonControl.config.getString("Buttons.night.world."+(i+1)));
					i++;
				}
				ButtonControl.config.setProperty("Buttons.NightAnzahl", ButtonControl.config.getInt("Buttons.NightAnzahl", 0)-1);
				ButtonControl.config.save();
				plugin.loadFile();
			}
			i++;
		}
	}
}
