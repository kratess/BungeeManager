package me.kratess.bungeemanager.antivpn;

import me.kratess.bungeemanager.Main;
import me.kratess.bungeemanager.utils.GetBodyURL;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class AutoDownloadFiles {

    public AutoDownloadFiles() {
        String body;

        // proxy-list@socks5
        body = GetBodyURL.getBody("https://www.proxy-list.download/api/v1/get?type=socks5");
        createFile("proxy-list@socks5", body);

        // proxy-list@socks4
        body = GetBodyURL.getBody("https://www.proxy-list.download/api/v1/get?type=socks4");
        createFile("proxy-list@socks4", body);

        // proxy-list@https
        body = GetBodyURL.getBody("https://www.proxy-list.download/api/v1/get?type=https");
        createFile("proxy-list@https", body);

        // proxy-list@http
        body = GetBodyURL.getBody("https://www.proxy-list.download/api/v1/get?type=http");
        createFile("proxy-list@http", body);
    }

    private void createFile(String name, String body) {
        File file = new File(Main.instance.getDataFolder() + "/VPN/", name + ".yml");

        try {
            if (!file.exists()) {
                InputStream in = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
                Files.copy(in, file.toPath());
            } else {
                FileWriter writer = new FileWriter(file);
                writer.write(body);
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
