package me.regdev.bungemotes.commands;

import me.regdev.bungemotes.BungEmotes;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;

public class EmojisCommand extends Command {
    private final BungEmotes plugin;
    public EmojisCommand(BungEmotes plugin) {
        super("emojis");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            ComponentBuilder builder = new ComponentBuilder();
            TextComponent l1 = new TextComponent("Emojis:\n");
            l1.setColor(ChatColor.GOLD);
            builder.append(l1);
            HashMap<String, String> emojis = plugin.getEmojiTable();
            emojis.forEach((key, value) -> {
                TextComponent ln = new TextComponent("| " + key + " -> " + value + "\n");
                ln.setColor(ChatColor.WHITE);
                ln.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, key));
                builder.append(ln);
            });
            commandSender.sendMessage(builder.create());
            return;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            if (commandSender instanceof ProxiedPlayer && !commandSender.hasPermission("bungemotes.reload")) {
                TextComponent l1 = new TextComponent("You don't have permission to reload the plugin!");
                l1.setColor(ChatColor.RED);
                commandSender.sendMessage(l1);
                return;
            }
            TextComponent comp;
            synchronized (plugin) {
                comp = new TextComponent(plugin.loadEmojiTable() ? "Reloaded emojis" : "Failed reloading the table");
            }
            comp.setColor(ChatColor.GREEN);
            commandSender.sendMessage(comp);
        }
    }
}
