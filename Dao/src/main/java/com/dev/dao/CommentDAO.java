/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dao;

/**
 *
 * @author salum
 */
import com.dev.model.Comment;
import com.dev.utilities.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    // Create Comment
    public boolean createComment(Comment comment) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Comments (NewsID, CommentText, UserName, CommentDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, comment.getNewsID());
            stmt.setString(2, comment.getCommentText());
            stmt.setString(3, comment.getUserName());
            stmt.setTimestamp(4, new Timestamp(comment.getCommentDate().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve All Comments for a News Item
    public List<Comment> getCommentsByNewsID(int newsID) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Comments WHERE NewsID = ?";
        List<Comment> commentList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newsID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setCommentID(rs.getInt("CommentID"));
                    comment.setNewsID(rs.getInt("NewsID"));
                    comment.setCommentText(rs.getString("CommentText"));
                    comment.setUserName(rs.getString("UserName"));
                    comment.setCommentDate(rs.getTimestamp("CommentDate"));
                    commentList.add(comment);
                }
            }
        }
        return commentList;
    }
}
