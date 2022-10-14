package pl.blackwater.spigotlobby.data;

import java.util.Map;
import java.util.UUID;

public interface SpigotUserManager {



    Map<UUID, SpigotUser> getUsers();

    void load();

    SpigotUser getUser(UUID uuid);

    void createUser(UUID uuid);
}


