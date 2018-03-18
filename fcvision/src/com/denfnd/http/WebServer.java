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


import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WebServer implements Server {
    private HttpServer server;

    public void init() throws IOException {
        server = HttpServer.create();
    }

    public void addHandler(String path, HttpHandler handler) {
        server.createContext(path, handler);
    }

    public void start(int port, int queue, int threads) throws IOException {
        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        server.bind(new InetSocketAddress(port), queue);
        server.setExecutor(executor);
        server.start();
    }
}
