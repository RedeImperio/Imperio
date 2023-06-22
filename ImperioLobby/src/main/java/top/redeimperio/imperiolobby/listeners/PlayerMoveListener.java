package top.redeimperio.imperiolobby.listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private FileConfiguration config;

    public PlayerMoveListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();

        if (world.getEnvironment() == World.Environment.NORMAL && location.getY() < 0) {
            Location lobbyLocation = getLobbyLocation(player);

            if (lobbyLocation != null) {
                player.teleport(lobbyLocation);
            }
        }
    }

    private Location getLobbyLocation(Player player) {
        double x = config.getDouble("lobby.x");
        double y = config.getDouble("lobby.y");
        double z = config.getDouble("lobby.z");
        float yaw = (float) config.getDouble("lobby.yaw");
        float pitch = (float) config.getDouble("lobby.pitch");

        return new Location(player.getWorld(), x, y, z, yaw, pitch);
    }
}
