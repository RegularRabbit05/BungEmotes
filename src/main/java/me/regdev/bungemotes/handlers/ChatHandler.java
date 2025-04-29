package me.regdev.bungemotes.handlers;

import me.regdev.bungemotes.BungEmotes;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;

public class ChatHandler implements Listener {
    @SuppressWarnings("FieldCanBeLocal")
    private final BungEmotes plugin;
    public ChatHandler(BungEmotes plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChatEvent(ChatEvent event) {
        if (event.isCancelled() || event.isCommand()) return;
        String message = event.getMessage();
        synchronized (plugin) {
            HashMap<String, String> emojis = plugin.getEmojiTable();
            String[] split = message.split("\\s+");
            for (int i = 0; i < split.length; i++) {
                int finalI = i;
                emojis.forEach((s, k) -> {
                    if (s.equalsIgnoreCase(split[finalI])) split[finalI] = k;
                });
            }
            StringBuilder builder = new StringBuilder();
            for (String part : split) builder.append(part).append(' ');
            message = builder.toString();
        }
        if (!message.equals(event.getMessage())) {
            event.setCancelled(true);
            ProxiedPlayer p = (ProxiedPlayer) event.getSender();
            String finalMessage = message;
            p.getServer().getInfo().getPlayers().forEach(player -> player.sendMessage(new TextComponent(String.format("<%s> %s", p.getName(), finalMessage))));
        }
    }
}
