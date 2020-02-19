package me.kratess.bungeemanager.utils;

public class PremiumUUID {

    public static String getPremiumUUID(String name) {
        String body = GetBodyURL.getBody("https://api.mojang.com/users/profiles/minecraft/" + name);
        if (body == null) {
            return null;
        } else {
            return body.split("id\":\"")[1].split("\",")[0];
        }
    }

}
