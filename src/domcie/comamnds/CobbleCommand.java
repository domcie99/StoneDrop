package domcie.comamnds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import domcie.stonedrop;
import domcie.data.Config;
import domcie.data.Message;
import domcie.objects.User;

public class CobbleCommand implements CommandExecutor {

	public CobbleCommand(stonedrop api) {
		api.getCommand("cobble").setExecutor(this);
		api.getCommand("cobblestone").setExecutor(this);
	}

	/*
	 * 
	 * »
	 * «
	 * 
	 * (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§4Komenda /drop jest dostepna tylko dla graczy!"); 
			return false;
		}
		
		Config cfg = Config.getInst();
		Message msg = Message.getInst();
		Player p = (Player) sender;
		User u = User.get(p.getUniqueId().toString());
		
		List<String> statusON = new ArrayList<String>();
		for(String s : cfg.gui$status$on) {
			statusON.add(s.replace("&", "§"));
		}
		List<String> statusOFF = new ArrayList<String>();
		for(String s : cfg.gui$status$off) {
			statusOFF.add(s.replace("&", "§"));
		}
		
		if(u.isCobblestone()) {
			u.setCobblestone(false);
			p.sendMessage(msg.getMessage("disableCobblestone"));
			return false;
		} else {
			u.setCobblestone(true);
			p.sendMessage(msg.getMessage("enableCobblestone"));
			return true;
		}
	}

}
