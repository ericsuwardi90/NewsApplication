package com.ericsuwardi.newsapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.helper.StringHelper;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.NewsListPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class HeadlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> data;
    private NewsListPresenter presenter;
    private Context context;

    public HeadlineAdapter(Context context, NewsListPresenter presenter, Article[] articles){

        this.data = new ArrayList<>();
        this.presenter = presenter;
        this.context = context;

        addArticles(articles);
    }

    public void addArticles(Article[] articles){
        Collections.addAll(data, articles);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_item_headline, parent, false);

        return new HeadlineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder != null){
            HeadlineViewHolder viewHolder = (HeadlineViewHolder) holder;
            viewHolder.bindData(data.get(position));
        }
    }

    private class HeadlineViewHolder extends RecyclerView.ViewHolder{

        FrameLayout headlineCardView;
        TextView headlineSource;
        ImageView headlineImage;
        TextView headlineTitle;

        HeadlineViewHolder(View v) {
            super(v);

            headlineCardView = v.findViewById(R.id.headline_cardview);
            headlineSource = v.findViewById(R.id.headline_source);
            headlineImage = v.findViewById(R.id.headline_image);
            headlineTitle = v.findViewById(R.id.headline_title);
        }

        void bindData(final Article article){

            headlineTitle.setText(article.getTitle());
            Picasso.with(context)
                    .load(StringHelper.isNullOrEmpty(article.getUrlToImage()) ? null : article.getUrlToImage())
                    .placeholder(R.color.gray)
                    .error(R.color.gray)
                    .fit().centerCrop()
                    .into(headlineImage);

            headlineCardView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    presenter.openNewsPage(article);
                }
            });

        }
    }

}
