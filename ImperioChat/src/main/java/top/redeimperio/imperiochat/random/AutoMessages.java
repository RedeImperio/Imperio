package top.redeimperio.imperiochat.random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import top.redeimperio.imperiochat.ImperioChat;

import java.util.ArrayList;
import java.util.List;

public class AutoMessages {

    private List<String> messages = new ArrayList<>();
    private int currentIndex = 0;
    private int taskId;

    public void RunAutoMessages() {
        // Carregar as mensagens do arquivo de configuração
        loadMessages();

        // Iniciar a tarefa de envio de mensagens
        startMessageTask();
    }

    private void loadMessages() {
        // Obter o arquivo de configuração
        FileConfiguration config = ImperioChat.Instance.getConfig();

        // Carregar as mensagens do arquivo de configuração
        List<String> configMessages = config.getStringList("messages");

        if (configMessages != null && !configMessages.isEmpty()) {
            // Processar cada mensagem no arquivo de configuração
            for (String configMessage : configMessages) {
                // Adicionar a mensagem com a cor ao array de mensagens
                messages.add(ChatColor.translateAlternateColorCodes('&', configMessage));
            }
        } else {
            // Definir mensagens padrão caso o arquivo de configuração não tenha sido configurado
            messages.add("&aBem-vindo ao servidor!");
            messages.add("&bDivirta-se jogando!");
            messages.add("&cLembre-se de respeitar as regras.");
        }
    }

    private void startMessageTask() {
        // Agendar uma tarefa para enviar uma mensagem a cada 30 segundos
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ImperioChat.Instance, () -> {
            // Verificar se há mensagens disponíveis
            if (!messages.isEmpty()) {
                // Obter a mensagem atual
                String message = messages.get(currentIndex);

                // Enviar a mensagem para todos os jogadores online
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));

                // Incrementar o índice da mensagem
                currentIndex++;

                // Verificar se o índice atingiu o tamanho máximo da lista de mensagens
                if (currentIndex >= messages.size()) {
                    // Reiniciar o índice para a primeira mensagem
                    currentIndex = 0;
                }
            }
        }, 0, 30 * 20); // 30 segundos (em ticks)

        // Registrar uma mensagem no console informando que a tarefa foi iniciada
        ImperioChat.Instance.getLogger().info("Tarefa de mensagens automáticas iniciada.");
    }

    public void cancelMessageTask() {
        // Cancelar a tarefa de envio de mensagens
        Bukkit.getScheduler().cancelTask(taskId);

        // Registrar uma mensagem no console informando que a tarefa foi cancelada
        ImperioChat.Instance.getLogger().info("Tarefa de mensagens automáticas cancelada.");
    }
}
