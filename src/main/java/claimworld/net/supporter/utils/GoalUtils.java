package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class GoalUtils {

    FileConfiguration config = Supporter.getPlugin().getConfig();

    private static GoalUtils instance = null;

    public static GoalUtils getInstance() {
        if (instance == null) instance = new GoalUtils();
        return instance;
    }

    private boolean shorterFishing;

    private final HashMap<Integer, String> rewardsMap = new HashMap<>();

    private void updateData() {
        if (!(config.getInt("goals.total") >= 1250)) return;
        shorterFishing = true;
        getLogger().log(Level.INFO, "updated value shorterFishing to true");
    }

    public boolean getShorterFishing() {
        return shorterFishing;
    }

    public String getReward(int reward) {
        return rewardsMap.get(reward);
    }

    public String getCurrentReward() {
        int currentGoal = config.getInt("goals.active_goal");
        return getReward(currentGoal);
    }

    public int getMissingAmount() {
        int currentAmount = config.getInt("goals.total");
        int activeGoal = config.getInt("goals.active_goal");

        return activeGoal - currentAmount;
    }

    public int getPercentAmount() {
        float currentAmount = config.getInt("goals.total");
        float activeGoal = config.getInt("goals.active_goal");

        int percentAmount = Math.round(currentAmount / activeGoal * 100);
        if (percentAmount == 100) return 99;
        return percentAmount;
    }

    public GoalUtils() {
        updateData();

        rewardsMap.put(250, "Mniej spiacych osob, by ustawic dzien");
        rewardsMap.put(500, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(750, "Nieco wiekszy randomTickSpeed");
        rewardsMap.put(1000, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(1250, "Szybsze lowienie");
        rewardsMap.put(1750, "Efekty krwawienia");
        rewardsMap.put(2150, "Kalendarz Logowania");
        rewardsMap.put(9999, "-");
    }
}
