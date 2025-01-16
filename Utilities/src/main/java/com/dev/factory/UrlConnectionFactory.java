/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.factory;

/**
 *
 * @author salum
 */
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionFactory {

    public static InputStream getInputStream(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return connection.getInputStream();
        } else {
            throw new RuntimeException("Failed to fetch data. HTTP Code: " + connection.getResponseCode());
        }
    }
}