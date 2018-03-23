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

package com.denfnd.hardware;


import com.denfnd.Path;
import com.denfnd.utils.Logger;


public class Camera implements CamDevice {
    private Logger log;

    public Camera(Logger log) {
        this.log = log;
    }

    public synchronized boolean getPhoto(int camera) {
        int retVal;
        Path path = Path.getInstance();
        String filename = path.get("camera") + "/photo" + String.valueOf(camera) + ".jpg";
        String cmd = path.get("camera") + "/camera -g " + String.valueOf(camera) + " " + filename;

        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
            proc.destroy();
            retVal = proc.exitValue();

            if (retVal != 0)
                return false;
        }
        catch (Exception e) {
            log.error("Fail to get photo: " + e.getMessage(), "CAMERA");
            return false;
        }
        return true;
    }
}
