package me.lordvakar.divinevillages.commands;

import java.io.IOException;

import me.lordvakar.divinevillages.DivineVillages;
import me.lordvakar.divinevillages.managers.VillageManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CmdVillage implements CommandExecutor{
	
	DivineVillages main;
	String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "DivineVillages> ";
	
	public CmdVillage(DivineVillages plugin) {
    	this.main = (DivineVillages) DivineVillages.pl;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("village")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(prefix + "You're not a player, silly!");
			} else {
				Player p = (Player) sender;
				if (args.length == 0) {
					p.sendMessage(prefix + "Please specify some arguments!");
				} 
/*Create*/		else if (args[0].equalsIgnoreCase("create")) {
					if (args[1] != null) {
						p.sendMessage(prefix + "You have created a new village named " + args[1] + "!");
						Bukkit.broadcastMessage(prefix + p.getName() + "has created a new village named " + args[1]);
						VillageManager.getManager().createVillage(args[1], "", p);
						p.sendMessage(prefix + "Please now do /village desc <villagename> <desc>!");
					} else {
						p.sendMessage(prefix + "Please specify the village name!");
					}
				}
/*Desc*/		else if (args[0].equalsIgnoreCase("desc")) {
					if (args[1] != null) {
						if (args[2] != null) {
							if(VillageManager.getManager().getVillage(args[1]) != null) {
						        String message = "";
						        for(int i = 0; i < args.length; i++) {
						            message += args[i];
						            if(i < (args.length - 1)) message += " ";
						        }
						        
								p.sendMessage(prefix + "You have set the description of your village to: " + message + "!");
								Bukkit.broadcastMessage(prefix + p.getName() + "has set their village desc to: " + message);
								VillageManager.getManager().getVillage(args[1]).setVillageDesc(args[2]);
						        
								FileConfiguration fc = DivineVillages.villageConfig;
								String path = "Villages." + args[1] + ".";
								fc.set(path + "villageDesc", message);
								try {
									fc.save(DivineVillages.villageFile);
									fc.load(DivineVillages.villageFile);
								} catch (IOException e) {
									e.printStackTrace();
								} catch (InvalidConfigurationException e) {
									e.printStackTrace();
								}
							} else {
								p.sendMessage(prefix + "The village you specified doesn't exist!");
							}
						} else {
							p.sendMessage(prefix + "Please specify the village desc!");
						}
					} else {
						p.sendMessage(prefix + "Please specify the village name!");
					}
				}
			}
		}
		return false;
	}
}
