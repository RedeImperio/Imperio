package top.redeimperio.imperioeconomia;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ImperioEconomia extends JavaPlugin {

    private Map<UUID, Double> playerBalances;
    private FileConfiguration playerDataConfig;
    private File playerDataFile;
    private UUID magnataId;
    private double magnataBalance;

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        int magnataCheckInterval = getConfig().getInt("magnataCheckInterval", 300);

        // Inicializa o mapa de saldos dos jogadores
        playerBalances = new HashMap<>();

        // Carrega os dados dos jogadores do arquivo
        playerDataFile = new File(getDataFolder(), "playerdata.yml");
        if (!playerDataFile.exists()) {
            saveResource("playerdata.yml", false);
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        loadPlayerBalances();

        // Agendando a tarefa de verificação a cada x segundos
        Bukkit.getScheduler().runTaskTimer(this, this::checkMagnata, 0, magnataCheckInterval * 20);
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

            if (args.length >= 2 && args[0].equalsIgnoreCase("set")) {
                String playerName = args[1];
                double amount;

                try {
                    amount = Double.parseDouble(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cValor inválido. Use um número válido para o saldo.");
                    return true;
                }

                setPlayerBalance(playerName, amount);
                player.sendMessage("§aO saldo de §6" + playerName + "§a foi definido como §f" + amount + "§a coins.");
                return true;
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("top")) {
                sendTopRichestPlayers(player);
                return true;
            } else {
                double balance = getPlayerBalance(player.getUniqueId());
                player.sendMessage("§aSeu saldo é: §f" + balance + "§a coins.");
                return true;
            }
        }
        return false;
    }

    private double getPlayerBalance(UUID playerId) {
        return playerBalances.getOrDefault(playerId, 0.0);
    }

    private void setPlayerBalance(String playerName, double amount) {
        Player targetPlayer = getServer().getPlayer(playerName);

        if (targetPlayer != null) {
            UUID playerId = targetPlayer.getUniqueId();
            playerBalances.put(playerId, amount);
            playerDataConfig.set(playerId.toString(), amount);
            savePlayerBalances();
        } else {
            getLogger().warning("§cJogador não encontrado: §f" + playerName);
        }
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

    private void sendTopRichestPlayers(Player player) {
        List<Map.Entry<UUID, Double>> sortedBalances = new ArrayList<>(playerBalances.entrySet());
        sortedBalances.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        player.sendMessage("§aTOP 10 MAIS RICOS:");
        int count = 1;
        for (Map.Entry<UUID, Double> entry : sortedBalances) {
            UUID playerId = entry.getKey();
            double balance = entry.getValue();
            String playerName = getPlayerName(playerId);
            player.sendMessage("§f" + count + ". §6" + playerName + "§f - " + balance + " coins");
            count++;
            if (count > 10) {
                break;
            }
        }
    }

    private String getPlayerName(UUID playerId) {
        Player player = getServer().getPlayer(playerId);
        if (player != null) {
            return player.getName();
        }
        return "Jogador Offline";
    }

    private void checkMagnata() {
        double maxBalance = 0;
        UUID newMagnataId = null;

        for (Map.Entry<UUID, Double> entry : playerBalances.entrySet()) {
            UUID playerId = entry.getKey();
            double balance = entry.getValue();
            if (balance > maxBalance) {
                maxBalance = balance;
                newMagnataId = playerId;
            }
        }

        if (newMagnataId != null && (magnataId == null || !newMagnataId.equals(magnataId))) {
            magnataId = newMagnataId;
            magnataBalance = maxBalance;
            String playerName = getPlayerName(magnataId);
            Bukkit.broadcastMessage("\n§f " + playerName + "§a é o novo §6MAGNATA§a do servidor com a quantia de §f" + magnataBalance + "§a coins!\n");
        }
    }
}
