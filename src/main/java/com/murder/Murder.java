package com.murder;

import com.murder.events.MurderNPCEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Murder extends JavaPlugin {

    static JavaPlugin plugin;
    public static JavaPlugin getMainPlugin() {

        return plugin;
    }

    @Override
    public void onEnable() {

        plugin = this;
        getServer().getPluginManager().registerEvents(new MurderNPCEvent(), this);
        getLogger().info( ChatColor.AQUA + " [MurderMystery] Plugin is enable! " );
    }

    @Override
    public void onDisable() {

        getLogger().info(ChatColor.RED + " [MurderMystery] Plugin is disable! " );
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("SetVillager")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("npc")) {
                    spawnMurderVillager(player.getWorld(), player.getLocation());
                    player.sendMessage(ChatColor.RED + "Villager was created!");
                    return true;
                }
            }
        }
        return false;
    }

    private void spawnMurderVillager(World world, Location location){
        Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + "> KLIKNIJ PRZYCISK <");
        villager.setAI(false);
    }
}