package top.redeimperio.imperiochat;

import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiochat.random.AutoMessages;

public final class ImperioChat extends JavaPlugin {

    public static ImperioChat Instance;
    @Override
    public void onEnable() {
        Instance = this;
        // Plugin startup logic
        AutoMessages autoMessages = new AutoMessages();
        autoMessages.RunAutoMessages();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
