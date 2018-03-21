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


public class MemData {
    private int used;
    private int total;

    public void setUsed(int mem) {
        used = mem;
    }

    public void setTotal(int mem) {
        total = mem;
    }

    public int getUsed() {
        return used;
    }

    public int getTotal() {
        return total;
    }
}
