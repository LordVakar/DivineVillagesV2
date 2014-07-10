package me.lordvakar.divinevillages.commands;

import java.io.File;
import java.io.IOException;

import me.lordvakar.divinevillages.DivineVillages;
import me.lordvakar.divinevillages.managers.VillageManager;
import me.lordvakar.divinevillages.objects.Village;
import me.lordvakar.divinevillages.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CmdVillage implements CommandExecutor{
	
	DivineVillages main;
	String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "DivineVillages> " + ChatColor.RESET + ChatColor.GOLD;
	
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
	 				if (args.length == 1) {
	 					p.sendMessage(prefix + "Please specify some arguments!");
	 				}
	 				else if (args[1] != null) {
	 					if(!VillageManager.getManager().isInVillage(p)) {
	 						p.sendMessage(prefix + "You have created a new village named " + args[1] + "!");
	 						Bukkit.broadcastMessage(prefix + p.getName() + " has created a new village named " + args[1]);
	 						VillageManager.getManager().createVillage(args[1], "", p);
	 						p.sendMessage(prefix + "Please now do /village desc <villagename> <desc>!");
	 						p.sendMessage(prefix + "Also, by default, your village is open to everyone to join! Do /village ");
	 					} else {
							p.sendMessage(prefix + "You are already in a village!");
	 					}
					} else {
						p.sendMessage(prefix + "Please specify the village name!");
					}
				}
/*Desc*/		else if (args[0].equalsIgnoreCase("desc")) {
					if (args.length == 1) {
						p.sendMessage(prefix + "Please specify some arguments!");
					}
					else if (args[1] != null) {
						if (args[2] != null) {
							if(VillageManager.getManager().isInVillage(p)) {
		 						if(VillageManager.getManager().villageExists(args[1])) {
		 							if(VillageManager.getManager().getVillage(args[1]).isVillageLeader(p)) {
			 							File folder = new File("plugins/DivineVillages/Villages");
			 					    	File villageFile = new File("plugins/DivineVillages/Villages" + "/" + args[1] + ".yml");
			 					    	YamlConfiguration villageConfig = YamlConfiguration.loadConfiguration(villageFile);
			 					    	if(!folder.exists()) folder.mkdir();
			 					    	if(!villageFile.exists()) {
			 					    		p.sendMessage(prefix + "Apparently your village file doesn't exist and there are so many");
			 					    		p.sendMessage(prefix + "if and else statements that you shouldn't even get this message so there is nothing built in to protect this happening!");
			 					    		p.sendMessage(prefix + "So now you could crash, the server may spit out errors, or maybe Vakar just made a bad plugin build and he has to fix this. ;3");
			 					    	}
			 							String message = "";
			 							for(int i = 2; i < args.length; i++) {
			 								message += args[i];
			 								if(i < (args.length - 1)) message += " ";
			 							}
						        
			 							p.sendMessage(prefix + "You have set the description of your village to: " + message + "!");
			 							Bukkit.broadcastMessage(prefix + p.getName() + "has set their village desc to: " + message);
			 							VillageManager.getManager().getVillage(args[1]).setVillageDesc(message);
			 							
			 							String path = args[1] + ".";
			 							villageConfig.set(path + "villageDesc", message);
			 							try {
			 								villageConfig.save(villageFile);
			 								villageConfig.load(villageFile);
			 							} catch (IOException e) {
			 								e.printStackTrace();
			 							} catch (InvalidConfigurationException e) {
			 								e.printStackTrace();
			 							}
			 					} else {
			 						p.sendMessage(prefix + "You are not the leader of that village!");
			 					}
		 					} else {
		 						p.sendMessage(prefix + "The village you specified doesn't exist!");
		 					}
		 				} else {
							p.sendMessage(prefix + "You aren't in a village!");
		 				}
					} else {
						p.sendMessage(prefix + "Please specify the village desc!");
					}
				} else {
					p.sendMessage(prefix + "Please specify the village name!");
				}
			}
