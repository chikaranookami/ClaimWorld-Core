package claimworld.net.supporter.utils.guis;

import java.util.HashMap;

public class BonusManager {

    private static BonusManager instance = null;
    private final HashMap<String, Integer> bonusPointPrices = new HashMap<>();
    private final HashMap<String, Boolean> bonuses = new HashMap<>();
    private final HashMap<String, String> bonusMessages = new HashMap<>();
    private final HashMap<String, String> bonusCommands = new HashMap<>();

    public static BonusManager getInstance() {
        if (instance == null) instance = new BonusManager();
        return instance;
    }
    
    public BonusManager() {
        bonuses.put("Bloki", false);
        bonuses.put("AntyFantom", false);
        bonuses.put("DoubleXP", false);
        bonuses.put("Podnoszenie+", false);
        bonuses.put("Diaxy+", false);
        bonuses.put("Rzucanie+", false);
        bonuses.put("Butelki", false);
        bonuses.put("Zadania", false);

        bonusMessages.put("Butelki", "rozdal troche butelki");
        bonusMessages.put("Bloki", "rozdal troche blokow");
        bonusMessages.put("Diaxy+", "wlaczyl dodatkowe diamenty z rud");
        bonusMessages.put("DoubleXP", "2x zwiekszyl zdobywane doswiadczenie");
        bonusMessages.put("AntyFantom", "wylaczyl fantomy");
        bonusMessages.put("Podnoszenie+", "wlaczyl podnoszenie wiekszosci bytow");
        bonusMessages.put("Rzucanie+", "zwiekszyl sile rzucania bytami");
        bonusMessages.put("Zadania", "wygenerowal nowe zadania");

        bonusCommands.put("Bloki", "fillupwarehouse blocks");
        bonusCommands.put("Butelki", "fillupwarehouse xpBottle");
        bonusCommands.put("AntyFantom", "gamerule doInsomnia false");

        bonusPointPrices.put("Butelki", 15);
        bonusPointPrices.put("Bloki", 20);
        bonusPointPrices.put("Diaxy+", 30);
        bonusPointPrices.put("DoubleXP", 30);
        bonusPointPrices.put("AntyFantom", 15);
        bonusPointPrices.put("Podnoszenie+", 30);
        bonusPointPrices.put("Rzucanie+", 30);
        bonusPointPrices.put("Zadania", 30);
    }

    public HashMap<String, Boolean> getBonuses() {
        return this.bonuses;
    }

    public HashMap<String, Integer> getPointPrices() {
        return this.bonusPointPrices;
    }

    public HashMap<String, String> getBonusMessages() {
        return this.bonusMessages;
    }

    public HashMap<String, String> getBonusCommands() {
        return this.bonusCommands;
    }
}
