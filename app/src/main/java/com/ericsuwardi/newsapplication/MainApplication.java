package com.ericsuwardi.newsapplication;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Iconify.with(new FontAwesomeModule());

    }
}
