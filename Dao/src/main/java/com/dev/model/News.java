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

public class News implements Comparable<News> {
    private int newsID;
    private String title;
    private String description;
    private String thumbnailImage;
    private String fullSizeImage;
    private Date publicationDate;
    private int categoryID;
    private String mediaDescription;

    // Getters and Setters
    public int getNewsID() { return newsID; }
    public void setNewsID(int newsID) { this.newsID = newsID; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getThumbnailImage() { return thumbnailImage; }
    public void setThumbnailImage(String thumbnailImage) { this.thumbnailImage = thumbnailImage; }
    public String getFullSizeImage() { return fullSizeImage; }
    public void setFullSizeImage(String fullSizeImage) { this.fullSizeImage = fullSizeImage; }
    public Date getPublicationDate() { return publicationDate; }
    public void setPublicationDate(Date publicationDate) { this.publicationDate = publicationDate; }
    public int getCategoryID() { return categoryID; }
    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }
    public String getMediaDescription() { return mediaDescription; }
    public void setMediaDescription(String mediaDescription) { this.mediaDescription = mediaDescription; }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return newsID == news.newsID && title.equals(news.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsID, title);
    }

    // toString
    @Override
    public String toString() {
        return "News{" +
                "newsID=" + newsID +
                ", title='" + title + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }

    // Comparable
    @Override
    public int compareTo(News o) {
        return this.publicationDate.compareTo(o.publicationDate);
    }
}

