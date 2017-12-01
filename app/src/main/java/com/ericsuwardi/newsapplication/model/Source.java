package com.ericsuwardi.newsapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ericsuwardi on 11/30/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {
    /*
        "id": "abc-news",
        "name": "ABC News",
        "description": "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.",
        "url": "http://abcnews.go.com",
        "category": "general",
        "language": "en",
        "country": "us"
    * */
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
