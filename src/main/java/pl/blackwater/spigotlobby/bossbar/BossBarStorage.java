package pl.blackwater.spigotlobby.bossbar;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BossBarStorage {

    public static Map<Player, BossBar> BOSS_BARS =  new HashMap<>();
}
