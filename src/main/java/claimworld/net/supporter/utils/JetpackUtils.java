package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class JetpackUtils {

    ReadyItems readyItems = ReadyItems.getInstance();
    TaskManager taskManager = TaskManager.getInstance();

    private static JetpackUtils instance = null;

    public static JetpackUtils getInstance() {
        if (instance == null) instance = new JetpackUtils();
        return instance;
    }

    private final List<String> playersUsingJetpack = new ArrayList<>();
    private final List<String> delayedPlayers = new ArrayList<>();

    private void renderEffects(Player player) {
        Location location = player.getLocation().add(0, 1, 0);
        player.playSound(location, Sound.BLOCK_CAMPFIRE_CRACKLE, 1.0f, 1.0f);
        player.playSound(location, Sound.ENTITY_GHAST_SHOOT, 0.25f, 0.25f);

        World world = player.getWorld();
        world.spawnParticle(Particle.FLAME, location, 1, 0.02, 0.02, 0.02, 0.02);
        world.spawnParticle(Particle.SWEEP_ATTACK, 0.05, 0.05, 0.05, 2);
        world.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location, 1, 0.02, 0.02, 0.02, 0.02);
    }

    private void updateDurability(String playerName, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        Damageable damageableItemMeta = (Damageable) itemMeta;
        if (damageableItemMeta == null) return;

        int totalDamage = damageableItemMeta.getDamage() + 1;
        if (totalDamage < 240) {
            damageableItemMeta.setDamage(totalDamage);
            item.setItemMeta(itemMeta);
            return;
        }

        disableJetpack(playerName);
        item.setAmount(0);

        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return;

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.2f, 1.2f);
    }

    private void use(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return;

        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, player.getLocation().add(0, 4, 0), 3);
        player.playSound(player, Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1.2f, 1.2f);
        player.playSound(player, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1.2f, 1.2f);

        player.setAllowFlight(true);
        player.setFlying(true);
        player.setVelocity(new Vector(0, 2, 0));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (playersUsingJetpack.contains(playerName) && player.isFlying()) {
                    updateDurability(playerName, player.getInventory().getItemInOffHand());
                    renderEffects(player);
                } else {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> delayedPlayers.remove(playerName), 20L);
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 4);
    }

    public boolean isJetpack(ItemStack item) {
        if (item == null) return false;
        if (item.getItemMeta() == null) return false;
        return item.getItemMeta().getDisplayName().equals(readyItems.get("Jetpack").getItemMeta().getDisplayName());
    }

    public boolean hasJetpack(String playerName) {
        return playersUsingJetpack.contains(playerName);
    }

    public void disableJetpack(String playerName) {
        playersUsingJetpack.remove(playerName);
    }

    public void switchJetpack(String playerName) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (playersUsingJetpack.contains(playerName)) {
                playersUsingJetpack.remove(playerName);
                return;
            }

            if (delayedPlayers.contains(playerName)) return;

            delayedPlayers.add(playerName);
            playersUsingJetpack.add(playerName);
            getScheduler().runTask(Supporter.getPlugin(), () -> use(playerName));

            Player player = Bukkit.getPlayer(playerName);
            if (player == null) return;

            taskManager.tryFinishTask(player, taskManager.getTaskMap().get("useFireScroll"));
        });
    }
}
