package claimworld.net.supporter;

import claimworld.net.supporter.commands.*;
import claimworld.net.supporter.events.*;
import claimworld.net.supporter.timers.AutoMessages;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Supporter extends JavaPlugin implements Listener {

    private static Supporter instance;

    public static Supporter getInstance() {
        return instance;
    }

    public static boolean TogglePhantoms;
    public static boolean ToggleEnd;
    public static boolean DoubleXp;

    @Override
    public void onEnable() {
        instance = this;

        //events
        PluginManager pluginManager = getServer().getPluginManager();
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

        //commands
        new CwAdmin();
        new Kosz();
        new Cws();
        new Pomoc();
        new Teleporter();
        new Punkty();
        new Sklep();
        new Vip();
        new DajPunkty();
        new ThrowEntity();
        new Fw();
        new Test();

        BukkitScheduler scheduler = Bukkit.getScheduler();

        //enablePhantoms
        scheduler.runTaskLater(this, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doInsomnia true");
        }, 20L);

        //play bossbars after 2 and 4 hours
        scheduler.runTaskLater(Supporter.getInstance(), AutoMessages::new, 100L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
