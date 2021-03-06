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
import java.io.IOException;


public interface Server {
    void init() throws IOException;
    void addHandler(String path, HttpHandler handler);
    void start(int port, int queu, int threads) throws IOException;
}
