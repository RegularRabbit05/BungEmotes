package me.regdev.bungemotes;

import me.regdev.bungemotes.commands.EmojisCommand;
import me.regdev.bungemotes.handlers.ChatHandler;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;

public final class BungEmotes extends Plugin {
    private HashMap<String, String> emojiTable = new HashMap<>();
    public boolean loadEmojiTable() {
        Config config = new Config(this);
        try {
            HashMap<String, String> configMap = config.getConfigMap();
            if (emojiTable == null) throw new IOException("Config is invalid");
            emojiTable = configMap;
        } catch (IOException e) {
            emojiTable = new HashMap<>();
            this.getLogger().severe("Couldn't load emoji table: " + e.getMessage());
            return false;
        }
        return true;
    }

    public HashMap<String, String> getEmojiTable() {
        return emojiTable;
    }

    @Override
    public void onEnable() {
        if (!this.loadEmojiTable()) this.getLogger().warning("Errors in the configuration");
        this.getProxy().getPluginManager().registerListener(this, new ChatHandler(this));
        this.getProxy().getPluginManager().registerCommand(this, new EmojisCommand(this));
    }

    @Override
    public void onDisable() {
        this.getProxy().getPluginManager().unregisterListeners(this);
        this.getProxy().getPluginManager().unregisterCommands(this);
    }
}
