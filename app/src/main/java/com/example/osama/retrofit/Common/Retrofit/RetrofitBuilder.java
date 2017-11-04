package com.example.osama.retrofit.Common.Retrofit;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.osama.retrofit.Common.Common;
import com.example.osama.retrofit.Helper.File.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by osama on 11/4/2017.
 */

public class RetrofitBuilder {

    private Context context;
    private Retrofit retrofit;
    private OkHttpClient.Builder okhttpClinetBuilder;
    private Map<String,RequestBody> partMap  = new HashMap<>();

    public RetrofitBuilder(Context context) {
        this.context = context;
        this.okhttpClientBuilderFunction();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Common.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClinetBuilder.build())
                .build();
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    private void okhttpClientBuilderFunction(){
        this.okhttpClinetBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClinetBuilder.addInterceptor(logging);
    }

    public void pushMap(String key , String value){
//        if(!TextUtils.isEmpty(value)){
            partMap.put(key,createPartFromString(value));
//        }
    }

    public Map<String,RequestBody> getMap(){
        return this.partMap;
    }

    @NonNull
    public RequestBody createPartFromString(String descriptionString){
        return RequestBody.create(okhttp3.MultipartBody.FORM , descriptionString);
    }

    @NonNull
    public MultipartBody.Part prepareFilePart(String fileName , Uri fileUri){
        File Original_file = FileUtils.getFile(this.context, fileUri);
        RequestBody filePart = RequestBody.create(
                MediaType.parse(this.context.getContentResolver().getType(fileUri)),
                Original_file
        );
        return MultipartBody.Part.createFormData(fileName,Original_file.getName(),filePart);
    }


}
