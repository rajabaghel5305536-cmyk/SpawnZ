package dev.allenalt.spawnz;

import dev.allenalt.spawnz.commands.SpawnZCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnZ extends JavaPlugin {

    private static SpawnZ instance;
    private Location spawnLocation;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadSpawn();

        getCommand("spawnz").setExecutor(new SpawnZCommand(this));
        getCommand("spawn").setExecutor(new SpawnZCommand(this));

        getLogger().info("SpawnZ plugin enabled by Dev_Allenalt_tw");
    }

    @Override
    public void onDisable() {
        getLogger().info("SpawnZ disabled.");
    }

    public static SpawnZ getInstance() {
        return instance;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location location) {
        spawnLocation = location;
        FileConfiguration config = getConfig();
        config.set("spawn.world", location.getWorld().getName());
        config.set("spawn.x", location.getX());
        config.set("spawn.y", location.getY());
        config.set("spawn.z", location.getZ());
        config.set("spawn.yaw", location.getYaw());
        config.set("spawn.pitch", location.getPitch());
        saveConfig();
    }

    public void deleteSpawnLocation() {
        spawnLocation = null;
        FileConfiguration config = getConfig();
        config.set("spawn", null);
        saveConfig();
    }

    private void loadSpawn() {
        FileConfiguration config = getConfig();
        if (config.contains("spawn.world")) {
            spawnLocation = new Location(
                Bukkit.getWorld(config.getString("spawn.world")),
                config.getDouble("spawn.x"),
                config.getDouble("spawn.y"),
                config.getDouble("spawn.z"),
                (float) config.getDouble("spawn.yaw"),
                (float) config.getDouble("spawn.pitch")
            );
        }
    }
}
