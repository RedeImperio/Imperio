package top.redeimperio.imperiochat.random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoMessages extends JavaPlugin {

    private List<String> messages = new ArrayList<>();
    private Random random = new Random();
    private int taskId;

    @Override
    public void onEnable() {
        // Carregar as mensagens do arquivo de configuração
        loadMessages();

        // Iniciar a tarefa de envio de mensagens
        startMessageTask();
    }

    @Override
    public void onDisable() {
        // Cancelar a tarefa de envio de mensagens ao desativar o plugin
        cancelMessageTask();
    }

    private void loadMessages() {
        // Carregar as mensagens do arquivo de configuração ou definir mensagens padrão
        List<String> configMessages = getConfig().getStringList("messages");

        if (configMessages != null && !configMessages.isEmpty()) {
            messages.addAll(configMessages);
        } else {
            // Definir mensagens padrão caso o arquivo de configuração não tenha sido configurado
            messages.add("Bem-vindo ao servidor!");
            messages.add("Divirta-se jogando!");
            messages.add("Lembre-se de respeitar as regras.");
        }
    }

    private void startMessageTask() {
        // Agendar uma tarefa para enviar uma mensagem a cada 30 segundos
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            // Selecionar uma mensagem aleatória
            String message = getRandomMessage();

            // Enviar a mensagem para todos os jogadores online
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        }, 0, 30 * 20); // 30 segundos (em ticks)

        // Registrar uma mensagem no console informando que a tarefa foi iniciada
        getLogger().info("Tarefa de mensagens automáticas iniciada.");
    }

    private void cancelMessageTask() {
        // Cancelar a tarefa de envio de mensagens
        Bukkit.getScheduler().cancelTask(taskId);

        // Registrar uma mensagem no console informando que a tarefa foi cancelada
        getLogger().info("Tarefa de mensagens automáticas cancelada.");
    }

    private String getRandomMessage() {
        // Selecionar uma mensagem aleatória da lista de mensagens
        int index = random.nextInt(messages.size());
        return messages.get(index);
    }
}