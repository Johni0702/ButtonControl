package me.johni0702.ButtonControl;


import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;

public class ButtonControlServerListener extends ServerListener{
	ButtonControl plugin = null;
	public ButtonControlServerListener(ButtonControl plugin){
		this.plugin = plugin;
	}
	
	public void onPluginDisable(PluginDisableEvent event) {
        if (plugin.iconomy != null) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
                plugin.iconomy = null;
            }
        }
    }

	
	@Override
	public void onPluginEnable(PluginEnableEvent event) {
        if (plugin.iconomy == null) {
            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iConomy.iConomy")) {
                    plugin.iconomy = (iConomy)iConomy;
                    System.out.println("[ButtonControl] iConomy was detected.");
                }
            }
        }
    }
}
