package com.example.osama.retrofit.User;

import com.example.osama.retrofit.Server.ServerResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by osama on 11/2/2017.
 */

public interface UploadInterface {

    @Multipart
    @POST("upload")
    Call<ServerResponse> upload(
            @Header("Authoirzation") String authorization,
            @PartMap Map<String, RequestBody> map
            );
}
