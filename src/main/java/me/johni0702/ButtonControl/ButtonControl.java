package me.johni0702.ButtonControl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import me.johni0702.ButtonControl.ControlButton.ControlType;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;



public class ButtonControl extends JavaPlugin
{
	public static Economy econ;
	static String mainDir = "plugins/ButtonControl";
	static File ConfigFile = new File(mainDir + File.separator + "ButtonControl.yml");
	static FileConfiguration config;
	
//	int amount = 5;
//	int item = 331;

	private ButtonControlListener listener = null;
	public static String[] messages={"Push a Button!",
			"You have no permissions to set button controls.",
			"The sun comes out.",
			"You don't have enough money ($have$ out of $need$)",
			"Let it rain",
			"GO! A STORM IS COMING!",
			"The rain-button has been set.",
			"The thunder-button has been set.",
			"The sunny-button has been set.",
			"The day-button has been set.",
			"The night-button has been set.",
			"Good morning!",
			"It's late, go home.",
			"You don't have permission to use button controls",};
//	public Block[] sunny = null;
//	public int[] sunnycost = null;
//	public Block[] thunder = null;
//	public int[] thundercost = null;
//	public Block[] rain = null;
//	public int[] raincost = null;
//	public Block[] day = null;
//	public int[] daycost = null;
//	public Block[] night = null;
//	public int[] nightcost = null;
	public ArrayList<ControlButton> buttons;
	int rd = 10;
	int td = 10;
	int sd = 10; //TODO: Read from configfile
	long rstart = 0;
	long tstart = 0;
	long sstart = 0;
	public HashMap<Player,ControlButton> onCreation;
//	public short push = 0;
//	public int pcost = 0;
//	public ItemStack pay = null;
//	String[] worldss = null;
//	String[] worldsr = null;
//	String[] worldst = null;
//	String[] worldsd = null;
//	String[] worldsn = null;
	String acc = null;

