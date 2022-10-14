package pl.blackwater.spigotlobby;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.blackwater.spigotlobby.config.LobbyConfiguration;
import pl.blackwater.spigotlobby.data.SpigotUserManager;
import pl.blackwater.spigotlobby.data.SpigotUserManagerImpl;
import pl.blackwater.spigotlobby.inventories.LobbyInventory;
import pl.blackwater.spigotlobby.handlers.InteractHandler;
import pl.blackwater.spigotlobby.listeners.JoinQuitListener;
import pl.blackwater.spigotlobby.listeners.PlayerInteractListener;
import pl.blackwater.spigotlobby.listeners.PlayerLobbyListener;
import pl.blackwater.spigotlobby.redis.LobbyPacketListener;
import pl.blackwater.spigotlobby.redis.RedisStorage;
import pl.blackwater.spigotlobby.redis.packets.PlayerConnectViaBungeePacket;
import pl.blackwater.spigotlobby.redis.packets.PlayerLoggedPacket;
import pl.blackwater.spigotlobby.threads.LobbyInventoryThread;
import pl.blackwater.spigotplugin.packets.PacketManager;
import pl.blackwater.spigotplugin.redis.RedisManager;
import pl.blackwater.spigotplugin.redis.enums.ChannelType;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.style.CommentStyle;
import pl.mikigal.config.style.NameStyle;

public class Main extends JavaPlugin {


    private SpigotUserManager spigotUserManager;
    private final PluginManager pm = Bukkit.getPluginManager();
    private LobbyInventory lobbyInventory;
    private LobbyConfiguration lobbyConfiguration;
    private LobbyInventoryThread lobbyInventoryThread;
    private InteractHandler interactHandler;

    private static Main instance;
    @Override
    public void onEnable() {

        //START LOBBY PLUGIN
        instance = this;

        //SPIGOT_USERS

        spigotUserManager = new SpigotUserManagerImpl(this);

        spigotUserManager.load();


        //REGISTER REDIS LISTENER
        RedisManager.registerListener(new LobbyPacketListener(ChannelType.PACKET_TO_LOBBY, RedisStorage.LOBBY_TOPIC));

        PacketManager.registerPacket(20, PlayerLoggedPacket.class);

        PacketManager.registerPacket(1006, PlayerConnectViaBungeePacket.class);

        //Listeners
        registerListeners(this,new JoinQuitListener(this), new PlayerInteractListener(this),new PlayerLobbyListener());

        //Config initialize
        lobbyConfiguration = ConfigAPI.init(LobbyConfiguration.class,
                NameStyle.UNDERSCORE,
                CommentStyle.ABOVE_CONTENT,
                true,
                this);

        //INVENTORY
        lobbyInventory = new LobbyInventory(this);
        lobbyInventory.buildMenu();

        this.lobbyInventoryThread = new LobbyInventoryThread(this);
        this.interactHandler = new InteractHandler();


    }

    @Override
    public void onDisable() {
    }

    public SpigotUserManager getSpigotUserManager() {
        return spigotUserManager;
    }


    public static Main getInstance() {
        return instance;
    }
    private void registerListeners(Plugin plugin, Listener... listeners){
        for(Listener listener : listeners){
            pm.registerEvents(listener,plugin);
        }
    }

    public LobbyInventory getLobbyInventory() {
        return lobbyInventory;
    }

    public LobbyConfiguration getLobbyConfiguration() {
        return lobbyConfiguration;
    }

    public InteractHandler getInteractHandler() {
        return interactHandler;
    }
}
