/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dev.dal;

/**
 *
 * @author salum
 */

import com.dev.model.Comment;
import com.dev.model.News;

import java.util.List;
import java.util.Optional;

public interface Repository {
    // News Management
    int createNews(News news) throws Exception;
    void createNews(List<News> newsList) throws Exception;
    void updateNews(int id, News news) throws Exception;
    void deleteNews(int id) throws Exception;
    Optional<News> selectNews(int id) throws Exception;
    List<News> selectAllNews() throws Exception;

    // Comment Management
    int createComment(Comment comment) throws Exception;
    void createComments(List<Comment> commentList) throws Exception;
    void updateComment(int id, Comment comment) throws Exception;
    void deleteComment(int id) throws Exception;
    Optional<Comment> selectComment(int id) throws Exception;
    List<Comment> selectCommentsByNewsID(int newsID) throws Exception;
}
