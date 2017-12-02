package com.ericsuwardi.newsapplication.view.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.presenter.ArticleDetailPresenter;
import com.ericsuwardi.newsapplication.view.iview.IArticleDetailView;

public class ArticleDetailActivity extends BaseActivity implements IArticleDetailView{

    WebView webView;
    ArticleDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        setTitle(getString(R.string.article_detail_activity_title));

        String url = getIntent().getExtras().getString("url", "");

        webView = (WebView) findViewById(R.id.web_view);
        presenter = new ArticleDetailPresenter(this);

        presenter.loadWebsite(url);

    }

    @Override
    public void loadWebsite(String url) {
        webView.loadUrl(url);
    }
}
