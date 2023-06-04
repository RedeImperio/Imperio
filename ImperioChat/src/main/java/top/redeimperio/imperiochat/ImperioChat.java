package top.redeimperio.imperiochat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiochat.random.AutoMessages;

public final class ImperioChat extends JavaPlugin {

    public static ImperioChat Instance;
    @Override
    public void onEnable() {
        Instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("§9ImperioChat §7ativado com §asucesso!");

        // Iniciar as mensagens automáticas
        AutoMessages autoMessages = new AutoMessages();
        autoMessages.RunAutoMessages();

        ChatManager chatManager = new ChatManager(this);
        getServer().getPluginManager().registerEvents(chatManager, this);
        getCommand("g").setExecutor(chatManager);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
