package top.redeimperio.imperiocleaner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ImperioCleaner extends JavaPlugin {

    private int taskId;

    @Override
    public void onEnable() {
        startCleanupTask();
    }

    @Override
    public void onDisable() {
        cancelCleanupTask();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("limpar")) {
            removeItemsFromGround();
            Bukkit.broadcastMessage("§c( ! ) Os itens foram removidos do chão!");
            return true;
        }
        return false;
    }

    private void startCleanupTask() {
        BukkitRunnable task = new BukkitRunnable() {
            int countdown = 600;

            @Override
            public void run() {
                if (countdown == 300) {
                    Bukkit.broadcastMessage("§c(!) Os itens serão removidos do chão em 5 minutos!");
                } else if (countdown == 60) {
                    Bukkit.broadcastMessage("\n§c(!) Os itens serão removidos do chão em 1 minuto!\n");
                } else if (countdown <= 3 && countdown> 0) {
                    Bukkit.broadcastMessage("§c(!) Os itens serão removidos do chão em " + countdown + " segundos!");
                }

                if (countdown <= 0) {
                    removeItemsFromGround();
                    Bukkit.broadcastMessage("§c(!) Os itens foram removidos do chão!");
                    cancelCleanupTask();
                    startCleanupTask();
                } else {
                    countdown--;
                }
            }
        };

        taskId = task.runTaskTimer(this, 20L, 20L).getTaskId();
    }

    private void cancelCleanupTask() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    private void removeItemsFromGround() {
        for (World world : Bukkit.getWorlds()) {
            for (Item item : world.getEntitiesByClass(Item.class)) {
                item.remove();
            }
        }
    }
}
