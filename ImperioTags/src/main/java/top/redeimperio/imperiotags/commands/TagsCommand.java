package top.redeimperio.imperiotags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Tag;

import java.util.List;

public class TagsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        List<Tag> tags = ImperioTags.instance.getTags();

        if (tags.isEmpty()) {
            sender.sendMessage("Não há tags disponíveis.");
            return true;
        }

        sender.sendMessage("Tags disponíveis:");
        for (Tag tag : tags) {
            sender.sendMessage("- " + tag.getName());
        }

        return true;
    }
}
