package claimworld.net.supporter.utils;

import claimworld.net.supporter.Supporter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class GoalUtils {

    private final FileConfiguration config = Supporter.getPlugin().getConfig();

    private final HashMap<Integer, String> rewardsMap = new HashMap<>();

    private boolean shorterFishing;

    public boolean getShorterFishing() {
        return shorterFishing;
    }

    public String getReward(int reward) {
        return rewardsMap.get(reward);
    }

    public GoalUtils() {
        if (config.getInt("goals.total") >= 1250) shorterFishing = true;

        rewardsMap.put(250, "Mniej spiacych osob, by ustawic dzien");
        rewardsMap.put(500, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(750, "Nieco wiekszy randomTickSpeed");
        rewardsMap.put(1000, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(1250, "Szybsze lowienie");
        rewardsMap.put(9999, "-");
    }
}
