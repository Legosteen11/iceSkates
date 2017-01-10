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

    @Override
    public void onEnable(){
        plugin = this;

        log = getLogger();
        log.info("Enabled");

        this.saveDefaultConfig();

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.register(new GiveIceSkatesCommand("giveiceskates","iceskates.giveiceskates","/giveiceskates <player>", true));

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
