package claimworld.net.supporter.tasks;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.battlepass.BattlePassManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.*;

public class TaskManager {

    private static TaskManager instance = null;

    public static TaskManager getInstance() {
        if (instance == null) instance = new TaskManager();
        return instance;
    }

    public static String getUserQuestDataObjectiveName = "userquestdata";

    private final Map<String, Task> taskMap = new HashMap<>();
    private final Map<String, Integer> taskCounterMap = new HashMap<>();
    private final List<Task> taskList = new ArrayList<>();

    private final List<Task> activeTasks = new ArrayList<>();
    private final List<String> playersWhoDidTask = new ArrayList<>();

    public Map<String, Task> getTaskMap() {
        return taskMap;
    }

    public List<Task> getActiveTasks() {return activeTasks;}

    private String getActiveQuests() {
        List<String> infoList = new ArrayList<>();

        for (Task task : getActiveTasks()) {
            infoList.add("§c-§8 " + task.getName() + "\n\n");
        }

        return String.join(" ", infoList);
    }

    public BaseComponent[] getActiveTaskComponent() {
        return new ComponentBuilder()
                .append("§cAktywne zadania\n")
                .append(getActiveQuests())
                .append("§8Mozesz wykonac tylko jedno zadanie.")
                .create();
    }

    public boolean renderNewTasks() {
        return this.renderNewTasks(false);
    }

