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

package com.denfnd.sys;


import java.io.IOException;


public interface SystemInfo {
    void powerOff() throws IOException;
    void reboot() throws IOException;
    void switchPwrLed(boolean state) throws IOException, InterruptedException;
    void switchStatLed(boolean state) throws IOException;
    DiskData getDiskInfo() throws IOException;
    int getCpuTemp() throws IOException;
    MemData getMemInfo() throws IOException;
    String getUptime() throws IOException;
}
