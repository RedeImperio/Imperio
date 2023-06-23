package top.redeimperio.imperioeconomia;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ImperioEconomia extends JavaPlugin {

    private Map<UUID, Double> playerBalances;
    private FileConfiguration playerDataConfig;
    private File playerDataFile;

    @Override
    public void onEnable() {
        // Inicializa o mapa de saldos dos jogadores
        playerBalances = new HashMap<>();

        // Carrega os dados dos jogadores do arquivo
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        if (!playerDataFile.exists()) {
            saveResource("playerdata.yml", false);
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        loadPlayerBalances();
    }

    @Override
    public void onDisable() {
        // Salva os dados dos jogadores no arquivo
        savePlayerBalances();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("money")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEsse comando só pode ser executado por jogadores.");
                return true;
            }

            Player player = (Player) sender;
            double balance = getPlayerBalance(player.getUniqueId());
            player.sendMessage("Seu saldo é: " + balance + " coins.");
            return true;
        }
        return false;
    }

    private double getPlayerBalance(UUID playerId) {
        return playerBalances.getOrDefault(playerId, 0.0);
    }

    private void loadPlayerBalances() {
        for (String key : playerDataConfig.getKeys(false)) {
            UUID playerId = UUID.fromString(key);
            double balance = playerDataConfig.getDouble(key);
            playerBalances.put(playerId, balance);
        }
    }

    private void savePlayerBalances() {
        for (Map.Entry<UUID, Double> entry : playerBalances.entrySet()) {
            playerDataConfig.set(entry.getKey().toString(), entry.getValue());
        }

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
