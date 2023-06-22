package top.redeimperio.imperiolobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.redeimperio.imperiolobby.other.LobbyScoreboard;


public class PlayerJoinListener implements Listener {

    private LobbyScoreboard scoreboard;

    public PlayerJoinListener(LobbyScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        event.setJoinMessage("");
        if (player.hasPermission("lobby.fly")) {
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setInvulnerable(true);

        player.performCommand("lobby");
        // Verificar permissões e enviar mensagem no chat
        String highestRankPermission = getHighestRankPermission(player);
        if (highestRankPermission != null) {
            String prefix = getRankPrefix(highestRankPermission);
            Bukkit.broadcastMessage(prefix + " " + playerName + " §6entrou no lobby!");
        }

        LobbyScoreboard score = new LobbyScoreboard();
        score.createScoreboard();
        score.showScoreboard(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    private String getHighestRankPermission(Player player) {
        String[] permissions = {"rank.ceo", "rank.admin", "rank.mod", "rank.dev", "rank.builder",
                "rank.ajd", "rank.old", "rank.beta", "rank.mvp", "rank.vip2", "rank.vip"};

        for (String permission : permissions) {
            if (player.hasPermission(permission)) {
                return permission;
            }
        }

        return null;
    }

    private String getRankPrefix(String permission) {
        switch (permission) {
            case "rank.ceo":
                return "§4(CEO)";
            case "rank.admin":
                return "§c(ADMIN)";
            case "rank.mod":
                return "§2(MOD)";
            case "rank.dev":
                return "§3(DEV)";
            case "rank.builder":
                return "§a(BUILDER)";
            case "rank.ajd":
                return "§7(AJD)";
            case "rank.old":
                return "§8(OLD)";
            case "rank.beta":
                return "§9(BETA)";
            case "rank.mvp":
                return "§e(MVP)";
            case "rank.vip2":
                return "§d(VIP+)";
            case "rank.vip":
                return "§b(VIP)";
            default:
                return null;
        }
    }
}
