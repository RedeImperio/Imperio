package top.redeimperio.imperiorandomstuff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ImperioRandomStuff extends JavaPlugin implements CommandExecutor, Listener {

    private String discordLink = "https://discord.gg/nzrZtGu2FC";
    private String regrasText = "Coloque aqui as regras do servidor.";
    private String aplicarLink = "https://exemplo.com/aplicar";
    private String vipSite = "https://exemplo.com/vip";

    @Override
    public void onEnable() {
        // Registrar os comandos
        getCommand("discord").setExecutor(this);
        getCommand("regras").setExecutor(this);
        getCommand("aplicar").setExecutor(this);
        getCommand("vip").setExecutor(this);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discord")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§aLink do Discord: " + discordLink);
            } else {
                sender.sendMessage("Link do Discord: " + discordLink);
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("regras")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§aRegras do servidor:\n" + regrasText);
            } else {
                sender.sendMessage("Regras do servidor:\n" + regrasText);
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("aplicar")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§aFormulário de aplicação da staff: " + aplicarLink);
            } else {
                sender.sendMessage("Formulário de aplicação da staff: " + aplicarLink);
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("vip")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("§aSite para comprar VIP: " + vipSite);
            } else {
                sender.sendMessage("Site para comprar VIP: " + vipSite);
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("imperio.staff")){
            e.getPlayer().setDisplayName(e.getPlayer().getName() + "*");
        }else{
            e.getPlayer().setDisplayName(e.getPlayer().getName());
        }
    }
}
