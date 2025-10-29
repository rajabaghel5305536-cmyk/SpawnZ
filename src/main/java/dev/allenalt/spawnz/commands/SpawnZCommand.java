package dev.allenalt.spawnz.commands;

import dev.allenalt.spawnz.SpawnZ;
import dev.allenalt.spawnz.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SpawnZCommand implements CommandExecutor {
    private final SpawnZ plugin;

    public SpawnZCommand(SpawnZ plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("spawnz")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players!");
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                if (player.hasPermission("spawnz.admin")) {
                    MessageUtil.sendCenteredMessages(player, plugin.getConfig().getStringList("admin-help"));
                } else {
                    MessageUtil.sendCenteredMessages(player, plugin.getConfig().getStringList("player-help"));
                }
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "set":
                    if (!player.hasPermission("spawnz.admin")) {
                        player.sendMessage(MessageUtil.color(plugin.getConfig().getString("messages.no-permission")));
                        return true;
                    }
                    plugin.setSpawnLocation(player.getLocation());
                    player.sendMessage("§aSpawn location set!");
                    break;

                case "delete":
                    if (!player.hasPermission("spawnz.admin")) {
                        player.sendMessage(MessageUtil.color(plugin.getConfig().getString("messages.no-permission")));
                        return true;
                    }
                    plugin.deleteSpawnLocation();
                    player.sendMessage("§cSpawn location deleted!");
                    break;

                default:
                    player.sendMessage("§eUsage: /spawnz <set|delete|help>");
            }
            return true;
        }

        // /spawn command
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this!");
                return true;
            }

            Player player = (Player) sender;
            Location loc = plugin.getSpawnLocation();

            if (loc == null) {
                player.sendMessage(MessageUtil.color(plugin.getConfig().getString("messages.no-spawn")));
                return true;
            }

            int delay = plugin.getConfig().getInt("teleport-delay", 5);
            player.sendMessage(MessageUtil.color(plugin.getConfig().getString("messages.teleport").replace("{0}", String.valueOf(delay))));
            player.sendActionBar(MessageUtil.color(plugin.getConfig().getString("messages.actionbar-teleport")));

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.teleport(loc);
                player.sendMessage(MessageUtil.color(plugin.getConfig().getString("messages.teleported")));
                player.sendActionBar(MessageUtil.color(plugin.getConfig().getString("messages.actionbar-teleported")));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            }, delay * 20L);
            return true;
        }

        return false;
    }
}
