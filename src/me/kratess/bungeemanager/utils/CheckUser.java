package me.kratess.bungeemanager.utils;

import java.util.HashMap;

public class CheckUser {

    private static boolean all_users;

    private static HashMap<String, String> users = new HashMap<>();

    public CheckUser() {
        all_users = FilesManager.Config.getBoolean("premium_lock.all_users");

        if (!all_users) {
            for (String d : FilesManager.PremiumUsers.getSection("users").getKeys()) {
                users.put(d, FilesManager.PremiumUsers.getString("users."+d));
            }
        }
    }

    public static boolean checkUser(String name, String ip) {
        if (!all_users) {
            for (String d : users.keySet()) {
                if (name.equalsIgnoreCase(d)) {
                    if (users.get(d).equalsIgnoreCase("0") || users.get(d).equalsIgnoreCase(ip)) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }

        return false;
    }

}
