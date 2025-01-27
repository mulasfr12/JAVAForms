package com.dev.utilities;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageDownloader {

    // Downloads an image from a URL and saves it to a local directory
    public static String downloadImage(String imageUrl, String saveDirectory) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get(saveDirectory));

            // Extract the file name from the URL
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            String filePath = saveDirectory + File.separator + fileName;

            // Download the image
            try (InputStream inputStream = new URL(imageUrl).openStream();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("Image downloaded to: " + filePath);
            return "assets/" + fileName; // Return relative path

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
