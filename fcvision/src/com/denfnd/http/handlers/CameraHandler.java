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
import com.denfnd.hardware.CamDevice;
import com.denfnd.http.ParamsDecoder;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;


public class CameraHandler implements HttpHandler {
    private Logger log;
    private CamDevice cam;

    public CameraHandler(Logger log, CamDevice cam) {
        this.log = log;
        this.cam = cam;
    }

    @Override
    public void handle(HttpExchange ex) {
        Path path = Path.getInstance();
        ParamsDecoder ps = new ParamsDecoder();

        try {
            ps.split(ex.getRequestURI().getQuery());
        }
        catch (Exception e) {
            log.error("Failed to decoding params", "CAM_HANDELR");
            try {
                String response = "<h1>403<br>Forbidden</h1>\n";
                ex.sendResponseHeaders(403, response.length());
                OutputStream os = ex.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "CAM_HANDELR");
                return;
            }
            return;
        }

        if (!cam.getPhoto(Integer.valueOf(ps.getParam("dev")))) {
            try {
                String response = "<h1>403<br>Forbidden</h1>\n";
                ex.sendResponseHeaders(403, response.length());
                OutputStream os = ex.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "CAM_HANDELR");
                return;
            }
            return;
        }

        try {
            File file = new File(path.get("camera") + "/photo" + ps.getParam("dev") + ".jpg");
            if (!file.isFile()) {
                String response = "<h1>404<br>Not Found</h1>\n";
                ex.sendResponseHeaders(404, response.length());
                OutputStream os = ex.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                ex.sendResponseHeaders(200, 0);
                OutputStream os = ex.getResponseBody();
                FileInputStream fs = new FileInputStream(file);
                final byte[] buffer = new byte[0x4096];
                int count;
                while ((count = fs.read(buffer)) >= 0) {
                    os.write(buffer, 0, count);
                }
                fs.close();
                os.close();
            }
        } catch (Exception e) {
            log.error("Failed to load photo: " + e.getMessage(), "CAM_HANDELR");
        }
    }
}