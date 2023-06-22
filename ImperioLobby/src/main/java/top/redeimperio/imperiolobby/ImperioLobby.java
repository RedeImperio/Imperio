package top.redeimperio.imperiolobby;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiolobby.commands.Lobby;
import top.redeimperio.imperiolobby.commands.SetLobby;
import top.redeimperio.imperiolobby.listeners.PlayerJoinListener;
import top.redeimperio.imperiolobby.listeners.PlayerMoveListener;

public final class ImperioLobby extends JavaPlugin {

    @Override
    public void onEnable() {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§aImperioLobby iniciado com sucesso");
        Bukkit.dispatchCommand(console, "difficulty peaceful");
        // Registro dos eventos
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(getConfig()), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Registro dos comandos
        getCommand("lobby").setExecutor(new Lobby(getConfig()));
        getCommand("setlobby").setExecutor(new SetLobby(getConfig()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§cDesligando ImperioLobby");
    }


}
