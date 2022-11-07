package pl.blackwater.spigotlobby.redis.packets;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.bossbar.BossBarStorage;
import pl.blackwater.spigotlobby.data.SpigotUser;
import pl.blackwater.spigotplugin.packets.RedisPacket;
import pl.blackwater.spigotplugin.packets.handler.PacketHandler;
import pl.blackwater.spigotplugin.spigot.CustomItemStorage;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerLoggedPacket extends RedisPacket {

    private final UUID uuid;
    private final boolean status;

    @Override
    public void handlePacket(PacketHandler handler) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(),() -> {
            if(status) {
                final Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
                    player.getInventory().setHeldItemSlot(4);
                    player.getInventory().setItem(4, CustomItemStorage.LOBBY_ITEM);

                    final SpigotUser spigotUser = Main.getInstance().getSpigotUserManager().getUser(player.getUniqueId());
                    if (spigotUser != null) {
                        spigotUser.setLogged(true);
                        //TODO not update necessary
                    }
                    final BossBar bossBar = BossBarStorage.BOSS_BARS.get(player);

                    if(bossBar != null){
                        bossBar.setTitle(ChatUtil.fixColor("&aWYBIERZ SERWER ZA POMOCA KOMPASU !"));
                        bossBar.setColor(BarColor.GREEN);
                    }
                }
            }
        },80L);


    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isStatus() {
        return status;
    }
}
