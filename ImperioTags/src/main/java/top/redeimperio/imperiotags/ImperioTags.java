package top.redeimperio.imperiotags;

import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiotags.commands.TagCommand;

import java.util.*;

public class ImperioTags extends JavaPlugin {

    private Map<UUID, Tag> playerTags = new HashMap<>();
    public static ImperioTags instance;
    private Tag defaultTag;
    private List<Tag> tags = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        getCommand("tag").setExecutor(new TagCommand());

        // Configuração das tags
        tags.add(new Tag("ceo", "§4(CEO)"));
        tags.add(new Tag("admin", "§c(ADM)"));
        tags.add(new Tag("dev", "§3(DEV)"));
        tags.add(new Tag("mod", "§e(MOD)"));
        tags.add(new Tag("ajd", "§9(AJD)"));
        tags.add(new Tag("builder", "§6(Builder)"));
        tags.add(new Tag("imperador", "§4(Imperador)"));
        tags.add(new Tag("campeao", "§3(Campeão)"));
        tags.add(new Tag("nobre", "§6(Nobre)"));

        defaultTag = new Tag("default", "§7"); // Tag padrão (vazia)

        // Carregar configuração
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void setPlayerTag(UUID playerId, Tag tag) {
        playerTags.put(playerId, tag);

        getConfig().set("tags." + playerId.toString(), tag.getName());
        saveConfig();
    }

    public Tag getPlayerTag(UUID playerId) {
        String tagName = getConfig().getString("tags." + playerId.toString());

        // Verifica se há uma tag configurada para o jogador
        if (tagName != null && !tagName.isEmpty()) {
            // Obtém a tag correspondente ao nome
            Tag tag = getTagByName(tagName);

            // Verifica se a tag é válida
            if (tag != null) {
                return tag;
            }
        }

        // Retorna a tag padrão (ou null se não houver)
        return defaultTag;
    }

    public Tag getTagByName(String name) {
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }


}
