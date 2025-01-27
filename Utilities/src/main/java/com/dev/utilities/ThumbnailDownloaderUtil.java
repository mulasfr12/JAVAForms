package com.dev.utilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author salum
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

public class ThumbnailDownloaderUtil {
    private static final String ASSETS_DIR = "assets/thumbnails/";

    // Method to download thumbnail
    public static String downloadThumbnail(String imageUrl, String imageName) {
        try {
            // Create directory if it doesn't exist
            File directory = new File(ASSETS_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Establish a connection to the URL
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the input stream
            InputStream inputStream = connection.getInputStream();

            // Define the file path
            String filePath = Paths.get(ASSETS_DIR, imageName).toString();

            // Write the file
            FileOutputStream outputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            outputStream.close();
            inputStream.close();

            return filePath; // Return relative path
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
