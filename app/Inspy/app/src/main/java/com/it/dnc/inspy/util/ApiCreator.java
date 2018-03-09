package com.it.dnc.inspy.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by zup on 31/08/2017.
 */


public class ApiCreator {

    private static Retrofit retrofit;
    private static OkHttpClient client;

    static{

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.126:8083")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getRetrofitInstance(){
        return retrofit;
    }

}
