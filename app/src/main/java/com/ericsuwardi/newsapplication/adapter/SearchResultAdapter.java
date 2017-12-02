package com.ericsuwardi.newsapplication.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.helper.DateTimeHelper;
import com.ericsuwardi.newsapplication.helper.StringHelper;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.SearchPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean loading = true;

    private List<Article> data;
    private SearchPresenter presenter;
    private Context context;

    public SearchResultAdapter(Context context, SearchPresenter presenter, Article[] articles) {

        this.data = new ArrayList<>();
        this.presenter = presenter;
        this.context = context;

        addArticles(articles);
    }

    public void addArticles(Article[] articles) {
        Collections.addAll(data, articles);
        setLoading(false);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_item_other_news, parent, false);

        return new OtherNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder != null) {
            OtherNewsViewHolder viewHolder = (OtherNewsViewHolder) holder;
            viewHolder.bindData(data.get(position));
        }
    }

    private class OtherNewsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView timeStamp;
        ImageView imageView;

        OtherNewsViewHolder(View v) {
            super(v);

            cardView = v.findViewById(R.id.other_news_cardview);
            title = v.findViewById(R.id.other_news_title);
            timeStamp = v.findViewById(R.id.other_news_timestamp);
            imageView = v.findViewById(R.id.other_news_image);
        }

        void bindData(final Article article) {

            title.setText(article.getTitle());
            timeStamp.setText("Published ");
            timeStamp.append(
                DateTimeHelper.writeFormattedDate(
                        "yyyy-MM-dd",
                        DateTimeHelper.getDate("dd-MM-yyyy'T'HH:mm:ss'Z'", article.getPublishedAt())
                )
            );

            Picasso.with(context)
                    .load(StringHelper.isNullOrEmpty(article.getUrlToImage()) ? null : article.getUrlToImage())
                    .fit().centerCrop()
                    .error(R.color.gray)
                    .placeholder(R.color.gray)
                    .into(imageView);

            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    presenter.openNewsPage(article);
                }
            });
        }
    }
}
