package top.redeimperio.imperiotags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;

public class ReloadTagsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reloadtags")) {
            if (sender instanceof Player && !sender.hasPermission("imperiotags.reloadtags")) {
                sender.sendMessage("Você não tem permissão para recarregar as tags.");
                return true;
            }

            // Chama o método para recarregar as tags
            ImperioTags.instance.reloadTags();

            sender.sendMessage("As tags foram recarregadas com sucesso.");
            return true;
        }
        return false;
    }
}
