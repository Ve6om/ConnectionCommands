package io.github.ve6om.Events;

import io.github.ve6om.ConnectionCommands;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class Join implements Listener {
    private final ConnectionCommands connectionCommands = ConnectionCommands.getInstance();

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final ConfigurationSection configurationSection = connectionCommands.getConfig().getConfigurationSection("connections");
        if (configurationSection != null) {
            for (String key : configurationSection.getKeys(true)) {
                if (key.equalsIgnoreCase(event.getHostname())) {
                    final List<String> commands = connectionCommands.getConfig().getStringList("connections." + key + ".commands");
                    for (String command : commands) {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("{player}", event.getPlayer().getName()));
                    }
                }
            }
        }
    }
}