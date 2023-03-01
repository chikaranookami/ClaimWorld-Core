package claimworld.net.supporter.timers;

import claimworld.net.supporter.Supporter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static org.bukkit.Bukkit.*;

public class BossTimer {
    public BossTimer() {
        String prefix = "[" + getClass().getSimpleName() + "] ";
        getLogger().log(Level.INFO, prefix + "Przygotowywanie...");

        DayOfWeek day = LocalDate.now().getDayOfWeek();
        if (day != DayOfWeek.SUNDAY && day != DayOfWeek.SATURDAY) {
            getLogger().log(Level.INFO, prefix + "Nie wykryto Soboty/Niedzieli. Anulowanie...");
            return;
        }

        if (LocalTime.now().getHour() != 18) {
            getLogger().log(Level.INFO, prefix + "Nie wykryto okna 18:00-0:00. Anulowanie...");
            return;
        }


        getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
            broadcastMessage(getBroadcastPrefix() + "Mini boss pojawi sie za 30 sekund na Stadionie (spawn)!");
            getScheduler().runTaskLater(Supporter.getPlugin(), () -> dispatchCommand(getConsoleSender(), "mm mobs spawn SkeletonKing 1 1,-752,85,-730"), 600);
        }, 72000);
    }
}
