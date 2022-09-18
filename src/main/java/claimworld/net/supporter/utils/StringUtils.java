package claimworld.net.supporter.utils;

import net.md_5.bungee.api.ChatColor;

public class StringUtils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
