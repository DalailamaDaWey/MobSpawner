package me.jamesbrown.mobspawner.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class deleteAllEntitiesCommand implements CommandExecutor {
    final int numberOfDimensions = 3;
    private final String dim1 = "NORMAL";
    private final String dim2 = "NETHER";
    private final String dim3 = "THE_END";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("killall-use")){
            commandSender.sendMessage(ChatColor.RED + "You do not have the permission to use this command!");
            return true;
        }

        if (args.length > numberOfDimensions){
            commandSender.sendMessage(ChatColor.RED + "You have given too many arguments!");
            return true;
        }

        if (!isRightFormat(args)){
            commandSender.sendMessage(ChatColor.RED + "You have written wrong arguments!");
            return true;
        }

        if (args.length == 0) {
            List<LivingEntity> listOfEntities = ((Player) commandSender).getWorld().getLivingEntities();
            World.Environment dimension = ((Player) commandSender).getWorld().getEnvironment();

            listOfEntities.forEach(entity -> {
                if (dimension == entity.getWorld().getEnvironment() && !(entity instanceof Player)) {
                    entity.remove();
                }
            });
            return true;
        }

        List<LivingEntity> listOfEntities = ((Player) commandSender).getWorld().getLivingEntities();

        for (String nameOfDimension : args){
            HashMap<String , World.Environment> dimensions = new HashMap<>(numberOfDimensions);
            initializeHashMap(dimensions);

            listOfEntities.forEach(entity -> {
                if (dimensions.get(nameOfDimension) == entity.getWorld().getEnvironment() && !(entity instanceof Player)) {
                    entity.remove();
                }
            });
        }

        return true;
        }

    private boolean isRightFormat(String[] arguments){
        List<String> possibleList = makePossibleList();
        for (String word : arguments){
            if (! possibleList.contains(word)){
                return false;
            }
        }
        return true;
    }
    private List<String> makePossibleList(){

        List<String> dimensionList = new ArrayList<>();

        dimensionList.add(dim1);
        dimensionList.add(dim2);
        dimensionList.add(dim3);

        return dimensionList;
    }
    private void initializeHashMap(HashMap<String, World.Environment> dimensions){
        dimensions.put(dim1, World.Environment.NORMAL);
        dimensions.put(dim2, World.Environment.NETHER);
        dimensions.put(dim3, World.Environment.THE_END);
    }

}

