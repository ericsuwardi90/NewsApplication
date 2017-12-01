package com.ericsuwardi.newsapplication.network;

import com.ericsuwardi.newsapplication.model.ResponseBody;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ericsuwardi on 11/30/17.
 */

public interface ApiService {

    String ENDPOINT_URL = "https://newsapi.org/v2/";

    // https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=dd06d51c5f4745369fb660e720b99c00
    @GET("top-headlines")
    Call<ResponseBody> getTopHeadlines(
            @Query("q") String q,
            @Query("sources") String sources,
            @Query("category") String category,
            @Query("language") String language,
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    // https://newsapi.org/v2/everything?q=bitcoin&apiKey=dd06d51c5f4745369fb660e720b99c00
    // https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=dd06d51c5f4745369fb660e720b99c00
    // https://newsapi.org/v2/everything?q=apple&from=2017-11-28&to=2017-11-28&sortBy=popularity&apiKey=dd06d51c5f4745369fb660e720b99c00
    // https://newsapi.org/v2/everything?domains=wsj.com&apiKey=dd06d51c5f4745369fb660e720b99c00
    @GET("everything")
    Call<ResponseBody> getEverything(
            @Query("q") String q,
            @Query("sources") String sources,
            @Query("from") String from,
            @Query("to") String to,
            @Query("sortBy") String sortBy,
            @Query("language") String language,
            @Query("page") int page,
            @Query("apiKey") String apiKey
    );

    // https://newsapi.org/v2/sources?apiKey=dd06d51c5f4745369fb660e720b99c00
    @GET("sources")
    Call<ResponseBody> getSources(
            @Query("apiKey") String apiKey
    );

    class Factory {

        private static ApiService service;

        public static ApiService getInstance() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            httpBuilder.connectTimeout(60, TimeUnit.SECONDS);
            httpBuilder.readTimeout(60, TimeUnit.SECONDS);
            httpBuilder.writeTimeout(60, TimeUnit.SECONDS);
            httpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request original = chain.request();
                    // Request customization: add request headers
                    okhttp3.Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Cache-Control", "no-cache")
                            .addHeader("Cache-Control", "no-store")
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json");

                    okhttp3.Request request = requestBuilder.build();

                    return chain.proceed(request);

                }
            });
            httpBuilder.addInterceptor(logging);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ENDPOINT_URL)
                        .addConverterFactory(JacksonConverterFactory.create(mapper))
                        .client(httpBuilder.build())
                        .build();

                service = retrofit.create(ApiService.class);
                return service;
            } else {
                return service;
            }
        }
    }
}
