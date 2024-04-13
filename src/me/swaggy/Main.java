package me.swaggy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    private final Map<UUID, Long> newPlayers = new HashMap<>();
    private Economy econ;
    private String welcomeMessage;
    private long timeThreshold;
    private double rewardAmount;
    private String rewardMessage;

    @Override
    public void onEnable() {
        // Create config if it doesn't exist
        saveDefaultConfig();

        // Read values from config
        welcomeMessage = getConfig().getString("welcome.message", "welcome").toLowerCase();
        timeThreshold = getConfig().getLong("welcome.timeThreshold", 15000L);
        rewardAmount = getConfig().getDouble("welcome.rewardAmount", 1000.0);
        rewardMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("welcome.rewardMessage", "&aWow! You welcomed a new player! You are so nice, here is $%amount% Shekels!"));

        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Setup economy
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            newPlayers.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        if (!message.equals(welcomeMessage)) return;

        UUID uuid = event.getPlayer().getUniqueId();
        newPlayers.entrySet().removeIf(entry -> {
            if (System.currentTimeMillis() - entry.getValue() <= timeThreshold) {
                if (econ.depositPlayer(Bukkit.getOfflinePlayer(uuid), rewardAmount).transactionSuccess()) {
                    String formattedMessage = rewardMessage.replace("%amount%", String.format("%.2f", rewardAmount));
                    event.getPlayer().sendMessage(formattedMessage);
                    return true; // Remove the player from the list
                }
            }
            return false;
        });
    }
}