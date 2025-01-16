package com.dev.utilities;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class Item {

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "pubDate")
    private String pubDate;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "guid")
    private String guid;

    @XmlElement(name = "thumbnail", namespace = "http://search.yahoo.com/mrss/")
    private MediaThumbnail mediaThumbnail;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public MediaThumbnail getMediaThumbnail() {
        return mediaThumbnail;
    }

    public void setMediaThumbnail(MediaThumbnail mediaThumbnail) {
        this.mediaThumbnail = mediaThumbnail;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MediaThumbnail {

        @XmlAttribute(name = "url")
        private String url;

        @XmlAttribute(name = "width")
        private int width;

        @XmlAttribute(name = "height")
        private int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
