package dev.allenalt.spawnz.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageUtil {
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg == null ? "" : msg);
    }

    public static void sendCenteredMessages(Player player, List<String> lines) {
        for (String line : lines) {
            player.sendMessage(color(line));
        }
    }
}
