package com.ericsuwardi.newsapplication.presenter;

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

    public NewsListPresenter(INewsListView view){
        this.view = view;
    }

    public void openNewsPage(Article article){
        view.openNewsActivity(article);
    }

    public void openSearchPage(){view.openSearchActivity();}

    public void onGetHeadlineNewsApi(String sourceId, String apiKey){
        view.onLoadHeadlines();

        ApiService.Factory.getInstance()
                .getTopHeadlines(
                        "",
                        sourceId,
                        "",
                        "",
                        "",
                        apiKey)
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

    public void onGetOtherNewsApi(String sourceId, int page, String apiKey){

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
                        apiKey)
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
