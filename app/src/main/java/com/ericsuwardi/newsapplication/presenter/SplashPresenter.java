package com.ericsuwardi.newsapplication.presenter;

import com.ericsuwardi.newsapplication.model.ResponseBody;
import com.ericsuwardi.newsapplication.network.ApiService;
import com.ericsuwardi.newsapplication.view.iview.ISplashView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public class SplashPresenter extends BasePresenter{

    private ISplashView view;

    public SplashPresenter(ISplashView view){
        this.view = view;
    }

    public void loadSourceApi(String apiKey){
        view.onLoadSourceApi();

        ApiService.Factory.getInstance().getSources(apiKey).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody responseBody = response.body();

                view.afterLoadSourceApi();
                if(responseBody.getStatus().equals("ok")){

                    view.sourceReadyToProcessed(responseBody.getSources());
                    view.navigateToNewsSourceActivity();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                view.afterLoadSourceApi();
            }
        });
    }
}
