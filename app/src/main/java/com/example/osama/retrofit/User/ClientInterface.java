package com.example.osama.retrofit.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by osama on 11/2/2017.
 */

public interface ClientInterface {

    @POST("user/create")
    Call<User> createAccount(@Body User user);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(
        @Part("description") RequestBody description,
        @Part MultipartBody.Part photo
    );


    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("photographer") RequestBody photographer,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(
            @PartMap Map<String,RequestBody> data,
            @Part MultipartBody.Part photo
    );

}
