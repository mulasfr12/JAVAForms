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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageDownloader {

    private static final String ASSETS_DIR = "src/main/resources/otherSources/assets/";

    public static String downloadImage(String imageUrl, String fileName) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null; // No image to download
        }

        try {
            // Ensure the assets directory exists
            Files.createDirectories(Paths.get(ASSETS_DIR));

            // Define the file path for the image
            String filePath = ASSETS_DIR + fileName;

            // Open a connection to the image URL and save it to the local directory
            URL url = new URL(imageUrl);
            try (InputStream inputStream = url.openStream();
                 OutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Return the relative path for storage in the database
            return "otherSources/assets/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download image: " + e.getMessage());
        }
    }
}
