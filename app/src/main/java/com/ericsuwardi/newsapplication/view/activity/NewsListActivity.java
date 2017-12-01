package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.adapter.HeadlineAdapter;
import com.ericsuwardi.newsapplication.adapter.OtherNewsAdapter;
import com.ericsuwardi.newsapplication.helper.StringHelper;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.NewsListPresenter;
import com.ericsuwardi.newsapplication.view.iview.INewsListView;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class NewsListActivity extends AppCompatActivity implements INewsListView{

    NewsListPresenter presenter;

    RecyclerView headlineRecyclerView;
    RecyclerView otherNewsRecyclerView;
    ProgressBar headlineProgressBar;
    ProgressBar otherNewsProgressBar;
    TextView headlineErrorTextView;
    TextView otherNewsErrorTextView;

    LinearLayoutManager othersLlm;

    HeadlineAdapter headlineAdapter;
    OtherNewsAdapter otherNewsAdapter;

    int page = 1;
    String q = "";
    String country = "";
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String source_id = getIntent().getExtras().getString("source_id","");
        final String source_name = getIntent().getExtras().getString("source_name","");

        setContentView(R.layout.activity_news_list);
        setTitle(StringHelper.isNullOrEmpty(source_name) ?
                getString(R.string.news_list_activity_default_title) :
                String.format(getString(R.string.news_list_activity_formatted_title), source_name));

        headlineRecyclerView = (RecyclerView) findViewById(R.id.headline_recyclerview);
        otherNewsRecyclerView = (RecyclerView) findViewById(R.id.other_recyclerview);
        headlineProgressBar = (ProgressBar) findViewById(R.id.headline_progressbar);
        otherNewsProgressBar = (ProgressBar) findViewById(R.id.other_progressbar);
        headlineErrorTextView = (TextView) findViewById(R.id.headline_error_message);
        otherNewsErrorTextView = (TextView) findViewById(R.id.other_error_message);

        presenter = new NewsListPresenter(this, this);
        othersLlm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        headlineAdapter = new HeadlineAdapter(this, presenter, new Article[0]);
        headlineRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        headlineRecyclerView.setAdapter(headlineAdapter);

        otherNewsAdapter = new OtherNewsAdapter(this, presenter, new Article[0]);
        otherNewsRecyclerView.setLayoutManager(othersLlm);
        otherNewsRecyclerView.setAdapter(otherNewsAdapter);
        otherNewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int firstVisibleItemPosition = othersLlm.findFirstVisibleItemPosition();
                int threshold = 10;

                if (!otherNewsAdapter.isLoading() && dy > 0) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - threshold) {
                        // load for next page
                        otherNewsAdapter.setLoading(true);
                        presenter.onGetOtherNewsApi(source_id, page);
                    }
                }
            }
        });

        presenter.onGetHeadlineNewsApi(source_id);
        presenter.onGetOtherNewsApi(source_id, page);
    }

    @Override
    public void onLoadHeadlines() {
        headlineProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFirstOtherNews() {
        otherNewsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadAdditionalOtherNews() {
        Toast.makeText(this, getString(R.string.headline_info_message_load_more_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterCallHeadlineApi() {
        headlineProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void afterCallOtherNewsApi() {
        otherNewsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void failedToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedGetHeadlines() {
        headlineRecyclerView.setVisibility(View.GONE);
        headlineErrorTextView.setVisibility(View.VISIBLE);
        headlineErrorTextView.setText(getString(R.string.headline_error_message_detault));
    }

    @Override
    public void failedGetFirstOtherNews() {
        headlineRecyclerView.setVisibility(View.GONE);
        headlineErrorTextView.setVisibility(View.VISIBLE);
        headlineErrorTextView.setText(getString(R.string.other_error_message_detault));
    }

    @Override
    public void failedGetAdditionalOtherNews() {
        Toast.makeText(this, getString(R.string.headline_error_message_more_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successGetHeadlines(Article[] articles) {
        headlineRecyclerView.setVisibility(View.VISIBLE);
        headlineErrorTextView.setVisibility(View.GONE);
        headlineAdapter.addArticles(articles);
    }

    @Override
    public void successGetFirstOtherNews(Article[] articles) {
        page += 1;
        otherNewsRecyclerView.setVisibility(View.VISIBLE);
        otherNewsErrorTextView.setVisibility(View.GONE);
        otherNewsAdapter.addArticles(articles);
    }

    @Override
    public void successGetAdditionalOtherNews(Article[] articles) {
        page += 1;
        otherNewsAdapter.addArticles(articles);
    }

    @Override
    public void openNewsActivity(Article article) {

        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", article.getUrl());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
