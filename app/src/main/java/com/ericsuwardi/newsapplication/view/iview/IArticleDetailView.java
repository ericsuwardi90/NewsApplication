package com.ericsuwardi.newsapplication.view.iview;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public interface IArticleDetailView {

    void onStartLoadWebsite();
    void loadWebsite(String url);
    void onFinishedLoadWebsite();
}
