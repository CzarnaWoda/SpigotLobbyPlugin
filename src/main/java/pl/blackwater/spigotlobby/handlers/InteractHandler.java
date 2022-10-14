package pl.blackwater.spigotlobby.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.inventories.LobbyInventory;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InteractHandler {

    private Map<UUID,Long> interactTimer;

    public InteractHandler(){
        interactTimer = new HashMap<>();
    }

    /**
     * @param event InteractEvent which one interact with handler
     * @param material Material with player interact (in hand)
     */
    public void handle(PlayerInteractEvent event, Material material) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        if (interactTimer.get(player.getUniqueId()) != null && interactTimer.get(player.getUniqueId()) > System.currentTimeMillis()) {
            return;
        }
        if (Main.getInstance().getSpigotUserManager().getUser(player.getUniqueId()).isLogged()) {
            switch (material) {
                case COMPASS:
                    Main.getInstance().getLobbyInventory().getGui().openInventory(player);
                    interactTimer.put(player.getUniqueId(), System.currentTimeMillis() + 2500L);
                    break;
                case AIR:
                    interactTimer.put(player.getUniqueId(), System.currentTimeMillis() + 2500L);
                    break;
                default:
                    ChatUtil.sendMessage(player, "&4Blad: &cNie rozpoznano interakcji !");
                    break;
            }
        }else{
            ChatUtil.sendMessage(player,"&4Blad: &cJestes nie zalogowany lub musisz odczekac 4 sekundy po zalogowaniu aby wybrac serwer");
        }
    }
}
