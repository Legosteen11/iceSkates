package io.github.legosteen11.iceSkates.Commands;

import io.github.legosteen11.iceSkates.Main;
import me.mrten.api.commands.Command;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Wouter on 10-1-2017.
 * COPYRIGHT WOUTER DOELAND/LEGOSTEEN11 (legosteen11.github.io)
 */
public class GiveIceSkatesCommand extends Command {
    public GiveIceSkatesCommand(String command, String permission, String usage, boolean console, int args) {
        super(command, permission, usage, console, args);
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if(args.length == 1) {
            if(Main.getPlugin().getServer().getPlayer(args[0]) != null) {
                Player player = Main.getPlugin().getServer().getPlayer(args[0]);

                ItemStack iceSkates = new ItemStack(Material.DIAMOND_BOOTS);
                
                String[] enchantmentArray = Main.getPlugin().getConfig().getStringList("enchantments").toArray(new String[0]);
                for (String enchantment:
                     enchantmentArray) {
                    String[] splitEnchantmentString = enchantment.split(",");
                    if(splitEnchantmentString.length == 2) {
                        boolean failed = false;
                        String enchantmentString = splitEnchantmentString[0];
                        int enchantmentLevel = 0;
                        try {
                            enchantmentLevel = Integer.parseInt(splitEnchantmentString[1]);
                        } catch (NumberFormatException e) {
                            failed = true;
                        }                        
                        
                        Enchantment enchantmentObject = Enchantment.getByName(enchantmentString);

                        if(enchantmentObject != null && !failed) {
                            iceSkates.addEnchantment(enchantmentObject, enchantmentLevel);
                        }
                    }
                }
                
                ItemMeta itemMeta = iceSkates.getItemMeta();
                itemMeta.setDisplayName(Main.getPlugin().getConfig().getString("defaultname").replaceAll("\\Q{PLAYER}\\E", player.getName()));
                List<String> defaultLore = Main.getPlugin().getConfig().getStringList("defaultlore");
                defaultLore.add("ICE_SKATES");
                defaultLore.add(player.getUniqueId().toString());
                itemMeta.setLore(defaultLore);
                iceSkates.setItemMeta(itemMeta);
                
                player.getInventory().addItem(iceSkates);
                return true;
            }
        }
        return false;
    }
}
