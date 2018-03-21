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

package com.denfnd.http;


import com.denfnd.http.handlers.FileHandler;
import com.denfnd.http.handlers.IndexHandler;
import com.denfnd.http.handlers.SysHandler;
import com.denfnd.sys.SystemInfo;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpHandler;


public class HandlersMaker implements Maker {
    private Logger log;
    private SystemInfo sys;

    public HandlersMaker(Logger logr, SystemInfo syst) {
        log = logr;
        sys = syst;
    }

    public HttpHandler makeHandler(String name) {
        if (name.equals("index"))
            return new IndexHandler(log, sys);
        if (name.equals("css") || name.equals("js") || name.equals("img"))
            return new FileHandler(log);
        if (name.equals("sys"))
            return new SysHandler(log, sys);
        return null;
    }
}
