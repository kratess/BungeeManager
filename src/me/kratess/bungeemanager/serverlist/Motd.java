package me.kratess.bungeemanager.serverlist;

import me.kratess.bungeemanager.utils.FilesManager;
import me.kratess.bungeemanager.utils.Replacer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class Motd implements Listener {

    private String motd;

    public Motd() {
        motd = FilesManager.Config.getString("server_list.motd");
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        TextComponent serverText = new TextComponent(Replacer.getReplaced(motd));

        e.getResponse().setDescriptionComponent(serverText);
    }

}
