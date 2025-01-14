/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dao;

/**
 *
 * @author salum
 */
import com.dev.model.News;
import com.dev.utilities.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    // Create News
    public boolean createNews(News news) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO News (Title, Description, ThumbnailImage, FullSizeImage, PublicationDate, CategoryID, MediaDescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getThumbnailImage());
            stmt.setString(4, news.getFullSizeImage());
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));
            stmt.setInt(6, news.getCategoryID());
            stmt.setString(7, news.getMediaDescription());
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve All News
    public List<News> getAllNews() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM News";
        List<News> newsList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                News news = new News();
                news.setNewsID(rs.getInt("NewsID"));
                news.setTitle(rs.getString("Title"));
                news.setDescription(rs.getString("Description"));
                news.setThumbnailImage(rs.getString("ThumbnailImage"));
                news.setFullSizeImage(rs.getString("FullSizeImage"));
                news.setPublicationDate(rs.getTimestamp("PublicationDate"));
                news.setCategoryID(rs.getInt("CategoryID"));
                news.setMediaDescription(rs.getString("MediaDescription"));
                newsList.add(news);
            }
        }
        return newsList;
    }

    // Update News
    public boolean updateNews(News news) throws SQLException, ClassNotFoundException {
        String query = "UPDATE News SET Title = ?, Description = ?, ThumbnailImage = ?, FullSizeImage = ?, PublicationDate = ?, CategoryID = ?, MediaDescription = ? WHERE NewsID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getThumbnailImage());
            stmt.setString(4, news.getFullSizeImage());
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));
            stmt.setInt(6, news.getCategoryID());
            stmt.setString(7, news.getMediaDescription());
            stmt.setInt(8, news.getNewsID());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete News
    public boolean deleteNews(int newsID) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM News WHERE NewsID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newsID);
            return stmt.executeUpdate() > 0;
        }
    }
}

