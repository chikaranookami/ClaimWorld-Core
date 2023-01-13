package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

import static claimworld.net.supporter.utils.MessageUtils.getUserPrefix;
import static org.bukkit.Bukkit.getScheduler;

public class PrivateChestsUtils {
    private NamespacedKey getKey() {
        return new NamespacedKey(Supporter.getPlugin(), "cwPrivateBlock");
    }
    private final String protectionDefaultsName = "protectionDefaults";

    private static PrivateChestsUtils instance = null;

    public static PrivateChestsUtils getInstance() {
        if (instance == null) instance = new PrivateChestsUtils();
        return instance;
    }

    private void createProtection(Player player, PersistentDataContainer container, TileState state) {
        container.set(getKey(), PersistentDataType.STRING, "|" + player.getUniqueId());
        getScheduler().runTask(Supporter.getPlugin(), () -> state.update());

        player.sendMessage(getUserPrefix() + "Utworzono zabezpieczenie.");

        addDefaultsToProtection(player, container, state);
    }

    private void addToProtection(Player player, PersistentDataContainer container, TileState state, UUID uuid) {
        String protectionString = container.get(getKey(), PersistentDataType.STRING);
        String playerString = "|" + uuid;

        assert protectionString != null;
        if (protectionString.contains(playerString)) {
            player.sendMessage(getUserPrefix() + "Gracz ma juz dostep do zabezpieczenia.");
            return;
        }

        container.set(getKey(), PersistentDataType.STRING, protectionString + playerString);
        getScheduler().runTask(Supporter.getPlugin(), () -> state.update());

        player.sendMessage(getUserPrefix() + "Dodano " + player.getName() + " do zabezpieczenia.");
    }

    private void addDefaultsToProtection(Player player, PersistentDataContainer container, TileState state) {
        FileConfiguration config = Supporter.getPlugin().getConfig();
        List<String> usersWithAccess = config.getStringList(protectionDefaultsName + "." + player.getName());
        if (usersWithAccess.isEmpty()) return;

        for (String userId : usersWithAccess) {
            addToProtection(player, container, state, UUID.fromString(userId));
        }
    }

    private void removeFromProtection(Player player, PersistentDataContainer container, TileState state, UUID uuid) {
        String playerString = "|" + uuid;
        String protectionString = container.get(getKey(), PersistentDataType.STRING);

        assert protectionString != null;
        if (!protectionString.contains(playerString)) {
            player.sendMessage(getUserPrefix() + "Ten gracz nie ma dostepu do zabezpieczenia.");
            return;
        }

        String finalProtectionString = protectionString.replaceFirst(playerString, "");
        if (finalProtectionString.length() < 1) {
            container.remove(getKey());
            getScheduler().runTask(Supporter.getPlugin(), () -> state.update());

            player.sendMessage(getUserPrefix() + "Usunieto dostep ostatniej osobie. Odbezpieczono.");
            return;
        }

        container.set(getKey(), PersistentDataType.STRING, finalProtectionString);
        getScheduler().runTask(Supporter.getPlugin(), () -> state.update());

        player.sendMessage(getUserPrefix() + "Usunieto gracza z zabezpieczenia.");
    }

    public void saveProtectionDefault(Player player, UUID uuid) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            FileConfiguration config = Supporter.getPlugin().getConfig();
            String idString = uuid.toString();

            if (config.getStringList(protectionDefaultsName + "." + player.getName()).contains(idString)) {
                player.sendMessage(getUserPrefix() + "Ta osoba jest juz na Twojej liscie domyslnych.");
                return;
            }

            config.set(protectionDefaultsName + "." + player.getName(), idString);
            Supporter.getPlugin().saveConfig();

            player.sendMessage(getUserPrefix() + "Dodano gracza do Twojej listy domyslnych.");
        });
    }

    public boolean hasProtection(PersistentDataContainer container) {
        return container.has(getKey(), PersistentDataType.STRING);
    }

    public boolean hasAccess(Player player, PersistentDataContainer container) {
        return container.get(getKey(), PersistentDataType.STRING).contains("|" + player.getUniqueId());
    }

    public void updateState(Player player, TileState state, String mode) {
        updateState(player, state, mode, player.getUniqueId());
    }

    public void updateState(Player player, TileState state, String mode, UUID uuid) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            PersistentDataContainer container = state.getPersistentDataContainer();
            if (mode.equals("create")) createProtection(player, container, state);

            if (container.get(getKey(), PersistentDataType.STRING) == null) {
                player.sendMessage(getUserPrefix() + "Ten obiekt nie jest zabezpieczony.");
                return;
            }

            if (mode.equals("addToProtection")) addToProtection(player, container, state, uuid);
            if (mode.equals("removeFromProtection")) removeFromProtection(player, container, state, uuid);
        });
    }
}
