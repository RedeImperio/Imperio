package top.redeimperio.imperiostaff.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class FreezeCommand implements CommandExecutor {

    private static Set<Player> frozenPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar se o jogador que executou o comando é um jogador válido
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        // Verificar se o jogador tem permissão para usar o comando
        if (!player.hasPermission("imperio.staff.freeze")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        // Verificar se a sintaxe do comando está correta
        if (args.length < 1) {
            player.sendMessage("§dUso correto: /freeze <jogador>");
            return true;
        }

        // Obter o jogador alvo do comando
        Player target = Bukkit.getPlayer(args[0]);

        // Verificar se o jogador alvo existe e está online
        if (target == null || !target.isOnline()) {
            player.sendMessage("§cO jogador especificado não foi encontrado ou não está online.");
            return true;
        }

        if (frozenPlayers.contains(target)) {
            // Jogador já está congelado, então vamos descongelá-lo
            target.setInvulnerable(false);
            target.setWalkSpeed(0.2f); // Defina a velocidade de caminhada padrão do jogador (0.2 é o valor padrão)
            target.setFlySpeed(0.1f); // Defina a velocidade de voo padrão do jogador (0.1 é o valor padrão)

            frozenPlayers.remove(target);
            player.sendMessage("§aVocê descongelou o jogador §c" + target.getName() + ".");
            target.sendMessage("§aVocê foi descongelado por um Staff.");
        } else {
            // Congelar o jogador alvo
            target.setInvulnerable(true);
            target.setWalkSpeed(0);
            target.setFlySpeed(0);

            frozenPlayers.add(target);
            player.sendMessage("§aVocê congelou o jogador §c" + target.getName() + ".");
            target.sendMessage("§cVocê foi congelado por um Staff. Aguarde suas instruções.");
        }

        return true;
    }
}
