/*
 * Future Camp Vision project
 *
 * Copyright (C) 2018 Sergey Denisov.
 * Written by Sergey Denisov aka LittleBuster (DenisovS21@gmail.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public Licence 3
 * as published by the Free Software Foundation; either version 3
 * of the Licence, or (at your option) any later version.
 */

package com.denfnd.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log implements Logger {
    private String path;

    private enum LogType {
        LOG_INFO,
        LOG_WARN,
        LOG_ERROR
    }

    private synchronized void saveToFile(String message, String filename) throws IOException {
        File file = new File(filename);
        FileWriter writer = new FileWriter(file, true);

        writer.append(message);
        writer.append("\n");

        writer.close();
    }

    private void makeMessage(String message, String module, LogType type) {
        String out;
        Date date = new Date();
        SimpleDateFormat dtm = new SimpleDateFormat("[MM.dd][HH:mm]");

        out = dtm.format(date);
        out += "[" + module + "][";

        switch (type) {
            case LOG_INFO:
                out += "INFO";
                break;
            case LOG_WARN:
                out += "WARN";
                break;
            case LOG_ERROR:
                out += "ERROR";
                break;
        }

        out += "] " + message;
        System.out.println(out);

        try {
            saveToFile(out, path);
        }
        catch (IOException e) {
            System.out.println("Fail to write to log file.");
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void info(String message, String module) {
        makeMessage(message, module, LogType.LOG_INFO);
    }

    public void warning(String message, String module) {
        makeMessage(message, module, LogType.LOG_WARN);
    }

    public void error(String message, String module) {
        makeMessage(message, module, LogType.LOG_ERROR);
    }
}
