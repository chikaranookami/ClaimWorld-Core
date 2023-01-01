package claimworld.net.supporter.guis;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Gui {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Gui(InventoryHolder owner, int size, String name) {
        this.name = name;
        this.size = size;
        this.owner = owner;

        this.inventory = Bukkit.createInventory(owner, size, name);
    }

    private int size;
    private InventoryHolder owner;
    private String name;
    private Inventory inventory;
}
