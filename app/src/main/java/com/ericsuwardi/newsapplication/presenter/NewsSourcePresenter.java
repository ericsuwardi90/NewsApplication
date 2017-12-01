package com.ericsuwardi.newsapplication.presenter;

import android.content.Context;

import com.ericsuwardi.newsapplication.event.SourceEvent;
import com.ericsuwardi.newsapplication.model.Source;
import com.ericsuwardi.newsapplication.view.iview.INewsSourceView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class NewsSourcePresenter {

    private INewsSourceView view;
    private Context context;

    public NewsSourcePresenter(INewsSourceView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    public void onSourceClicked(Source source){
        view.onSourceItemSelected(source);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getSourceEvent(SourceEvent event){
        view.loadSources(event.getSources());
    }

}
