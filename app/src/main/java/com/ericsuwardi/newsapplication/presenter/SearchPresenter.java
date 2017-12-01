package com.ericsuwardi.newsapplication.presenter;

import android.content.Context;

import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.view.iview.ISearchView;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class SearchPresenter extends BasePresenter{

    ISearchView view;
    Context context;

    public SearchPresenter(ISearchView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void openNewsPage(Article article){
        view.openNewsActivity(article);
    }
}
