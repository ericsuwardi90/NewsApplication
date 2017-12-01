package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.adapter.OtherNewsAdapter;
import com.ericsuwardi.newsapplication.adapter.SearchResultAdapter;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.SearchPresenter;
import com.ericsuwardi.newsapplication.view.iview.ISearchView;

public class SearchActivity extends BaseActivity implements ISearchView{

    SearchPresenter presenter;

    RecyclerView recyclerView;
    SearchResultAdapter adapter;

    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        presenter = new SearchPresenter(this, this);
        adapter = new SearchResultAdapter(this, presenter, new Article[0]);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getNewQuery(Article[] articles) {
        page = 1;
    }

    @Override
    public void getMoreResult(Article[] articles) {
        page +=1;


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
