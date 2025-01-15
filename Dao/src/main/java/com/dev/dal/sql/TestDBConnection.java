/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dal.sql;

/**
 *
 * @author salum
 */
import javax.sql.DataSource;
import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            DataSource dataSource = DataSourceSingleton.getInstance();
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Connected to the database successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}