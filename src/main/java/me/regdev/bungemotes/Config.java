package me.regdev.bungemotes;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class Config {
    private final BungEmotes plugin;
    private final String CONFIG_NAME = "config.yml";
    public Config(BungEmotes plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeConfig() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        File file = new File(plugin.getDataFolder(), CONFIG_NAME);
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(CONFIG_NAME)) {
                Files.copy(in, file.toPath());
            }
        }
    }

    private Configuration getConfig() throws IOException {
        makeConfig();
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), CONFIG_NAME));
    }

    public HashMap<String, String> getConfigMap() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Configuration conf = getConfig();
        Configuration key = conf.getSection("emojis");
        if (key == null) {
            return null;
        }
        key.getKeys().forEach(s -> {
            if (key.getString(s) != null) {
                map.put(s, key.getString(s));
            }
        });
        return map;
    }
}
