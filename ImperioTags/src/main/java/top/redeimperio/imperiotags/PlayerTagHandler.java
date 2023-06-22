package top.redeimperio.imperiotags;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import top.redeimperio.imperiotags.ImperioTags;

public class PlayerTagHandler {

    public static String getFormattedName(Player player) {
        String playerName = player.getName();
        Tag playerTag = ImperioTags.instance.getPlayerTag(player.getUniqueId());
        String tag = playerTag != null ? playerTag.getPrefix() : "";

        if (tag != null) {
            return PlaceholderAPI.setPlaceholders(player, tag + " " + playerName);
        } else {
            return playerName;
        }
    }
}
