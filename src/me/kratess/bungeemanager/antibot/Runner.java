package me.kratess.bungeemanager.antibot;

import java.util.HashMap;

public class Runner implements Runnable {

    @Override
    public void run() {
        JoinEvent.ip_login = new HashMap<>();
    }

}
