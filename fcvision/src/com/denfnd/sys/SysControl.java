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


import java.io.*;


public class SysControl implements SystemInfo {
    public void powerOff() throws IOException {
        Runtime.getRuntime().exec("poweroff");
    }

    public void reboot() throws IOException {
        Runtime.getRuntime().exec("reboot");
    }

    public void switchPwrLed(boolean state) throws IOException {
        File file = new File("/sys/class/leds/green_led/brightness");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        if (state)
            bos.write("1".getBytes());
        else
            bos.write("0".getBytes());
        bos.close();
    }

    public void switchStatLed(boolean state) throws IOException {
        File file = new File("/sys/class/leds/red_led/brightness");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        if (state)
            bos.write("1".getBytes());
        else
            bos.write("0".getBytes());
        bos.close();
    }

    public DiskData getDiskInfo() throws IOException {
        DiskData data = new DiskData();
        Process proc = Runtime.getRuntime().exec("df -ah");
        InputStream stdout = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        for (int i = 0; i < 7; i++)
            reader.readLine();

        String[] parts = reader.readLine().split("  ");

        data.setTotal(parts[1]);
        data.setUsed(parts[2]);

        stdout.close();
        reader.close();
        proc.destroy();

        return data;
    }

    public int getCpuTemp() throws IOException {
        Process proc = Runtime.getRuntime().exec("cat /sys/class/thermal/thermal_zone0/temp");
        InputStream stdout = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        String line = reader.readLine();

        stdout.close();
        reader.close();
        proc.destroy();

        return Integer.valueOf(line);
    }

    public MemData getMemInfo() throws IOException {
        int j = 0;
        MemData data = new MemData();
        Process proc = Runtime.getRuntime().exec("free -m");
        InputStream stdout = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        reader.readLine();

        String[] parts = reader.readLine().split(" ");
        for (String part : parts) {
            if (part.length() > 0) {
                if (j == 1) {
                    data.setTotal(Integer.valueOf(part));
                    break;
                }
                j++;
            }
        }
        parts = reader.readLine().split(" ");
        j = 0;
        for (String part : parts) {
            if (part.length() > 0) {
                if (j == 2) {
                    data.setUsed(Integer.valueOf(part));
                    break;
                }
                j++;
            }
        }
        stdout.close();
        reader.close();
        proc.destroy();
        return data;
    }

    public String getUptime() throws IOException {
        StringBuilder outTime = new StringBuilder();
        Process proc = Runtime.getRuntime().exec("uptime -p");
        InputStream stdout = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        String[] sTime = reader.readLine().split(" ");
        for (int i = 1; i < sTime.length; i++) {
            if (sTime[i].contains("minute"))
                outTime.append("m");
            else if (sTime[i].contains("hour"))
                outTime.append("h.");
            else if (sTime[i].contains("day"))
                outTime.append("d.");
            else if (sTime[i].contains("week"))
                outTime.append("w.");
            else if (sTime[i].contains("year"))
                outTime.append("y.");
            else
                outTime.append(sTime[i]);
        }

        stdout.close();
        reader.close();
        proc.destroy();
        return outTime.toString();
    }
}
