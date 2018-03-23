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
import com.denfnd.http.ParamsDecoder;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;


public class PhotoHandler implements HttpHandler {
    private Logger log;

    public PhotoHandler(Logger log) {
        this.log = log;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String page;
        Path path = Path.getInstance();
        PageParser parser = new PageParser();
        ParamsDecoder ps = new ParamsDecoder();

        try {
            ps.split(exchange.getRequestURI().getQuery());
        }
        catch (Exception e) {
            log.error("Failed to decoding params", "PHOTO_HANDLER");
            try {
                String response = "<h1>403<br>Forbidden</h1>\n";
                exchange.sendResponseHeaders(403, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "PHOTO_HANDLER");
                return;
            }
            return;
        }

        try {
            parser.loadTemplate(path.get("html") + "/photo.html");
        }
        catch (Exception e) {
            log.error("Failed to load template: photo.html", "PHOTO_HANDLER");
            try {
                page = "<h1>403<br>Forbidden</h1>";
                exchange.sendResponseHeaders(403, page.length());
                OutputStream os = exchange.getResponseBody();
                os.write(page.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "PHOTO_HANDLER");
                return;
            }
            return;
        }
        parser.setHtml("last", "<img src=\"/camera?dev=" + ps.getParam("cam") + "\" width=\"1024\" height=\"768\" />");

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
            log.error("Failed to send answer: " + e.getMessage(), "PHOTO_HANDLER");
            return;
        }
    }
}