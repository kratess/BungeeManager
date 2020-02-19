package me.kratess.bungeemanager.protocol;

import me.kratess.bungeemanager.utils.FilesManager;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;

public class ChangeProtocol implements Listener {

    private HashMap<String, String> protocols = new HashMap<>();

    public ChangeProtocol() {
        for (String d : FilesManager.Config.getSection("protocol.custom").getKeys()) {
            protocols.put(d, FilesManager.Config.getString("protocol.custom." + d));
        }
    }

    private String default_name = FilesManager.Config.getString("protocol.name");

    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent e) {
        String name = default_name;

        int protocol = e.getResponse().getVersion().getProtocol();

        for (String d : protocols.keySet()) {
            if (d.charAt(0) == '<') {
                if (protocol < Integer.valueOf(d.substring(1))) {
                    name = protocols.get(d);
                }
            } else if (d.contains("-")) {
                if (protocol >= Integer.valueOf(d.split("-")[0]) && protocol <= Integer.valueOf(d.split("-")[1])) {
                    name = protocols.get(d);
                }
            } else if (d.charAt(0) == '>') {
                if (protocol > Integer.valueOf(d.substring(1))) {
                    name = protocols.get(d);
                }
            } else if (protocol == Integer.valueOf(d)) {
                name = d;
            }
        }

        if (!name.isEmpty()) {
            e.getResponse().setVersion(new ServerPing.Protocol(name, protocol));
        }
    }

}
