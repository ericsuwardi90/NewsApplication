package com.ericsuwardi.newsapplication.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ericsuwardi.newsapplication.R;

public class SearchActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }
}
