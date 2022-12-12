package claimworld.net.supporter.utils.tasks;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

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
                String taskName = task.getName();
                if (!activeTask.getName().equals(taskName)) continue;

                String playerName = player.getName();
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

                getLogger().log(Level.INFO, "updated counter of task " + taskName + " to value " + value);

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
        //first set (17)
        taskMap.put("useChorus", new Task("Przemiesc sie o 100 metrow chorusem.", "", 0));
        taskMap.put("placeInvisibleFrame", new Task("Powies niewidzialna ramke.", "", 0));
        taskMap.put("destroyNetheriteSword", new Task("Zuzyj netherytowy miecz.", "", 0));
        taskMap.put("fishOutInkSac", new Task("Wylow torbiel.", "", 0));
        taskMap.put("killWarden", new Task("Pokonaj Wardena.", "", 0));
        taskMap.put("craftBeacon", new Task("Zrob beacona.", "", 0));
        taskMap.put("doShit", new Task("Zrob kupe.", "", 0));
        taskMap.put("winRaid", new Task("Wygraj raida.", "", 0));
        taskMap.put("doubleItemAtBlacksmith", new Task("Podwoj przedmiot u kowala.", "", 0));
        taskMap.put("setSpawnerToZombie", new Task("Ustaw zombie w spawnerze.", "", 0));
        taskMap.put("spawnWitch", new Task("Zresp wiedzme jajkiem.", "", 0));
        taskMap.put("sleepThruNights", new Task("Przespij 3 noce.", "counter", 3));
        taskMap.put("obtainNetherWarts", new Task("Dropnij 32 brodawki z piglinow.", "counter", 32));
        taskMap.put("breakEmeralds", new Task("Rozkop 6 emeraldow.", "counter", 6));
        taskMap.put("breakDiamonds", new Task("Rozkop stack diaxow.", "counter", 64));
        taskMap.put("killIronGolems", new Task("Zabij 24 zelazne golemy.", "counter", 24));
        taskMap.put("killSomeMobs", new Task("Zabij 100 dowolnych potworow.", "counter", 100));

        //second set (12)
        taskMap.put("killDragon", new Task("Pokonaj Smoka.", "", 0));
        taskMap.put("starveToDeath", new Task("Umrzyj z glodu.", "", 0));
        taskMap.put("getHitByLightningStrike", new Task("Oberwij piorunem.", "", 0));
        taskMap.put("timeoutRaid", new Task("Przeczekaj atak na wioske.", "", 0));
        taskMap.put("getAchievementDone", new Task("Ukoncz dowolne osiagniecie.", "", 0));
        taskMap.put("listenToBossbar", new Task("Wysluchaj serwerowych ogloszen.", "", 0));
        taskMap.put("transformZombieToVillager", new Task("Badz blisko nowego wiesniaka z przemienienia.", "", 0));
        taskMap.put("stayCloseToSomeNewEndermite", new Task("Badz blisko nowego endermita z ender perly.", "", 0));
        taskMap.put("destroyItemsAtBlacksmith", new Task("Spal u kowala 3 przedmioty.", "counter", 3));
        taskMap.put("throwNumberUsingDice", new Task("Wyrzuc 12 razy liczbe 12 na kostce.", "counter", 12));
        taskMap.put("eatSuspiciousStew", new Task("Zjedz 8 podejrzanych potrawek.", "counter", 8));
        taskMap.put("getHitByDragon", new Task("Bezposrednio oberwij od smoka 6 razy.", "counter", 6));

        //third set (5)
        taskMap.put("beOnlineWhenTraderSpawns", new Task("Badz online, gdy pojawi sie handlarz.", "", 0));
        taskMap.put("destroyDiamondPickaxe", new Task("Zuzyj diamentowy kilof.", "", 0));
        taskMap.put("doSmallShit", new Task("Niech zbiera Ci sie na cos ciezszego.", "", 0));
        taskMap.put("openDragonChest", new Task("Otworz Magiczna Skrzynke.", "", 0));
        taskMap.put("useActiveTeleporter", new Task("Uzyj aktywnego Teleportera.", "", 0));

        //fourth set (2)
        taskMap.put("obtainChristmasBlessing", new Task("Otrzymaj Swiateczne Blogoslawienstwo, po prostu grajac.", "", 0));
        taskMap.put("getPrezent", new Task("Otrzymaj Prezent od Swietego Mikolaja, po prostu grajac.", "", 0));

        //fifth set
        taskMap.put("pickupCreeper", new Task("Podnies Creepera.", "", 0));
        taskMap.put("upgradeItemAtBlacksmith", new Task("Ulepsz przedmiot u kowala.", "", 0));
        taskMap.put("pressTrader", new Task("Wybierz oferte u dowolnego wiesniaka / handlarza.", "", 0));
        taskMap.put("writeHeartOnSign", new Task("Postaw \"serduszko\" w pierwszej linijce dowolnej tabliczki.", "", 0));

        //prepare list of tasks
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) taskList.add(entry.getValue());

        renderNewTasks();
    }
}
