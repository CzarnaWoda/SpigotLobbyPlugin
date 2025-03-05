# SpigotLobbyPlugin

## Opis projektu
SpigotLobbyPlugin to plugin dla serwerów Minecraft opartych na Spigot, który zapewnia funkcjonalność lobby dla graczy. Pozwala na zarządzanie serwerami, przechowywanie danych użytkowników oraz interakcję poprzez Redis. Plugin umożliwia graczom wybór serwera poprzez GUI, zarządzanie monetami i logowaniem oraz obsługę interakcji poprzez różne systemy.

## Funkcjonalności
- **System Lobby** – umożliwia graczom wybór serwera z poziomu interfejsu GUI.
- **Integracja z Redis** – przechowywanie i zarządzanie użytkownikami oraz komunikacja z innymi serwerami.
- **Przechowywanie danych użytkowników** – informacje o użytkownikach (np. monety, status zalogowania) są przechowywane w Redis.
- **Automatyczna aktualizacja GUI** – dynamiczne odświeżanie interfejsu serwera.
- **Obsługa interakcji** – wykrywanie kliknięć graczy i przekierowanie na odpowiednie serwery.
- **System pakietów Redis** – wysyłanie komunikatów do serwerów lobby.

## Struktura katalogów

- **`bossbar`** – zarządzanie BossBarami w lobby.
- **`config`** – konfiguracja lobby (np. lista serwerów).
- **`data`** – przechowywanie informacji o użytkownikach i serwerach.
- **`handlers`** – obsługa interakcji graczy z lobby (np. wybór serwera, interakcja z przedmiotami).
- **`inventories`** – zarządzanie interfejsem GUI dla lobby.
- **`listeners`** – nasłuchiwanie zdarzeń w grze.
- **`redis`** – obsługa bazy danych Redis i komunikacji między serwerami.
- **`threads`** – zarządzanie wątkami, np. dynamiczne aktualizowanie GUI.
- **`utils`** – narzędzia pomocnicze dla pluginu.

## Kluczowe klasy

### **1. Konfiguracja serwerów lobby**

**LobbyConfiguration.java** – Konfiguracja lobby przechowywana w pliku `config.yml`:
```java
@ConfigName("config.yml")
public interface LobbyConfiguration extends Config {
    default List<SpigotServer> getSpigotServers() {
        return Collections.singletonList(new SpigotServer("creative", new ItemStack(Material.DIAMOND_BLOCK),"&8->> &cCREATIVE 8&<<-",10, 25565));
    }
}
```

### **2. Przechowywanie danych serwera**

**SpigotServer.java** – Model serwera w systemie lobby:
```java
@Data
public class SpigotServer implements Serializable {
    private String name;
    private ItemStack guiItem;
    private String guiItemTitle;
    private int guiPosition;
    private int port;
    public SpigotServer(String name, ItemStack guiItem, String guiItemTitle, int guiPosition, int port) {
        this.name = name;
        this.guiItem = guiItem;
        this.guiItemTitle = guiItemTitle;
        this.guiPosition = guiPosition;
        this.port = port;
    }
}
```

### **3. Przechowywanie danych użytkowników**

**SpigotUser.java** – Model użytkownika przechowywanego w Redis:
```java
@RequiredArgsConstructor
public class SpigotUser {
    private final UUID uuid;
    private boolean logged = false;
    private int coins = 0;
    public void update(boolean async) {
        if(async) {
            RedisStorage.SPIGOT_USERS.putAsync(this.uuid, GsonUtil.toJson(this));
        } else {
            RedisStorage.SPIGOT_USERS.put(this.uuid, GsonUtil.toJson(this));
        }
    }
}
```

### **4. Zarządzanie użytkownikami**

**SpigotUserManagerImpl.java** – Zarządzanie użytkownikami w Redis:
```java
@Override
public void load() {
    RedisStorage.SPIGOT_USERS.forEach((uuid, s) -> {
        final SpigotUser user = GsonUtil.fromJson(s, SpigotUser.class);
        this.users.put(uuid,user);
    });
}
```

### **5. Obsługa kliknięcia w GUI**

**ConnectToServerViaInventoryHandler.java** – Obsługa wyboru serwera:
```java
@Override
public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType type) {
    final PlayerConnectViaBungeePacket packet = new PlayerConnectViaBungeePacket(player.getUniqueId(),server.getName());
    RedisLobbyAPI.sendLobbyPacket(packet);
}
```

### **6. Obsługa interakcji z przedmiotami**

**InteractHandler.java** – Obsługa przedmiotów w lobby:
```java
public void handle(PlayerInteractEvent event, Material material) {
    if (material == Material.COMPASS) {
        Main.getInstance().getLobbyInventory().getGui().openInventory(player);
    }
}
```

### **7. System pakietów Redis**

**LobbyPacketListener.java** – Obsługa pakietów Redis:
```java
@Override
public void onMessage(CharSequence charSequence, String packet) {
    final String[] split = packet.split("@");
    Class<? extends RedisPacket> clzPacket = PacketManager.getPacketClass(Integer.parseInt(split[0]));
    final RedisPacket p = GsonUtil.fromJson(split[1], clzPacket);
    p.handlePacket(packetHandler);
}
```

### **8. Aktualizacja GUI wątkami**

**LobbyInventoryThread.java** – Automatyczna aktualizacja GUI:
```java
this.executorService.scheduleAtFixedRate(() -> {
    for(SpigotServer server : plugin.getLobbyConfiguration().getSpigotServers()) {
        gui.setItem(server.getGuiPosition(), serverItem.build(), new ConnectToServerViaInventoryHandler(instance, server));
    }
}, 5, 1, TimeUnit.SECONDS);
```

## Instalacja i konfiguracja
1. Pobierz plik `.jar` i umieść go w katalogu `plugins` serwera Spigot.
2. Uruchom serwer, aby wygenerować plik konfiguracyjny `config.yml`.
3. Skonfiguruj dostępne serwery w `config.yml`.
4. Restartuj serwer, aby wczytać zmiany.

## Podsumowanie
SpigotLobbyPlugin to kompleksowe rozwiązanie dla lobby serwera Minecraft, umożliwiające interakcję graczy, dynamiczne aktualizowanie interfejsu oraz integrację z Redis do przechowywania danych i zarządzania użytkownikami. Dzięki modularnej architekturze możliwa jest łatwa rozbudowa i dostosowanie pluginu do własnych potrzeb.
