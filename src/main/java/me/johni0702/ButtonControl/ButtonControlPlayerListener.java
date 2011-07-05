package me.johni0702.ButtonControl;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.iConomy.iConomy;
import com.iConomy.system.Holdings;

public class ButtonControlPlayerListener extends PlayerListener{
	ButtonControl plugin = null;
	Block block = null;
	public ButtonControlPlayerListener(ButtonControl plugin){
		this.plugin = plugin;
	}
	public  void onPlayerInteract(PlayerInteractEvent e)
	{
		if (!e.hasBlock()) return;
		if (e.getClickedBlock().getTypeId() != 77 && e.getClickedBlock().getTypeId() != 70) return;
		
		int i = plugin.sunny.length - 1;
		while (i >= 0)
		{
			if (e.getClickedBlock() == plugin.sunny[i])
			{
				if (ButtonControlPermissions.sunny(e.getPlayer())) {
					if (plugin.lastaction > System.currentTimeMillis()-plugin.cooldown*1000)return;
					plugin.amount = plugin.sunnycost[i];
					if (plugin.icon && plugin.iconomy != null)
					{
						Holdings bank = iConomy.getAccount(e.getPlayer().getName()).getHoldings();
						if (!bank.hasUnder(plugin.amount))
						{
							bank.subtract(plugin.amount);
							iConomy.getAccount(plugin.acc).getHoldings().add(plugin.amount);
							plugin.rstart = 0;
							plugin.tstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldss[i]).setStorm(false);
							plugin.getServer().getWorld(plugin.worldss[i]).setThundering(false);
							plugin.sstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[2]);
						}
					}else
						if (e.getPlayer().getInventory().getItemInHand().getAmount() >= plugin.amount &&
								(e.getPlayer().getInventory().getItemInHand().getTypeId() == plugin.item  || plugin.amount == 0) && !plugin.icon)
						{
							if (e.getPlayer().getInventory().getItemInHand().getAmount() > plugin.amount)
								e.getPlayer().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - plugin.amount);
							else
								e.getPlayer().getInventory().setItemInHand(null);
							plugin.rstart = 0;
							plugin.tstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldss[i]).setStorm(false);
							plugin.getServer().getWorld(plugin.worldss[i]).setThundering(false);
							plugin.sstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[2]);
						}
						else
							e.getPlayer().sendMessage(plugin.messages[3]);
				} else {
					e.getPlayer().sendMessage(plugin.messages[13]);
					e.setCancelled(true);
				}
			}
			i--;
		}
		
		i = plugin.rain.length - 1;
		while (i >= 0)
		{
			if (e.getClickedBlock() == plugin.rain[i])
			{
				if (ButtonControlPermissions.rain(e.getPlayer())) {
					if (plugin.lastaction > System.currentTimeMillis()-plugin.cooldown*1000)return;
					plugin.amount = plugin.raincost[i];
					if (plugin.icon && plugin.iconomy != null)
					{
						Holdings bank = iConomy.getAccount(e.getPlayer().getName()).getHoldings();
						if (!bank.hasUnder(plugin.amount))
						{
							bank.subtract(plugin.amount);
							iConomy.getAccount(plugin.acc).getHoldings().add(plugin.amount);
							plugin.sstart = 0;
							plugin.tstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldsr[i]).setStorm(true);
							plugin.getServer().getWorld(plugin.worldsr[i]).setThundering(false);
							plugin.rstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[4]);
						}
					}else
						if (e.getPlayer().getInventory().getItemInHand().getAmount() >= plugin.amount &&
								(e.getPlayer().getInventory().getItemInHand().getTypeId() == plugin.item  || plugin.amount == 0) && !plugin.icon)
						{
							if (e.getPlayer().getInventory().getItemInHand().getAmount() > plugin.amount)
								e.getPlayer().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - plugin.amount);
							else
								e.getPlayer().getInventory().setItemInHand(null);
							plugin.sstart = 0;
							plugin.tstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldsr[i]).setStorm(true);
							plugin.getServer().getWorld(plugin.worldsr[i]).setThundering(false);
							plugin.rstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[4]);
						}
						else
							e.getPlayer().sendMessage(plugin.messages[3]);
				} else {
					e.getPlayer().sendMessage(plugin.messages[13]);
					e.setCancelled(true);
				}
			}
			i--;
		}
		
		i = plugin.thunder.length - 1;
		while (i >= 0)
		{
			if (e.getClickedBlock() == plugin.thunder[i])
			{
				if (ButtonControlPermissions.thunder(e.getPlayer())) {
					if (plugin.lastaction > System.currentTimeMillis()-plugin.cooldown*1000)return;
					plugin.amount = plugin.thundercost[i];
					if (plugin.icon && plugin.iconomy != null)
					{
						Holdings bank = iConomy.getAccount(e.getPlayer().getName()).getHoldings();
						if (!bank.hasUnder(plugin.amount))
						{
							bank.subtract(plugin.amount);
							iConomy.getAccount(plugin.acc).getHoldings().add(plugin.amount);
							plugin.sstart = 0;
							plugin.rstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldst[i]).setStorm(true);
							plugin.getServer().getWorld(plugin.worldst[i]).setThundering(true);
							plugin.tstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[5]);
						}
					}else
						if (e.getPlayer().getInventory().getItemInHand().getAmount() >= plugin.amount &&
								(e.getPlayer().getInventory().getItemInHand().getTypeId() == plugin.item  || plugin.amount == 0) && !plugin.icon)
						{
							if (e.getPlayer().getInventory().getItemInHand().getAmount() > plugin.amount)
								e.getPlayer().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - plugin.amount);
							else
								e.getPlayer().getInventory().setItemInHand(null);
							plugin.sstart = 0;
							plugin.rstart = 0;
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldst[i]).setStorm(true);
							plugin.getServer().getWorld(plugin.worldst[i]).setThundering(true);
							plugin.tstart = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[5]);
						}
						else
							e.getPlayer().sendMessage(plugin.messages[3]);
				} else {
					e.getPlayer().sendMessage(plugin.messages[13]);
					e.setCancelled(true);
				}
			}
			i--;
		}
		
		i = plugin.day.length - 1;
		while (i >= 0)
		{
			if (e.getClickedBlock() == plugin.day[i])
			{
				if (ButtonControlPermissions.day(e.getPlayer())) {
					if (plugin.lastaction > System.currentTimeMillis()-plugin.cooldown*1000)return;
					plugin.amount = plugin.daycost[i];
					if (plugin.icon && plugin.iconomy != null)
					{
						Holdings bank = iConomy.getAccount(e.getPlayer().getName()).getHoldings();
						if (!bank.hasUnder(plugin.amount))
						{
							bank.subtract(plugin.amount);
							plugin.lastaction = System.currentTimeMillis();
							iConomy.getAccount(plugin.acc).getHoldings().add(plugin.amount);
							plugin.getServer().getWorld(plugin.worldsd[i]).setTime(0);
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[11]);
						}
					}else
						if (e.getPlayer().getInventory().getItemInHand().getAmount() >= plugin.amount &&
								(e.getPlayer().getInventory().getItemInHand().getTypeId() == plugin.item  || plugin.amount == 0)&& !plugin.icon)
						{
							if (e.getPlayer().getInventory().getItemInHand().getAmount() > plugin.amount)
								e.getPlayer().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - plugin.amount);
							else
								e.getPlayer().getInventory().setItemInHand(null);
							plugin.getServer().getWorld(plugin.worldsd[i]).setTime(0);
							plugin.lastaction = System.currentTimeMillis();
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[11]);
						}
						else
							e.getPlayer().sendMessage(plugin.messages[3]);
				} else {
					e.getPlayer().sendMessage(plugin.messages[13]);
					e.setCancelled(true);
				}
			}
			i--;
		}
		
		i = plugin.night.length - 1;
		while (i >= 0)
		{
			if (e.getClickedBlock() == plugin.night[i])
			{
				if (ButtonControlPermissions.night(e.getPlayer())) {
					if (plugin.lastaction > System.currentTimeMillis()-plugin.cooldown*1000)return;
					plugin.amount = plugin.nightcost[i];
					if (plugin.icon && plugin.iconomy != null)
					{
						Holdings bank = iConomy.getAccount(e.getPlayer().getName()).getHoldings();
						if (!bank.hasUnder(plugin.amount))
						{
							bank.subtract(plugin.amount);
							plugin.lastaction = System.currentTimeMillis();
							iConomy.getAccount(plugin.acc).getHoldings().add(plugin.amount);
							plugin.getServer().getWorld(plugin.worldsn[i]).setTime(14000);
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[12]);
						}
					}else
						if (e.getPlayer().getInventory().getItemInHand().getAmount() >= plugin.amount &&
								(e.getPlayer().getInventory().getItemInHand().getTypeId() == plugin.item  || plugin.amount == 0) && !plugin.icon)
						{
							if (e.getPlayer().getInventory().getItemInHand().getAmount() > plugin.amount)
								e.getPlayer().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount() - plugin.amount);
							else
								e.getPlayer().getInventory().setItemInHand(null);
							plugin.lastaction = System.currentTimeMillis();
							plugin.getServer().getWorld(plugin.worldsn[i]).setTime(14000);
							e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[12]);
						}
						else
							e.getPlayer().sendMessage(plugin.messages[3]);
				} else {
					e.getPlayer().sendMessage(plugin.messages[13]);
					e.setCancelled(true);
				}
			}
			i--;
		}
		
		if ((e.getClickedBlock().getTypeId() == 77  || e.getClickedBlock().getTypeId() == 70) && plugin.push != 0)
		{
			if (plugin.push == 1)
			{
				int num = plugin.rain.length-1;
				plugin.rain[num] = e.getClickedBlock();
				plugin.worldsr[num] = e.getClickedBlock().getWorld().getName();
				ButtonControl.config.setProperty("Buttons.RainAnzahl", plugin.rain.length);
				ButtonControl.config.setProperty("Buttons.Rain.world."+num , e.getClickedBlock().getWorld().getName());
				ButtonControl.config.setProperty("Buttons.Rain.X."+num , e.getClickedBlock().getLocation().getBlockX());
				ButtonControl.config.setProperty("Buttons.Rain.Y."+num , e.getClickedBlock().getLocation().getBlockY());
				ButtonControl.config.setProperty("Buttons.Rain.Z."+num , e.getClickedBlock().getLocation().getBlockZ());
				ButtonControl.config.setProperty("Buttons.Rain.cost."+num , plugin.pcost);
				ButtonControl.config.save();
				plugin.loadFile();
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[6]);
				plugin.push = 0;
			}
			if (plugin.push == 2)
			{
				int num = plugin.thunder.length-1;
				plugin.thunder[num] = e.getClickedBlock();
				plugin.worldst[num] = e.getClickedBlock().getWorld().getName();
				ButtonControl.config.setProperty("Buttons.ThunderAnzahl", plugin.thunder.length);
				ButtonControl.config.setProperty("Buttons.Thunder.world."+num , e.getClickedBlock().getLocation().getWorld().getName());
				ButtonControl.config.setProperty("Buttons.Thunder.X."+num , e.getClickedBlock().getLocation().getBlockX());
				ButtonControl.config.setProperty("Buttons.Thunder.Y."+num , e.getClickedBlock().getLocation().getBlockY());
				ButtonControl.config.setProperty("Buttons.Thunder.Z."+num , e.getClickedBlock().getLocation().getBlockZ());
				ButtonControl.config.setProperty("Buttons.Thunder.cost."+num , plugin.pcost);
				ButtonControl.config.save();
				plugin.loadFile();
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[7]);
				plugin.push = 0;
			}
			if (plugin.push == 3)
			{
				int num = plugin.sunny.length-1;
				plugin.sunny[num] = e.getClickedBlock();
				plugin.worldss[num] = e.getClickedBlock().getWorld().getName();
				ButtonControl.config.setProperty("Buttons.SunnyAnzahl", plugin.sunny.length);
				ButtonControl.config.setProperty("Buttons.Sunny.world."+num , e.getClickedBlock().getLocation().getWorld().getName());
				ButtonControl.config.setProperty("Buttons.Sunny.X."+num , e.getClickedBlock().getLocation().getBlockX());
				ButtonControl.config.setProperty("Buttons.Sunny.Y."+num , e.getClickedBlock().getLocation().getBlockY());
				ButtonControl.config.setProperty("Buttons.Sunny.Z."+num , e.getClickedBlock().getLocation().getBlockZ());
				ButtonControl.config.setProperty("Buttons.Sunny.cost."+num , plugin.pcost);
				ButtonControl.config.save();
				plugin.loadFile();
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[8]);
				plugin.push = 0;
			}
			if (plugin.push == 4)
			{
				int num = plugin.day.length-1;
				plugin.day[num] = e.getClickedBlock();
				plugin.worldsd[num] = e.getClickedBlock().getWorld().getName();
				ButtonControl.config.setProperty("Buttons.DayAnzahl", plugin.day.length);
				ButtonControl.config.setProperty("Buttons.day.world."+num , e.getClickedBlock().getLocation().getWorld().getName());
				ButtonControl.config.setProperty("Buttons.day.X."+num , e.getClickedBlock().getLocation().getBlockX());
				ButtonControl.config.setProperty("Buttons.day.Y."+num , e.getClickedBlock().getLocation().getBlockY());
				ButtonControl.config.setProperty("Buttons.day.Z."+num , e.getClickedBlock().getLocation().getBlockZ());
				ButtonControl.config.setProperty("Buttons.day.cost."+num , plugin.pcost);
				ButtonControl.config.save();
				plugin.loadFile();
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[9]);
				plugin.push = 0;
			}
			if (plugin.push == 5)
			{
				int num = plugin.night.length-1;
				plugin.night[num] = e.getClickedBlock();
				plugin.worldsn[num] = e.getClickedBlock().getWorld().getName();
				ButtonControl.config.setProperty("Buttons.NightAnzahl", plugin.night.length);
				ButtonControl.config.setProperty("Buttons.night.world."+num , e.getClickedBlock().getLocation().getWorld().getName());
				ButtonControl.config.setProperty("Buttons.night.X."+num , e.getClickedBlock().getLocation().getBlockX());
				ButtonControl.config.setProperty("Buttons.night.Y."+num , e.getClickedBlock().getLocation().getBlockY());
				ButtonControl.config.setProperty("Buttons.night.Z."+num , e.getClickedBlock().getLocation().getBlockZ());
				ButtonControl.config.setProperty("Buttons.night.cost."+num , plugin.pcost);
				ButtonControl.config.save();
				plugin.loadFile();
				e.getPlayer().sendMessage(ChatColor.RED + plugin.messages[10]);
				plugin.push = 0;
			}
		}
	}
}
