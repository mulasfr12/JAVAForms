/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.utilities;

/**
 *
 * @author salum
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
   private static final String URL = "jdbc:sqlserver://localhost;databaseName=NewsApplicationDB;integratedSecurity=true;encrypt=false;";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL);
    }
}