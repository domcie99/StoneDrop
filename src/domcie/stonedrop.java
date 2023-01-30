package domcie;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import domcie.comamnds.CobbleCommand;
import domcie.comamnds.DropCommand;
import domcie.comamnds.TurbodropCommand;
import domcie.data.Config;
import domcie.data.DataManager;
import domcie.data.Message;
import domcie.listeners.AsyncPlayerChatListener;
import domcie.listeners.BlockBreakListener;
import domcie.listeners.InventoryClickListener;
import domcie.listeners.PlayerJoinListener;
import domcie.objects.Turbodrop;
import domcie.objects.User;

public class stonedrop extends JavaPlugin {

	public static stonedrop inst;
	public static PluginDescriptionFile desc;
	
	public static stonedrop getInst(){
		return inst;
	}
	public static void error(String s, boolean disable) {
		Bukkit.getConsoleSender().sendMessage("§c["+desc.getName()+" v"+desc.getVersion()+"] "+s);
		if(disable) Bukkit.getPluginManager().disablePlugin(inst);
	}
	public static void success(String s) {
		Bukkit.getConsoleSender().sendMessage("§a["+desc.getName()+" v"+desc.getVersion()+"] "+s);
	}
	public void onEnable() {
		inst = this;
		desc = this.getDescription();
		DataManager.check();
		Config.getInst().load();
		Message.getInst().load();
		DataManager.load();
		
		
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		
		new DropCommand(this);
		new CobbleCommand(this);
		new TurbodropCommand(this);
		
		User.chceckPlayersExist();
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				for(int i=0; i < Turbodrop.turbodrops.size(); i++) {
					Turbodrop tb = Turbodrop.turbodrops.get(i);
					if(tb != null) {
						if(tb.getTimeLeft() <= 0) {
							tb.remove();
						} else {
							tb.setTimeLeft(tb.getTimeLeft()-1);
						}	
					}
				}
			}
		}.runTaskTimer(this, 20, 20);
		
	}
	
	public void onDisable() {
		DataManager.save();
	}
	
}
