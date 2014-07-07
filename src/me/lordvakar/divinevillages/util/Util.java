package me.lordvakar.divinevillages.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Util 
{
    public static boolean isPlayer(Object o) {
        return o instanceof Player;
    }
    
    public static Player getPlayer(Object o) {
        return (Player) o;
    }
    
    public static OfflinePlayer getOfflinePlayer(Player player) {
        return Bukkit.getOfflinePlayer(player.getName());
    }
    
    public static OfflinePlayer getOfflinePlayer(String player) {
        return Bukkit.getOfflinePlayer(player);
    }
}
