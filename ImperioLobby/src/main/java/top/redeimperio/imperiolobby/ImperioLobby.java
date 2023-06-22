package top.redeimperio.imperiolobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiolobby.commands.Lobby;
import top.redeimperio.imperiolobby.commands.SetLobby;
import top.redeimperio.imperiolobby.listeners.PlayerChatListener;
import top.redeimperio.imperiolobby.listeners.PlayerJoinListener;
import top.redeimperio.imperiolobby.listeners.PlayerMoveListener;

public final class ImperioLobby extends JavaPlugin {

    public static ImperioLobby instance;

    FileConfiguration config;
    @Override
    public void onEnable() {
        instance = this;
        LoadConfig();

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§aImperioLobby iniciado com sucesso");
        Bukkit.dispatchCommand(console, "difficulty peaceful");
        // Registro dos eventos
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);

        // Registro dos comandos
        getCommand("lobby").setExecutor(new Lobby(getConfig()));
        getCommand("setlobby").setExecutor(new SetLobby());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§cDesligando ImperioLobby");
    }

    void LoadConfig(){
        saveDefaultConfig();
        config = getConfig();
    }

    public Location getLobbyLocation(Player player) {
        double x = config.getDouble("lobby.x");
        double y = config.getDouble("lobby.y");
        double z = config.getDouble("lobby.z");
        float yaw = (float) config.getDouble("lobby.yaw");
        float pitch = (float) config.getDouble("lobby.pitch");

        return new Location(player.getWorld(), x, y, z, yaw, pitch);
    }


}
