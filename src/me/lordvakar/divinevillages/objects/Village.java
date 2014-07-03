package me.lordvakar.divinevillages.objects;

import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.managers.VillageManager;

import org.bukkit.entity.Player;

public class Village 
{
	public List<String> villageMembers = new ArrayList<String>();
	private String villageName;
	private String villageDesc;
	private Player villageLeader;
	
	public Village(String villageName, String villageDesc, Player villageLeader) {
		this.villageName = villageName;
		this.villageDesc = villageDesc;
		this.villageLeader = villageLeader;
		
		VillageManager.villages.add(this);
		villageMembers.add(villageLeader.getName());
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
}
