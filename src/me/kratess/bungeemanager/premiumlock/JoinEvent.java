package me.kratess.bungeemanager.premiumlock;

import me.kratess.bungeemanager.utils.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {

    private String switch_server;
    private String false_premium;

    public JoinEvent() {
        switch_server = FilesManager.Config.getString("premium_lock.switch_server");
        false_premium = FilesManager.Messages.getString("false_premium").replace("&", "§");
    }

    @EventHandler
    public void onJoin(PreLoginEvent e) {
        if (e.isCancelled()) return;

        String name = e.getConnection().getName();
        String ip = e.getConnection().toString().split("\\[/")[1].split(":")[0];

        if (CheckUser.checkUser(name, ip)) {

            String UUID = PremiumUUID.getPremiumUUID(e.getConnection().getName());
            if (UUID == null) {
                e.setCancelled(true);
                e.setCancelReason(new TextComponent(false_premium));
                return;
            }

            e.getConnection().setOnlineMode(true);

        } else {

        }

    }

    @EventHandler
    public void connectEvent(ServerConnectEvent e) {
        if (!switch_server.isEmpty()) {

            if (e.getPlayer().getPendingConnection().isOnlineMode()) {

                ServerInfo server = ProxyServer.getInstance().getServerInfo(switch_server);

                if (e.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)
                        || e.getReason().equals(ServerConnectEvent.Reason.KICK_REDIRECT)
                        || e.getReason().equals(ServerConnectEvent.Reason.LOBBY_FALLBACK)
                        || e.getReason().equals(ServerConnectEvent.Reason.SERVER_DOWN_REDIRECT)) {
                    e.setTarget(server);
                }

            }
        }
    }

}
