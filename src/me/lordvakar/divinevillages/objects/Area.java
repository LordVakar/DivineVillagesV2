package me.lordvakar.divinevillages.objects;

import me.lordvakar.divinevillages.managers.AreaManager;

import org.bukkit.Location;

public class Area 
{
	private int areaID;
	private int x;
	private int z;
	private Village villageOwner;
	private String worldName;
	private boolean claimed = false;
	public static int areaSize = 16;
	//public Date claimDate;

	public Area(Location loc, Village villageOwner, int areaID) {
		this.x = loc.getChunk().getX();
		this.z = loc.getChunk().getZ();
		this.setVillageOwner(villageOwner);
		this.worldName = loc.getWorld().getName();
		this.claimed = true;
		this.areaID = areaID;
		
		AreaManager.getManager().areas.add(this);
	}
	
	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public Village getVillageOwner() {
		return villageOwner;
	}

	public void setVillageOwner(Village villageOwner) {
		this.villageOwner = villageOwner;
	}
	
	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	
	public static int getAreaSize() {
		return areaSize;
	}

	public static void setAreaSize(int areaSize) {
		Area.areaSize = areaSize;
	}

	public boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}
}
