/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.utilities;

/**
 *
 * @author salum
 */
import com.dev.utilities.DBConnection;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Database connection established successfully!");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (Exception e) {
            System.err.println("Error while connecting to the database:");
            e.printStackTrace();
        }
    }
}