    public boolean renderNewTasks(boolean forced) {
        if (!forced) if (playersWhoDidTask.size() > 0) return false;

        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            if (activeTasks.size() > 0) activeTasks.clear();

            for (int i = 0; i < 2; i++) {
                Task task = taskList.get(new Random().nextInt(taskList.size()));
                if (!activeTasks.contains(task)) {
                    activeTasks.add(task);
                    continue;
                }
                i--;
            }
        });

        taskCounterMap.clear();

        return true;
    }

    public void tryFinishTask(Player player, Task task) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (Task activeTask : getActiveTasks()) {
                String playerName = player.getName();
                String taskName = task.getName();

                if (taskName == null) {
                    getLogger().log(Level.WARNING, "Nie ma zadania: " + taskName + ", gracz: " + playerName);
                    return;
                }

                if (!activeTask.getName().equals(taskName)) continue;
                if (playersWhoDidTask.contains(playerName)) return;

                int value;
                if (taskCounterMap.get(playerName) == null) {
                    value = 0;
                } else {
                    value = taskCounterMap.get(playerName);
                }

                if (!task.getData().equals("counter")) {
                    playersWhoDidTask.add(playerName);
                    BattlePassManager.getInstance().updatePoints(player, 1);
                    return;
                }

                int newValue = value + 1;
                if (newValue == task.getNumber()) {
                    playersWhoDidTask.add(playerName);
                    BattlePassManager.getInstance().updatePoints(player, 1);
                }
                taskCounterMap.put(playerName, newValue);

                getLogger().log(Level.INFO, "counter of " + taskName + " updated to " + value + " (" + playerName + ")");

                break;
/*
                if (task.getData().equals("counter")) {
                    Objective objective = player.getScoreboard().getObjective(getUserQuestDataObjectiveName);
                    assert objective != null;

                    Score score = objective.getScore(playerName);
                    score.setScore(score.getScore() + 1);
                    getLogger().log(Level.INFO, "updated counter score of task " + task.getName() + " to value " + score.getScore());

                    if (!(score.getScore() < task.getNumber())) {
                        playersWhoDidTask.add(playerName);
                    }

                    if (score.getScore() == task.getNumber()) {
                        BattlePassManager.getInstance().updatePoints(player, 1);
                    }

                    break;
                }*/
            }
        });
    }

    public TaskManager() {
        //first set
        taskMap.put("useChorus", new Task("Przemiesc sie o 200 metrow chorusem.", "", 0));
        taskMap.put("placeInvisibleFrame", new Task("Powies niewidzialna ramke.", "", 0));
        taskMap.put("destroySword", new Task("Zuzyj kamienny miecz.", "", 0));
        taskMap.put("fishOutStick", new Task("Wylow patyk.", "", 0));
        taskMap.put("killWarden", new Task("Pokonaj Wardena.", "", 0));
        taskMap.put("craftBeacon", new Task("Zrob beacona.", "", 0));
        taskMap.put("doShit", new Task("Zrob kupe.", "", 0));
        taskMap.put("winRaid", new Task("Wygraj raida.", "", 0));
        taskMap.put("doubleItemAtBlacksmith", new Task("Podwoj 2 przedmioty u kowala.", "counter", 2));
        taskMap.put("setSpawnerToZombie", new Task("Ustaw zombie w spawnerze.", "", 0));
        taskMap.put("spawnWitch", new Task("Zresp wiedzme jajkiem.", "", 0));
        taskMap.put("sleepThruNights", new Task("Przespij 3 noce.", "counter", 3));
        taskMap.put("obtainNetherWarts", new Task("Dropnij 32 brodawki z piglinow.", "counter", 32));
        taskMap.put("breakEmeralds", new Task("Rozkop 4 emeraldy.", "counter", 4));
        taskMap.put("breakDiamonds", new Task("Rozkop 16 diaxow.", "counter", 16));
        taskMap.put("killIronGolems", new Task("Zabij 20 zelaznych golemow.", "counter", 20));
        taskMap.put("killCreepers", new Task("Zabij 30 creeperow.", "counter", 30));

        //second set
        taskMap.put("killDragon", new Task("Pokonaj Smoka.", "", 0));
        taskMap.put("starveToDeath", new Task("Umrzyj z glodu.", "", 0));
        taskMap.put("getHitByLightningStrike", new Task("Oberwij piorunem.", "", 0));
        taskMap.put("timeoutRaid", new Task("Przeczekaj atak na wioske.", "", 0));
        taskMap.put("getAchievementDone", new Task("Ukoncz dowolne osiagniecie.", "", 0));
        taskMap.put("listenToBossbar", new Task("Wysluchaj serwerowych ogloszen.", "", 0));
        taskMap.put("transformZombieToVillager", new Task("Badz blisko nowego wiesniaka z przemienienia.", "", 0));
        taskMap.put("stayCloseToSomeNewEndermite", new Task("Badz blisko nowego endermita z ender perly.", "", 0));
        taskMap.put("destroyItemsAtBlacksmith", new Task("Spal u kowala 2 przedmioty.", "counter", 2));
        taskMap.put("throwNumberUsingDice", new Task("Wyrzuc 6 razy liczbe 12 na kostce.", "counter", 6));
        taskMap.put("eatSuspiciousStew", new Task("Zjedz 5 podejrzanych potrawek.", "counter", 5));
        taskMap.put("getHitByAnything", new Task("Oberwij 40 razy.", "counter", 40));

        //third set
        taskMap.put("beOnlineWhenTraderSpawns", new Task("Badz online, gdy pojawi sie handlarz.", "", 0));
        taskMap.put("destroyPickaxe", new Task("Zuzyj diamentowy kilof.", "", 0));
        taskMap.put("doSmallShit", new Task("Popusc odrobine.", "", 0));
        taskMap.put("openDragonChest", new Task("Otworz Skrzynie Smoka.", "", 0));

        //fifth set
        taskMap.put("pickupCreeper", new Task("Podnies Creepera.", "", 0));
        taskMap.put("upgradeItemAtBlacksmith", new Task("Ulepsz przedmiot u kowala.", "", 0));
        taskMap.put("writeHeartOnSign", new Task("Postaw \"serduszko\" w pierwszej linijce dowolnej tabliczki.", "", 0));

        //sixth set
        taskMap.put("dieDueToFire", new Task("Splon zywcem dwukrotnie.", "counter", 2));
        taskMap.put("killCats", new Task("Zabij 6 kotow.", "counter", 4));
        taskMap.put("teleportYourself", new Task("Przeteleportuj sie 10 razy.", "counter", 10));
        taskMap.put("eatRottenFlashes", new Task("Zjedz 32 sztuki zgnilego miesa.", "counter", 32));
        taskMap.put("beOnlineWhenPlayerBuysFromShop", new Task("Badz online, gdy ktos zrealizuje dzienny cel w sklepie (Chikaraa chetnie rozda voucher!).", "", 0));

        //seventh set
        taskMap.put("singleTeleportYourself", new Task("Przeteleportuj sie na Monopoly.", "", 0));
        taskMap.put("placeReinforcedDeepslate", new Task("Postaw zbrojony lupek.", "", 0));
        taskMap.put("fishOutBow", new Task("Wylow luk.", "", 0));
        taskMap.put("useJetpack", new Task("Uzyj Jetpacka.", "", 0));
        taskMap.put("useFireScroll", new Task("Uzyj Zwoju Ognia.", "", 0));
        taskMap.put("craftGrindstone", new Task("Stworz Kamien Szlifierski.", "", 0));
        taskMap.put("takeBookFromLectern", new Task("Zabierz ksiazke z mownicy (a pozniej ja odloz).", "", 0));
        taskMap.put("riptideYourself", new Task("Wytorpeduj sie trojzebem 10 razy.", "counter", 10));
        taskMap.put("shearSomething", new Task("Ogol wlasnorecznie 10 owieczek.", "counter", 10));
        taskMap.put("breakDebris", new Task("Rozkop pradawne zgliszcza.", "", 0));
        taskMap.put("placeAnything", new Task("Postaw lacznie 300 blokow.", "counter", 300));
        taskMap.put("breakAnything", new Task("Zniszcz lacznie 300 blokow.", "counter", 300));

        //prepare list of tasks
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) taskList.add(entry.getValue());

        renderNewTasks();
    }
}
