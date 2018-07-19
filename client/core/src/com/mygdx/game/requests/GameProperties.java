package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class GameProperties {
    private final Properties appProps = new Properties();
    private final String IP4V_REGEX
            = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
    private final Pattern IP_PATTERN = Pattern.compile(IP4V_REGEX);
    private final String PORT_REGEX =
            "([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])";
    private final Pattern PORT_PATTERN = Pattern.compile(PORT_REGEX);

    public GameProperties() {
        // FileHandle propertiesFileHandle = Gdx.files.internal("config.properties");
        // try {
        //    appProps.load(new BufferedInputStream(propertiesFileHandle.read()));
        //} catch (IOException e) {
        //  e.printStackTrace();
        //}
    }

    public String getDomain() {
        String domain = "http://"
                + appProps.getProperty("server.ip")
                + ":"
                + appProps.getProperty("server.port");
        return domain;
    }

    public boolean ipIsValid(String ip) {
        return IP_PATTERN.matcher(ip).matches();
    }

    public boolean portIsValid(String port) {
        return PORT_PATTERN.matcher(port).matches();
    }

    //TODO write to properties file
    public void setDomain(String ip, String port) {
        appProps.setProperty("server.ip", ip);
        appProps.setProperty("server.port", port);
        FileHandle propertiesFileHandle = Gdx.files.internal("config.properties");
        try {
            appProps.store(new BufferedOutputStream(propertiesFileHandle.write(false)), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
