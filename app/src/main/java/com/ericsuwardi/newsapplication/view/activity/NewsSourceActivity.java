package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.adapter.NewsSourceAdapter;
import com.ericsuwardi.newsapplication.model.Source;
import com.ericsuwardi.newsapplication.presenter.NewsSourcePresenter;
import com.ericsuwardi.newsapplication.view.iview.INewsSourceView;

public class NewsSourceActivity extends AppCompatActivity implements INewsSourceView {

    NewsSourcePresenter presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source);
        setTitle(R.string.news_source_activity_title);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        presenter = new NewsSourcePresenter(this, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onSourceItemSelected(Source source) {
        // navigate to news list activity
        Intent intent = new Intent(this, NewsListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("source_id",source.getId());
        bundle.putString("source_name", source.getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void loadSources(Source[] sources) {

        NewsSourceAdapter adapter = new NewsSourceAdapter(sources, presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}