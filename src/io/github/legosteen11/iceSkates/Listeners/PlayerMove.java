package io.github.legosteen11.iceSkates.Listeners;

import io.github.legosteen11.iceSkates.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static java.lang.Math.abs;

/**
 * Created by Wouter on 16-11-2016.
 * COPYRIGHT WOUTER DOELAND/LEGOSTEEN11 (legosteen11.github.io)
 */
public class PlayerMove implements Listener {
    private final double minimumDifference = Main.getPlugin().getConfig().getDouble("minimumDistanceDifference");
    private final int speed = Main.getPlugin().getConfig().getInt("speed");
    private final int duration = Main.getPlugin().getConfig().getInt("duration");
    private final int timePassedBeforeCheck = Main.getPlugin().getConfig().getInt("timePassedBeforeCheck");

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if(e.getPlayer().hasPermission("iceskates.skate")) {
            if (e.getPlayer().getPotionEffect(PotionEffectType.SPEED) != null) {
                if (e.getPlayer().getPotionEffect(PotionEffectType.SPEED).getDuration() > duration - timePassedBeforeCheck) {
                    return;
                }
            }
            if (abs(e.getFrom().getX() - e.getTo().getX()) > minimumDifference || abs(e.getFrom().getZ() - e.getTo().getZ()) > minimumDifference) {
                Location location = e.getTo().clone();
                location.setY(e.getTo().getY() - 2);
                Material blockType = location.getBlock().getType();
                if (blockType == Material.ICE || blockType == Material.PACKED_ICE || blockType == Material.FROSTED_ICE) {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, speed));
                }
            }
        }
    }
}
