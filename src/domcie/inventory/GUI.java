package domcie.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import domcie.comparators.DropComparator;
import domcie.data.Config;
import domcie.enums.TurbodropType;
import domcie.objects.Drop;
import domcie.objects.Turbodrop;
import domcie.objects.User;
import domcie.utils.ItemUtil;
import domcie.utils.Util;

public class GUI {
	
	public static Inventory getMainGUI(Player p) {
		Config cfg = Config.getInst();
		
		List<String> statusON = new ArrayList<String>();
		for(String s : cfg.gui$status$on) {
			statusON.add(s.replace("&", "§"));
		}
		List<String> statusOFF = new ArrayList<String>();
		for(String s : cfg.gui$status$off) {
			statusOFF.add(s.replace("&", "§"));
		}
		
		User u = User.get(p.getUniqueId().toString());
		if(u == null) new User(p.getName(), p.getUniqueId().toString(), 0, 0);
		Inventory inv = Bukkit.createInventory(null, 27, cfg.gui$name.replace("&", "§"));
		
		//1 linijka
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		
		//2 linijka
		inv.setItem(9, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		
		//Drop
		List<String> dropLore = new ArrayList<String>();
		for(String s : cfg.gui$drop$lore) {
			dropLore.add(s.replace("&", "§"));
		}
		inv.setItem(10, ItemUtil.createIM(Material.getMaterial(cfg.gui$drop$item), cfg.gui$drop$name.replace("&", "§"), 1, dropLore));
		
		//Cobblestone
		inv.setItem(11, ItemUtil.createIM(Material.getMaterial(cfg.gui$cobble$item), cfg.gui$cobble$name.replace("&", "§"), 1, (u.isCobblestone() ? statusON : statusOFF)));
		
		//Wylaczenie/Wlaczenie wszystkich dropow
		inv.setItem(12, ItemUtil.createIM(Material.getMaterial(cfg.gui$dropdisable$item), cfg.gui$dropdisable$name.replace("&", "§"), 1, (u.isStatusDrops() ? statusON : statusOFF)));
		
		inv.setItem(13, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		//Turbodrop
		List<String> tbLore = new ArrayList<String>();
		for(String s : cfg.gui$turbodrop$lore) {
			tbLore.add(s
					.replace("&", "§")
					.replace("{TIMELEFT}", ""+(u.haveTurbodrop() ? Util.convertTime(u.getTurbodropType() == TurbodropType.TURBODROP_ALL ? Turbodrop.turbodropAll.getTimeLeft() : u.getTurbodrop().getTimeLeft()) : "0s"))
			);
			
		}
		inv.setItem(14, ItemUtil.createIM(Material.getMaterial(cfg.gui$turbodrop$item), cfg.gui$turbodrop$name.replace("&", "§"), 1, tbLore));
				
		//Statystyki
		List<String> statsLore = new ArrayList<String>();
		for(String s : cfg.gui$stats$lore) {
			statsLore.add(s.replace("&", "§"));
		}
		inv.setItem(15, ItemUtil.createIM(Material.getMaterial(cfg.gui$stats$item), cfg.gui$stats$name.replace("&", "§"), 1, statsLore));
		
		//Top 9 gornikow
		List<String> topLore = new ArrayList<String>();
		for(String s : cfg.gui$top$lore) {
			topLore.add(s.replace("&", "§"));
		}
		inv.setItem(16, ItemUtil.createIM(Material.getMaterial(cfg.gui$top$item), cfg.gui$top$name.replace("&", "§"), 1, topLore));
		
		inv.setItem(17, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(20, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		//Wiadomosci
		inv.setItem(22, ItemUtil.createIM(Material.getMaterial(cfg.gui$msg$item), cfg.gui$msg$name.replace("&", "§"), 1, (u.isMessages() ? statusON : statusOFF)));

		inv.setItem(23, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(24, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		return inv;
	}
	
	public static Inventory getDropGUI(Player p) {
		Config cfg = Config.getInst();
		User u = User.get(p.getUniqueId().toString());
		
		Inventory inv = Bukkit.createInventory(null, 27, cfg.gui$drop$name.replace("&", "§"));
		
		int id = 0;
		for(Drop d : Drop.drops) {
			List<String> lore = new ArrayList<String>();
			for (String s : cfg.gui$drop$show$lore) {
				lore.add(s
						.replace("&", "§")
						.replace("{MINY}", ""+d.getMinY())
						.replace("{MAXY}", ""+d.getMaxY())
						.replace("{CHANCE}", ""+d.getChance()+"%")
						.replace("{FORTUNE}", (d.isFortuneEnabled() ? cfg.active.replace("&", "§") : cfg.disable.replace("&", "§")))
						.replace("{BIOMES}", Util.getBiomes(d))
						.replace("{TOOLS}", Util.getTools(d))
						.replace("{STATUS}", (u.isEnabled(d)) ?  cfg.active.replace("&", "§") : cfg.disable.replace("&", "§"))
				);
			}
			inv.setItem(id, ItemUtil.createIM(d.getMaterial(), 1, lore));
			
			id++;
		}
		return inv;
	}
	
	public static Inventory getStatsGUI(Player p) {
		Config cfg = Config.getInst();
		User u = User.get(p.getUniqueId().toString());
		
		Inventory inv = Bukkit.createInventory(null, 9, cfg.gui$stats$name.replace("&", "§"));
		
		inv.setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(2, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(3, ItemUtil.createIM(Material.getMaterial(cfg.gui$stats$show$level$item), cfg.gui$stats$show$level$name.replace("&", "§").replace("{LEVEL}", ""+u.getLevel()), 1));
		inv.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(5, ItemUtil.createIM(Material.getMaterial(cfg.gui$stats$show$xp$item), cfg.gui$stats$show$xp$name.replace("&", "§").replace("{XP}", ""+u.getXp()).replace("{NEED}", ""+(u.getLevel()+1)*Config.getInst().xpToLevel), 1));
		inv.setItem(6, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		inv.setItem(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14));
		
		return inv;
	}
	public static Inventory getTopGUI(Player p) {
		Config cfg = Config.getInst();
		
		Inventory inv = Bukkit.createInventory(null, 9, cfg.gui$top$name.replace("&", "§"));
		
		List<User> users = User.players;
		Collections.sort(users, new DropComparator<User>());
		for(int i=1; i<=9; i++){
			if(i <= users.size()){
				User u = users.get(users.size()-i);
				
				ItemStack head = new ItemStack(Material.SKULL_ITEM, i, (short)3);
				SkullMeta skullmeta = (SkullMeta) head.getItemMeta();
				skullmeta.setOwner(u.getName());
				head.setItemMeta(skullmeta);
				
				List<String> lore = new ArrayList<String>();
				for(String s : cfg.gui$top$show$lore) {
					lore.add(s
							.replace("&", "§")
							.replace("{XP}", ""+u.getXp())
							.replace("{LEVEL}", ""+u.getLevel())
							.replace("{NEED}", ""+(u.getLevel()+1)*Config.getInst().xpToLevel)
					);
				}
				inv.setItem(i-1, ItemUtil.createIM(head, cfg.gui$top$show$name.replace("&", "§").replace("{POZYCJA}", ""+i).replace("{NICKNAME}", u.getName()), lore));
			} else {
				ItemStack head = new ItemStack(Material.SKULL_ITEM, i, (short)3);
				SkullMeta skullmeta = (SkullMeta) head.getItemMeta();
				skullmeta.setOwner("Steve");
				head.setItemMeta(skullmeta);
				
				List<String> lore = new ArrayList<String>();
				for(String s : cfg.gui$top$show$lore) {
					lore.add(s
							.replace("&", "§")
							.replace("{XP}", "---")
							.replace("{LEVEL}", "---")
							.replace("{NEED}", "---")
					);
				}
				inv.setItem(i-1, ItemUtil.createIM(head, cfg.gui$top$show$name.replace("&", "§").replace("{POZYCJA}", ""+i).replace("{NICKNAME}", "---"), lore));
			}
		}
		return inv;
	}

}
