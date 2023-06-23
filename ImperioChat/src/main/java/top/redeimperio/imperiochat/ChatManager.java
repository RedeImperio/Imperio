package top.redeimperio.imperiochat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Tag;

public class ChatManager implements Listener, CommandExecutor {
    private Plugin plugin;

    public ChatManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String message = event.getMessage();

        // Percorrer todos os jogadores online
        for (Player recipient : Bukkit.getOnlinePlayers()) {
            // Verificar se o jogador está dentro do alcance
            if (sender.getLocation().distanceSquared(recipient.getLocation()) <= 100 * 100) {
                // Enviar a mensagem para o jogador próximo
                Tag tag = ImperioTags.instance.getPlayerTag(sender.getUniqueId());
                recipient.sendMessage("§e[l] "+ tag.getPrefix() + " " + sender.getDisplayName() + ": §e" + message);
            }
        }

        // Cancelar o evento para evitar que a mensagem seja enviada para todos os jogadores
        event.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("g")) {
                if (args.length >= 1) {
                    // Concatenar a mensagem enviada pelo jogador
                    String message = String.join(" ", args);

                    // Enviar a mensagem para todos os jogadores online
                    for (Player recipient : Bukkit.getOnlinePlayers()) {
                        Tag tag = ImperioTags.instance.getPlayerTag(player.getUniqueId());
                        recipient.sendMessage("§7[g] " + tag.getPrefix() + " " + player.getDisplayName() + ": " + tag.getChatColor() + message);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Uso incorreto! Utilize /g <mensagem>");
                }
            }
        }

        return true;
    }
}
