package io.github.ve6om;

import io.github.ve6om.Events.Join;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public final class ConnectionCommands extends JavaPlugin implements Listener {
    private static ConnectionCommands instance;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        if (this.getConfig().getBoolean("update-check.enabled")) {
            this.checkForUpdate(version -> {
                if (this.getPluginMeta().getVersion().equalsIgnoreCase(version)) {
                    this.getServer().getLogger().log(Level.INFO, "[ConnectionCommands] You are using the latest version.");
                } else {
                    this.getServer().getLogger().log(Level.WARNING, "[ConnectionCommands] There's an update available!");
                    this.getServer().getLogger().log(Level.WARNING, "[ConnectionCommands] Current version: " + this.getPluginMeta().getVersion() + ", Latest Version: " + version);
                    this.getServer().getLogger().log(Level.WARNING, "[ConnectionCommands] Update here: https://www.spigotmc.org/resources/connectioncommands.107454/updates");
                }
            });
        }

        this.getServer().getPluginManager().registerEvents(new Join(), this);
        this.getServer().getLogger().log(Level.INFO, "[ConnectionCommands] Successfully started.");
    }

    private void checkForUpdate(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try (final InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=107454/~").openStream(); final Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                this.getServer().getLogger().log(Level.WARNING, "[ConnectionCommands] Unable to complete update check: " + e.getMessage());
            }
        });
    }

    @Override
    public void onDisable() {
        this.getServer().getLogger().log(Level.INFO, "[ConnectionCommands] Successfully stopped.");
    }

    public static ConnectionCommands getInstance() {
        return instance;
    }
}
