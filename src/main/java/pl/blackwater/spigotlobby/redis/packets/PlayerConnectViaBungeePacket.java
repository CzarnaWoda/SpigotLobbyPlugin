package pl.blackwater.spigotlobby.redis.packets;

import lombok.RequiredArgsConstructor;
import pl.blackwater.spigotplugin.packets.RedisPacket;
import pl.blackwater.spigotplugin.packets.handler.PacketHandler;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerConnectViaBungeePacket extends RedisPacket {

    private final UUID targetUUID;
    private final String serverName;

    @Override
    public void handlePacket(PacketHandler packetHandler) {
        //TODO remove
        System.out.println("Sending player " + targetUUID + " to " + serverName);
    }
}
