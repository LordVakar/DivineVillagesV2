package me.lordvakar.divinevillages.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.DivineVillages;
import me.lordvakar.divinevillages.objects.Village;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VillageManager 
{
	public static List<Village> villages = new ArrayList<Village>();
	String prefix = ChatColor.GOLD + "" + ChatColor.BOLD + "DivineVillages> ";
	DivineVillages main = (DivineVillages) DivineVillages.pl;
	private JavaPlugin plugin = DivineVillages.getJavaPlugin();

	private static VillageManager am = new VillageManager();
	
	public static VillageManager getManager() {
		return am;
	}
	
    public Village getVillage(String villageName) {
        for (Village village : villages) {
        	if (village.getVillageName().equals(villageName)) {
                return village;
            }
        }
        return null;
    }
    
    public void createVillage(String villageName, String villageDesc, Player villageLeader) {
    	
    	Village vil = new Village(villageName, villageDesc, villageLeader);
    	
		FileConfiguration fc = DivineVillages.villageConfig;
		
		fc.set("Villages." + villageName, null);
		String path = "Villages." + villageName + ".";
		fc.set(path + "villageDesc", villageDesc);
		fc.set(path + "villageLeader", villageLeader.getName());
		try {
			fc.save(DivineVillages.villageFile);
			fc.load(DivineVillages.villageFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
    }
    
    public void removeVillage(String villageName) {
        FileConfiguration fc = DivineVillages.villageConfig;
        String path = "Villages." + villageName;
        fc.set(path, null);
        villages.remove(getVillage(villageName));
		try {
			fc.save(DivineVillages.villageFile);
			fc.load(DivineVillages.villageFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
    }
}
