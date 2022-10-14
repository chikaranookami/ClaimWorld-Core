package claimworld.net.supporter;

import claimworld.net.supporter.commands.*;
import claimworld.net.supporter.events.*;
import claimworld.net.supporter.timers.AutoMessages;
import claimworld.net.supporter.commands.Goal;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getScheduler;

public final class Supporter extends JavaPlugin implements Listener {

    private static Supporter plugin;

    public static Supporter getPlugin() {
        return plugin;
    }
    public static boolean togglePhantoms;
    public static boolean toggleEnd;
    public static boolean doubleXp;
    public static boolean pickupAll;
    public static boolean doubledForce;
    public static boolean moreFromOres;
    public static boolean loadedWarehouse;

    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        PluginManager pluginManager = getServer().getPluginManager();

        //below name text & prefixes

        //events
        pluginManager.registerEvents(new PlayerRespawnEvent(), this);
        pluginManager.registerEvents(new PlayerLoginEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
        pluginManager.registerEvents(new PlayerChangedWorldEvent(), this);
        pluginManager.registerEvents(new EntityDeathEvent(), this);
        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new TradeSelectEvent(), this);
        pluginManager.registerEvents(new SignChangeEvent(), this);
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEntityEvent(), this);
        pluginManager.registerEvents(new EntityDamageEvent(), this);
        pluginManager.registerEvents(new EntitySpawnEvent(), this);
        pluginManager.registerEvents(new HangingPlaceEvent(), this);
        pluginManager.registerEvents(new PlayerItemConsumeEvent(), this);
        pluginManager.registerEvents(new InventoryClickEvent(), this);
        pluginManager.registerEvents(new PrepareAnvilEvent(), this);

        //commands
        new LoadLokacja();
        new SetLokacja();
        new CwAdmin();
        new Cws();
        new Pomoc();
        new Teleporter();
        new Punkty();
        new Sklep();
        new Vip();
        new DajPunkty();
        new Fw();
        new ShopAnnouncement();
        new Goal();

        //systems
        new AutoMessages();

        //others
        getScheduler().runTaskLater(this, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doInsomnia true");
        }, 100L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
