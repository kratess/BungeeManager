package me.kratess.bungeemanager.antibot;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinEvent implements Listener {

    public static HashMap<String, ArrayList<String>> ip_login = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PreLoginEvent e) {
        if (e.isCancelled()) return;

        String name = e.getConnection().getName();
        String ip = e.getConnection().toString().split("\\[/")[1].split(":")[0];

        if (ip_login.containsKey(ip)) {
            ArrayList<String> names = ip_login.get(ip);
            if (names.size() >= 2) {
                e.setCancelled(true);
                e.setCancelReason(new TextComponent("§4Antibot enabled"));
                return;
            }
            for (String previous_name : names) {
                if (!previous_name.equalsIgnoreCase(name)) {
                    if (isBot(previous_name) && isBot(name)) {
                        e.setCancelled(true);
                        e.setCancelReason(new TextComponent("§4Please rejoin"));
                        break;
                    }
                }
            }

            names.add(name);
            ip_login.put(ip, names);
        } else {
            ArrayList<String> names = new ArrayList<String>();
            names.add(name);
            ip_login.put(ip, names);
        }

    }


    private static boolean isBot(String name) {
        ArrayList<String> strings = new ArrayList<>();

        String d = "";

        Pattern pattern = Pattern.compile("[aeiouâãäåæçèéêëìíîïðñòóôõøùúûü]", Pattern.CASE_INSENSITIVE);

        for (char c : name.toCharArray()) {
            d += c;
            if (pattern.matcher(String.valueOf(c)).find()) {
                strings.add(d);
                d = "";
            }
        }

        if (d.length() > 0) strings.add(d);

        for (String dd : strings) {
            int per = dd.length() * 100 / name.length();
            if (per > 45) {
                return true;
            }
        }

        return false;
    }

    private static boolean isBot(String name, int min_per) {
        ArrayList<String> strings = new ArrayList<>();

        String d = "";

        Pattern pattern = Pattern.compile("[aeiouâãäåæçèéêëìíîïðñòóôõøùúûü]", Pattern.CASE_INSENSITIVE);

        for (char c : name.toCharArray()) {
            d += c;
            if (pattern.matcher(String.valueOf(c)).find()) {
                strings.add(d);
                d = "";
            }
        }

        if (d.length() > 0) strings.add(d);

        for (String dd : strings) {
            int per = dd.length() * 100 / name.length();
            if (per > min_per) {
                return true;
            }
        }

        return false;
    }

}
