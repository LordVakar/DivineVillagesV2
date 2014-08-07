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
		Village vil = VillageManager.getManager().getPlayersVillage(player);
    	File folder = new File("plugins/DivineVillages/Areas");
    	File areaFile = new File("plugins/DivineVillages/Areas" + "/" + vil.getVillageName() + ".yml");
    	YamlConfiguration areaConfig = YamlConfiguration.loadConfiguration(areaFile);
		if(VillageManager.getManager().isInVillage(player)) {
			//if chunk in not claimed
			if(a == null) {
				if (!folder.exists()) {
					folder.mkdir();
					try {
						areaFile.createNewFile();
						Area area = new Area(chunk, vil, areaID);
						area.setClaimed(true);
						//Config
						String path = vil.getVillageName() + "." + areaID;
						areaConfig.set(path + "areaID", areaID);
						areaConfig.set(path + "villageOwner", area.getVillageOwner().getVillageName());
						areaConfig.set(path + "villageX", area.getX());
						areaConfig.set(path + "villageZ", area.getZ());
						areaConfig.set(path + "world", area.getWorldName());
						areaConfig.set(path + "claimed", area.isClaimed());
						areaConfig.save(areaFile);
						areaConfig.load(areaFile);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
				} else if (!areaFile.exists()) {
					try {
						areaFile.createNewFile();
						Area area = new Area(chunk, vil, areaID);
						area.setClaimed(true);
						//Config
						String path = vil.getVillageName() + "." + areaID;
						areaConfig.set(path + "areaID", areaID);
						areaConfig.set(path + "villageOwner", area.getVillageOwner().getVillageName());
						areaConfig.set(path + "villageX", area.getX());
						areaConfig.set(path + "villageZ", area.getZ());
						areaConfig.set(path + "world", area.getWorldName());
						areaConfig.set(path + "claimed", area.isClaimed());
						areaConfig.save(areaFile);
						areaConfig.load(areaFile);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
				} 	else if(areaFile.exists()) {
				}
			} else {
				player.sendMessage(prefix + "This area is already claimed!");
			}
		}
	}
	
	public void unclaimArea(Area area) 
	{
		//Config
    	File areaFile = new File("plugins/DivineVillages/Areas" + "/" + area.getVillageOwner().getVillageName() + ".yml");
    	YamlConfiguration areaConfig = YamlConfiguration.loadConfiguration(areaFile);
		String path = area.getVillageOwner().getVillageName() + "." + area.getAreaID();
		areaConfig.set(path, null);
        areas.remove(getArea(area));
        try {
			areaConfig.save(areaFile);
	        areaConfig.load(areaFile);
	        areaFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
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
    
    public Area getArea(int areaID) {
        for (Area area : areas) {
        	if(area.getAreaID() == areaID) {
        		return area;
        	} else {
        		return null;
        	}
        }
    	return null;
    }
    
    public void loadAllAreas() {
    	//If this works, then FUCK YEA!
    	File folder = new File("plugins/DivineVillages/Areas");
        if (!folder.exists()) folder.mkdir();
        File[] villages = folder.listFiles();
        for (File f : villages) {
          Area v = loadArea(f);
          if (v == null) { Util.log(prefix + "Unable to load " + f.getName() + " as a Village!"); }
        }
    }
    
    public Area loadArea(File f) {
    	if (!f.getName().toLowerCase().endsWith(".yml".toLowerCase())) {
    		return null;
    	} else {
    		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
    		String filename = f.getName();
    		int pos = filename.lastIndexOf(".");
    		String justName = pos > 0 ? filename.substring(0, pos) : filename;
    		int areaID = 0; //INITIZIALIZE TODO:
            for (Area area : areas) {
            	if(area.getVillageOwner().getVillageName().equals(justName)) {
            		areaID = area.getAreaID();
            	}
            }
    		String path = justName + "." + areaID;
			int configAreaID = yml.getInt(path + "areaID");
			String villageOwner = yml.getString(path + "villageOwner");
			int villageX = yml.getInt(path + "villageX");
			int villageZ = yml.getInt(path + "villageZ");
			String world = yml.getString(path + "world");
			boolean claimed = yml.getBoolean(path + "claimed");
			Village vil = VillageManager.getManager().getPlayersVillage(Util.getPlayer(villageOwner));
			Location loc = new Location(Bukkit.getWorld(world), villageX, 64, villageZ);
			Area area = new Area(loc, vil, configAreaID);
			area.setClaimed(claimed);
    		try {
				yml.save(f);
				yml.load(f);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
    		return area;
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
