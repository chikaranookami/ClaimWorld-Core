package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.NamespacedKey;

public class PrivateChestsUtils {
    public NamespacedKey getKey() {
        return new NamespacedKey(Supporter.getPlugin(), "privateBlock");
    }
}
