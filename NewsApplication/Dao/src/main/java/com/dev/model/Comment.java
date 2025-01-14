/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.model;

/**
 *
 * @author salum
 */
import java.util.Date;
import java.util.Objects;

public class Comment {
    private int commentID;
    private int newsID;
    private String commentText;
    private String userName;
    private Date commentDate;

    // Getters and Setters
    public int getCommentID() { return commentID; }
    public void setCommentID(int commentID) { this.commentID = commentID; }
    public int getNewsID() { return newsID; }
    public void setNewsID(int newsID) { this.newsID = newsID; }
    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Date getCommentDate() { return commentDate; }
    public void setCommentDate(Date commentDate) { this.commentDate = commentDate; }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentID == comment.commentID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID);
    }

    // toString
    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", newsID=" + newsID +
                ", userName='" + userName + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
}
