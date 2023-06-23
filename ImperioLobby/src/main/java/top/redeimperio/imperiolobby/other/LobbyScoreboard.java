package top.redeimperio.imperiolobby.other;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Tag;

public class LobbyScoreboard {
    private Scoreboard scoreboard;
    private Objective objective;
    private Team playerTeam;
    private Team onlineCountTeam;

    public void createScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            scoreboard = scoreboardManager.getNewScoreboard();
            objective = scoreboard.registerNewObjective("lobby", "dummy", "§6§lIMPERIO");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score0 = objective.getScore("");
            score0.setScore(8);

            Score score1 = objective.getScore(" ");
            score1.setScore(7);

            Score score2 = objective.getScore("Rank: §7" + ImperioTags.instance.getPlayerTag(player.getUniqueId()).getPrefix());
            score2.setScore(6);

            Score score3 = objective.getScore("Online: §a" + Bukkit.getOnlinePlayers().size());
            score3.setScore(5);

            Score score4 = objective.getScore("  ");
            score4.setScore(4);

            Score score5 = objective.getScore("Lobby: §a1");
            score5.setScore(3);

            Score score6 = objective.getScore(" ");
            score6.setScore(2);

            Score score7 = objective.getScore("§ewww.redeimperio.top");
            score7.setScore(1);

            showScoreboard(player); // Exibir a scoreboard para cada jogador online
        }

        startUpdateTask();
    }




    public void showScoreboard(Player player) {
        if (scoreboard != null) {
            player.setScoreboard(scoreboard);
        }
    }



    private void startUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
            }
        }.runTaskTimer(ImperioTags.instance, 0, 20); // Atualiza a cada segundo (20 ticks)
    }
}
