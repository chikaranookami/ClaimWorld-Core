package claimworld.net.supporter;

import claimworld.net.supporter.commands.*;
import claimworld.net.supporter.events.*;
import claimworld.net.supporter.timers.AutoMessageTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalTime;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getScheduler;

public final class Supporter extends JavaPlugin implements Listener {

    private static Supporter plugin;
    public static Supporter getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        PluginManager pluginManager = getServer().getPluginManager();

        //events
        pluginManager.registerEvents(new PlayerQuitEvent(), this);
        pluginManager.registerEvents(new RaidStopEvent(), this);
        pluginManager.registerEvents(new LightningStrikeEvent(), this);
        pluginManager.registerEvents(new PlayerAdvancementDoneEvent(), this);
        pluginManager.registerEvents(new PlayerRespawnEvent(), this);
        pluginManager.registerEvents(new PlayerLoginEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
        pluginManager.registerEvents(new EntityDeathEvent(), this);
        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new TradeSelectEvent(), this);
        pluginManager.registerEvents(new SignChangeEvent(), this);
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEntityEvent(), this);
        pluginManager.registerEvents(new EntityDamageEvent(), this);
        pluginManager.registerEvents(new CreatureSpawnEvent(), this);
        pluginManager.registerEvents(new HangingPlaceEvent(), this);
        pluginManager.registerEvents(new PlayerItemConsumeEvent(), this);
        pluginManager.registerEvents(new InventoryClickEvent(), this);
        pluginManager.registerEvents(new InventoryCloseEvent(), this);
        pluginManager.registerEvents(new PlayerTeleportEvent(), this);
        pluginManager.registerEvents(new PlayerResourcePackStatusEvent(), this);
        pluginManager.registerEvents(new EntityDamagedByEntityEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEvent(), this);
        pluginManager.registerEvents(new PlayerFishEvent(), this);
        pluginManager.registerEvents(new CraftItemEvent(), this);
        pluginManager.registerEvents(new RaidFinishEvent(), this);
        pluginManager.registerEvents(new PlayerItemBreakEvent(), this);
        pluginManager.registerEvents(new PlayerBedLeaveEvent(), this);
        pluginManager.registerEvents(new ServerListPingEvent(), this);
        pluginManager.registerEvents(new PlayerSwapHandItemsEvent(), this);
        pluginManager.registerEvents(new PrepareAnvilEvent(), this);
        pluginManager.registerEvents(new WeatherChangeEvent(), this);
        pluginManager.registerEvents(new PlayerTakeLecternBookEvent(), this);
        pluginManager.registerEvents(new PlayerRiptideEvent(), this);
        pluginManager.registerEvents(new PlayerShearEntityEvent(), this);
        pluginManager.registerEvents(new BlockPlaceEvent(), this);

        //commands
        //new Protection();
        new Mod();
        new ChestBooster();
        new Seriafw();
        new Zadania();
        new Attributes();
        new PrivateMessage();
        new AddToWarehouse();
        new Kowal();
        new Ogloszenia();
        new Dice();
        new LoadLokacja();
        new SetLokacja();
        new CwAdmin();
        new Cws();
        new Pomoc();
        new Teleporter();
        new Punkty();
        new ShopCommands();
        new DajPunkty();
        new Fw();
        new ShopAnnouncement();
        new FillUpWarehouse();
        new OpenChest();
        new BuyChests();
        new GlobalFreeChest();
        new KalendarzLogowania();

        //systems
        new AutoMessageTimer();

        //others
        getScheduler().runTaskLater(this, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doInsomnia true");
            getLogger().log(Level.INFO, "Lokalna godzina: " + LocalTime.now().getHour() + "h " + LocalTime.now().getMinute() + "m");
        }, 100L);
    }
}
