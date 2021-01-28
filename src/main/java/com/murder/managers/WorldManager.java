package com.murder.managers;

import com.murder.Murder;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldManager {
   public JavaPlugin plugin;

    public WorldManager(){

        this.plugin = Murder.getMainPlugin();
    }

    public void createWorld(String world){
        File dataFolder = new File(plugin.getDataFolder().getPath());
        String strData = dataFolder.toString();

        String[] split = strData.toString().split(File.pathSeparator);
        String rootFolder = split[split.length - 3];

        File root = new File(rootFolder);
        File srcDir = new File(root+File.pathSeparator+"backup");

        if (!srcDir.exists()) {
            Bukkit.getLogger().warning("Backup does not exist!");
            return;
        }

        File destDir = new File(root+File.pathSeparator+world);

        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Bukkit.getServer().createWorld(new WorldCreator(world));
    }

    public void deleteWorld(String world) {
        Bukkit.getServer().unloadWorld(world, true);
        File dir = new File(Bukkit.getServer().getWorld(world).getWorldFolder().getPath());

        try {
            FileUtils.deleteDirectory(dir);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void teleportPlayersToLocation(List<Player> players, String world, int x, int y, int z){
        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        for(int i=0; i<=players.size(); i++) {
            Player player = players.get(i);
            player.teleport(location);
        }

    }

}