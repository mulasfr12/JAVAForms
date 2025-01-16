package com.dev.utilities;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.URL;

public class RssFeedParser {

    public static void main(String[] args) {
        String rssFeedUrl = "https://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml";

        try {
            URL url = new URL(rssFeedUrl);
            InputStream inputStream = url.openStream();

            JAXBContext context = JAXBContext.newInstance(RssFeed.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            RssFeed rssFeed = (RssFeed) unmarshaller.unmarshal(inputStream);

            System.out.println("Feed Title: " + rssFeed.getChannel().getTitle());
            System.out.println("Feed Description: " + rssFeed.getChannel().getDescription());

            rssFeed.getChannel().getItems().forEach(item -> {
                System.out.println("-------------------------");
                System.out.println("Title: " + item.getTitle());
                System.out.println("Description: " + item.getDescription());
                System.out.println("Publication Date: " + item.getPubDate());
                System.out.println("Link: " + item.getLink());
                if (item.getMediaThumbnail() != null) {
                    System.out.println("Thumbnail URL: " + item.getMediaThumbnail().getUrl());
                    System.out.println("Thumbnail Dimensions: " + item.getMediaThumbnail().getWidth() + "x" + item.getMediaThumbnail().getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
