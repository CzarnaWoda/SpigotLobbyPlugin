package pl.blackwater.spigotlobby.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.data.SpigotUser;
import pl.blackwater.spigotplugin.SpigotPlugin;

import java.util.List;

@RequiredArgsConstructor
public class JoinQuitListener implements Listener {

    private final Main plugin;


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);

        final Player player = e.getPlayer();

        if(plugin.getSpigotUserManager().getUser(player.getUniqueId()) == null){
            plugin.getSpigotUserManager().createUser(player.getUniqueId());
        }

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);

        final SpigotUser spigotUser = plugin.getSpigotUserManager().getUser(e.getPlayer().getUniqueId());

        spigotUser.setLogged(false);


    }
}
