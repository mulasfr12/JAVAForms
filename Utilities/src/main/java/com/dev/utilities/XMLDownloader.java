/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.utilities;

/**
 *
 * @author salum
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;

public class XMLDownloader {

    // Method to download the XML file
    public static void downloadXML(String xmlUrl, String fileName) {
        String saveDirectory = "src/main/resources/xml";

        try {
            // Create the save directory if it doesn't exist
            Files.createDirectories(Paths.get(saveDirectory));

            // Open connection to the URL
            URL url = new URL(xmlUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check for a valid response code
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read data from the URL
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(saveDirectory + "/" + fileName)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("XML file downloaded successfully to " + saveDirectory);
                }
            } else {
                System.out.println("Failed to download XML file. HTTP Response Code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            System.err.println("Error downloading XML file: " + e.getMessage());
        }
    }
}
