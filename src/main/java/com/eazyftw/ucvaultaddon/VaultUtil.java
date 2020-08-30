package com.eazyftw.ucvaultaddon;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtil {

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public static void setupVault() {
        setupEconomy();
        setupChat();
        setupPermissions();
    }

    public static boolean setupEconomy() {
        try {
            if (!hasVault()) return false;

            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

            if (rsp == null) return false;
            econ = rsp.getProvider();
            return econ != null;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean setupChat() {
        try {
            RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
            chat = rsp.getProvider();
            return chat != null;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean hasVault() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("Vault");
    }

    public static boolean setupPermissions() {
        try {
            RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
            perms = rsp.getProvider();
            return perms != null;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}
