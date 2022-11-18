package claimworld.net.supporter.utils.tasks;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.battlepass.BattlePassManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.*;

public class TaskManager {

    private static TaskManager instance = null;

    public static TaskManager getInstance() {
        if (instance == null) {instance = new TaskManager();}
        return instance;
    }

    public static String getUserQuestDataObjectiveName = "userquestdata";

    private final List<Task> taskList = new ArrayList<>();
    private final List<Task> activeTasks = new ArrayList<>();
    private final List<String> playersWhoDidTask = new ArrayList<>();

    public List<Task> getTasks() {
        return taskList;
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
        if (playersWhoDidTask.size() > 0) return false;

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

        return true;
    }

    public void tryFinishTask(Player player, Task task) {
        getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
            for (Task activeTask : getActiveTasks()) {
                if (!activeTask.getName().equals(task.getName())) continue;

                String playerName = player.getName();
                if (playersWhoDidTask.contains(playerName)) return;

                if (task.getData().equals("counter")) {
                    Objective objective = player.getScoreboard().getObjective(getUserQuestDataObjectiveName);
                    assert objective != null;

                    Score score = objective.getScore(playerName);
                    score.setScore(score.getScore() + 1);

                    if (!(score.getScore() < task.getNumber())) {
                        playersWhoDidTask.add(playerName);
                        BattlePassManager.getInstance().updatePoints(player, 1);
                    }

                    break;
                }

                playersWhoDidTask.add(playerName);
                BattlePassManager.getInstance().updatePoints(player, 1);
            }
        });
    }

    public TaskManager() {
        //first set
        taskList.add(new Task("Przemiesc sie o 100 metrow chorusem.", "", 0));
        taskList.add(new Task("Powies niewidzialna ramke.", "", 0));
        taskList.add(new Task("Zniszcz netherytowy miecz.", "", 0));
        taskList.add(new Task("Wylow torbiel.", "", 0));
        taskList.add(new Task("Pokonaj wardena.", "", 0));
        taskList.add(new Task("Zrob beacona.", "", 0));
        taskList.add(new Task("Zrob kupe.", "", 0));
        taskList.add(new Task("Ukoncz raida.", "", 0));
        taskList.add(new Task("Podwoj przedmiot.", "", 0));
        taskList.add(new Task("Ustaw zombie w spawnerze.", "", 0));
        taskList.add(new Task("Zresp wiedzme.", "", 0));
        taskList.add(new Task("Przespij 3 noce.", "counter", 3));
        taskList.add(new Task("Aktywuj Mob Killera 32 razy.", "counter", 32));
        taskList.add(new Task("Dropnij 32 brodawki.", "counter", 32));
        taskList.add(new Task("Rozkop kilka emeraldow.", "counter", 4));
        taskList.add(new Task("Rozkop stack diaxow.", "counter", 64));
        taskList.add(new Task("Zabij 24 golemy.", "counter", 24));
        taskList.add(new Task("Zabij 208 potworow.", "counter", 208));

        //second set
        taskList.add(new Task("Pokonaj Smoka.", "", 0));
        taskList.add(new Task("Umrzyj z glodu.", "", 0));
        taskList.add(new Task("Oberwij od admina.", "", 0));
        taskList.add(new Task("Przeczekaj swiatowy atak.", "", 0));
        taskList.add(new Task("Ukoncz osiagniecie.", "", 0));
        taskList.add(new Task("Wysluchaj ogloszen.", "", 0));
        taskList.add(new Task("Spal 4 przedmioty.", "counter", 4));
        taskList.add(new Task("Parszywa 12stka... 12 razy?", "counter", 12));
        taskList.add(new Task("Nafaszeruj sie dziwna zupa.", "counter", 8));
        taskList.add(new Task("Pooddychaj chwile smokiem.", "counter", 8));
        taskList.add(new Task("Badz blisko 8 nowych endermitow.", "counter", 8));

        //check if it's working
        taskList.add(new Task("Wesprzyj leczenie wiesniaka.", "", 0));

        renderNewTasks();
    }
}
