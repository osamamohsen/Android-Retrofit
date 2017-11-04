package com.example.osama.retrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.osama.retrofit.Common.Permission.PermissionStorage;
import com.example.osama.retrofit.Common.Retrofit.RetrofitBuilder;
import com.example.osama.retrofit.Helper.File.FileUtils;
import com.example.osama.retrofit.Helper.Image;
import com.example.osama.retrofit.User.ClientInterface;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.io.File;

import info.hoang8f.widget.FButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadActivity extends AppCompatActivity {

    FButton btnUploadImage,btnSubmit;
    MaterialEditText edtUploadDescription;
    Uri selectedImage = null,selectedFile = null;

    String mediaPath;
    ProgressDialog progressDialog;
    String [] mediaColumns = {MediaStore.Video.Media._ID };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");

        edtUploadDescription = (MaterialEditText) findViewById(R.id.edtUploadDescription);
        btnUploadImage = (FButton) findViewById(R.id.btnUploadImage);
        btnSubmit = (FButton) findViewById(R.id.btnSubmit);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(PermissionStorage.IsEnable(UploadActivity.this))
                    Image.chooseFile(UploadActivity.this);
                else{
                    PermissionStorage.EnableStorage(UploadActivity.this);
                    if(PermissionStorage.IsEnable(UploadActivity.this))
                        Image.chooseFile(UploadActivity.this);
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage != null || selectedFile != null){
                    if(PermissionStorage.IsEnable(UploadActivity.this))
                        uploadFile(selectedImage);
                    else{
                        PermissionStorage.EnableStorage(UploadActivity.this);
                        if(PermissionStorage.IsEnable(UploadActivity.this))
                            uploadFile(selectedImage);
                    }
                }else
                    Toast.makeText(UploadActivity.this, "Image is required", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString){
        return RequestBody.create(okhttp3.MultipartBody.FORM , descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String fileName , Uri fileUri){
        File Original_file = FileUtils.getFile(this, fileUri);
        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                Original_file
        );
        return MultipartBody.Part.createFormData(fileName,Original_file.getName(),filePart);
    }

    public void uploadFile(Uri fileUri){

        progressDialog.show();

        RetrofitBuilder retrofitBuilder = new RetrofitBuilder(this);

        ClientInterface client = retrofitBuilder.getRetrofit().create(ClientInterface.class);

        retrofitBuilder.pushMap("description",edtUploadDescription.getText().toString());
        retrofitBuilder.pushMap("name","name"+edtUploadDescription.getText().toString());
        retrofitBuilder.pushMap("country","");

        Call<ResponseBody> call = client.uploadPhoto(
                retrofitBuilder.getMap(),
                retrofitBuilder.prepareFilePart("file",fileUri)
            );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Log.d("result",response.body().toString());
                Toast.makeText(UploadActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Fail",t.getMessage());
                Toast.makeText(UploadActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == Image.PICK_IMAGE_GALLERY || requestCode == Image.PICK_FILE_STORAGE) &&
                resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedImage = data.getData();
            btnUploadImage.setText("Image Selected From Gallery");
        }//end if
        else if(requestCode == Image.PICK_IMAGE_CAMERA && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            btnUploadImage.setText("Image Selected From Camera");
        }
    }
}
