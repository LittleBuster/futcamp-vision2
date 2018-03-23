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


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class PageParser {
    private Document html;

    public void loadTemplate(String filename) throws IOException {
        String line;
        File pgFile = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(pgFile.getCanonicalFile()));
        StringBuilder page = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            page.append(line);
        }
        html = Jsoup.parse(page.toString());
    }

    public String getValue(String id) {
        Element element = html.body().getElementById(id);
        return element.text();
    }

    public void setValue(String id, String value) {
        Element element = html.body().getElementById(id);
        element.text(value);
    }

    public void setHtml(String id, String value) {
        Element element = html.body().getElementById(id);
        element.after(value);
    }

    public String buildPage() {
        return html.outerHtml();
    }
}
