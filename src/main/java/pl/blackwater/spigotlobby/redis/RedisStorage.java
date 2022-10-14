package pl.blackwater.spigotlobby.redis;

import pl.blackwater.redisson.api.RMap;
import pl.blackwater.redisson.api.RTopic;
import pl.blackwater.spigotplugin.redis.RedisManager;

import java.util.UUID;

public class RedisStorage {

    public static RMap<UUID,String> SPIGOT_USERS;
    public static RTopic LOBBY_TOPIC;

    static {
        SPIGOT_USERS = RedisManager.getRedisConnection().getMap("SPIGOT_USERS");
        LOBBY_TOPIC = RedisManager.getRedisConnection().getTopic("LobbyTopic");
    }

}
