package top.redeimperio.imperiospawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ImperioSpawn extends JavaPlugin implements CommandExecutor, Listener {
    private Location spawnLocation;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        loadConfig();

        if (config.contains("spawn")) {
            spawnLocation = getLocationFromConfig(config.getConfigurationSection("spawn"));
        }

        // Registra o comando e o evento
        getCommand("setspawn").setExecutor(this);
        getCommand("spawn").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (spawnLocation != null) {
            config.set("spawn", getLocationAsConfig(spawnLocation));
            saveConfigFile();
        }

    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void saveConfigFile() {
        try {
            config.save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Este comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;
            spawnLocation = player.getLocation();
            player.sendMessage("§aO spawn foi definido em sua localização!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEste comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;
            if (spawnLocation == null) {
                player.sendMessage("§eO spawn ainda não foi definido.");
                return true;
            }

            player.teleport(spawnLocation);
            player.sendMessage("§aVocê foi teleportado para o spawn.");
            return true;
        }

        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (spawnLocation != null) {
            player.teleport(spawnLocation);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (spawnLocation != null) {
            event.setRespawnLocation(spawnLocation);
        }
    }

    private Location getLocationFromConfig(ConfigurationSection configSection) {
        double x = configSection.getDouble("x");
        double y = configSection.getDouble("y");
        double z = configSection.getDouble("z");
        float yaw = (float) configSection.getDouble("yaw");
        float pitch = (float) configSection.getDouble("pitch");
        String worldName = configSection.getString("world");
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    private ConfigurationSection getLocationAsConfig(Location location) {
        FileConfiguration config = new YamlConfiguration();
        ConfigurationSection configSection = config.createSection("spawn");
        configSection.set("x", location.getX());
        configSection.set("y", location.getY());
        configSection.set("z", location.getZ());
        configSection.set("yaw", location.getYaw());
        configSection.set("pitch", location.getPitch());
        configSection.set("world", location.getWorld().getName());
        return configSection;
    }
}
