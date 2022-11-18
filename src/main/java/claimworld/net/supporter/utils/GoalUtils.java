package claimworld.net.supporter.utils;

import java.util.HashMap;

public class GoalUtils {

    private final HashMap<Integer, String> rewardsMap = new HashMap<>();

    public GoalUtils() {
        rewardsMap.put(250, "Mniej spiacych osob, by ustawic dzien");
        rewardsMap.put(500, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(750, "Nieco wiekszy randomTickSpeed");
        rewardsMap.put(1000, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(1250, "Powiekszenie borderu (Overworld)");
        rewardsMap.put(9999, "-");
    }

    public String getReward(int reward) {
        return rewardsMap.get(reward);
    }
}
