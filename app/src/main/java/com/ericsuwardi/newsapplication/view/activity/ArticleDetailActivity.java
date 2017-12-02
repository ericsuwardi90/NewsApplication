package com.ericsuwardi.newsapplication.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.presenter.ArticleDetailPresenter;
import com.ericsuwardi.newsapplication.view.iview.IArticleDetailView;

public class ArticleDetailActivity extends BaseActivity implements IArticleDetailView{

    WebView webView;
    ProgressBar progressBar;
    ArticleDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        setTitle(getString(R.string.article_detail_activity_title));

        String url = getIntent().getExtras().getString("url", "");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        webView = (WebView) findViewById(R.id.web_view);
        presenter = new ArticleDetailPresenter(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient(presenter));
        presenter.loadWebsite(url);

    }

    @Override
    public void onStartLoadWebsite() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadWebsite(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void onFinishedLoadWebsite() {
        progressBar.setVisibility(View.GONE);
    }

    private class CustomWebViewClient extends WebViewClient{

        ArticleDetailPresenter mPresenter;

        CustomWebViewClient(ArticleDetailPresenter presenter){

            mPresenter = presenter;
            mPresenter.onLoadWebsite();
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            mPresenter.onFinishedLoadWebsite();
        }
    }
}
