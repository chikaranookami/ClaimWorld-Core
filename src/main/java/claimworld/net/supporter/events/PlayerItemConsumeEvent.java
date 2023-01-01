package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.tasks.Task;
import claimworld.net.supporter.tasks.TaskManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerItemConsumeEvent implements Listener {

    TaskManager taskManager = TaskManager.getInstance();

    Map<String, Task> taskMap = taskManager.getTaskMap();

    private final List<Material> blockedMaterials = Arrays.asList(Material.SWEET_BERRIES, Material.CHORUS_FRUIT, Material.MELON_SLICE, Material.DRIED_KELP);

    private void renderPoopEffects(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIRT, 1));
        world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.6f, 2f);
        world.spawnParticle(Particle.SPELL, location, 25, 0.75, 0.75, 0.75, 0);
        world.spawnParticle(Particle.SLIME, location, 15, 0.75, 0.75, 0.75);
        world.spawnParticle(Particle.SQUID_INK, location, 5, 0.75, 0.75, 0.75);
    }

    private void renderPoopPoison(World world, Location location) {
        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        assert potionMeta != null;
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true, true), false);
        potion.setItemMeta(potionMeta);

        ThrownPotion thrownPotion = world.spawn(location, ThrownPotion.class);
        thrownPotion.setItem(potion);
    }

    private void smallPoop(Player player, World world, Location location) {
        renderPoopEffects(world, location);
        renderPoopPoison(world, location);
        player.sendMessage(MessageUtils.getUserPrefix() + "TE, zwolnij tam, bo zaczynasz popuszczac!");

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("doSmallShit")));
    }

    private void bigPoop(Player player, World world, Location location) {
        ItemStack kupa = ReadyItems.getInstance().get("Kupa");

        location.getBlock().setType(Material.DIRT);

        renderPoopEffects(world, location);
        world.dropItem(location, kupa);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_PLACE, 0.85f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.6f, 2f);

        renderPoopPoison(world, location);

        Bukkit.broadcastMessage(colorize(MessageUtils.getBroadcastPrefix() + "Gracz " + player.getDisplayName() + " &fwlasnie sie... zesral."));

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("doShit")));
    }

    @EventHandler
    public void playerItemConsumeEvent(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (blockedMaterials.contains(event.getItem().getType())) return;

        Player player = event.getPlayer();

        Material material = event.getItem().getType();
        if (material == Material.ROTTEN_FLESH) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("eatRottenFlashes")));
            return;
        }
        if (material == Material.SUSPICIOUS_STEW) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> taskManager.tryFinishTask(player, taskMap.get("eatSuspiciousStew")));
            return;
        }

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            int poopChance = new Random().nextInt(1000);
            if (poopChance > 16) return;

            World world = player.getWorld();
            Location location = player.getLocation();

            if (poopChance > 4) {
                getScheduler().runTask(Supporter.getPlugin(), () -> smallPoop(player, world, location));
                return;
            }

            if (location.getBlock().getType() != Material.AIR) return;

            getScheduler().runTask(Supporter.getPlugin(), () -> bigPoop(player, world, location));
        });
    }
}