package domcie.comamnds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import domcie.stonedrop;
import domcie.inventory.GUI;

public class DropCommand implements CommandExecutor {
	
	public DropCommand(stonedrop api) {
		api.getCommand("drop").setExecutor(this);
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
		Player p = (Player) sender;
		p.openInventory(GUI.getMainGUI(p));
		return false;
	}

}
