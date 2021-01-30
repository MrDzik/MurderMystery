package com.murder.runnables;

import com.murder.Murder;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;


public class WaitingRoom extends BukkitRunnable {

    private final World world;
    private final int stage;
    private int time;
    private int delay;

    public WaitingRoom(World world, int stage) {

        this.world = world;
        this.stage = stage;
    }

    public void run() {
        updateTimeAndDelay();
        List<Player> players = world.getPlayers();
        if (stage != 6) {
            int mods = 0;
            for (Player player : players) {
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    mods++;
                }
            }
            int playersCount = players.size() - mods;
            if (playersCount >= 1) {
                players.forEach(player -> player.sendMessage("Gra rozpocznie się za "+time+" sekund"));
                new WaitingRoom(world, stage + 1).runTaskLaterAsynchronously(Murder.getMainPlugin(), delay * 20L);
            }
            else {
                new WaitingRoom(world, 1).runTaskLaterAsynchronously(Murder.getMainPlugin(), 5 * 20);
                if (stage != 1) {
                    players.forEach(player -> player.sendMessage("Rozpoczecie gry zostalo przerwane"));
                }
            }
        } else {
            players.forEach(player -> player.sendMessage("Gra właśnie się rozpoczęła"));
        }
    }
    private void updateTimeAndDelay(){
        switch (stage) {
            case 1:
                time = 60;
                delay = 30;
                break;
            case 2:
                time = 30;
                delay = 15;
                break;
            case 3:
                time = 15;
                delay = 5;
                break;
            case 4:
                time = 10;
                delay = 5;
                break;
            case 5:
                time = 5;
                delay = 5;
                break;
        }
    }
}