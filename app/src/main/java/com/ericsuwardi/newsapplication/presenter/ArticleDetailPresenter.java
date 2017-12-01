package com.ericsuwardi.newsapplication.presenter;

import android.content.Context;

import com.ericsuwardi.newsapplication.view.iview.IArticleDetailView;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class ArticleDetailPresenter {

    private IArticleDetailView view;
    private Context context;

    public ArticleDetailPresenter(IArticleDetailView view, Context context){
        this.view = view;
        this.context = context;
    }
}
