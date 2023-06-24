package top.redeimperio.imperiotags;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import top.redeimperio.imperiotags.commands.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ImperioTags extends JavaPlugin implements Listener {

    private List<Tag> tags = new ArrayList<>();
    private List<Medal> medals = new ArrayList<>();
    private FileConfiguration playerTagsConfig;
    public static ImperioTags instance;

    public Tag defaultTag;
    @Override
    public void onEnable() {
        instance = this;

        // Carrega as tags disponíveis
        loadTags();

        // Carrega o arquivo de tags dos jogadores
        File playerTagsFile = new File("D:\\Development\\Trivalent\\ImperioMC\\Database\\playertags.yml");
        playerTagsConfig = YamlConfiguration.loadConfiguration(playerTagsFile);

        getCommand("tag").setExecutor(new TagCommand());
        getCommand("tags").setExecutor(new TagsCommand());

        getCommand("medal").setExecutor(new MedalCommand());
        getCommand("medals").setExecutor(new MedalsCommand());

        getCommand("reloadtags").setExecutor(new ReloadTagsCommand());

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void setPlayerTag(UUID playerId, String tagName) {
        playerTagsConfig.set("players." + playerId.toString() + ".tag", tagName);
        savePlayerTagsConfig();
    }

    public Tag getPlayerTag(UUID playerId) {
        String tagName = playerTagsConfig.getString("players." + playerId.toString() + ".tag");

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
        return new Tag("membro", "§7", "§7");
    }

    public void setPlayerMedal(UUID playerId, String medalName) {
        playerTagsConfig.set("players." + playerId.toString() + ".medal", medalName);
        savePlayerTagsConfig();
    }
    public Tag getTagByName(String name) {
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }

    public Medal getPlayerMedal(UUID playerId) {
        String medalName = playerTagsConfig.getString("players." + playerId.toString() + ".medal");

        // Verifica se há uma tag configurada para o jogador
        if (medalName != null && !medalName.isEmpty()) {
            // Obtém a tag correspondente ao nome
            Medal medal = getMedalByName(medalName);

            // Verifica se a tag é válida
            if (medal != null) {
                return medal;
            }
        }

        // Retorna null se não houver)
        return null;
    }

    public Medal getMedalByName(String name) {
        for (Medal medal : medals) {
            if (medal.getName().equalsIgnoreCase(name)) {
                return medal;
            }
        }
        return null;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Medal> getMedals() {
        return medals;
    }

    private void loadTags() {
        File tagsFile = new File("D:\\Development\\Trivalent\\ImperioMC\\Database\\tags.yml");
        FileConfiguration tagsConfig = YamlConfiguration.loadConfiguration(tagsFile);

        if (tagsConfig.contains("tags")) {
            List<Map<?, ?>> tagList = tagsConfig.getMapList("tags");
            for (Map<?, ?> tagMap : tagList) {
                String name = (String) tagMap.get("name");
                String prefix = (String) tagMap.get("prefix");
                String color = (String) tagMap.get("color");

                if (name != null && prefix != null && color != null) {
                    tags.add(new Tag(name, prefix, color));
                }
            }
        }

        if (tagsConfig.contains("medals")) {
            List<Map<?, ?>> medalList = tagsConfig.getMapList("medals");
            for (Map<?, ?> tagMap : medalList) {
                String name = (String) tagMap.get("name");
                String prefix = (String) tagMap.get("prefix");

                if (name != null && prefix != null) {
                    medals.add(new Medal(name, prefix));
                }
            }
        }
    }

    public void reloadTags() {
        tags.clear();
        loadTags();
    }

    private void savePlayerTagsConfig() {
        try {
            File playerTagsFile = new File("D:\\Development\\Trivalent\\ImperioMC\\Database\\playertags.yml");
            playerTagsConfig.save(playerTagsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event){
        if(getPlayerTag(event.getPlayer().getUniqueId()) == null){
            setPlayerTag(event.getPlayer().getUniqueId(), "default");
        }
    }
}
