package pl.blackwater.spigotlobby.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotlobby.data.SpigotServer;
import pl.mikigal.config.Config;
import pl.mikigal.config.annotation.ConfigName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ConfigName("config.yml")
public interface LobbyConfiguration extends Config {

    default List<SpigotServer> getSpigotServers(){
        return Collections.singletonList(new SpigotServer("creative", new ItemStack(Material.DIAMOND_BLOCK),"&8->> &cCREATIVE &8<<-",10, 25565));
    }

}
