package top.redeimperio.imperiotags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§eUso incorreto do comando. Use §6/tag §f<nome>");
            return true;
        }

        String tagName = args[0]; // Obtém o nome da tag a partir do argumento

        // Obtenha a tag correspondente ao nome
        Tag tag = ImperioTags.instance.getTagByName(tagName);

        if (tag == null) {
            sender.sendMessage("§eA tag '" + tagName + "' não existe.");
            return true;
        }

        if (!player.hasPermission("imperiotags.tag." + tag.getName())) {
            sender.sendMessage("§cVocê não possui permissão para usar essa tag.");
            return true;
        }

        // Aplica a tag ao jogador
        ImperioTags.instance.setPlayerTag(player.getUniqueId(), tag);
        sender.sendMessage("§eSua tag foi alterada para: " + tag.getPrefix());

        return true;
    }
}