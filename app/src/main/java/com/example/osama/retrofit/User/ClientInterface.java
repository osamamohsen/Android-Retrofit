package com.example.osama.retrofit.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by osama on 11/2/2017.
 */

public interface ClientInterface {

    @POST("user/create")
    Call<User> createAccount(@Body User user);
}
