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


import com.denfnd.hardware.CamDevice;
import com.denfnd.http.handlers.*;
import com.denfnd.sys.SystemInfo;
import com.denfnd.utils.Configurable;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpHandler;


public class HandlersMaker implements Maker {
    private Logger log;
    private SystemInfo sys;
    private CamDevice cam;
    private Configurable cfg;

    public HandlersMaker(Logger log, SystemInfo sys, CamDevice cam, Configurable cfg) {
        this.log = log;
        this.sys = sys;
        this.cam = cam;
        this.cfg = cfg;
    }

    public HttpHandler makeHandler(String name) {
        if (name.equals("index"))
            return new IndexHandler(log, sys, cfg);
        if (name.equals("css"))
            return new FileHandler(log);
        if (name.equals("sys"))
            return new SysHandler(log, sys);
        if (name.equals("camera"))
            return new CameraHandler(log, cam);
        if (name.equals("photo"))
            return new PhotoHandler(log);
        return null;
    }
}
