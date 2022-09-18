package claimworld.net.supporter.utils;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class Messages {
    public static String getUserPrefix() {
        return colorize("&7| ");
    }

    public static String getGlobalPrefix() {
        return colorize("&a@ALL|&f ");
    }

    public static String getShopAnnouncementPrefix() {return colorize("&e[✦ &fSKLEP &e✦]&f ");}

    public static String getBroadcastPrefix() {
        return colorize("&e[Ogloszenie]&f ");
    }
}
