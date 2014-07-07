package me.lordvakar.divinevillages.objects;

import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.managers.VillageManager;

import org.bukkit.entity.Player;

public class Village 
{
	public List<String> villageCitizens = new ArrayList<String>();
	private String villageName;
	private String villageDesc;
	private Player villageLeader;
	
	public Village(String villageName, String villageDesc, Player villageLeader) {
		this.villageName = villageName;
		this.villageDesc = villageDesc;
		this.villageLeader = villageLeader;
		
		VillageManager.villages.add(this);
		villageCitizens.add(villageLeader.getName());
		//Citizen.enlistCitizen(villageLeader);
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

/*    public static List<String> getRegisteredCitizens() {
        return villageCitizenst<String>();
    }*/
}
