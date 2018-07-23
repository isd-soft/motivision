package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class RememberMe {
    private static Properties appProps = new Properties();
    RememberMe() {

    }

    public static void savePlayer(String login, String password, String checked) {
        FileHandle file = Gdx.files.local("app.properties");
        file.writeString("player.login=" + login + "\nplayer.password=" + password + "\ncheckbox.checked=" + checked, false);
    }

    public static boolean rememberMeFileExists() {
        File file = new File("/data/data/com.mygdx.game/files/app.properties");

        if (file.exists() == true)
            return true;
        return false;
    }

    public static String getLogin() {
        String login;
        FileHandle appPropertiesFileHandle = Gdx.files.local("app.properties");
        try {

            appProps.load(new BufferedInputStream(appPropertiesFileHandle.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        login = appProps.getProperty("player.login");
        return login;
    }

    public static String getPassword() {
        String password;
        FileHandle appPropertiesFileHandle = Gdx.files.local("app.properties");
        try {

            appProps.load(new BufferedInputStream(appPropertiesFileHandle.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        password = appProps.getProperty("player.password");
        return password;
    }

    public static Boolean wasCheckBoxChecked() {
        String checked;
        Boolean checkedBoolean;

        FileHandle appPropertiesFileHandle = Gdx.files.local("app.properties");
        try {
            appProps.load(new BufferedInputStream(appPropertiesFileHandle.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        checked = appProps.getProperty("checkbox.checked");
        checkedBoolean = Boolean.valueOf(checked);
        return checkedBoolean;
    }
}
