package top.redeimperio.imperiohomes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImperioHomes extends JavaPlugin {

    private Map<UUID, Map<String, Location>> playerHomes;
    private FileConfiguration homesConfig;
    private File homesFile;

    @Override
    public void onEnable() {
        playerHomes = new HashMap<>();
        homesFile = new File(getDataFolder(), "homes.yml");
        if (!homesFile.exists()) {
            saveResource("homes.yml", false);
        }
        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        loadHomes();
    }

    @Override
    public void onDisable() {
        saveHomes();
        playerHomes.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEsse comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage("§cUso correto: /sethome <nomedacasa>");
                return true;
            }

            String homeName = args[0].toLowerCase();

            if (!isValidHomeName(homeName)) {
                player.sendMessage("§cNome de casa inválido. Use apenas letras, números e underscores (_).");
                return true;
            }

            if (!hasMaxHomes(player)) {
                player.sendMessage("§cVocê já atingiu o limite máximo de casas.");
                return true;
            }

            setPlayerHome(player, homeName);
            player.sendMessage("§aA casa §6" + homeName + "§a foi definida com sucesso.");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("home")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEsse comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage("§cUso correto: /home <nomedacasa>");
                return true;
            }

            String homeName = args[0].toLowerCase();

            if (!isValidHomeName(homeName)) {
                player.sendMessage("§cNome de casa inválido. Use apenas letras, números e underscores (_).");
                return true;
            }

            if (!hasPlayerHome(player, homeName)) {
                player.sendMessage("§cA casa §6" + homeName + "§c não existe.");
                return true;
            }

            teleportToHome(player, homeName);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("delhome")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEsse comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage("§cUso correto: /delhome <nomedacasa>");
                return true;
            }

            String homeName = args[0].toLowerCase();

            if (!isValidHomeName(homeName)) {
                player.sendMessage("§cNome de casa inválido. Use apenas letras, números e underscores (_).");
                return true;
            }

            if (!hasPlayerHome(player, homeName)) {
                player.sendMessage("§cA casa §6" + homeName + "§c não existe.");
                return true;
            }

            deletePlayerHome(player, homeName);
            player.sendMessage("§aA casa §6" + homeName + "§a foi deletada.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("homes")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Esse comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;
            Map<String, Location> homes = getPlayerHomes(player);

            if (homes.isEmpty()) {
                player.sendMessage(ChatColor.YELLOW + "Você não tem nenhuma home definida.");
                return true;
            }

            player.sendMessage(ChatColor.YELLOW + "Suas homes:");

            for (String homeName : homes.keySet()) {
                player.sendMessage(ChatColor.YELLOW + "- " + homeName);
            }

            return true;
        }

        return false;
    }

    private boolean isValidHomeName(String homeName) {
        return homeName.matches("[A-Za-z0-9_]+");
    }

    private boolean hasMaxHomes(Player player) {
        int maxHomes = 3;

        if (player.hasPermission("rank.vip")) {
            maxHomes = 5;
        } else if (player.hasPermission("rank.vip2")) {
            maxHomes = 7;
        } else if (player.hasPermission("rank.mvp")) {
            maxHomes = 10;
        }

        return getPlayerHomes(player).size() < maxHomes;
    }

    private Map<String, Location> getPlayerHomes(Player player) {
        return playerHomes.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>());
    }

    private boolean hasPlayerHome(Player player, String homeName) {
        return getPlayerHomes(player).containsKey(homeName);
    }

    private void setPlayerHome(Player player, String homeName) {
        Map<String, Location> homes = getPlayerHomes(player);
        homes.put(homeName, player.getLocation());
        saveHomes();
    }

    private void deletePlayerHome(Player player, String homeName) {
        Map<String, Location> homes = getPlayerHomes(player);
        homes.remove(homeName);
        saveHomes();
    }

    private void teleportToHome(Player player, String homeName) {
        Map<String, Location> homes = getPlayerHomes(player);
        Location homeLocation = homes.get(homeName);
        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendMessage("§aTeleportado para a casa §6" + homeName + "§a.");
        }
    }

    private void saveHomes() {
        for (Map.Entry<UUID, Map<String, Location>> entry : playerHomes.entrySet()) {
            UUID playerId = entry.getKey();
            Map<String, Location> homes = entry.getValue();

            ConfigurationSection playerSection = homesConfig.createSection(playerId.toString());
            for (Map.Entry<String, Location> homeEntry : homes.entrySet()) {
                String homeName = homeEntry.getKey();
                Location homeLocation = homeEntry.getValue();
                String locationString = serializeLocation(homeLocation);
                playerSection.set(homeName, locationString);
            }
        }

        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            getLogger().severe("Erro ao salvar o arquivo de homes: " + e.getMessage());
        }
    }

    private void loadHomes() {
        ConfigurationSection configSection = homesConfig.getConfigurationSection("");
        if (configSection != null) {
            for (String playerIdString : configSection.getKeys(false)) {
                UUID playerId = UUID.fromString(playerIdString);
                ConfigurationSection playerSection = configSection.getConfigurationSection(playerIdString);
                if (playerSection != null) {
                    Map<String, Location> homes = new HashMap<>();
                    for (String homeName : playerSection.getKeys(false)) {
                        String locationString = playerSection.getString(homeName);
                        Location homeLocation = deserializeLocation(locationString);
                        if (homeLocation != null) {
                            homes.put(homeName, homeLocation);
                        }
                    }
                    playerHomes.put(playerId, homes);
                }
            }
        }
    }

    private String serializeLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    private Location deserializeLocation(String locationString) {
        String[] parts = locationString.split(",");
        if (parts.length == 6) {
            String worldName = parts[0];
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
        return null;
    }

}
