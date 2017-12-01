package com.ericsuwardi.newsapplication.event;

import com.ericsuwardi.newsapplication.model.Source;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class SourceEvent {

    private Source[] sources;

    public Source[] getSources() {
        return sources;
    }

    public void setSources(Source[] sources) {
        this.sources = sources;
    }
}
