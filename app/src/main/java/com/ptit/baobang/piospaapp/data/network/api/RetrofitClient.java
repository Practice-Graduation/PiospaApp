package com.ptit.baobang.piospaapp.data.network.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final int TIME_OUT = 1;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getRequestHeader())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



    public static OkHttpClient getRequestHeader() {
        if(okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.connectTimeout(TIME_OUT, TimeUnit.MINUTES)
                    .writeTimeout(TIME_OUT, TimeUnit.MINUTES)
                    .readTimeout(TIME_OUT, TimeUnit.MINUTES)
                    .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
                    .build();
        }
        return okHttpClient;
    }
}
