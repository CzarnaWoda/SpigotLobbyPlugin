package pl.blackwater.spigotlobby.threads;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.config.LobbyConfiguration;
import pl.blackwater.spigotlobby.data.SpigotServer;
import pl.blackwater.spigotlobby.handlers.ConnectToServerViaInventoryHandler;
import pl.blackwater.spigotlobby.inventories.LobbyInventory;
import pl.blackwater.spigotlobby.utils.PaneUtil;
import pl.blackwater.spigotplugin.SpigotPlugin;
import pl.blackwater.spigotplugin.spigot.SpigotInstance;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LobbyInventoryThread {

    private final ScheduledExecutorService executorService;


    public LobbyInventoryThread(final Main plugin){
        this.executorService = Executors.newSingleThreadScheduledExecutor();

        this.executorService.scheduleAtFixedRate(() -> {
            final InventoryGUI gui = plugin.getLobbyInventory().getGui();

            final ItemStack pane = PaneUtil.getRandomPaneAsItemStack();
            for(int p : LobbyInventory.PANES){
                gui.setItem(p,pane,null);
            }

            for(SpigotServer server : plugin.getLobbyConfiguration().getSpigotServers()){
                final ItemBuilder serverItem = new ItemBuilder(server.getGuiItem().getType()).setTitle(server.getGuiItemTitle());
                if(SpigotPlugin.getPlugin().getSpigotInstanceStorage().getInstances().get(server.getName()) != null){
                    final SpigotInstance instance = SpigotPlugin.getPlugin().getSpigotInstanceStorage().getInstances().get(server.getName());

                    serverItem.addLore("");
                    serverItem.addLore("  &8->> &7ONLINE: " + instance.getOnline());
                    serverItem.addLore("");

                    gui.setItem(server.getGuiPosition(),serverItem.build(),new ConnectToServerViaInventoryHandler(instance,server));
                }
            }

        },5, 1, TimeUnit.SECONDS);
    }
}
