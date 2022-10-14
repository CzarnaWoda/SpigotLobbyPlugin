package pl.blackwater.spigotlobby.data;

import pl.blackwater.spigotlobby.Main;
import pl.blackwater.spigotlobby.redis.RedisStorage;
import pl.blackwater.spigotplugin.SpigotPlugin;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class SpigotUserManagerImpl implements SpigotUserManager {

    private final Map<UUID, SpigotUser> users;
    private final Main plugin;

    public SpigotUserManagerImpl(Main plugin){

        this.plugin = plugin;
        this.users = new HashMap<>();

    }

    @Override
    public Map<UUID, SpigotUser> getUsers() {
        return users;
    }

    @Override
    public void load() {
        final AtomicInteger ai = new AtomicInteger(0);

        RedisStorage.SPIGOT_USERS.forEach((uuid, s) -> {
            final SpigotUser user = GsonUtil.fromJson(s, SpigotUser.class);

            this.users.put(uuid,user);

            ai.getAndIncrement();

        });

        plugin.getLogger().log(Level.INFO,"[SPIGOT_USERS] RMap loaded " + ai.get() + " spigot_users from database !");
    }

    @Override
    public SpigotUser getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void createUser(UUID uuid) {
        final SpigotUser spigotUser = new SpigotUser(uuid);

        users.put(uuid,spigotUser);

        spigotUser.update(true);
    }
}
