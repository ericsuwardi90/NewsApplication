package com.ericsuwardi.newsapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ericsuwardi on 11/30/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBody {
    /*

        "status": "error",
        "code": "apiKeyMissing",
        "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
    * */

    private String status;
    private String code;
    private String message;

    private Article[] articles;
    private Source[] sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public Source[] getSources() {
        return sources;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }
}
