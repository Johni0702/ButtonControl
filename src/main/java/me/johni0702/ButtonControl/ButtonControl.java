package me.johni0702.ButtonControl;

import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.iConomy.iConomy;

public class ButtonControl extends JavaPlugin
{
	public boolean icon = false;
	public iConomy iconomy = null;
	static String mainDir = "plugins/ButtonControl";
	static File BW = new File(mainDir + File.separator + "ButtonControl.yml");
	static Configuration config = new Configuration(BW);
	
	int amount = 5;
	int item = 331;

	private ButtonControlBlockListener BL = null;
	private ButtonControlPlayerListener PL = null;
	private ButtonControlServerListener SL = null;
	private ButtonControlWeatherListener WL = null;
	String[] messages={"Push a Button!",
			"You have no Permissions to set Weather-Buttons.",
			"The sun comes out.",
			"You don´t have enough money",
			"Let it rain",
			"GO! A STORM IS COMING!",
			"The rain-button has been set.",
			"The thunder-button has been set.",
			"The stop-button has been set.",
			"The day-button has been set.",
			"The night-button has been set.",
			"Good morning!",
			"It´s late. Go home.",};
	public Block[] stop = null;
	public int[] stopcost = null;
	public Block[] thunder = null;
	public int[] thundercost = null;
	public Block[] rain = null;
	public int[] raincost = null;
	public Block[] day = null;
	public int[] daycost = null;
	public Block[] night = null;
	public int[] nightcost = null;
	int rd = 10;
	int td = 10;
	int sd = 10;
	long rstart = 0;
	long tstart = 0;
	long sstart = 0;
	public short push = 0;
	public int pcost = 0;
	public ItemStack pay = null;
	String[] worldss = null;
	String[] worldsr = null;
	String[] worldst = null;
	String[] worldsd = null;
	String[] worldsn = null;
	public void onEnable(){
		new File(mainDir).mkdir();
		if (!BW.exists())
		{
			try
			{
				BW.createNewFile();
				config.setProperty("Duration.sun" , sd);
				config.setProperty("Duration.rain" , rd);
				config.setProperty("Duration.thunder" , td);
				config.setProperty("Cost.Item" , item);
				config.setProperty("iConomy.useiConomy" , icon);
				config.setProperty("Buttons.StopAnzahl", 0);
				config.setProperty("Buttons.RainAnzahl", 0);
				config.setProperty("Buttons.StormAnzahl", 0);
				config.setProperty("Messages.push_a_button", messages[0]);
				config.setProperty("Messages.not_permissions", messages[1]);
				config.setProperty("Messages.sun_comes", messages[2]);
				config.setProperty("Messages.not_enough_money", messages[3]);
				config.setProperty("Messages.rain_comes", messages[4]);
				config.setProperty("Messages.storm_comes", messages[5]);
				config.setProperty("Messages.rain_set", messages[6]);
				config.setProperty("Messages.thunder_set", messages[7]);
				config.setProperty("Messages.stop_set", messages[8]);
				config.setProperty("Messages.day_set", messages[9]);
				config.setProperty("Messages.night_set", messages[10]);
				config.setProperty("Messages.day_comes", messages[11]);
				config.setProperty("Messages.night_comes", messages[12]);
				config.save();
				System.out.println("[ButtonControl] Config-File created.");
				this.loadFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			loadFile();
		}
		this.PL = new ButtonControlPlayerListener(this);
		this.BL = new ButtonControlBlockListener(this);
		this.SL = new ButtonControlServerListener(this);
		this.WL = new ButtonControlWeatherListener(this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BREAK, BL, Priority.Normal , this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, PL, Priority.Normal , this);
		pm.registerEvent(Event.Type.WEATHER_CHANGE, WL, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, SL, Priority.Normal , this);
		pm.registerEvent(Event.Type.PLUGIN_DISABLE, SL, Priority.Normal , this);
		PluginDescriptionFile pdf = this.getDescription();
		System.out.println("ButtonControl v" + pdf.getVersion() + " aktiviert.");
		
	}
	public void onDisable(){
		config.save();
		System.out.println("ButtonControl deaktiviert.");
	}
	public void loadFile(){
		config.load();
		int tmp =  0;
		item = config.getInt("Cost.Item", item);
		amount = config.getInt("Cost.Amount", amount);
		icon = config.getBoolean("iConomy.useiConomy", icon);
		int i = 0;
		tmp = config.getInt("Buttons.StopAnzahl", tmp);
		worldss = new String[config.getInt("Buttons.StopAnzahl", i)+1];
		stop = new Block[config.getInt("Buttons.StopAnzahl", i)+1];
		stopcost = new int[config.getInt("Buttons.StopAnzahl", i)+1];
		while (i < tmp)
		{
			worldss[i] = config.getString("Buttons.Stop.world."+i);
			stop[i] = this.getServer().getWorld(worldss[i]).getBlockAt(config.getInt("Buttons.Stop.X."+i , 0),config.getInt("Buttons.Stop.Y."+i , 0),config.getInt("Buttons.Stop.Z."+i , 0));
			stopcost[i] = config.getInt("Buttons.Stop.cost."+i, stopcost[i]);
			System.out.println(i + " " + tmp);
			i++;
		}
		i = 0;
		worldsr = new String[config.getInt("Buttons.RainAnzahl", i)+1];
		rain = new Block[config.getInt("Buttons.RainAnzahl", i)+1];
		tmp = config.getInt("Buttons.RainAnzahl", tmp);
		raincost = new int[config.getInt("Buttons.RainAnzahl", i)+1];
		while (i < tmp)
		{
			worldsr[i] = config.getString("Buttons.Rain.world."+i);
			rain[i] = this.getServer().getWorld(worldsr[i]).getBlockAt(config.getInt("Buttons.Rain.X."+i , 0),config.getInt("Buttons.Rain.Y."+i , 0),config.getInt("Buttons.Rain.Z."+i , 0));
			raincost[i] = config.getInt("Buttons.Rain.cost."+i, raincost[i]);
			i++;
		}
		i = 0;
		worldst = new String[config.getInt("Buttons.StormAnzahl", i)+1];
		thunder = new Block[config.getInt("Buttons.StormAnzahl", i)+1];
		tmp = config.getInt("Buttons.StormAnzahl", tmp);
		thundercost = new int[config.getInt("Buttons.StormAnzahl", i)+1];
		while (i < tmp)
		{
			worldst[i] = config.getString("Buttons.Storm.world."+i);
			thunder[i] = this.getServer().getWorld(worldst[i]).getBlockAt(config.getInt("Buttons.Storm.X."+i , 0),config.getInt("Buttons.Storm.Y."+i , 0),config.getInt("Buttons.Storm.Z."+i , 0));
			thundercost[i] = config.getInt("Buttons.Storm.cost."+i, thundercost[i]);
			i++;
		}
		i = 0;
		worldsd = new String[config.getInt("Buttons.DayAnzahl", i)+1];
		day = new Block[config.getInt("Buttons.DayAnzahl", i)+1];
		tmp = config.getInt("Buttons.DayAnzahl", tmp);
		daycost = new int[config.getInt("Buttons.DayAnzahl", i)+1];
		while (i < tmp)
		{
			worldsd[i] = config.getString("Buttons.day.world."+i);
			day[i] = this.getServer().getWorld(worldsd[i]).getBlockAt(config.getInt("Buttons.day.X."+i , 0),config.getInt("Buttons.day.Y."+i , 0),config.getInt("Buttons.day.Z."+i , 0));
			daycost[i] = config.getInt("Buttons.day.cost."+i, daycost[i]);
			i++;
		}
		i = 0;
		worldsn = new String[config.getInt("Buttons.NightAnzahl", i)+1];
		night = new Block[config.getInt("Buttons.NightAnzahl", i)+1];
		tmp = config.getInt("Buttons.NightAnzahl", tmp);
		nightcost = new int[config.getInt("Buttons.NightAnzahl", i)+1];
		while (i < tmp)
		{
			worldsn[i] = config.getString("Buttons.night.world."+i);
			night[i] = this.getServer().getWorld(worldsn[i]).getBlockAt(config.getInt("Buttons.night.X."+i , 0),config.getInt("Buttons.night.Y."+i , 0),config.getInt("Buttons.night.Z."+i , 0));
			nightcost[i] = config.getInt("Buttons.night.cost."+i, nightcost[i]);
			i++;
		}
		messages[0]=config.getString("Messages.push_a_button", messages[0]);
		messages[1]=config.getString("Messages.not_permissions", messages[1]);
		messages[2]=config.getString("Messages.sun_comes", messages[2]);
		messages[3]=config.getString("Messages.not_enough_money", messages[3]);
		messages[4]=config.getString("Messages.rain_comes", messages[4]);
		messages[5]=config.getString("Messages.storm_comes", messages[5]);
		messages[6]=config.getString("Messages.rain_set", messages[6]);
		messages[7]=config.getString("Messages.thunder_set", messages[7]);
		messages[8]=config.getString("Messages.stop_set", messages[8]);
		messages[9]=config.getString("Messages.day_set", messages[9]);
		messages[10]=config.getString("Messages.night_set", messages[10]);
		messages[11]=config.getString("Messages.day_comes", messages[11]);
		messages[12]=config.getString("Messages.night_comes", messages[12]);
		
	}
	public boolean onCommand(CommandSender sender , Command cmd , String commandLabel , String[] args ){
		if (!(sender instanceof Player))return false;
		if (cmd.getName().equalsIgnoreCase("buttonrain")){
			if (!sender.isOp()) 
			{
				sender.sendMessage(messages[1]);
				return true;
			}
			if (args.length != 1) return false;
			push = 1;
			pcost = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.RED + messages[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("buttonthunder")){
			if (!sender.isOp()) 
			{
				sender.sendMessage(messages[1]);
				return true;
			}
			if (args.length != 1) return false;
			push = 2;
			pcost = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.RED + messages[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("buttonstop")){
			if (!sender.isOp()) 
			{
				sender.sendMessage(messages[1]);
				return true;
			}
			if (args.length != 1) return false;
			push = 3;
			pcost = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.RED + messages[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("buttonday")){
			if (!sender.isOp()) 
			{
				sender.sendMessage(messages[1]);
				return true;
			}
			if (args.length != 1) return false;
			push = 4;
			pcost = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.RED + messages[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("buttonnight")){
			if (!sender.isOp()) 
			{
				sender.sendMessage(messages[1]);
				return true;
			}
			if (args.length != 1) return false;
			push = 5;
			pcost = Integer.parseInt(args[0]);
			sender.sendMessage(ChatColor.RED + messages[0]);
			return true;
		}
		return false;
	}
}
