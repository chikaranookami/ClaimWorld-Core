package claimworld.net.supporter.battlepass;

public class BattlePassLevel {
    private final int index;
    private final String reward;
    private final String command;

    public int getIndex() {
        return index;
    }

    public String getReward() {
        return reward;
    }

    public String getCommand() {
        return command;
    }

    public BattlePassLevel(int index, String command, String reward) {
        this.index = index;
        this.command = command;
        this.reward = reward;
    }
}
