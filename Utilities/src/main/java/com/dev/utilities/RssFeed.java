package com.dev.utilities;

import jakarta.xml.bind.annotation.*;
import java.util.List;

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
        private String description; // Add this field

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

        public String getDescription() { // Add this getter
            return description;
        }

        public void setDescription(String description) { // Add this setter
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
    }
}
