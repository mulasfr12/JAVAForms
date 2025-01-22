/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dal.sql;

/**
 *
 * @author salum
 */
import com.dev.dal.Repository;
import com.dev.model.Comment;
import com.dev.model.News;
import com.dev.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqlRepository implements Repository {

    // SQL Queries for News
   private static final String CREATE_NEWS = "INSERT INTO News (Title, Description, ThumbnailImage, FullSizeImage, PublicationDate, MediaDescription) VALUES (?, ?, ?, ?, ?, ?)";
   private static final String UPDATE_NEWS = "UPDATE News SET Title = ?, Description = ?, ThumbnailImage = ?, FullSizeImage = ?, PublicationDate = ?, MediaDescription = ? WHERE NewsID = ?";

    private static final String DELETE_NEWS = "DELETE FROM News WHERE NewsID = ?";
    private static final String SELECT_NEWS = "SELECT * FROM News WHERE NewsID = ?";
    private static final String SELECT_ALL_NEWS = "SELECT * FROM News";

    // SQL Queries for Comments
    private static final String CREATE_COMMENT = "INSERT INTO Comments (NewsID, CommentText, UserName, CommentDate) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_COMMENT = "UPDATE Comments SET CommentText = ?, UserName = ?, CommentDate = ? WHERE CommentID = ?";
    private static final String DELETE_COMMENT = "DELETE FROM Comments WHERE CommentID = ?";
    private static final String SELECT_COMMENT = "SELECT * FROM Comments WHERE CommentID = ?";
    private static final String SELECT_COMMENTS_BY_NEWS = "SELECT * FROM Comments WHERE NewsID = ?";

    // News Management
    @Override
    public int createNews(News news) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_NEWS, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getThumbnailImage());
            stmt.setString(4, news.getFullSizeImage());
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));        
            stmt.setString(6, news.getMediaDescription());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating news failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void createNews(List<News> newsList) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_NEWS)) {
            for (News news : newsList) {
                stmt.setString(1, news.getTitle());
                stmt.setString(2, news.getDescription());
                stmt.setString(3, news.getThumbnailImage());
                stmt.setString(4, news.getFullSizeImage());
                stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));        
                stmt.setString(6, news.getMediaDescription());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public void updateNews(int id, News news) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_NEWS)) {
            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getThumbnailImage());
            stmt.setString(4, news.getFullSizeImage());
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));      
            stmt.setString(6, news.getMediaDescription());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteNews(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_NEWS)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<News> selectNews(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_NEWS)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new News(
                            rs.getInt("NewsID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("ThumbnailImage"),
                            rs.getString("FullSizeImage"),
                            rs.getTimestamp("PublicationDate"),                     
                            rs.getString("MediaDescription")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<News> selectAllNews() throws Exception {
        List<News> newsList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_NEWS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                newsList.add(new News(
                        rs.getInt("NewsID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("ThumbnailImage"),
                        rs.getString("FullSizeImage"),
                        rs.getTimestamp("PublicationDate"),                    
                        rs.getString("MediaDescription")
                ));
            }
        }
        return newsList;
    }

    // Comment Management
    @Override
    public int createComment(Comment comment) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, comment.getNewsID());
            stmt.setString(2, comment.getCommentText());
            stmt.setString(3, comment.getUserName());
            stmt.setTimestamp(4, new Timestamp(comment.getCommentDate().getTime()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating comment failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void createComments(List<Comment> commentList) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_COMMENT)) {
            for (Comment comment : commentList) {
                stmt.setInt(1, comment.getNewsID());
                stmt.setString(2, comment.getCommentText());
                stmt.setString(3, comment.getUserName());
                stmt.setTimestamp(4, new Timestamp(comment.getCommentDate().getTime()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public void updateComment(int id, Comment comment) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_COMMENT)) {
            stmt.setString(1, comment.getCommentText());
            stmt.setString(2, comment.getUserName());
            stmt.setTimestamp(3, new Timestamp(comment.getCommentDate().getTime()));
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteComment(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_COMMENT)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Comment> selectComment(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COMMENT)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Comment(
                            rs.getInt("CommentID"),
                            rs.getInt("NewsID"),
                            rs.getString("CommentText"),
                            rs.getString("UserName"),
                            rs.getTimestamp("CommentDate")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Comment> selectCommentsByNewsID(int newsID) throws Exception {
        List<Comment> commentList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COMMENTS_BY_NEWS)) {
            stmt.setInt(1, newsID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    commentList.add(new Comment(
                            rs.getInt("CommentID"),
                            rs.getInt("NewsID"),
                            rs.getString("CommentText"),
                            rs.getString("UserName"),
                            rs.getTimestamp("CommentDate")
                    ));
                }
            }
        }
        return commentList;
    }
}
