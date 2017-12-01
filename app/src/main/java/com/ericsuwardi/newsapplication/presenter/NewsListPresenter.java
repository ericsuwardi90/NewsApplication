package com.ericsuwardi.newsapplication.presenter;

import android.content.Context;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.model.ResponseBody;
import com.ericsuwardi.newsapplication.network.ApiService;
import com.ericsuwardi.newsapplication.view.iview.INewsListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class NewsListPresenter extends BasePresenter{

    private INewsListView view;
    private Context context;

    public NewsListPresenter(INewsListView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void openNewsPage(Article article){
        view.openNewsActivity(article);
    }

    public void openSearchPage(){view.openSearchActivity();}

    public void onGetHeadlineNewsApi(String sourceId){
        view.onLoadHeadlines();

        ApiService.Factory.getInstance()
                .getTopHeadlines(
                        "",
                        sourceId,
                        "",
                        "",
                        "",
                        context.getString(R.string.news_api_key))
                .enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody responseBody = response.body();

                view.afterCallHeadlineApi();
                if(responseBody.getStatus().equals("ok")){
                    view.successGetHeadlines(responseBody.getArticles());
                } else {
                    view.failedGetHeadlines();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void onGetOtherNewsApi(String sourceId, int page){

        if(page <= 1 ){
            view.onLoadFirstOtherNews();
        }

        ApiService.Factory.getInstance()
                .getEverything(
                        "",
                        sourceId,
                        "",
                        "",
                        "",
                        "",
                        page,
                        context.getString(R.string.news_api_key))
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();

                        view.afterCallOtherNewsApi();
                        if(responseBody.getStatus().equals("ok")){
                            view.successGetFirstOtherNews(responseBody.getArticles());
                        } else {
                            view.failedGetFirstOtherNews();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        view.afterCallOtherNewsApi();

                    }
                });

    }
}
