package claimworld.net.supporter.utils.wip;

import org.bukkit.ChatColor;

public class ErrorMessages {

    public final String getUserResponse(int code) {
        switch (code) {
            case 0:
                return ChatColor.GRAY + "[#KOD BLEDU " + code + "] " + "Skontaktuj sie z deweloperem.";
            case 1:
                return ChatColor.GRAY + "[#KOD BLEDU " + code + "] " + "Wprowadz poprawny nick gracza online.";
            case 2:
                return ChatColor.GRAY + "[#KOD BLEDU " + code + "] " + "Wprowadz poprawna liczbe.";
            default:
                break;
        }

        return ChatColor.GRAY + "Niepoprawny kod bledu.";
    }

    public final String getByCode(int code) {
        switch (code) {
            case 0:
                return "Nie odnaleziono rangi.";
            case 1:
                return "Nie znaleziono gracza";
            case 2:
                return "Niepoprawna liczba.";
            default:
                break;
        }

        return "Niepoprawny kod bledu.";
    }
}
