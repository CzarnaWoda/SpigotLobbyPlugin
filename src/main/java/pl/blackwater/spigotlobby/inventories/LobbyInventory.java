package pl.blackwater.spigotlobby.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class LobbyInventory {



    public static int[] PANES = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44};

    private final InventoryGUI gui;

    private final Main plugin;
    /**
    @param plugin provide instance of Main class
    */
    public LobbyInventory(Main plugin){

        this.plugin = plugin;
        this.gui = new InventoryGUI(ChatUtil.fixColor("&8->> &6&nMENU WYBORU SERWEROW&8 <<-"),5);
        gui.setFinalInventory(true);
    }

    public void buildMenu(){

        for(int p : PANES){
            final ItemBuilder a = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
            gui.setItem(p, a.build(),null);
        }


    }

    /**
     * @param player provide player to open inventory
     */

    public void openMenu(Player player){
        gui.openInventory(player);
    }


    public InventoryGUI getGui(){
        return gui;
    }
}
