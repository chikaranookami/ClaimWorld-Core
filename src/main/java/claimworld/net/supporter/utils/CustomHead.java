package claimworld.net.supporter.utils;

import claimworld.net.supporter.utils.guis.ReadyItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class CustomHead extends CustomItem{
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CustomHead(String name, Player player, int amount, List<String> lore) {
        super(name, Material.PLAYER_HEAD, amount, lore, 0);
        this.player = player;

        SkullMeta skullMeta = (SkullMeta) this.getItem().getItemMeta();
        assert skullMeta != null;

        skullMeta.setOwningPlayer(player);

        this.getItem().setItemMeta(skullMeta);
    }
}
