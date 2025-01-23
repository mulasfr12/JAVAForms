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

        // Handle potential null for publicationDate
        if (news.getPublicationDate() != null) {
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));
        } else {
            stmt.setNull(5, java.sql.Types.TIMESTAMP);
        }

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
    if (newsList == null || newsList.isEmpty()) {
        throw new IllegalArgumentException("News list cannot be null or empty.");
    }

    // Process the list using Streams
    List<News> processedNews = newsList.stream()
            .filter(news -> news.getTitle() != null && !news.getTitle().isEmpty()) // Filter out news with empty titles
            .distinct() // Ensure the list is distinct based on equals() and hashCode()
            .sorted((n1, n2) -> {
                // Handle null publication dates by pushing them to the end of the list
                if (n1.getPublicationDate() == null) return 1;
                if (n2.getPublicationDate() == null) return -1;
                return n1.getPublicationDate().compareTo(n2.getPublicationDate());
            })
            .toList(); // Collect to a new list

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(CREATE_NEWS)) {
        for (News news : processedNews) {
            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getThumbnailImage());
            stmt.setString(4, news.getFullSizeImage());

            // Handle potential null for publicationDate
            if (news.getPublicationDate() != null) {
                stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

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

        // Handle potential null for publicationDate
        if (news.getPublicationDate() != null) {
            stmt.setTimestamp(5, new Timestamp(news.getPublicationDate().getTime()));
        } else {
            stmt.setNull(5, java.sql.Types.TIMESTAMP);
        }

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

    // Process the list using Streams
    return newsList.stream()
            .filter(news -> news.getTitle() != null && !news.getTitle().isEmpty()) // Filter out news with empty titles
            .distinct() // Ensure the list is distinct
            .sorted((n1, n2) -> n1.getTitle().compareToIgnoreCase(n2.getTitle())) // Sort alphabetically by title
            .toList(); // Collect to a new list
}


    // Comment Management
    @Override
public int createComment(Comment comment) throws Exception {
    String query = "INSERT INTO Comments (NewsID, CommentText, UserName, CommentDate) VALUES (?, ?, ?, ?)";

    try (Connection conn = DataSourceSingleton.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, comment.getNewsID());
        stmt.setString(2, comment.getCommentText());
        stmt.setString(3, comment.getUserName());
        stmt.setTimestamp(4, new Timestamp(comment.getCommentDate().getTime()));

        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1); // Return the auto-generated CommentID
            }
        }
    }
    return 0; // Return 0 if no ID is generated
}


    public void createComments(List<Comment> commentList) throws Exception {
    // Validate input list
    if (commentList == null || commentList.isEmpty()) {
        throw new IllegalArgumentException("Comment list cannot be null or empty.");
    }

    // Use Streams to filter and process comments
    List<Comment> processedComments = commentList.stream()
            .filter(comment -> comment.getCommentText() != null && !comment.getCommentText().isEmpty()) // Filter out empty comments
            .distinct() // Ensure comments are distinct based on equals() and hashCode()
            .sorted((c1, c2) -> c1.getCommentDate().compareTo(c2.getCommentDate())) // Sort by comment date
            .skip(1) // Skip the first comment (optional, for demonstration)
            .toList(); // Collect the processed list

    // Use Streams to demonstrate additional operations
    boolean hasShortComments = commentList.stream()
            .anyMatch(comment -> comment.getCommentText().length() < 5); // Check if any comment is very short

    if (hasShortComments) {
        System.out.println("Warning: Some comments are shorter than 5 characters.");
    }

    // Find the earliest comment date
    commentList.stream()
            .min((c1, c2) -> c1.getCommentDate().compareTo(c2.getCommentDate()))
            .ifPresent(comment -> System.out.println("Earliest comment date: " + comment.getCommentDate()));

    // Batch insert using the processed comments
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(CREATE_COMMENT)) {

        for (Comment comment : processedComments) {
            stmt.setInt(1, comment.getNewsID());
            stmt.setString(2, comment.getCommentText());
            stmt.setString(3, comment.getUserName());
            stmt.setTimestamp(4, new Timestamp(comment.getCommentDate().getTime()));
            stmt.addBatch();
        }

        stmt.executeBatch(); // Execute the batch insert
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
