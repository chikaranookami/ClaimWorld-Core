package claimworld.net.supporter.utils;

import static claimworld.net.supporter.utils.StringUtils.colorize;

public class MessageUtils {

    public static String getUserPrefix() {
        return colorize("&7| ");
    }
    public static String getBroadcastPrefix() {
        return colorize("&e[Ogloszenie]&f ");
    }

    public static String getBattlepassIcon() {return "ღ";}
    public static String getAttributeIcon() {return "¤";}
}
