package claimworld.net.supporter.utils;

import org.geysermc.geyser.api.GeyserApi;

import java.util.UUID;

public class GeyserUtils {

    GeyserApi geyserApi = GeyserApi.api();

    public boolean isPlayerFromGeyser(UUID uuid) {
        return geyserApi.connectionByUuid(uuid) != null;
    }
}
