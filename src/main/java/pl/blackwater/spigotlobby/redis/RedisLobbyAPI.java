package pl.blackwater.spigotlobby.redis;

import pl.blackwater.spigotplugin.packets.PacketManager;
import pl.blackwater.spigotplugin.packets.RedisPacket;
import pl.blackwater.spigotplugin.redis.RedisManager;
import pl.blackwater.spigotplugin.redis.enums.ChannelType;
import pl.blackwater.spigotplugin.util.GsonUtil;

public class RedisLobbyAPI {

    public static void sendLobbyPacket(RedisPacket packet)
    {
        final int packetID = PacketManager.getPacketID(packet.getClass());
        RedisManager.getRedisListener(ChannelType.PACKET_TO_LOBBY).sendMessage(packetID + "@" + GsonUtil.toJson(packet));
    }
}
