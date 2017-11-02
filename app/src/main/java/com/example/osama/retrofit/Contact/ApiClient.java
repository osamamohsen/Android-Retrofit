package com.example.osama.retrofit.Contact;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by osama on 10/26/2017.
 */

public class ApiClient {
//    private static final String BASE_URL = "http://192.168.1.106/Mobile/laraTest/";
    private static final String BASE_URL = "http://192.168.1.106/contact/";
    public static Retrofit retrofit= null;
    public static Retrofit getApiClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