/*Join*/	else if (args[0].equalsIgnoreCase("join")) {
				if (args.length == 1) {
					p.sendMessage(prefix + "Please specify some arguments!");
				}
				else if (args[1] != null) {
					if(!VillageManager.getManager().isInVillage(p)) {
 						if(VillageManager.getManager().villageExists(args[1])) {
 							Village vil = VillageManager.getManager().getVillage(args[1]);
 							if(vil.isOpen() || vil.getInvitedPlayers().contains(p.getUniqueId())) {
 								vil.messageAllCitizens(prefix + p.getName() + " has joined the village!");
 								VillageManager.getManager().joinVillage(args[1], p);
 							} else {
 								p.sendMessage(prefix + "The village is not open and you are not invited to it!");
 							}
 						} else {
 							p.sendMessage(prefix + "The village you specified doesn't exist!");
 						}
					} else {
						p.sendMessage(prefix + "You are already in a village!");
					}
				} else {
					p.sendMessage(prefix + "Please specify the village name!");
				}
			}
/*Invite*/	else if (args[0].equalsIgnoreCase("invite")) {
				if (args.length == 1) {
					p.sendMessage(prefix + "Please specify some arguments!");
				}
				else if (args[1] != null) {
					if(VillageManager.getManager().isInVillage(p)) {
						Village vil = VillageManager.getManager().getPlayersVillage(p);
 						if(VillageManager.getManager().villageExists(vil.getVillageName())) {
 							if(vil.isVillageLeader(p)) {
 								VillageManager.getManager().invitePlayer(vil.getVillageName(), Util.getPlayer(args[1]));
 								p.sendMessage(prefix + "You have invited " + args[1] + " to your village!");
 							} else {
 								p.sendMessage(prefix + "You are not the leader of that village!");
 							}
 						} else {
 							p.sendMessage(prefix + "The village you specified doesn't exist!");
 						}
					} else {
						p.sendMessage(prefix + "You aren't in a village!");
					}
				}
			}
/*SetSpawn*/else if (args[0].equalsIgnoreCase("setspawn")) {
				if(VillageManager.getManager().isInVillage(p)) {
					Village vil = VillageManager.getManager().getPlayersVillage(p);
					if(VillageManager.getManager().villageExists(vil.getVillageName())) {
						if(vil.isVillageLeader(p)) {
 					    	File villageFile = new File("plugins/DivineVillages/Villages" + "/" + vil.getVillageName() + ".yml");
 					    	YamlConfiguration villageConfig = YamlConfiguration.loadConfiguration(villageFile);
 							String path = vil.getVillageName() + ".";
							vil.setVillageSpawn(p.getLocation());
							villageConfig.set(path + "villageSpawn", VillageManager.getManager().serializeLoc(p.getLocation()));
							p.sendMessage(prefix + "You set the village spawn to your current location!");
						} else {
							p.sendMessage(prefix + "You are not the leader of your village!");
						}
					} else {
						p.sendMessage(prefix + "You really shouldn't be getting this message!");
						p.sendMessage(prefix + "If Vakar's if and elses fail then you'll get this message!");
						p.sendMessage(prefix + "This means you're in a village but it doesn't exist! lel 1337699001");
					}
				} else {
					p.sendMessage(prefix + "You aren't in a village!");
				}
			} else if (args[0].equalsIgnoreCase("name")) {
				//FUCK YEA LETS RENAME SOME SHIT
				//EDIT: That method was complicated and long to do this o.o
				//EDIT: I could of done other things to rename, but I guess my way is efficient.
				if (args.length == 1) {
					p.sendMessage(prefix + "Please specify some arguments!");
				}
				else if (args[1] != null) {
					if(!VillageManager.getManager().isInVillage(p)) {
						Village vil = VillageManager.getManager().getPlayersVillage(p);
 						if(VillageManager.getManager().villageExists(vil.getVillageName())) {
 							if(vil.isVillageLeader(p)) {
 								VillageManager.getManager().renameVillage(vil.getVillageName(), args[1]);
 								p.sendMessage(prefix + "You have renamed your village to " + args[1]);
 							}
 						}
					}
				}
			}
		}
	}
	return false;
	}
}