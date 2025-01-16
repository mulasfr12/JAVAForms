/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.utilities;

/**
 *
 * @author salum
 */
public class DBConnectionTest {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to database...");
            DBConnection.getConnection();
            System.out.println("Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}