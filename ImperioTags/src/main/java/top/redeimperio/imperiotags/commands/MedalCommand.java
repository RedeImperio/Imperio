package top.redeimperio.imperiotags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;
import top.redeimperio.imperiotags.Medal;
import top.redeimperio.imperiotags.Tag;

import java.util.ArrayList;
import java.util.List;

public class MedalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§eUso incorreto do comando. Use §6/badge §f<nome>");
            return true;
        }

        String medalName = args[0]; // Obtém o nome da tag a partir do argumento

        // Obtenha a tag correspondente ao nome
        Medal medal = ImperioTags.instance.getMedalByName(medalName);


        if (medal == null) {
            sender.sendMessage("§eA insignia '" + medalName + "' não existe.");
            return true;
        }

        if (!player.hasPermission("medal." + medal.getName())) {
            sender.sendMessage("§cVocê não possui permissão para usar essa insignia.");
            return true;
        }

        // Aplica a tag ao jogador
        ImperioTags.instance.setPlayerMedal(player.getUniqueId(), medal.getName());
        sender.sendMessage("§eSua insignia foi alterada para: " + medal.getPrefix());

        return true;
    }
}