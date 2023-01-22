package claimworld.net.supporter.events;

import claimworld.net.supporter.battlepass.SkillManager;
import claimworld.net.supporter.items.ReadyItems;
import claimworld.net.supporter.utils.AttributesManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class EntityDamagedByEntityEvent implements Listener {

    ReadyItems readyItems = ReadyItems.getInstance();

    private final AttributesManager attributesManager = new AttributesManager();
    private final SkillManager skillManager = new SkillManager();

    @EventHandler
    public void entityDamagedByEntityEvent(EntityDamageByEntityEvent event) {
        double damage = event.getDamage();

        EntityType entityType = event.getDamager().getType();
        if (entityType == EntityType.SNOWMAN) {
            event.setDamage(event.getDamage() * 6);
            return;
        }

        if (event.getDamager().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getDamager();

        if (entityType == EntityType.CREEPER) {
            if (event.getDamager().getType() != EntityType.PLAYER) return;

            ItemMeta itemMeta = player.getInventory().getItemInOffHand().getItemMeta();
            if (itemMeta == null) return;

            if (!itemMeta.hasDisplayName()) return;
            if (!itemMeta.getDisplayName().equals(readyItems.getKosaNaMoby().getItemMeta().getDisplayName())) return;

            event.setDamage(500);
            return;
        }

        if (entityType != EntityType.PLAYER) return;

        event.setDamage(damage + attributesManager.getAdditionalDamage(player));

        if (!skillManager.canActivateSkill(player, "Mob Killer")) return;
        if (new Random().nextInt(8) != 0) return;

        event.setDamage(damage * 2);
        skillManager.renderSkillEffect(event.getEntity().getLocation());
    }
}
