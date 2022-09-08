package claimworld.net.supporter.utils;

import org.bukkit.ChatColor;

import java.util.*;

public class TablistInfo {

    private final HashMap<Date, String> updateDates = new HashMap<Date, String>();
    private Date nearestDate;

    public TablistInfo() {
        updateDates.put(getDate(2022, 9, 9), ChatColor.AQUA + "Duze Powiekszenie Mapy" + " \n " + ChatColor.WHITE + "Data: " + ChatColor.AQUA + "09.09.2022");
        updateDates.put(getDate(2022, 9, 10), ChatColor.AQUA + "Turniej Siatkowki" + " \n " + ChatColor.WHITE + "Data: " + ChatColor.AQUA + "10.09.2022");
        updateDates.put(getDate(2022, 9, 16), ChatColor.AQUA + "Magiczne Skrzynki" + "\n " + ChatColor.WHITE + "Data: " + ChatColor.AQUA + "16.09.2022");
        updateDates.put(getDate(2022, 9, 17), ChatColor.AQUA + "Konkurs Talentow" + " \n " + ChatColor.WHITE + "Data: " + ChatColor.AQUA + "17.09.2022");
    }

    private Date getNearestDate(List<Date> dates, Date choosenDate) {
        long minimumDifference = -1, currentTime = choosenDate.getTime();
        Date minimumDate = null;
        for (Date date : dates) {
            long diff = Math.abs(currentTime - date.getTime());
            if ((minimumDifference == -1) || (diff < minimumDifference)) {
                minimumDifference = diff;
                minimumDate = date;
            }
        }
        return minimumDate;
    }

    private Date getDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTime();
    }

    public String getCurrentMessage() {
        Date currentDate = new Date();

        for (Map.Entry<Date, String> entry : updateDates.entrySet()) {
            if (currentDate.compareTo(entry.getKey()) < 0) continue;
            nearestDate = getNearestDate(new ArrayList<>(updateDates.keySet()), entry.getKey());
            break;
        }

        if (nearestDate == null) return "";

        return updateDates.get(nearestDate);
    }
}
