package com.ericsuwardi.newsapplication.view.iview;

import com.ericsuwardi.newsapplication.model.Article;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public interface INewsListView {

    void onLoadHeadlines();
    void onLoadFirstOtherNews();
    void onLoadAdditionalOtherNews();

    void failedToastMessage(String message);

    void failedGetHeadlines();
    void failedGetFirstOtherNews();
    void failedGetAdditionalOtherNews();

    void afterCallHeadlineApi();
    void afterCallOtherNewsApi();

    void successGetHeadlines(Article[] articles);
    void successGetFirstOtherNews(Article[] articles);
    void successGetAdditionalOtherNews(Article[] articles);

    void openNewsActivity(Article article);
    void openSearchActivity();

}
