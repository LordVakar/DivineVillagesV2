package me.lordvakar.divinevillages.managers;

import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.DivineVillages;
import me.lordvakar.divinevillages.objects.Area;
import me.lordvakar.divinevillages.objects.Village;
import me.lordvakar.divinevillages.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
	
	public void claimArea(Location loc, String playerName) 
	{
		Player player = Util.getPlayer(playerName);
		Location chunk = player.getLocation();
		if(VillageManager.getManager().isInVillage(player)) {
			//if chunk in not claimed
			//if()
			Village vil = VillageManager.getManager().getPlayersVillage(player);
			Area area = new Area(chunk, vil);
			//Config
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
            	//Wilderness
            }
        }
        return null;
    }
    
    public Area getArea(Player player) {
    	Area a = getArea(player.getLocation());
		return a;
    }
}
