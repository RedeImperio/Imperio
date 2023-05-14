package top.redeimperio.imperiostaff.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar se o comando foi executado por um jogador
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar a permissão do jogador
        if (!player.hasPermission("imperio.staff")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return true;
        }

        // Verificar se a mensagem foi fornecida
        if (args.length == 0) {
            player.sendMessage("§dUtilize /sc <mensagem>");
            return true;
        }

        // Construir a mensagem a ser enviada aos staffs
        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String message = messageBuilder.toString().trim();

        // Enviar mensagem aos staffs online
        for (Player staff : player.getServer().getOnlinePlayers()) {
            if (staff.hasPermission("imperio.staff")) {
                staff.sendMessage("§9[StaffChat]§d " + player.getName() + ": " + message);
            }
        }

        return true;
    }
}
