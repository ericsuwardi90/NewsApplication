package com.ericsuwardi.newsapplication.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.model.Source;
import com.ericsuwardi.newsapplication.presenter.NewsSourcePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class NewsSourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Source> data;
    private NewsSourcePresenter presenter;

    public NewsSourceAdapter(Source[] sources, NewsSourcePresenter presenter){

        this.data = new ArrayList<>();
        this.presenter = presenter;

        addAllSources(sources);
    }

    public void addAllSources(Source[] sources){
        Collections.addAll(data, sources);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_item_source, parent, false);

        return new NewsSourceVH(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder != null){
            NewsSourceVH viewHolder = (NewsSourceVH) holder;
            viewHolder.bindData(data.get(position));
        }
    }

    private class NewsSourceVH extends RecyclerView.ViewHolder{

        private TextView sourceName;
        private TextView sourceDesc;
        private CardView container;

        NewsSourceVH(View v) {
            super(v);

            sourceName = v.findViewById(R.id.source_name);
            sourceDesc = v.findViewById(R.id.source_description);
            container  = v.findViewById(R.id.source_cardview);
        }

        void bindData(final Source source){

            sourceName.setText(source.getName());
            sourceDesc.setText(source.getDescription());

            container.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    presenter.onSourceClicked(source);
                }
            });

        }

    }

}