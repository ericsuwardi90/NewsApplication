package com.ericsuwardi.newsapplication.presenter;

import com.ericsuwardi.newsapplication.view.iview.IArticleDetailView;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class ArticleDetailPresenter extends BasePresenter{

    private IArticleDetailView view;

    public ArticleDetailPresenter(IArticleDetailView view){
        this.view = view;
    }

    public void loadWebsite(String url){
        view.loadWebsite(url);
    }

    public void onLoadWebsite(){view.onStartLoadWebsite();}

    public void onFinishedLoadWebsite(){view.onFinishedLoadWebsite();}
}
