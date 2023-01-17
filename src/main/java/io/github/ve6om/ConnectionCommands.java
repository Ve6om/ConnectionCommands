package io.github.ve6om;

import io.github.ve6om.Events.Join;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ConnectionCommands extends JavaPlugin implements Listener {
    private static ConnectionCommands instance;


    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new Join(), this);
        this.getServer().getLogger().log(Level.INFO, "[ConnectionCommands] Successfully started.");
    }

    @Override
    public void onDisable() {
        this.getServer().getLogger().log(Level.INFO, "[ConnectionCommands] Successfully stopped.");
    }

    public static ConnectionCommands getInstance() {
        return instance;
    }
}
