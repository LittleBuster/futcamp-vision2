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


import com.denfnd.http.ParamsDecoder;
import com.denfnd.sys.DiskData;
import com.denfnd.sys.MemData;
import com.denfnd.sys.SystemInfo;
import com.denfnd.utils.Logger;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;


public class SysHandler implements HttpHandler {
    private Logger log;
    private SystemInfo sys;

    public SysHandler(Logger log, SystemInfo sys) {
        this.log = log;
        this.sys = sys;
    }

    @Override
    public void handle(HttpExchange exchange) {
        String page = "{\"result\": false}";
        ParamsDecoder ps = new ParamsDecoder();

        try {
            ps.split(exchange.getRequestURI().getQuery());
        }
        catch (Exception e) {
            log.error("Failed to decoding params", "SYS_HANDLER");
            try {
                page = "<h1>403<br>Forbidden</h1>";
                exchange.sendResponseHeaders(403, page.length());
                OutputStream os = exchange.getResponseBody();
                os.write(page.getBytes());
                os.close();
            }
            catch (Exception err) {
                log.error("Failed to send answer: " + err.getMessage(), "SYS_HANDLER");
                return;
            }
            return;
        }

        if (ps.getParam("cmd").equals("cpu_temp")) {
            try {
                page = "{\"result\": true, \"cpu_temp\": " + String.valueOf(sys.getCpuTemp()) + "}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to read CPU temp: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("ram_info")) {
            try {
                MemData data = sys.getMemInfo();
                page = "{\"result\": true, \"ram_used\": \"" + data.getUsed() + "\", \"ram_total\": \"" + data.getTotal() + "\"}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to read RAM info: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("disk_info")) {
            try {
                DiskData data = sys.getDiskInfo();
                page = "{\"result\": true, \"disk_used\": \"" + data.getUsed() + "\", \"disk_total\": \"" + data.getTotal() + "\"}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to read disk info: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("uptime")) {
            try {
                page = "{\"result\": true, \"uptime\": \"" + sys.getUptime() + "\"}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to read uptime: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("reboot")) {
            try {
                sys.reboot();
                page = "{\"result\": true}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to reboot: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("power_led")) {
            try {
                if (ps.getParam("status").equals("on")) {
                    sys.switchPwrLed(true);
                    page = "{\"result\": true}";
                }
                else if (ps.getParam("status").equals("off")) {
                    sys.switchPwrLed(false);
                    page = "{\"result\": true}";
                }
                else {
                    page = "{\"result\": false}";
                }
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to switch power led: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("status_led")) {
            try {
                if (ps.getParam("status").equals("on")) {
                    sys.switchStatLed(true);
                    page = "{\"result\": true}";
                }
                else if (ps.getParam("status").equals("off")) {
                    sys.switchStatLed(false);
                    page = "{\"result\": true}";
                }
                else {
                    page = "{\"result\": false}";
                }
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to switch power led: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        if (ps.getParam("cmd").equals("poweroff")) {
            try {
                sys.powerOff();
                page = "{\"result\": true}";
            } catch (Exception e) {
                page = "{\"result\": false}";
                log.error("Fail to power off: " + e.getMessage(), "SYS_HANDLER");
            }
        }

        try {
            exchange.sendResponseHeaders(200, page.length());
            OutputStream os = exchange.getResponseBody();
            os.write(page.getBytes());
            os.close();
        }
        catch (Exception e) {
            log.error("Failed to send answer: " + e.getMessage(), "SYS_HANDLER");
            return;
        }
    }
}