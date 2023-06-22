package top.redeimperio.imperiolobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message = event.getMessage();

        // Altere o formato da mensagem para "Nickname: mensagem"
        String formattedMessage = "§7" + playerName + ": " + message;

        // Defina a mensagem formatada para o evento
        event.setMessage(formattedMessage);
    }
}
