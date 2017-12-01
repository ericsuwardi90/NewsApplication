package com.ericsuwardi.newsapplication.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.presenter.ArticleDetailPresenter;
import com.ericsuwardi.newsapplication.view.iview.IArticleDetailView;

public class ArticleDetailActivity extends AppCompatActivity implements IArticleDetailView{

    WebView webView;
    ArticleDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        setTitle(getString(R.string.article_detail_activity_title));

        String url = getIntent().getExtras().getString("url", "");

        webView = (WebView) findViewById(R.id.web_view);
        presenter = new ArticleDetailPresenter(this, this);

        webView.loadUrl(url);

    }
}