package me.lordvakar.divinevillages.listeners;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.HashMap;

public class CrateClick implements Listener {

    HashMap<Location, String> cratelocations = new HashMap<Location, String>();


    @EventHandler
    public void onCrateClick(PlayerInteractEvent e) {
    	Player p = e.getPlayer();
    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		Block b = e.getClickedBlock();
    		if (b.getType() == Material.PISTON_BASE && b.getData() == (byte) 6) {
    			if(cratelocations.get(b) == null) {
    				cratelocations.put(b.getLocation(), p.getName());
    				Firework fw = p.getWorld().spawn(p.getEyeLocation(), Firework.class);
    				FireworkMeta data = fw.getFireworkMeta();
    				data.addEffects(FireworkEffect.builder().withColor(Color.BLUE, Color.AQUA, Color.NAVY).with(FireworkEffect.Type.BALL).build());
    				data.setPower(1);
    				fw.setFireworkMeta(data);
    				p.sendMessage("You used a random crate!");
                        } else p.sendMessage("§cYou have already used this crate!");
                    }
}
    }
}