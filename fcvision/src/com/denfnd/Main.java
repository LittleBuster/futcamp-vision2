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


import com.denfnd.http.*;
import com.denfnd.sys.SysControl;
import com.denfnd.sys.SystemInfo;
import com.denfnd.utils.*;


public class Main {
    public static void main(String[] args) {
        Logger log = new Log();
        Configurable cfg = new Configs();
        Server wserver = new WebServer();
        SystemInfo sys = new SysControl();
        Maker hmaker = new HandlersMaker(log, sys);
        Application app = new Application(log, cfg, wserver, hmaker);

        app.start();
    }
}
