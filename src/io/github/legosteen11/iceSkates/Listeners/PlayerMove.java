package io.github.legosteen11.iceSkates.Listeners;

import io.github.legosteen11.iceSkates.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
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
        Player player = e.getPlayer();
        if(player.hasPermission("iceskates.skate")) {
            if(Main.ENABLE_SKATE_ITEMS) {
                ItemStack boots = player.getInventory().getBoots();
                if(boots != null) {
                    if(boots.getItemMeta().getLore() != null) {
                        if (boots.getItemMeta().getLore().contains(Main.ICE_SKATES_ITEM_IDENTIFIER)) {
                            if (Main.PLAYERSPECIFIC_SKATE_ITEMS && !boots.getItemMeta().getLore().contains(player.getUniqueId().toString())) {
                                return;
                            }
                            if (player.getPotionEffect(PotionEffectType.SPEED) != null) {
                                if (player.getPotionEffect(PotionEffectType.SPEED).getDuration() > duration - timePassedBeforeCheck) {
                                    return;
                                }
                            }
                            if (abs(e.getFrom().getX() - e.getTo().getX()) > minimumDifference || abs(e.getFrom().getZ() - e.getTo().getZ()) > minimumDifference) {
                                Location location = e.getTo().clone();
                                location.setY(e.getTo().getY() - 1);
                                Material blockType = location.getBlock().getType();
                                if (blockType == Material.ICE || blockType == Material.PACKED_ICE || blockType == Material.FROSTED_ICE) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, speed));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
