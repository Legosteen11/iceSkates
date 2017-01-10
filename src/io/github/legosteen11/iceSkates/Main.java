package io.github.legosteen11.iceSkates;

import io.github.legosteen11.iceSkates.Commands.GiveIceSkatesCommand;
import io.github.legosteen11.iceSkates.Listeners.PlayerMove;
import me.mrten.api.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Wouter on 16-11-2016.
 * COPYRIGHT WOUTER DOELAND/LEGOSTEEN11 (legosteen11.github.io)
 */
public class Main extends JavaPlugin {
    private static Plugin plugin;
    private Logger log;
    public static boolean ENABLE_SKATE_ITEMS;
    public static boolean PLAYERSPECIFIC_SKATE_ITEMS;
    public static final String ICE_SKATES_ITEM_IDENTIFIER = "ICE_SKATES";

    @Override
    public void onEnable(){
        plugin = this;

        log = getLogger();
        log.info("Enabled");

        this.saveDefaultConfig();
        
        ENABLE_SKATE_ITEMS = getConfig().getBoolean("enableskateitems");
        PLAYERSPECIFIC_SKATE_ITEMS = getConfig().getBoolean("playerspecificskateitems");

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register(new GiveIceSkatesCommand("giveiceskates","iceskates.giveiceskates","/giveiceskates <player>", true, 1));

        registerEvents(this, new PlayerMove());
    }

    @Override
    public void onDisable(){
        plugin = null;
        log.info("Disabled");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static void registerEvents(Plugin plugin, Listener... listeners){    // Sets the listeners
        for (Listener listener : listeners){
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

}
