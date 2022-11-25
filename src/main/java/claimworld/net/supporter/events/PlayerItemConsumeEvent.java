package claimworld.net.supporter.events;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.MessageUtils;
import claimworld.net.supporter.utils.items.ReadyItems;
import claimworld.net.supporter.utils.tasks.Task;
import claimworld.net.supporter.utils.tasks.TaskManager;
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
import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class PlayerItemConsumeEvent implements Listener {

    private final List<Material> blockedMaterials = Arrays.asList(Material.SWEET_BERRIES, Material.CHORUS_FRUIT, Material.MELON_SLICE, Material.DRIED_KELP);

    private void renderPoopEffects(World world, Location location) {
        world.dropItem(location, new ItemStack(Material.DIRT, 2));
        world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.6f, 2f);
        world.spawnParticle(Particle.SPELL, location, 15, 0.75, 0.75, 0.75, 0);
        world.spawnParticle(Particle.SLIME, location, 15, 0.75, 0.75, 0.75);
    }

    private void smallPoop(Player player, World world, Location location) {
        renderPoopEffects(world, location);
        player.sendMessage(MessageUtils.getUserPrefix() + "Chyba zbiera Ci sie na cos ciezszego...");

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(player, new Task("Niech zbiera Ci sie na cos ciezszego.", "", 0));
        });
    }

    private void bigPoop(Player player, World world, Location location) {
        ItemStack kupa = ReadyItems.getInstance().get("Kupa");

        location.getBlock().setType(Material.DIRT);

        renderPoopEffects(world, location);
        world.dropItem(location, kupa);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_PLACE, 0.85f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.6f, 2f);

        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        assert potionMeta != null;
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true, true), false);
        potion.setItemMeta(potionMeta);

        ThrownPotion thrownPotion = world.spawn(location, ThrownPotion.class);
        thrownPotion.setItem(potion);

        Bukkit.broadcastMessage(colorize(MessageUtils.getBroadcastPrefix() + "Gracz " + player.getDisplayName() + " &fwlasnie sie... zesral."));

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            TaskManager.getInstance().tryFinishTask(player, new Task("Zrob kupe.", "", 0));
        });
    }

    @EventHandler
    public void playerItemConsumeEvent(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        if (blockedMaterials.contains(event.getItem().getType())) return;

        Player player = event.getPlayer();

        if (event.getItem().getType() == Material.SUSPICIOUS_STEW) {
            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> TaskManager.getInstance().tryFinishTask(player, new Task("Zjedz 8 dziwnych zup.", "counter", 8)));
            return;
        }

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            int poopChance = new Random().nextInt(1000);
            if (poopChance > 10) return;

            World world = player.getWorld();
            Location location = player.getLocation();

            if (poopChance > 2) {
                getScheduler().runTask(Supporter.getPlugin(), () -> smallPoop(player, world, location));
                return;
            }

            if (location.getBlock().getType() != Material.AIR) return;

            getScheduler().runTask(Supporter.getPlugin(), () -> bigPoop(player, world, location));
        });
    }
}