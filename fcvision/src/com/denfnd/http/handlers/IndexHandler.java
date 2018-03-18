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
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;


public class IndexHandler implements HttpHandler {
    private Logger log;

    public IndexHandler(Logger logr) {
        log = logr;
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
            log.error("Failed to load template: index.html", "IndexHandler");
            return;
        }

        /* temporary values */
        parser.setValue("cpu", "22");
        parser.setValue("ram", "56/256");
        parser.setValue("disk", "1.4G/3.4G");
        parser.setValue("up", "1 hour 22 minutes");

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
            log.error("Failed to send answere: " + e.getMessage(), "IndexHandler");
            return;
        }
    }
}
