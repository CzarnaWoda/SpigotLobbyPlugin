package pl.blackwater.spigotlobby.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotlobby.Main;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemInteract(final PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand() != null){
            final ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();

            plugin.getInteractHandler().handle(e, itemStack.getType());


        }
    }
}
