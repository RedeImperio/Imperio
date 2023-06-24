package top.redeimperio.imperiolobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Tag;
import top.redeimperio.imperiotags.Medal;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message = event.getMessage();


        // Obtém a tag do jogador
        Tag playerTag = ImperioTags.instance.getPlayerTag(player.getUniqueId());
        Medal playerMedal = ImperioTags.instance.getPlayerMedal(player.getUniqueId());

        String medal = playerMedal.getPrefix();

        if(playerMedal == null) { medal = "";}

        // Verifica se o jogador possui uma tag
        if (playerTag != null) {
            String formattedMessage = medal + playerTag.getPrefix() + " " + playerName + ": §f" + message;
            event.setCancelled(true);
            Bukkit.broadcastMessage(formattedMessage);
        }
    }
}
