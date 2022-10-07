package claimworld.net.supporter.events;

import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class PlayerItemConsumeEvent implements Listener {

    @EventHandler
    public void playerItemConsumeEvent(org.bukkit.event.player.PlayerItemConsumeEvent event) {
        Material material = event.getItem().getType();

        if (material == Material.SWEET_BERRIES) return;
        if (material == Material.MELON_SLICE) return;
        if (material == Material.DRIED_KELP) return;

        int poopChance = new Random().nextInt(1000);

        if (poopChance > 10) return;

        int random = new Random().nextInt(2);

        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();
        Block block1 = player.getLocation().add(0, 1, 0).getBlock();
        World world = player.getWorld();
        Location location = player.getLocation();

        if (poopChance > 2) {
            world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.5f, 2f);
            world.spawnParticle(Particle.SPELL, location, 10, 0.75, 0.75, 0.75, 0);
            world.spawnParticle(Particle.SLIME, location, 10, 0.75, 0.75, 0.75);
            world.dropItem(location, new ItemStack(Material.DIRT));
            player.sendMessage(Messages.getUserPrefix() + "Chyba zbiera Ci sie na cos ciezszego...");
            return;
        }

        if (block.getType() != Material.AIR) return;
        block.setType(Material.DIRT);

        if (random == 1) block1.setType(Material.DIRT);

        world.dropItem(location, new ReadyItems().get("Kupa"));
        world.dropItem(location, new ItemStack(Material.DIRT, 3));

        world.playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 0.6f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_PLACE, 0.85f, 2f);
        world.playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 0.6f, 2f);
        world.spawnParticle(Particle.SPELL, location, 20, 0.75, 0.75, 0.75, 0);
        world.spawnParticle(Particle.SLIME, location, 20, 0.75, 0.75, 0.75);

        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        assert potionMeta != null;
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0, true, true), false);
        potion.setItemMeta(potionMeta);

        ThrownPotion thrownPotion = world.spawn(location, ThrownPotion.class);
        thrownPotion.setItem(potion);

        Bukkit.broadcastMessage(colorize(Messages.getBroadcastPrefix() + "Gracz " + player.getDisplayName() + " &fwlasnie sie... zesral."));
    }
}