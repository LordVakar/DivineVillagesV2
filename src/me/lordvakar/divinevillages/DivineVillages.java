package me.lordvakar.divinevillages;

import java.util.logging.Logger;

import me.lordvakar.divinevillages.commands.CmdVillage;

import me.lordvakar.divinevillages.managers.VillageManager;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DivineVillages extends JavaPlugin
{

	public static Plugin pl;
	public static FileConfiguration config;
	/*public static File folder = new File("plugins/DivineVillages");
	public static File villageFile = new File("plugins/DivineVillages" + "/" + "Villages" + ".yml");
	public static YamlConfiguration villageConfig = YamlConfiguration.loadConfiguration(villageFile);*/
	Logger log;

	public void onEnable() {
		pl = this;
		config = pl.getConfig();
	    log = getLogger();
		registerEvents(this// , new
		);
		registerCommands();
		VillageManager.getManager().loadAllVillages();
	}

	public void onDisable() {
		
	}

	// Main Utils
	public void registerCommands() {
		 registerCommand("village", new CmdVillage(this));
	}

	public void registerCommand(String command, CommandExecutor commandexecutor) {
		Bukkit.getServer().getPluginCommand(command)
				.setExecutor(commandexecutor);
	}

	public static void registerEvents(org.bukkit.plugin.Plugin plugin,
			Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager()
					.registerEvents(listener, plugin);
		}
	}
	
	 public static JavaPlugin getJavaPlugin() {
		 JavaPlugin plugin = (JavaPlugin) pl;
		 return plugin;
	 }
	 
/*	 public static void spreadPlayers(int centerx, int centerz, int min, int max, boolean team) {
	        for (Player p : Bukkit.getOnlinePlayers()) {
	            if (p !=null) {
	                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spreadplayers " + centerx + " " + centerz + " " + min + " " + max + " " + team + " " + p.getName());
	            }
	        }
	    }*/
}
