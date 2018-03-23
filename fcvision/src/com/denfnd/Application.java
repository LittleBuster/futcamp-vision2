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

package com.denfnd;


import com.denfnd.http.Maker;
import com.denfnd.http.Server;
import com.denfnd.utils.Configurable;
import com.denfnd.utils.Logger;
import java.io.IOException;


public class Application {
    private Logger log;
    private Configurable cfg;
    private Server websrv;
    private Maker hmaker;

    public Application(Logger log, Configurable cfg, Server websrv, Maker hmaker) {
        this.log = log;
        this.cfg = cfg;
        this.websrv = websrv;
        this.hmaker = hmaker;
    }

    public void start() {
        Path path = Path.getInstance();

        log.setPath(path.get("log"));

        try {
            cfg.loadFromFile(path.get("cfg"));
        }
        catch (IOException e) {
            log.error("Configs load error: " + e.getMessage(), "APP");
            return;
        }
        log.info("Configs loaded", "APP");

        try {
            websrv.init();
            websrv.addHandler("/", hmaker.makeHandler("index"));
            websrv.addHandler("/css", hmaker.makeHandler("css"));
            websrv.addHandler("/photo", hmaker.makeHandler("photo"));
            websrv.addHandler("/camera", hmaker.makeHandler("camera"));
            websrv.addHandler("/sys", hmaker.makeHandler("sys"));
            websrv.start(cfg.getInt("server_port"),
                         cfg.getInt("server_queue"),
                         cfg.getInt("server_threads"));
        }
        catch (Exception e) {
            log.error("Failed to start WebServer: " + e.getMessage(), "APP");
        }
    }
}
