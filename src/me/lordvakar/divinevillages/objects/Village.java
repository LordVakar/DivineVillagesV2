package me.lordvakar.divinevillages.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.lordvakar.divinevillages.managers.VillageManager;
import me.lordvakar.divinevillages.util.Util;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Village 
{
	public static List<UUID> villageCitizens = new ArrayList<UUID>();
	public static List<UUID> invitedPlayers = new ArrayList<UUID>();
	private String villageName;
	private String villageDesc;
	private String villageCreationDate;
	private Player villageLeader;
	private Location villageSpawn;
	private boolean open;
	
	public Village(String villageName, String villageDesc, Player villageLeader) {
		this.villageName = villageName;
		this.villageDesc = villageDesc;
		this.villageLeader = villageLeader;
		this.villageSpawn = villageLeader.getLocation(); //To Prevent NPEs
		this.setOpen(true);
		
		VillageManager.villages.add(this);
		villageCitizens.add(villageLeader.getUniqueId());
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

	public List<UUID> getVillageCitizens() {
		return villageCitizens;
	}
	
	public void makeCitizen(Player player) {
		villageCitizens.add(player.getUniqueId());
		if(invitedPlayers.contains(player.getUniqueId())) {
			invitedPlayers.remove(player.getUniqueId());
		}
	}
	
	public void addInvited(Player player) {
		invitedPlayers.add(player.getUniqueId());
	}
	
	public List<UUID> getInvitedPlayers() {
		return invitedPlayers;
	}

	public boolean isVillageCitizen(Player player) {
		if(villageCitizens.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}
	
    public UUID inferCitizen(String uuid) {
        for(UUID r: villageCitizens) {
        	Player p = Util.getPlayerByUuid(r);
            if(p.getUniqueId().toString().startsWith(uuid)) return r;
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

	public String getVillageCreationDate() {
		return villageCreationDate;
	}

	public void setVillageCreationDate(String villageCreationDate) {
		this.villageCreationDate = villageCreationDate;
	}

	public Location getVillageSpawn() {
		return villageSpawn;
	}

	public void setVillageSpawn(Location villageSpawn) {
		this.villageSpawn = villageSpawn;
	}
	
	public void messageAllCitizens(String message) {
		for (UUID allP : villageCitizens) {
			Util.getPlayer(allP).sendMessage(message);
		}
	}
}
