package com.ericsuwardi.newsapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ericsuwardi on 11/30/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    /*
    source": {
        "id": null,
        "name": "Sostav.ru"
    },
    "author": null,
    "title": "Bitcoin",
    "description": "Cryptocurrencies are gaining more and more popularity and on the background of this interest in them by the ordinary web users is justified. Is it really worth to get there if you do not understand anything? The answer is very simple - only the latest news in…",
    "url": "http://www.sostav.ru/blogs/132684/23861/",
    "urlToImage": null,
    "publishedAt": "2017-10-28T06:34:12Z"
    * */

    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
