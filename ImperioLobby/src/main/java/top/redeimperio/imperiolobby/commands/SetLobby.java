package top.redeimperio.imperiolobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.redeimperio.imperiolobby.ImperioLobby;

public class SetLobby implements CommandExecutor {


    FileConfiguration config;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();

        config = ImperioLobby.instance.getConfig();
        config.set("lobby.x", x);
        config.set("lobby.y", y);
        config.set("lobby.z", z);
        config.set("lobby.yaw", yaw);
        config.set("lobby.pitch", pitch);
        ImperioLobby.instance.saveConfig();
        player.sendMessage("§eA posição atual foi definida como o lobby!");

        return true;
    }
}