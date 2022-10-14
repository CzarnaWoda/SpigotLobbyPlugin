package pl.blackwater.spigotlobby.redis;

import pl.blackwater.redisson.api.RTopic;
import pl.blackwater.redisson.api.listener.MessageListener;
import pl.blackwater.spigotplugin.packets.PacketManager;
import pl.blackwater.spigotplugin.packets.RedisPacket;
import pl.blackwater.spigotplugin.packets.handler.PacketHandler;
import pl.blackwater.spigotplugin.packets.handler.PacketHandlerImpl;
import pl.blackwater.spigotplugin.redis.enums.ChannelType;
import pl.blackwater.spigotplugin.redis.listeners.api.RedisListener;
import pl.blackwater.spigotplugin.util.GsonUtil;

public class LobbyPacketListener<T extends String> extends RedisListener<T> {


    final PacketHandler packetHandler = new PacketHandlerImpl();

    public LobbyPacketListener(ChannelType type, RTopic rTopic) {
        super(type, rTopic);
        getTopic().addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String packet) {
                final String[] split = packet.split("@");
                Class<? extends RedisPacket> clzPacket = PacketManager.getPacketClass(Integer.parseInt(split[0]));
                if (clzPacket == null) return;
                final RedisPacket p = GsonUtil.fromJson(split[1], clzPacket);
                p.handlePacket(packetHandler);
            }
        });
    }

    @Override
    public void sendMessage(T message) {
        getTopic().publish(message);
    }
}
