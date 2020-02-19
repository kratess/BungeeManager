package me.kratess.bungeemanager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBodyURL {

    public static String getBody(String URL) {
        try {
            String url = URL;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.addRequestProperty("User-Agent", "BungeeManager/1.0@pluginBungeeCord");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + System.lineSeparator());
            }

            in.close();

            if (responseCode == 204 || responseCode == 404) {
                return null;
            } else {
                return response.toString();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
