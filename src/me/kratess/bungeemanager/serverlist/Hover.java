package me.kratess.bungeemanager.serverlist;

import me.kratess.bungeemanager.utils.FilesManager;
import me.kratess.bungeemanager.utils.Replacer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class Hover implements Listener {

    private String hover;

    public Hover() {
        hover = FilesManager.Config.getString("server_list.hover");
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        e.getResponse().getPlayers().setSample(new ServerPing.PlayerInfo[]{
                new ServerPing.PlayerInfo(Replacer.getReplaced(hover), UUID.fromString("0-0-0-0-0"))
        });
    }
}
