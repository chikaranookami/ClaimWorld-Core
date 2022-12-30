package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;

import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class RamMangerTimer {
    public RamMangerTimer() {
        getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> getScheduler().runTaskTimerAsynchronously(Supporter.getPlugin(), () -> {
            long freeMemory = Runtime.getRuntime().freeMemory() / 1048576;
            if (freeMemory > 1000) return;

            getLogger().log(Level.INFO, "Low freeMemory (" + freeMemory + "), executing save-all...");
            getScheduler().runTask(Supporter.getPlugin(), () -> dispatchCommand(getConsoleSender(), "save-all"));

            getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> getLogger().log(Level.INFO, "Executed save-all due to low freeMemory. Current amount: " + freeMemory), 100L);
        }, 0, 6000), 216000); //216000 = 3h
    }
}
