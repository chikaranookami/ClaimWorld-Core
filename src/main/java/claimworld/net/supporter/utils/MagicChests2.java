package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.logging.Level;

import static claimworld.net.supporter.Supporter.quequedChests;
import static org.bukkit.Bukkit.*;

public class MagicChests2 {
    private final long initialDelay = 20L;
    private final long barUpdateDelay = 4L;

    private final ItemStack[] rewards = {new ItemStack(Material.STONE), new ItemStack(Material.DIRT)};
    private final MultipleHandles multipleHandles = new MultipleHandles();
    private final BossBar bossBar = Bukkit.createBossBar("Otwieranie " + ChatColor.LIGHT_PURPLE + "Magicznej Skrzynki" + ChatColor.WHITE + "... ", BarColor.PINK, BarStyle.SOLID, BarFlag.DARKEN_SKY);

    private final BukkitTask openChestTask = new BukkitRunnable() {
        @Override
        public void run() {
            getLogger().log(Level.INFO, "Running openChestTask...");

            if (quequedChests == 0) {
                cancel();
                return;
            }

            if (showOpeningProgressTask.isCancelled()) {
                getScheduler().runTask(Supporter.getInstance(), (Runnable) showOpeningProgressTask);
                quequedChests -= 1;
            }
        }
    }.runTaskTimer(Supporter.getInstance(), initialDelay, barUpdateDelay * 100 + initialDelay);

    private final BukkitTask showOpeningProgressTask = new BukkitRunnable() {
        double barProgress = 0.0;

        @Override
        public void run() {
            getLogger().log(Level.INFO, "Running showOpeningProgressTask...");

            if (barProgress >= 1.0) {
                bossBar.removeAll();
                generateRewards();
                cancel();
                return;
            }

            bossBar.setProgress(barProgress);
            barProgress += 0.01;
        }
    }.runTaskTimer(Supporter.getInstance(), initialDelay, barUpdateDelay);

    public void generateRewards() {
        getScheduler().runTaskLater(Supporter.getInstance(), () -> {
            getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "> Przyznano nagrody!");

            for (Player player : getOnlinePlayers()) {
                multipleHandles.giveOrDropItems(player, new ItemStack[]{rewards[new Random().nextInt(rewards.length)]});
            }
        }, initialDelay);
    }

    public void openChest(int amount) {
        if (quequedChests < 0) quequedChests = 0;

        quequedChests += amount;

        if (!openChestTask.isCancelled()) {
            getLogger().log(Level.INFO, "OpenChestTask is not cancelled.");
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

        getScheduler().runTask(Supporter.getInstance(), (Runnable) openChestTask);
    }
}
