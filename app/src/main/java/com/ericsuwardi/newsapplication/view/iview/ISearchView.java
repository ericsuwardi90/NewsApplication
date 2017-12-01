package com.ericsuwardi.newsapplication.view.iview;

import com.ericsuwardi.newsapplication.model.Article;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public interface ISearchView {

    void getNewQuery(Article[] articles);
    void getMoreResult(Article[] articles);
    void openNewsActivity(Article article);

}
