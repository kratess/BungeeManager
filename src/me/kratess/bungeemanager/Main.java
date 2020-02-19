package me.kratess.bungeemanager;

import me.kratess.bungeemanager.antibot.Runner;
import me.kratess.bungeemanager.antivpn.AutoDownloadFiles;
import me.kratess.bungeemanager.antivpn.JoinEvent;
import me.kratess.bungeemanager.premiumlock.Command;
import me.kratess.bungeemanager.protocol.ChangeProtocol;
import me.kratess.bungeemanager.serverlist.Hover;
import me.kratess.bungeemanager.serverlist.Motd;
import me.kratess.bungeemanager.utils.CheckUser;
import me.kratess.bungeemanager.utils.FilesManager;
import me.kratess.bungeemanager.utils.Metrics;
import me.kratess.bungeemanager.utils.SpigotChecker;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class Main extends Plugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getProxy().getScheduler().schedule(this, new CheckSpigotEveryTime(), 0, 30, TimeUnit.MINUTES);

        new FilesManager();

        new CheckUser();

        // antivpn

        if (FilesManager.Config.getBoolean("anti_vpn.autodownload_files")) {
            new AutoDownloadFiles();
        }

        if (FilesManager.Config.getBoolean("anti_vpn.check_files")) {
            getProxy().getPluginManager().registerListener(this, new JoinEvent());
        }

        // protocol

        getProxy().getPluginManager().registerListener(this, new ChangeProtocol());

        // premiumlock

        if (FilesManager.Config.getBoolean("premium_lock.use_premium_lock")) {
            getProxy().getPluginManager().registerListener(this, new me.kratess.bungeemanager.premiumlock.JoinEvent());
        }

        if (!FilesManager.Config.getString("premium_lock.command").equalsIgnoreCase("") &&
                !FilesManager.Config.getBoolean("premium_lock.all_users")) {
            getProxy().getPluginManager().registerCommand(this, new Command());
        }

        // antibot

        if (FilesManager.Config.getBoolean("anti_bot.use_anti_bot")) {
            getProxy().getPluginManager().registerListener(this, new me.kratess.bungeemanager.antibot.JoinEvent());
            getProxy().getScheduler().schedule(this, new Runner(), 5, 5, TimeUnit.MINUTES);
        }

        // serverlist

        if (FilesManager.Config.getBoolean("server_list.use_motd")) {
            getProxy().getPluginManager().registerListener(this, new Motd());
        }

        if (FilesManager.Config.getBoolean("server_list.use_hover")) {
            getProxy().getPluginManager().registerListener(this, new Hover());
        }

        Metrics metrics = new Metrics(this, 6552);

        getLogger().info("BungeeManager has been activated and SpigotUpdater is successfully working");
        getLogger().info("It is advisable to manually check the new versions");

    }

    public class CheckSpigotEveryTime implements Runnable {

        @Override
        public void run() {
            switch (new SpigotChecker().getStatus()) {
                case 0x00:
                    break;
                case 0x01:
                    break;
                case 0x02:
                    getLogger().severe("***");
                    getLogger().severe("");
                    getLogger().severe("Older plugin versions are vulnerable.");
                    getLogger().severe("YOU MUST DOWNLOAD THE NEWEST VERSION!");
                    getLogger().severe("");
                    getLogger().severe("This error could be vulnerable for your server!!!");
                    getLogger().severe("");
                    getLogger().severe("PLEASE UPDATE PLUGIN");
                    getLogger().severe("");
                    getLogger().severe("***");
                    getProxy().stop();
                case 0x03:
                    getLogger().info("You should download the newest version");
            }
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
