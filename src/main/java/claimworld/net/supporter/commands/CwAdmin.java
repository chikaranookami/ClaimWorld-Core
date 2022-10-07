package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.Messages;
import claimworld.net.supporter.utils.Ranks;
import claimworld.net.supporter.utils.guis.Gui;
import claimworld.net.supporter.utils.guis.GuiManager;
import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getScheduler;

public class CwAdmin {
    public CwAdmin() {
        new CommandBase("cwadmin", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                Player player = Bukkit.getPlayer(arguments[0]);
                String action = arguments[1];

                if (player == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                if (action.equals("doShit")) {
                    World world = player.getWorld();
                    Location location = player.getLocation();
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
                    return true;
                }

                if (action.equals("openWarehouse")) {
                    new GuiManager(player, new Gui(null, 27, "Magazyn"));
                    return true;
                }

                if (action.equals("fillWarehouse")) {
                    getScheduler().runTaskLater(Supporter.getPlugin(), () -> {

                    }, 200L);
                    return true;
                }

                if (action.equals("spawnBall")) {
                    Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
                    chicken.setAware(false);
                    sender.sendMessage("ball has been spawned");
                    return true;
                }

                if (action.equals("updateRank")) {
                    sender.sendMessage("Trying to update rank of " + player.getDisplayName());
                    new Ranks().updateRank(player);
                    return true;
                }

                if (action.equals("giveCustomItems")) {
                    assert player.getLocation().getWorld() != null;
                    player.getLocation().getWorld().dropItem(player.getLocation(), new ReadyItems().get("$1"));
                    player.getLocation().getWorld().dropItem(player.getLocation(), new ReadyItems().get("Uniwersalny bilet"));
                    player.getLocation().getWorld().dropItem(player.getLocation(), new ReadyItems().get("Kupa"));
                    player.getLocation().getWorld().dropItem(player.getLocation(), new ReadyItems().get("Niewidzialna ramka"));
                    return true;
                }

                return false;
            }

            @Override
            public String getUsage() {
                return "/cwadmin <nick> <action>";
            }
        }.setPermission("claimworld.mod");
    }
}
