/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dao;

/**
 *
 * @author salum
 */
import com.dev.utilities.DBConnection;
import com.dev.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    // Create Category
    public boolean createCategory(Category category) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Categories (Name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve All Categories
    public List<Category> getAllCategories() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Categories";
        List<Category> categoryList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setName(rs.getString("Name"));
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
