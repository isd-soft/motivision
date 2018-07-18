package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class GameProperties {
    private final Properties appProps = new Properties();
    //private final String regex = "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    public GameProperties() {
        FileHandle propertiesFileHandle = Gdx.files.internal("config.properties");
        try {
            appProps.load(new BufferedInputStream(propertiesFileHandle.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDomain() {
        String domain = "http://"
                + appProps.getProperty("server.ip")
                + ":"
                + appProps.getProperty("server.port");
        return domain;
    }

    //TODO validate both IP4V and IP6V
    public boolean ipIsValid(String ip) {
        return false;
    }

    //TODO validate port
    public boolean portIsValid(String port) {
        return false;
    }

    public void setDomain(String ip, String port) {
        appProps.setProperty("server.ip", ip);
        appProps.setProperty("server.port", port);
        FileOutputStream output = null;
        try {
            //output = new FileOutputStream();
            appProps.store(output, "This description goes to the header of a file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new GameProperties().getDomain());
    }
}
