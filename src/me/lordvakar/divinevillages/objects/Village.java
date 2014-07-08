package me.lordvakar.divinevillages.objects;

import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.managers.VillageManager;
import me.lordvakar.divinevillages.util.Util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Village 
{
	public static List<String> villageCitizens = new ArrayList<String>();
	private String villageName;
	private String villageDesc;
	private Player villageLeader;
	private boolean open;
	
	public Village(String villageName, String villageDesc, Player villageLeader) {
		this.villageName = villageName;
		this.villageDesc = villageDesc;
		this.villageLeader = villageLeader;
		this.setOpen(true);
		
		VillageManager.villages.add(this);
		villageCitizens.add(villageLeader.getName());
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getVillageDesc() {
		return villageDesc;
	}

	public void setVillageDesc(String villageDesc) {
		this.villageDesc = villageDesc;
	}

	public Player getVillageLeader() {
		return villageLeader;
	}

	public void setVillageLeader(Player villageLeader) {
		this.villageLeader = villageLeader;
	}
	
	public boolean isVillageLeader(Player player) {
		if(player.equals(villageLeader)) {
			return true;
		} else {
			return false;
		}
	}
	
/*    public static Citizen guessCitizen(String string) {
        for(Citizen r : Citizens) {
            if(r.getName().toLowerCase().startsWith(string.toLowerCase())) return r;
        }
        
        return null;
    }*/

	public List<String> getVillageCitizens() {
		return villageCitizens;
	}
	
	public boolean isVillageCitizen(Player player) {
		if(villageCitizens.contains(player.getName())) {
			return true;
		}
		return false;
	}
	
    public String inferCitizen(String string) {
        for(String r: villageCitizens) {
        	Player p = Util.getPlayer(r);
            if(p.getName().toLowerCase().startsWith(string.toLowerCase())) return r;
        }   
        return null;
    }
    
    public String getCitizen(Player player) {
        return getCitizen(Util.getPlayer(player));
    }
    
    public String getCitizen(CommandSender player) {
        if(!Util.isPlayer(player)) return null;
        return getCitizen(Util.getPlayer(player));
    }
    
    public String getCitizen(String player) {
        if(player == null) return null;
        return getCitizen(Util.getPlayer(player));
    }
    
    public String getCitizen(Entity player) {
        if(player == null) return null;
        if(!Util.isPlayer(player)) return null;
        return getCitizen(Util.getPlayer(player));
    }

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
