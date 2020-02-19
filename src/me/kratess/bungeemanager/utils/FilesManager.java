package me.kratess.bungeemanager.utils;

import me.kratess.bungeemanager.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FilesManager {

    public FilesManager() {
        Directory();
        Config();

        VPNDirecotry();

        Messages();

        PremiumUsers();
    }

    private void Directory() {
        if (!(new File(Main.instance.getDataFolder(), "")).exists()) {
            (new File(Main.instance.getDataFolder(), "")).mkdir();
        }
    }

    public static Configuration Config;
    public static File File_Config = new File(Main.instance.getDataFolder(), "config.yml");

    private void Config() {
        try {
            if (!File_Config.exists()) {
                InputStream in = Main.instance.getResourceAsStream("config.yml");
                Files.copy(in, File_Config.toPath());
            }

            Config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(File_Config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void VPNDirecotry() {
        if (!(new File(Main.instance.getDataFolder() + "/VPN/", "")).exists()) {
            (new File(Main.instance.getDataFolder() + "/VPN/", "")).mkdir();
        }
    }

    public static Configuration Messages;
    public static File File_Messages = new File(Main.instance.getDataFolder(), "messages.yml");

    private void Messages() {
        try {
            if (!File_Messages.exists()) {
                InputStream in = Main.instance.getResourceAsStream("messages.yml");
                Files.copy(in, File_Messages.toPath());
            }

            Messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(File_Messages);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration PremiumUsers;
    public static File File_PremiumUsers = new File(Main.instance.getDataFolder(), "premium_users.yml");

    private void PremiumUsers() {
        try {
            if (!File_PremiumUsers.exists()) {
                InputStream in = Main.instance.getResourceAsStream("premium_users.yml");
                Files.copy(in, File_PremiumUsers.toPath());
            }

            PremiumUsers = ConfigurationProvider.getProvider(YamlConfiguration.class).load(File_PremiumUsers);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void savePremiumUsers() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(FilesManager.PremiumUsers, new File(Main.instance.getDataFolder(), "premium_users.yml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void reloadPremiumUsers() {
        try {
            PremiumUsers = ConfigurationProvider.getProvider(YamlConfiguration.class).load(File_PremiumUsers);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
