package domcie.data;

import java.io.File;
import java.sql.SQLException;

import domcie.stonedrop;
import domcie.database.Flat;
import domcie.database.MySQL;
import domcie.database.SQLite;

public class DataManager {
	
	private static File mainDir = stonedrop.getInst().getDataFolder();
	private static File cfgFile = new File(mainDir, "config.yml");
	private static File users = new File(mainDir, "userdata");
	private static File messages = new File(stonedrop.getInst().getDataFolder(), "messages.yml");
	

	public static void check(){
		if(!mainDir.exists()) mainDir.mkdir();
		if(!users.exists()) users.mkdir();
		if(!messages.exists()) stonedrop.getInst().saveResource("messages.yml", true);
		if(!cfgFile.exists()) stonedrop.getInst().saveDefaultConfig();
	}
	public static File getUsersFolder(){
		return users;
	}
	public static File getMessagesFile(){
		return messages;
	}
	public static File getConfigFile(){
		return cfgFile;
	}
	
	
	public static void load() {
		Config cfg = Config.getInst();
		if(cfg.datatype.equalsIgnoreCase("mysql")) {
			try {
				MySQL.setConnection("jdbc:mysql://"+cfg.mysql_ip+":"+cfg.mysql_port+"/"+cfg.mysql_dbname, cfg.mysql_user, cfg.mysql_pass);
				MySQL.load();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		} else 	if(cfg.datatype.equalsIgnoreCase("sqlite")) {
			try {
				SQLite.setConnection("jdbc:sqlite:plugins/" + stonedrop.desc.getName() + "/database.db");
				SQLite.load();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		} else {
			Flat.load(); 
		}
	}
	public static void save() {
		Config cfg = Config.getInst();
		if(cfg.datatype.equalsIgnoreCase("mysql")) {
			try {
				MySQL.save();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		} else if(cfg.datatype.equalsIgnoreCase("sqlite")) {
			try {
				SQLite.save();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		} else {
			Flat.save();
		}
	}

}
