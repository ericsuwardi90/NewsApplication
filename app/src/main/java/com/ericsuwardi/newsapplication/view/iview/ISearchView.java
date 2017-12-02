package com.ericsuwardi.newsapplication.view.iview;

import com.ericsuwardi.newsapplication.model.Article;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public interface ISearchView {

    void onLoadNewQuery();
    void afterCallSearchApi();

    void getNewQuery(Article[] articles);
    void getMoreResults(Article[] articles);

    void failedGetNewQuery();
    void failedGetMoreResults();

    void openNewsActivity(Article article);
}
