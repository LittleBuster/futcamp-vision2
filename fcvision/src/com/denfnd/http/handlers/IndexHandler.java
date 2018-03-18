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


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;


public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder builder = new StringBuilder();

        exchange.sendResponseHeaders(200, builder.length());
        builder.append("<h1>Index Page</h1>");

        OutputStream os = exchange.getResponseBody();
        os.write(builder.toString().getBytes());
        os.close();
    }
}
