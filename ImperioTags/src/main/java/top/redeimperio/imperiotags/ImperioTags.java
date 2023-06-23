package top.redeimperio.imperiotags;

import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiotags.commands.TagCommand;
import top.redeimperio.imperiotags.commands.TagsCommand;

import java.util.*;

public class ImperioTags extends JavaPlugin {

    private Map<UUID, Tag> playerTags = new HashMap<>();
    private List<Tag> tags = new ArrayList<>();
    public static ImperioTags instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("tag").setExecutor(new TagCommand());
        getCommand("tags").setExecutor(new TagsCommand()); // Adicionando o comando /tags
        loadTags(); // Carregando as tags do arquivo de configuração
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
        return null;
    }

    public Tag getTagByName(String name) {
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }

    public List<Tag> getTags() {
        return tags;
    }

    private void loadTags() {
        // Carregar as tags do arquivo de configuração ou qualquer outra fonte de dados
        // Exemplo:
        tags.add(new Tag("ceo", "§0(CEO)§f", "§b"));
        tags.add(new Tag("admin", "§c(ADM)","§fb"));
        tags.add(new Tag("dev", "§d(DEV)","§a"));
        tags.add(new Tag("mod", "§2(MOD)","§a"));
        tags.add(new Tag("ajd", "§9(AJD)","§d"));
        tags.add(new Tag("builder", "§6(Builder)","§d"));
        tags.add(new Tag("old", "&0&k!&6(OLD)&0&k!&6","§6"));
        tags.add(new Tag("beta", "§l§1(BETA)&l§1","§6"));
        tags.add(new Tag("imperador", "§4(Imperador)","§6"));
        tags.add(new Tag("campeao", "§3(Campeão)","§6"));
        tags.add(new Tag("nobre", "§6(Nobre)","§6"));
        tags.add(new Tag("membro", "§7","§7"));
    }

}
