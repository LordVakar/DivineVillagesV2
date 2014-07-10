package me.lordvakar.divinevillages.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.DivineVillages;
import me.lordvakar.divinevillages.objects.Area;
import me.lordvakar.divinevillages.objects.Village;
import me.lordvakar.divinevillages.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AreaManager 
{
	public List<Area> areas = new ArrayList<Area>();
	
	String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "DivineVillages> " + ChatColor.RESET + ChatColor.GOLD;
	DivineVillages main = (DivineVillages) DivineVillages.pl;
	private static JavaPlugin plugin = DivineVillages.getJavaPlugin();
	static int areaSize = 16;

	private static AreaManager am = new AreaManager();
	
	public static AreaManager getManager() {
		return am;
	}
	
	public static JavaPlugin getPlugin() {
		return plugin;
	}
	
	public void claimArea(Location loc, String playerName, int areaID) 
	{
		Player player = Util.getPlayer(playerName);
		Location chunk = player.getLocation();
		Area a = getArea(chunk);
    	File folder = new File("plugins/DivineVillages/Areas");
    	File areaFile = new File("plugins/DivineVillages/Areas" + "/" + areaID + ".yml");
    	YamlConfiguration areaConfig = YamlConfiguration.loadConfiguration(areaFile);
		if(VillageManager.getManager().isInVillage(player)) {
			//if chunk in not claimed
			if(a == null) {
				if (!folder.exists()) {
					folder.mkdir();
					try {
						areaFile.createNewFile();
						Village vil = VillageManager.getManager().getPlayersVillage(player);
						Area area = new Area(chunk, vil, areaID);
						//Config
						String path = Integer.toString(areaID) + ".";
						areaConfig.set(path + "areaID", areaID);
						areaConfig.save(areaFile);
						areaConfig.load(areaFile);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
				}
			} else {
				player.sendMessage(prefix + "This area is already claimed!");
			}
		}
	}
	
	public void unclaimArea(Area area) 
	{
		//Config
        areas.remove(getArea(area));
	}
	
    public Area getArea(Area a) {
        for (Area area : areas) {
        	if (area.getX() == a.getX()) {
        		if (area.getZ() == a.getZ()) {
        			return area;
        		}
            } else {
            	return null;
            }
        }
        return null;
    }
    
    public Area getArea(Location loc) {
        for (Area area : areas) {
        	if (area.getX() == loc.getChunk().getX()) {
        		if (area.getZ() == loc.getChunk().getZ()) {
        			return area;
        		}
            } else {
            	return null;
            	//Wilderness class?
            	//null?
            }
        }
        return null;
    }
    
    public Area getArea(Player player) {
    	Area a = getArea(player.getLocation());
		return a;
    }
    
    public void loadAllVillages() {
    	//If this works, then FUCK YEA!
    	File folder = new File("plugins/DivineVillages/Villages");
        if (!folder.exists()) folder.mkdir();
        File[] villages = folder.listFiles();
        for (File f : villages) {
          Village v = loadVillage(f);
          if (v == null) { Util.log(prefix + "Unable to load " + f.getName() + " as a Village!"); }
        }
    }
    
    public Village loadVillage(File f) {
    	if (!f.getName().toLowerCase().endsWith(".yml".toLowerCase())) {
    		return null;
    	} else {
    		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
    		
    		String filename = f.getName();
    		int pos = filename.lastIndexOf(".");
    		String justName = pos > 0 ? filename.substring(0, pos) : filename;
    		
    		String path = justName + ".";
    		String villageName = yml.getString(path + "villageName");
    		String villageDesc = yml.getString(path + "villageDesc");
    		String villageLeader = yml.getString(path + "villageLeader");
    		String villageCreationDate = yml.getString(path + "villageCreationDate");
    		Player leaderPlayerObject = Util.getPlayer(villageLeader);
    		boolean villageOpen = yml.getBoolean(path + "villageOpen");
			Location villageSpawn = deserializeLoc(yml.getString(path + "villageSpawn"));
    		Village v = new Village(villageName, villageDesc, leaderPlayerObject);
    		v.setVillageName(villageName);
    		v.setVillageDesc(villageDesc);
    		v.setVillageLeader(leaderPlayerObject);
    		v.setVillageCreationDate(villageCreationDate);
    		v.setOpen(villageOpen);
    		v.setVillageSpawn(villageSpawn);
    		try {
				yml.save(f);
				yml.load(f);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
    		return v;
    	}
    }
    
    public String serializeLoc(Location l){
        return l.getWorld().getName()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ()+","+l.getPitch()+","+l.getYaw();
    }
    
    public Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]) , Float.parseFloat(st[4]), Float.parseFloat(st[5]));
    }
}
