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

package com.denfnd.http.handlers;


import com.denfnd.Path;
import com.denfnd.http.PageParser;
import com.denfnd.sys.DiskData;
import com.denfnd.sys.MemData;
import com.denfnd.sys.SystemInfo;
import com.denfnd.utils.Configurable;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;


public class IndexHandler implements HttpHandler {
    private Logger log;
    private SystemInfo sys;
    private Configurable cfg;

    public IndexHandler(Logger log, SystemInfo sys, Configurable cfg) {
        this.log = log;
        this.sys = sys;
        this.cfg = cfg;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String page;
        Path path = Path.getInstance();
        PageParser parser = new PageParser();

        try {
            parser.loadTemplate(path.get("html") + "/index.html");
        }
        catch (Exception e) {
            log.error("Failed to load template: index.html", "INDEX_HANDLER");
            try {
                page = "<h1>403<br>Forbidden</h1>";
                exchange.sendResponseHeaders(403, page.length());
                OutputStream os = exchange.getResponseBody();
                os.write(page.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "INDEX_HANDLER");
                return;
            }
            return;
        }

        try {
            parser.setValue("cpu", String.valueOf(sys.getCpuTemp()));
        }
        catch (Exception e) {
            parser.setValue("cpu", "error");
            log.error("Fail to read cpu temp: " + e.getMessage(), "INDEX_HANDLER");
        }

        try {
            MemData data = sys.getMemInfo();
            parser.setValue("ram", data.getUsed() + "/" + data.getTotal());
        }
        catch (Exception e) {
            parser.setValue("ram", "error");
            log.error("Fail to read RAM info: " + e.getMessage(), "INDEX_HANDLER");
        }

        try {
            DiskData data = sys.getDiskInfo();
            parser.setValue("disk", data.getUsed() + "/" + data.getTotal());
        }
        catch (Exception e) {
            parser.setValue("disk", "error");
            log.error("Fail to read disk info: " + e.getMessage(), "INDEX_HANDLER");
        }

        try {
            parser.setValue("up", sys.getUptime());
        }
        catch (Exception e) {
            parser.setValue("up", "error");
            log.error("Fail to read uptime: " + e.getMessage(), "INDEX_HANDLER");
        }

        for (int i = 0; i < cfg.getInt("cams_count"); i++) {
            parser.setHtml("last", "<a href=\"/photo?cam=" + i + "\"><img src=\"/camera?dev=" + i + "\" width=\"320\" height=\"240\" /></a> ");
        }

        page = parser.buildPage();
        try {
            exchange.sendResponseHeaders(200, 0);
            try (BufferedOutputStream out = new BufferedOutputStream(exchange.getResponseBody())) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(page.getBytes())) {
                    int count;
                    byte [] buffer = new byte[4096];
                    while ((count = bis.read(buffer)) != -1) {
                        out.write(buffer, 0, count);
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Failed to send answer: " + e.getMessage(), "INDEX_HANDLER");
        }
    }
}
