package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.event.SourceEvent;
import com.ericsuwardi.newsapplication.helper.ContextHelper;
import com.ericsuwardi.newsapplication.model.Source;
import com.ericsuwardi.newsapplication.presenter.SplashPresenter;
import com.ericsuwardi.newsapplication.view.iview.ISplashView;

import org.greenrobot.eventbus.EventBus;

public class SplashActivity extends AppCompatActivity implements ISplashView{

    SplashPresenter presenter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        presenter = new SplashPresenter(this);
        presenter.loadSourceApi(ContextHelper.getApiKey(this));
    }

    @Override
    public void navigateToNewsSourceActivity() {
        Intent intent = new Intent(this, NewsSourceActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoadSourceApi() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterLoadSourceApi() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sourceReadyToProcessed(Source[] sources) {
        SourceEvent event = new SourceEvent();
        event.setSources(sources);
        EventBus.getDefault().postSticky(event);
    }
}