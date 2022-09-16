package claimworld.net.supporter.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class InvisibleArmorStands {

    public void create(Location location, String content) {
        assert location.getWorld() != null;

        location.setX(location.getBlockX() + 0.5D);
        location.setZ(location.getBlockZ() + 0.5D);

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        armorStand.setVisible(false); // so the players can't see the armor stand
        armorStand.setInvulnerable(true); // so the players can't accidentally break the armor stand
        armorStand.setGravity(false); // so the armor stand doesn't fall
        armorStand.setSmall(true); // just to make it a smaller armour stand

        armorStand.setCustomNameVisible(true); // I think this is the default option but just in case
        armorStand.setCustomName(content);

        getLogger().log(Level.INFO, "A new invisible armorstand has been created at " + location + " with name: " + content);
    }
}
