package com.example.osama.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.osama.retrofit.Common.Common;
import com.example.osama.retrofit.User.ClientInterface;
import com.example.osama.retrofit.User.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegisterActivity extends AppCompatActivity {

    MaterialEditText edtRegisterEmail,edtRegisterPassword,edtRegisterName;
    FButton btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        edtRegisterName = (MaterialEditText) findViewById(R.id.edtRegisterName);
        edtRegisterEmail = (MaterialEditText) findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = (MaterialEditText) findViewById(R.id.edtRegisterPassword);
        btnRegister = (FButton) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(
                        edtRegisterName.getText().toString(),
                        edtRegisterEmail.getText().toString(),
                        edtRegisterPassword.getText().toString());

                sendNetworkRequest(user);
            }
        });
    }

    private void sendNetworkRequest(User user) {

        OkHttpClient.Builder okhttpClinetBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClinetBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Common.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClinetBuilder.build());

        Retrofit retrofit = builder.build();
        ClientInterface client = retrofit.create(ClientInterface.class);
        Call<User> call = client.createAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("success","Register success");
                User user = response.body();
                Toast.makeText(UserRegisterActivity.this, user.getId().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserRegisterActivity.this, user.getName().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserRegisterActivity.this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("fail","Error in Register user");
                Toast.makeText(UserRegisterActivity.this, "Wrong Error in Register", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
