package me.johni0702.ButtonControl;

import java.util.regex.Matcher;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ControlButton 
{
	static enum ControlType
	{
		SUN,RAIN,THUNDER,DAY,NIGHT;
	}
	
	private Block block;
	private int price;
	private String receiver;
	private ControlType type;
	private static long lastaction = 0;
	public static int cooldown; //TODO:Setzen
	public static int rain_duration=10,sun_duration=10,thunder_duration=10;
	private static long rain_start=0,sun_start=0,thunder_start=0;
	private int item;

	public ControlButton(ControlType type, Block block, int price)
	{
		this.block = block;
		this.price = price;
		this.type = type;
	}
	
	public ControlButton(ControlType type, int price)
	{
		this.block = null;
		this.price = price;
		this.type = type;
	}
	
	public ControlButton(ControlType type, Block block, int price, int item)
	{
		this.block = block;
		this.price = price;
		this.type = type;
		this.item = item;
	}
	
	public ControlButton(ControlType type, int price, int item)
	{
		this.block = null;
		this.price = price;
		this.type = type;
		this.item = item;
	}
	
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}
	
	public ControlButton(ControlType type, Block block)
	{
		this(type,block,0);
	}
	
	public ControlButton(ControlType type)
	{
		this(type,null,0);
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public ControlType getType()
	{
		return type;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public int getItem()
	{
		return item;
	}
	
	public String getReceiver()
	{
		return receiver;
	}
	
	public void onPressed(Player p)
	{
		System.out.println("onRressed");
		//Check if the player has enough permissions
		switch (type)
		{
		case DAY:
			if (!ButtonControlPermissions.day(p))
					return;
			break;
		case NIGHT:
			if (!ButtonControlPermissions.night(p))
					return;
			break;
		case RAIN:
			if (!ButtonControlPermissions.rain(p))
					return;
			break;
		case SUN:
			if (!ButtonControlPermissions.sunny(p))
					return;
			break;
		case THUNDER:
			if (!ButtonControlPermissions.thunder(p))
					return;
			break;
		}
		
		//Enough time since last button pressed?
		if (lastaction > System.currentTimeMillis()-cooldown*1000)
			return;
		
		//Let him pay
		if (ButtonControl.econ != null)
		{
			//Economy plugin is enabled
			if (!pay(p,price,receiver))
			{
				p.sendMessage(ChatColor.RED + ButtonControl.messages[3]
						.replaceAll(Matcher.quoteReplacement("$need$"), Integer.toString(price))
						.replaceAll(Matcher.quoteReplacement("$have$"), Double.toString(ButtonControl.econ.getBalance(p.getName()))));
				return;
			}
		}
		else
		{
			//Item payment
			if (!pay(p,price,item))
			{
				int number = 0;
				for (ItemStack stack : p.getInventory().all(item).values())
					number+=stack.getAmount();
				p.sendMessage(ChatColor.RED + ButtonControl.messages[3]
						.replaceAll(Matcher.quoteReplacement("$need$"), price + " " + Material.getMaterial(item).toString().toLowerCase())
						.replaceAll(Matcher.quoteReplacement("$have$"), number + " " + Material.getMaterial(item).toString().toLowerCase()));
				return;
			}
		}
		
		//Now change something
		switch (type)
		{
		case DAY:
			block.getWorld().setTime(0);
			p.sendMessage(ChatColor.RED + ButtonControl.messages[11]);
			break;
		case NIGHT:
			block.getWorld().setTime(14000);
			p.sendMessage(ChatColor.RED + ButtonControl.messages[12]);
			break;
		case RAIN:
			block.getWorld().setStorm(true);
			block.getWorld().setThundering(false);
			rain_start = System.currentTimeMillis();
			p.sendMessage(ChatColor.RED + ButtonControl.messages[4]);
			break;
		case SUN:
			block.getWorld().setStorm(false);
			block.getWorld().setThundering(false);
			sun_start = System.currentTimeMillis();
			p.sendMessage(ChatColor.RED + ButtonControl.messages[2]);
			break;
		case THUNDER:
			block.getWorld().setStorm(false);
			block.getWorld().setThundering(true);
			thunder_start = System.currentTimeMillis();
			p.sendMessage(ChatColor.RED + ButtonControl.messages[5]);
			break;
		}
		
		lastaction = System.currentTimeMillis();
	}
	
	public boolean isAt(Block b)
	{
		return block.getX() == b.getX() &&
				block.getY() == b.getY() &&
				block.getZ() == b.getZ();
	}
	
	private static boolean pay(Player p, int amount, int item)
	{
		if (amount == 0)
			return true;
		
		if (p.getInventory().contains(item, amount))
		{
			p.getInventory().removeItem(new ItemStack(item,amount));
			return true;
		}
		else
			return false;
	}
	
	private static boolean pay(Player p, int price, String to)
	{
		if (price == 0)
			return true;
		
		if (!ButtonControl.econ.has(p.getName(), price))
		{
			ButtonControl.econ.withdrawPlayer(p.getName(), price);
			ButtonControl.econ.depositPlayer(to, price);
			return true;
		}
		else
			return false;
	}
	
	public static boolean canWeatherChange()
	{
		if (thunder_start + thunder_duration*1000 > System.currentTimeMillis())
			return false;
		if (rain_start + rain_duration*1000 > System.currentTimeMillis())
			return false;
		if (sun_start + sun_duration*1000 > System.currentTimeMillis())
			return false;
		return true;
	}

	public void setBlock(Block block) 
	{
		this.block = block;
	}
}
