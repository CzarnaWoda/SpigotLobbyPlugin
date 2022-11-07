package pl.blackwater.spigotlobby.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.bossbar.BossBarStorage;
import pl.blackwater.spigotlobby.data.SpigotUser;
import pl.blackwater.spigotplugin.SpigotPlugin;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.List;

@RequiredArgsConstructor
public class JoinQuitListener implements Listener {

    private final Main plugin;


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);

        final Player player = e.getPlayer();

        final BossBar bossBar = Bukkit.createBossBar(ChatUtil.fixColor("&cPOCZEKAJ NA POTWIERDZENIE ZALOGOWANIA LUB ZALOGUJ SIE!"), BarColor.RED, BarStyle.SOLID);
        bossBar.setProgress(1);

        BossBarStorage.BOSS_BARS.put(player,bossBar);
        bossBar.addPlayer(player);

        if(plugin.getSpigotUserManager().getUser(player.getUniqueId()) == null){
            plugin.getSpigotUserManager().createUser(player.getUniqueId());
        }

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);

        final SpigotUser spigotUser = plugin.getSpigotUserManager().getUser(e.getPlayer().getUniqueId());

        spigotUser.setLogged(false);

        if(BossBarStorage.BOSS_BARS.get(e.getPlayer()) != null){
            final BossBar bossBar = BossBarStorage.BOSS_BARS.get(e.getPlayer());
            bossBar.removePlayer(e.getPlayer());

            BossBarStorage.BOSS_BARS.remove(e.getPlayer());
        }


    }
}
