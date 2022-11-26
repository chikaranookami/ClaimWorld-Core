package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.items.CustomItem;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class OpenChest {

    private final List<ItemStack> randomItems = new ArrayList<>();

    private void playVisuals(World world, Location location) {
        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 20, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 3) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 8);

        //faze2
        delay += 24;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 5, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 5, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 4), delay);

        //faze3
        delay += 24;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 3, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORKS_SPARK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 9) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 2), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 1, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORKS_SPARK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 15) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 1), delay);

        //faze5
        delay += 15;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> world.spawnParticle(Particle.EXPLOSION_HUGE, location, 1), delay);
    }

    private void playSounds(World world, Location location) {
        Sound sound = Sound.BLOCK_NOTE_BLOCK_BASS;
        float volume = 0.6f;
        
        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 4) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 5);

        //faze2
        delay += 20;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 5) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 4), delay);

        //faze3
        delay += 20;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 3), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 7) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 2), delay);

        //faze5
        delay += 14;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 8) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 1), delay);

        //faze6
        delay += 8;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, volume, volume), delay);
    }

    public OpenChest() {
        //last amount update - 26.11.2022
        //total amount > 350
        
        ReadyItems readyItems = ReadyItems.getInstance();

        for (Map.Entry<String, CustomItem> entry : readyItems.getItemMap().entrySet()) {
            if (entry.getKey().equals("Menu") || entry.getKey().equals("Cofnij")) continue;
            randomItems.add(entry.getValue().getItem());
        }

        randomItems.add(readyItems.get("$1", 32));
        randomItems.add(readyItems.get("$1", 48));
        randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 32));
        randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 48));

        randomItems.add(new ItemStack(Material.ELYTRA));
        randomItems.add(new ItemStack(Material.BEACON));
        randomItems.add(new ItemStack(Material.SHULKER_BOX));
        randomItems.add(new ItemStack(Material.ENDER_CHEST));
        
        for (int i = 1; i < 6; i++) {
            randomItems.add(readyItems.get("$1", 4 * i));
            randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 4 * i));

            randomItems.add(new ItemStack(Material.NETHER_WART, 4 * i));
            randomItems.add(new ItemStack(Material.NETHER_WART_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.BROWN_MUSHROOM_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.RED_MUSHROOM_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.ACACIA_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.BIRCH_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.DARK_OAK_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.JUNGLE_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.MANGROVE_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.OAK_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.SPRUCE_WOOD, 4 * i));
            randomItems.add(new ItemStack(Material.DIAMOND, 4 * i));
            randomItems.add(new ItemStack(Material.EMERALD, 4 * i));
            randomItems.add(new ItemStack(Material.EMERALD_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.IRON_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.IRON_INGOT, 4 * i));
            randomItems.add(new ItemStack(Material.GOLD_INGOT, 4 * i));
            randomItems.add(new ItemStack(Material.GOLD_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.JACK_O_LANTERN, 4 * i));
            randomItems.add(new ItemStack(Material.LAPIS_LAZULI, 4 * i));
            randomItems.add(new ItemStack(Material.LAPIS_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.CHARCOAL, 4 * i));
            randomItems.add(new ItemStack(Material.COAL, 4 * i));
            randomItems.add(new ItemStack(Material.COPPER_ORE, 4 * i));
            randomItems.add(new ItemStack(Material.COPPER_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.COPPER_INGOT, 4 * i));
            randomItems.add(new ItemStack(Material.SHROOMLIGHT, 4 * i));
            randomItems.add(new ItemStack(Material.GRASS_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.GLOWSTONE, 4 * i));
            randomItems.add(new ItemStack(Material.OBSIDIAN, 4 * i));
            randomItems.add(new ItemStack(Material.CRYING_OBSIDIAN, 4 * i));
            randomItems.add(new ItemStack(Material.FIREWORK_ROCKET, 4 * i));
            randomItems.add(new ItemStack(Material.FIREWORK_ROCKET, 4 * i));
            randomItems.add(new ItemStack(Material.MUD, 4 * i));
            randomItems.add(new ItemStack(Material.RED_SAND, 4 * i));
            randomItems.add(new ItemStack(Material.DRIPSTONE_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.AMETHYST_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.AMETHYST_SHARD, 4 * i));
            randomItems.add(new ItemStack(Material.END_STONE, 4 * i));
            randomItems.add(new ItemStack(Material.NETHER_BRICK, 4 * i));
            randomItems.add(new ItemStack(Material.BLACKSTONE, 4 * i));
            randomItems.add(new ItemStack(Material.STONE_BRICKS, 4 * i));
            randomItems.add(new ItemStack(Material.REDSTONE, 4 * i));
            randomItems.add(new ItemStack(Material.REDSTONE_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.TNT, 4 * i));
            randomItems.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 4 * i));
            randomItems.add(new ItemStack(Material.SHULKER_SHELL, 4 * i));
            randomItems.add(new ItemStack(Material.STONE, 4 * i));
            randomItems.add(new ItemStack(Material.COBBLESTONE, 4 * i));
            randomItems.add(new ItemStack(Material.DIRT, 4 * i));
            randomItems.add(new ItemStack(Material.SAND, 4 * i));
            randomItems.add(new ItemStack(Material.DEEPSLATE, 4 * i));
            randomItems.add(new ItemStack(Material.GLASS, 4 * i));
            randomItems.add(new ItemStack(Material.MOSSY_COBBLESTONE, 4 * i));
            randomItems.add(new ItemStack(Material.PURPUR_BLOCK, 4 * i));
            randomItems.add(new ItemStack(Material.TERRACOTTA, 4 * i));
            randomItems.add(new ItemStack(Material.GRANITE, 4 * i));
            randomItems.add(new ItemStack(Material.DIORITE, 4 * i));
            randomItems.add(new ItemStack(Material.ANDESITE, 4 * i));
            randomItems.add(new ItemStack(Material.CLAY, 4 * i));
            randomItems.add(new ItemStack(Material.GRAVEL, 4 * i));
            randomItems.add(new ItemStack(Material.SANDSTONE, 4 * i));
        }

        randomItems.add(new ItemStack(Material.GOLDEN_AXE));
        randomItems.add(new ItemStack(Material.IRON_PICKAXE));
        randomItems.add(new ItemStack(Material.GOLDEN_PICKAXE));
        randomItems.add(new ItemStack(Material.DIAMOND_SHOVEL));
        randomItems.add(new ItemStack(Material.DIAMOND_PICKAXE));
        randomItems.add(new ItemStack(Material.NAME_TAG));
        randomItems.add(new ItemStack(Material.SADDLE));

        new CommandBase("openchest", 1, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                if (player == null) {
                    sender.sendMessage("player is null");
                    return true;
                }

                Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0, 2, 0);
                location.setY(player.getLocation().getY() + 1);

                World world = location.getWorld();
                playVisuals(world, location);
                playSounds(world, location);

                getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                    assert world != null;
                    getScheduler().runTask(Supporter.getPlugin(), () -> {
                        world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                        world.playSound(location, Sound.ITEM_BUNDLE_DROP_CONTENTS, 1f, 1f);

                        Item item = world.dropItem(location, randomItems.get(new Random().nextInt(randomItems.size())));
                        item.setPickupDelay(40);
                        item.setVisualFire(true);
                        item.setCustomNameVisible(true);
                        item.setOwner(player.getUniqueId());

                        ItemMeta itemMeta = item.getItemStack().getItemMeta();
                        String displayName = itemMeta.getDisplayName();
                        item.setCustomName(displayName);

                        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                            String playerName = player.getName();
                            if (itemMeta.hasDisplayName()) broadcastMessage(getBroadcastPrefix() + colorize(playerName + " znalazl " + item.getItemStack().getAmount() +  "x " + displayName + "&f w &cSkrzyni Smoka"));
                        });
                    });
                }, 100);

                return true;
            }

            @Override
            public String getUsage() {
                return "/openchest <nick>";
            }
        }.setPermission("claimworld.admin");
    }
}
