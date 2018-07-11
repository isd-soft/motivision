package com.mygdx.game.logger;

import java.util.Date;

public class Logger {
    private Date date;

    public void info(String log) {
        date = new Date();
        System.out.println(date + " INFO " + log);
    }

    public void warn(String log) {
        date = new Date();
        System.out.println(date + " WARN " + log);
    }

    public void error(String log) {
        date = new Date();
        System.out.println(date + " ERROR " + log);
    }

    public void fatal(String log) {
        date = new Date();
        System.out.println(date + " FATAL " + log);
    }
}
