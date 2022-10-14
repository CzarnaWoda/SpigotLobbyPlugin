package pl.blackwater.spigotlobby.utils;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.ItemBuilder;
import pl.blackwater.spigotplugin.util.RandomUtil;

import java.util.Arrays;
import java.util.List;

public class PaneUtil {

    public static List<Material> PANE_UTIL = Arrays.asList(Material.BLACK_STAINED_GLASS_PANE,Material.BLUE_STAINED_GLASS_PANE,Material.BROWN_STAINED_GLASS_PANE,Material.CYAN_STAINED_GLASS_PANE,Material.GREEN_STAINED_GLASS_PANE,Material.LIGHT_BLUE_STAINED_GLASS_PANE,Material.LIME_STAINED_GLASS_PANE,Material.MAGENTA_STAINED_GLASS_PANE,Material.ORANGE_STAINED_GLASS_PANE,Material.PINK_STAINED_GLASS_PANE,Material.PURPLE_STAINED_GLASS_PANE,Material.ORANGE_STAINED_GLASS_PANE,Material.YELLOW_STAINED_GLASS_PANE);


    public static Material getRandomPane(){
        final int randomInteger = RandomUtil.getRandInt(0,PANE_UTIL.size()-1);

        return PANE_UTIL.get(randomInteger);
    }
    public static ItemStack getRandomPaneAsItemStack(){
        final int randomInteger = RandomUtil.getRandInt(0,PANE_UTIL.size()-1);

        return new ItemBuilder(PANE_UTIL.get(randomInteger)).setTitle("&7#").addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,1).addFlag(ItemFlag.HIDE_ENCHANTS).build();
    }
}
