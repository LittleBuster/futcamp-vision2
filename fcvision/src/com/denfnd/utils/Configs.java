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


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Configs implements Configurable {
    private Properties ps = new Properties();

    public void loadFromFile(String filename) throws IOException {
        FileInputStream stream = new FileInputStream(filename);
        ps.load(stream);
        stream.close();
    }

    public String getStr(String name) {
        return ps.getProperty(name);
    }

    public int getInt(String name) {
        return Integer.valueOf(ps.getProperty(name));
    }
}
