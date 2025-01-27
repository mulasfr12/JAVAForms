package com.dev.utilities;

import jakarta.xml.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class RssFeed {

    @XmlElement(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Channel {

        @XmlElement(name = "title")
        private String title;

        @XmlElement(name = "description")
        private String description;

        @XmlElement(name = "link")
        private String link;

        @XmlElement(name = "item")
        private List<Item> items;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Item {

            @XmlElement(name = "title")
            private String title;

            @XmlElement(name = "description")
            private String description;

            @XmlElement(name = "pubDate")
            private String publicationDate;

            @XmlElement(name = "media:thumbnail", namespace = "http://search.yahoo.com/mrss/")
            private MediaThumbnail mediaThumbnail;

            @XmlElement(name = "media:content", namespace = "http://search.yahoo.com/mrss/")
            private MediaContent mediaContent;

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

            public String getPublicationDate() {
                return publicationDate;
            }

            public void setPublicationDate(String publicationDate) {
                this.publicationDate = publicationDate;
            }

            public MediaThumbnail getMediaThumbnail() {
                if (mediaThumbnail != null && mediaThumbnail.getUrl() != null) {
                    System.out.println("Parsed Thumbnail URL: " + mediaThumbnail.getUrl());
                } else {
                    System.out.println("No Thumbnail URL found for item: " + title);
                }
                return mediaThumbnail;
            }

            public void setMediaThumbnail(MediaThumbnail mediaThumbnail) {
                this.mediaThumbnail = mediaThumbnail;
            }

            public MediaContent getMediaContent() {
                return mediaContent;
            }

            public void setMediaContent(MediaContent mediaContent) {
                this.mediaContent = mediaContent;
            }

            public java.sql.Timestamp getPublicationDateAsTimestamp() {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                    Date date = format.parse(publicationDate);
                    return new java.sql.Timestamp(date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null; // Handle invalid date formats gracefully
                }
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class MediaThumbnail {

            @XmlAttribute(name = "url")
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class MediaContent {

            @XmlAttribute(name = "url")
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
