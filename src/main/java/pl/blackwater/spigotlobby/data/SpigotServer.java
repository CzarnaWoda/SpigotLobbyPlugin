package pl.blackwater.spigotlobby.data;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Data
public class SpigotServer implements Serializable {

    private String name;
    private ItemStack guiItem;
    private String guiItemTitle;
    private int guiPosition;
    private int port;

    public SpigotServer(String name, ItemStack guiItem, String guiItemTitle, int guiPosition, int port){
        this.name = name;
        this.guiItem = guiItem;
        this.guiItemTitle = guiItemTitle;
        this.guiPosition = guiPosition;
        this.port = port;
    }

    public SpigotServer(){}


}
