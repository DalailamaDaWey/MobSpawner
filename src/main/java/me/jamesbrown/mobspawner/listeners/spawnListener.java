package me.jamesbrown.mobspawner.listeners;
import me.jamesbrown.mobspawner.MobSpawner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class spawnListener implements Listener {
    private final MobSpawner plugin;
    public spawnListener(MobSpawner plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn (CreatureSpawnEvent event){
        Entity entity = event.getEntity();
        if (!isSpawnable(entity)){
            event.setCancelled(true);
        }
    }

    private boolean isSpawnable (Entity entity){
        String entityTypeName = entity.getType().toString();
        return searchConfigValue(entityTypeName);
    }
    private boolean searchConfigValue(String typeNameOfEntity){
        final int numberOfTypesOfAggression = 3;
        final String pathOfSpawning = "mob-spawning";
        List<String> aggressionTypes = new ArrayList<>(numberOfTypesOfAggression);

        fillInAggressionTypes(aggressionTypes);

        for (String type : aggressionTypes){
            String path = pathOfSpawning + "." + type + "." + typeNameOfEntity;

            boolean valueOfConfig = plugin.getConfig().getBoolean(path);
            if (valueOfConfig){
                return true;
            }
        }
        return false;
    }
    private void fillInAggressionTypes(List<String> typesOfAggression){
        final String passiveMobs = "passive";
        final String neutralMobs = "neutral";
        final String hostileMobs = "hostile";

        typesOfAggression.add(passiveMobs);
        typesOfAggression.add(neutralMobs);
        typesOfAggression.add(hostileMobs);
    }
}
