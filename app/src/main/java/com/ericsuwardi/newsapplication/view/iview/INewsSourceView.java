package com.ericsuwardi.newsapplication.view.iview;

import com.ericsuwardi.newsapplication.model.Source;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public interface INewsSourceView {

    void onSourceItemSelected(Source source);
    void loadSources(Source[] sources);

}
