package me.lordvakar.divinevillages.objects;

import java.util.ArrayList;
import java.util.List;

import me.lordvakar.divinevillages.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Citizen {
	
    private static final List<Citizen> Citizens = new ArrayList<Citizen>();
    
    public static Citizen enlistCitizen(OfflinePlayer player) {
        Citizen r = new Citizen(player.getName());
        Citizens.add(r);
        return r;
    }
    
    public static Citizen getCitizen(Player player) {
        return getCitizen(Util.getOfflinePlayer(player));
    }
    
    public static Citizen getCitizen(CommandSender player) {
        if(!Util.isPlayer(player)) return null;
        return getCitizen(Util.getPlayer(player));
    }
    
    public static Citizen getCitizen(String player) {
        if(player == null) return null;
        return getCitizen(Util.getOfflinePlayer(player));
    }
    
    public static Citizen getCitizen(Entity player) {
        if(player == null) return null;
        if(!Util.isPlayer(player)) return null;
        return getCitizen(Util.getPlayer(player));
    }
    
    public static Citizen getCitizen(OfflinePlayer player) {
        for(Citizen r : Citizens) {
            if(r.getName().equalsIgnoreCase(player.getName())) return r;
        }
        
        return enlistCitizen(player);
    }
    
    public static Citizen guessCitizen(String string) {
        for(Citizen r : Citizens) {
            if(r.getName().toLowerCase().startsWith(string.toLowerCase())) return r;
        }
        
        return null;
    }

    public static List<Citizen> getRegisteredCitizens() {
        return new ArrayList<Citizen>(Citizens);
    }
    
    private final String player;
    
    private Citizen(String player) {
        this.player = player;
    }
    
    public String getName() {return this.player;}
    public OfflinePlayer getOfflinePlayer() {return Bukkit.getOfflinePlayer(this.player);}
    public Player getPlayer() {return this.getOfflinePlayer().getPlayer();}
}