package domcie.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import domcie.objects.User;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(User.get(e.getPlayer().getUniqueId().toString()) == null) {
			new User(e.getPlayer().getName(),e.getPlayer().getUniqueId().toString(),0,0);
		}
	}
	

}
