package com.example.rps;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static Retrofit retrofit;
    private static final String BaseUrl = "";

    public static APIinterface getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(APIinterface.class);
    }
}
