package com.ericsuwardi.newsapplication.presenter;

import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.model.ResponseBody;
import com.ericsuwardi.newsapplication.network.ApiService;
import com.ericsuwardi.newsapplication.view.iview.ISearchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ericsuwardi on 12/1/17.
 */

public class SearchPresenter extends BasePresenter{

    private ISearchView view;

    public SearchPresenter(ISearchView view){
        this.view = view;
    }

    public void openNewsPage(Article article){
        view.openNewsActivity(article);
    }

    public void showDatePicker(int resourceId){
        view.openingDatePicker(resourceId);
    }

    public void getOtherNewsApi(String query, String sourceId,
                                  String from, String to,
                                  String sortBy, String language, final int page, String apiKey){

        if(page <= 1 ){
            view.onLoadNewQuery();
        }

        ApiService.Factory.getInstance()
                .getEverything(query, sourceId, from, to,
                        sortBy, language, page, apiKey)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();

                        view.afterCallSearchApi();
                        if(responseBody.getStatus().equals("ok")){
                            if(page <= 1)
                                view.getNewQuery(responseBody.getArticles());
                            else
                                view.getMoreResults(responseBody.getArticles());
                        } else {
                            if(page <= 1)
                                view.failedGetNewQuery();
                            else
                                view.failedGetMoreResults();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(page <= 1)
                            view.failedGetNewQuery();
                        else
                            view.failedGetMoreResults();

                    }
                });
    }
}
