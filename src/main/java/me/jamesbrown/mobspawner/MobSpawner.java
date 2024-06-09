package me.jamesbrown.mobspawner;

import me.jamesbrown.mobspawner.commands.deleteAllEntitiesCommand;
import me.jamesbrown.mobspawner.listeners.spawnListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MobSpawner extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new spawnListener(this), this);
        Objects.requireNonNull(getCommand("killall")).setExecutor(new deleteAllEntitiesCommand());
    }

}
