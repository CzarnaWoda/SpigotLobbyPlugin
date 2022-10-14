package pl.blackwater.spigotlobby.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerLobbyListener implements Listener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        e.setCancelled(true);
    }
}
