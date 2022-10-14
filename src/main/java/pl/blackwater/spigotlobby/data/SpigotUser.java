package pl.blackwater.spigotlobby.data;

import lombok.RequiredArgsConstructor;
import pl.blackwater.spigotlobby.redis.RedisStorage;
import pl.blackwater.spigotplugin.redis.channels.RedisChannel;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.UUID;

@RequiredArgsConstructor
public class SpigotUser {

    private final UUID uuid;
    private boolean logged = false;
    private int coins = 0;


    public UUID getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isLogged() {
        return logged;
    }


    /**
     *
     * @param async async put to if you are going to put not async you have to have good reason for that <!!?>@Warning async is better</!!?>
     */
    public void update(boolean async){
        if(async) {
            RedisStorage.SPIGOT_USERS.putAsync(this.uuid, GsonUtil.toJson(this));
        } else {
            RedisStorage.SPIGOT_USERS.put(this.uuid, GsonUtil.toJson(this));
        }
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
