package com.ericsuwardi.newsapplication.helper;

import android.content.Context;

import com.ericsuwardi.newsapplication.R;

/**
 * Created by ericsuwardi on 12/2/17.
 */

public class ContextHelper {

    public static String getApiKey(Context context){
        return context.getString(R.string.news_api_key);
    }
}
