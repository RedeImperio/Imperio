package top.redeimperio.imperiotags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Medal;
import top.redeimperio.imperiotags.Tag;

import java.util.List;

public class MedalsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        List<Medal> medals = ImperioTags.instance.getMedals();

        if (medals.isEmpty()) {
            sender.sendMessage("§cNão há medalhas disponíveis.");
            return true;
        }

        sender.sendMessage("§9Insignias disponíveis:");
        for (Medal medal : medals) {
            sender.sendMessage("§7- " + medal.getPrefix());
        }

        return true;
    }
}