	public void onEnable()
	{
		onCreation = new HashMap<Player, ControlButton>();
		buttons = new ArrayList<ControlButton>();
		
		new File(mainDir).mkdir();
		config = getConfig();
		if (!ConfigFile.exists())
		{
			try
			{
				ConfigFile.createNewFile();
				config.set("Duration.sun" , sd);
				config.set("Duration.rain" , rd);
				config.set("Duration.thunder" , td);
				config.createSection("Buttons");
				config.set("Messages.push_a_button", messages[0]);
				config.set("Messages.not_permissions", messages[1]);
				config.set("Messages.sun_comes", messages[2]);
				config.set("Messages.not_enough_money", messages[3]);
				config.set("Messages.rain_comes", messages[4]);
				config.set("Messages.thunder_comes", messages[5]);
				config.set("Messages.rain_set", messages[6]);
				config.set("Messages.thunder_set", messages[7]);
				config.set("Messages.sunny_set", messages[8]);
				config.set("Messages.day_set", messages[9]);
				config.set("Messages.night_set", messages[10]);
				config.set("Messages.day_comes", messages[11]);
				config.set("Messages.night_comes", messages[12]);
				config.set("Messages.not_use", messages[13]);
				config.set("Cooldown", 0);
				config.save(ConfigFile);
				ButtonControlLogger.info("Config-File created.");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		loadFile();
		ButtonControlLogger.info("Loaded " + buttons.size() + " buttons.");
		
		setupEconomy();
		
		listener = new ButtonControlListener(this);
		this.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	public void onDisable()
	{
	}
	
	public void loadFile()
	{
		try {
			config.load(ConfigFile);
		} catch (FileNotFoundException e) 
		{
			ButtonControlLogger.severe("Config file not found:", e);
		} catch (IOException e) 
		{
			ButtonControlLogger.severe("Error while loading config file:", e);
		} catch (InvalidConfigurationException e) 
		{
			ButtonControlLogger.severe("Your config file is invalid. (Possibly it contains tabs?)", e);
		}

//		ConfigurationSection cs = config.getConfigurationSection("Buttons");
//		System.out.println(cs.);
		buttons.clear();
		for (int i = 0; config.contains("Buttons." + Integer.toString(i)); i++)
		{
			ConfigurationSection s = config.getConfigurationSection("Buttons." + i);

			String type = s.getString("type", null);
//			System.out.println(type);
			if (type == null || ControlType.valueOf(type) == null)
			{
				continue;
			}
			
			World w = getServer().getWorld(s.getString("world"));
//			System.out.println(w);
			if (w == null)
			{
				continue;
			}
			
			Block block = w.getBlockAt(s.getInt("x" , 0),s.getInt("y" , 0),s.getInt("z" , 0));
			int price = s.getInt("price", 0);
			int item = s.getInt("item", 0);
			String receiver = s.getString("receiver", "");
			
			ControlButton cb = new ControlButton(ControlType.valueOf(type), block, price, item);
			cb.setReceiver(receiver);
			buttons.add(cb);
		}
		
		messages[0]=config.getString("Messages.push_a_button", messages[0]);
		messages[1]=config.getString("Messages.not_permissions", messages[1]);
		messages[2]=config.getString("Messages.sun_comes", messages[2]);
		messages[3]=config.getString("Messages.not_enough_money", messages[3]);
		messages[4]=config.getString("Messages.rain_comes", messages[4]);
		messages[5]=config.getString("Messages.thunder_comes", messages[5]);
		messages[6]=config.getString("Messages.rain_set", messages[6]);
		messages[7]=config.getString("Messages.thunder_set", messages[7]);
		messages[8]=config.getString("Messages.sunny_set", messages[8]);
		messages[9]=config.getString("Messages.day_set", messages[9]);
		messages[10]=config.getString("Messages.night_set", messages[10]);
		messages[11]=config.getString("Messages.day_comes", messages[11]);
		messages[12]=config.getString("Messages.night_comes", messages[12]);
		messages[13]=config.getString("Messages.not_use", messages[13]);
		ControlButton.cooldown=config.getInt("Cooldown", 0);
		
	}
	
	public void saveFile()
	{
		config.createSection("Buttons");
		ConfigurationSection cs = config.getConfigurationSection("Buttons");
		int i = 0;
		for (ControlButton cb : buttons)
		{
			if (cb == null)
				continue;
			cs.createSection(Integer.toString(i));
			ConfigurationSection s = cs.getConfigurationSection(Integer.toString(i));

			s.set("x", cb.getBlock().getLocation().getBlockX());
			s.set("y", cb.getBlock().getLocation().getBlockY());
			s.set("z", cb.getBlock().getLocation().getBlockZ());
			s.set("world", cb.getBlock().getLocation().getWorld().getName());
			s.set("type", cb.getType().toString());
			s.set("price", cb.getPrice());
			s.set("item", cb.getItem());
			s.set("receiver", cb.getReceiver());
			i++;
		}
		
		try 
		{
			config.save(ConfigFile);
		} catch (IOException e) 
		{
			ButtonControlLogger.severe("Failed to save config file: ", e);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
        String commandName = command.getName().toLowerCase();
        
		
		if (commandName.equals("buttoncontrol") || commandName.equals("bc")) 
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage("Only players can use this command!");
				return true;
			}
			Player player = (Player)sender;
			
			if (ButtonControlPermissions.canSetButtons(player)) 
			{
				if (args.length != 3)
				{
					if (econ == null)
						player.sendMessage("Useage: /"+commandName+" <sunny/rain/thunder/day/night> <amount> <item>");
					else
						player.sendMessage("Useage: /"+commandName+" <sunny/rain/thunder/day/night> <price> <PayTo>");
					return true;
				}
				
				//What type of changing should be done?
				ControlType type = null;
				if (args[0].equalsIgnoreCase("rain"))
					type = ControlType.RAIN;
				else if (args[0].equalsIgnoreCase("sunny"))
					type = ControlType.SUN;
				else if (args[0].equalsIgnoreCase("thunder"))
					type = ControlType.THUNDER;
				else if (args[0].equalsIgnoreCase("day"))
					type = ControlType.DAY;
				else if (args[0].equalsIgnoreCase("night"))
					type = ControlType.NIGHT;
				if (type == null)
				{
					sender.sendMessage("Invalide type: " + args[0]);
					sender.sendMessage("You can use: sunny/rain/thunder/day/night");
					return true;
				}
					
				
				//Getting the first argument
				int arg1;
				try
				{
					arg1 = Integer.parseInt(args[1]);
				} catch (NumberFormatException e)
				{
					sender.sendMessage("\""+args[1]+"\" isn´t a number.");
					return true;
				}
				
				if (econ == null)
				{
					//Itembased payment
					
					//Getting the secound argument, the item
					int item;
					try
					{
						item = Integer.parseInt(args[2]);
					} catch (NumberFormatException e)
					{
						Material material = Material.getMaterial(args[2].toUpperCase());
						if (material == null)
						{
							sender.sendMessage("\""+args[2]+"\" wasn´t found.");
							return true;
						}
						item = material.getId();
					}
					
					//Create the ControlButton and add it to the toDoList
					ControlButton cb = new ControlButton(type, arg1, item);
					onCreation.put(player, cb);

					//Informing player
					sender.sendMessage(ChatColor.RED + messages[0]);
					return true;
				}
				else
				{
					//Moneybased payment
					
					//Getting the secound argument
					String receiver = args[2];
					
					//Create the ControlButton and add it to the toDoList
					ControlButton cb = new ControlButton(type, arg1);
					cb.setReceiver(receiver);
					onCreation.put(player, cb);

					//Informing player
					sender.sendMessage(ChatColor.RED + messages[0]);
					return true;
				}
			} 
			else 
			{
				//Player has no permissions
				sender.sendMessage(messages[1]);
				return true;
			}
		}
		return false;
	}
	
	 private boolean setupEconomy() 
	 {
	        if (getServer().getPluginManager().getPlugin("Vault") == null)
	            return false;
	        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }
}
