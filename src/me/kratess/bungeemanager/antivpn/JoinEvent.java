package me.kratess.bungeemanager.antivpn;

import me.kratess.bungeemanager.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PreLoginEvent e) throws IOException {
        if (e.isCancelled()) return;

        String ip = e.getConnection().toString().split("\\[/")[1].split(":")[0];

        // AutoDownloadFiles
        File folder = new File(Main.instance.getDataFolder() + "/VPN/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    for (String ip_port : sc.nextLine().split(System.lineSeparator())) {
                        if (ip_port.split(":")[0].equalsIgnoreCase(ip)) {
                            System.out.println("BungeeManager AntiVPN has detected (" + e.getConnection().getName() + "@" + ip + ") as vpn from file " + file.getName());
                            e.setCancelled(true);
                            e.setCancelReason(new TextComponent("§cPlease contact an admin. VPN DETECT"));
                            return;
                        }
                    }
                }
            }
        }

    }

}
