package top.redeimperio.imperiostaff;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiostaff.commands.FreezeCommand;
import top.redeimperio.imperiostaff.commands.StaffChatCommand;

public final class ImperioStaff extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("§9ImperioStaff §7ativado com §asucesso!");

        EnableCommands();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§9ImperioStaff §7foi §cdesligado!");
    }

    public void EnableCommands(){
        getCommand("sc").setExecutor(new StaffChatCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());

    }
}
