package com.ericsuwardi.newsapplication.view.iview;

import com.ericsuwardi.newsapplication.model.Source;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public interface ISplashView {

    void navigateToNewsSourceActivity();
    void onLoadSourceApi();
    void afterLoadSourceApi();
    void sourceReadyToProcessed(Source[] sources);
}
