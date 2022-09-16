package claimworld.net.supporter.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class MagicChests {

    private final List<Location> oldLocations = new ArrayList<>();

    private final String chestName = ChatColor.AQUA + "Magiczna Skrzynka";

    private Location location;

    public String getChestName() {
        return chestName;
    }

    public ItemStack getItem(int amount) {
        ItemStack item = getItem();
        item.setAmount(amount);
        return item;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Otworz ja, by wszyscy");
        lore.add(ChatColor.GRAY + "gracze online otrzymali");
        lore.add(ChatColor.GRAY + "jakies ciekawe nagrody!");

        assert itemMeta != null;
        itemMeta.setDisplayName(getChestName());
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        oldLocations.add(location);
        location = newLocation;
        getLogger().log(Level.INFO, "Magic Chest location has been udpated to " + location);
    }

    public void clearLocations() {
        if (oldLocations.isEmpty()) {
            getLogger().log(Level.INFO, "No old locations to clear.");
            return;
        }

        for (Location oldLocation : oldLocations) {
            if (oldLocation == null) continue;

            oldLocation.getBlock().setType(Material.AIR);
            getLogger().log(Level.INFO, "Cleared block from old Magic Chest location: " + oldLocation);
        }

        oldLocations.clear();
    }

    public void updateBlock() {
        clearLocations();

        location.getBlock().setType(Material.ENDER_CHEST);
        getLogger().log(Level.INFO, "Changed block at current location to ender chest.");

        new InvisibleArmorStands().create(location, getChestName());
    }

    public void create(Location newLocation) {
        getLogger().log(Level.INFO, "Attempting to create a Magic Chest at " + newLocation + "...");
        setLocation(newLocation);
        updateBlock();
    }

    public void open() {
        getLogger().log(Level.INFO, "opening a chest bjacz");
    }
}
