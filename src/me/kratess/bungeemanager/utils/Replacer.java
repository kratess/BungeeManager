package me.kratess.bungeemanager.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacer {

    public static String getReplaced(String d) {
        Pattern p = Pattern.compile("\\{online@(.*?)\\}");
        Matcher m = p.matcher(d);

        while (m.find()) {
            d = d.replace("{online@"+m.group(1)+"}", getCount(m.group(1)));
        }

        return d.replaceAll("&", "§");
    }

    private static String getCount(String server) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server);
        int count = serverInfo.getPlayers().size();

        return String.valueOf(count);
    }

}
