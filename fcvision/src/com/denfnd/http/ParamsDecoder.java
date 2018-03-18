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


import java.util.HashMap;
import java.util.Map;


class ParamsDecoder {
    private Map<String, String> ps = new HashMap<>();

    public boolean split(String request) {
        boolean notExists = true;
        String[] parts = request.split("&");

        if (parts.length == 0) {
            String[] params = request.split("=");
            if (params.length < 2)
                return false;

            ps.put(params[0], params[1]);
            return true;
        }

        for (int i = 0; i < parts.length; i++) {
            String[] params = parts[i].split("=");

            if (params.length < 2)
                continue;

            ps.put(params[0], params[1]);
            notExists = false;
        }

        if (notExists)
            return false;
        else
            return true;
    }

    String getParam(String name) {
        return ps.get(name);
    }
}