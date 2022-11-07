package pl.blackwater.spigotlobby.handlers;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotlobby.data.SpigotServer;
import pl.blackwater.spigotlobby.redis.RedisLobbyAPI;
import pl.blackwater.spigotlobby.redis.packets.PlayerConnectViaBungeePacket;
import pl.blackwater.spigotplugin.redis.client.RedisClient;
import pl.blackwater.spigotplugin.spigot.SpigotInstance;
import pl.blackwater.spigotplugin.spigot.inventory.actions.IAction;
import pl.blackwater.spigotplugin.util.ChatUtil;


@RequiredArgsConstructor
public class ConnectToServerViaInventoryHandler implements IAction {

    private final SpigotInstance instance;
    private final SpigotServer server;

    @Override
    public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType type) {
        if(System.currentTimeMillis() - instance.getLastUpdate() <= 3500L){
            player.closeInventory();

            ChatUtil.sendMessage(player,"&7Trwa laczenie z serwerem docelowym &8(&c" + instance.getInstanceName() + "&8)&7...");

            final PlayerConnectViaBungeePacket packet = new PlayerConnectViaBungeePacket(player.getUniqueId(),server.getName());

            ChatUtil.sendMessage(player,"&7Wysylanie pakietu do bungee aby polaczyc sie z serwerem &8(&c" + instance.getInstanceName() + "&8)&7...");

            RedisLobbyAPI.sendLobbyPacket(packet);
        }
    }
}
