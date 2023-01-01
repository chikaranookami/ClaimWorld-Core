package claimworld.net.supporter.battlepass;

public class Skill {
    private final int requiredLevel;
    private final String name;
    private final String help;

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public Skill(String name, int requiredLevel, String help) {
        this.name = name;
        this.help = help;
        this.requiredLevel = requiredLevel;
    }
}
