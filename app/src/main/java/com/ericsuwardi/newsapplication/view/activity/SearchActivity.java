package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;


import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.adapter.SearchResultAdapter;
import com.ericsuwardi.newsapplication.helper.StringHelper;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.SearchPresenter;
import com.ericsuwardi.newsapplication.view.iview.ISearchView;
import com.joanzapata.iconify.widget.IconTextView;

public class SearchActivity extends BaseActivity implements ISearchView, View.OnClickListener{

    SearchPresenter presenter;
    SearchResultAdapter adapter;

    EditText searchEditText;
    IconTextView searchIcon;
    RecyclerView recyclerView;
    LinearLayoutManager llm;

    int page = 1;
    String query;
    String from;
    String to;
    String sortBy;
    String language;

    String source_id;
    String source_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        source_id = getIntent().getExtras().getString("source_id","");
        source_name = getIntent().getExtras().getString("source_name","");

        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchEditText = (EditText) findViewById(R.id.search_query);
        searchIcon = (IconTextView) findViewById(R.id.search_icon_submit);

        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        presenter = new SearchPresenter(this, this);
        adapter = new SearchResultAdapter(this, presenter, new Article[0]);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();
                int threshold = 10;

                if (!adapter.isLoading() && dy > 0) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - threshold) {
                        // load for next page
                        adapter.setLoading(true);
                        presenter.getOtherNewsApi(query,source_id,from,to,sortBy,language, page);
                    }
                }
            }
        });

        searchIcon.setOnClickListener(this);
    }

    @Override
    public void onLoadNewQuery() {

    }

    @Override
    public void afterCallSearchApi() {

    }

    @Override
    public void getNewQuery(Article[] articles) {
        page +=1;
        adapter.clearData();
        adapter.addArticles(articles);
    }

    @Override
    public void getMoreResults(Article[] articles) {
        page +=1;
        adapter.addArticles(articles);
    }

    @Override
    public void failedGetNewQuery() {

    }

    @Override
    public void failedGetMoreResults() {

    }

    @Override
    public void openNewsActivity(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", article.getUrl());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.search_icon_submit){
            page = 1;
            query = StringHelper.getAppendedQuery(searchEditText.getText().toString());
            presenter.getOtherNewsApi(query,source_id,from,to,sortBy,language, page);
        }
    }
}
