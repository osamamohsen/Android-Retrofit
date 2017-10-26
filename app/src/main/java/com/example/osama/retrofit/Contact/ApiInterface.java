package com.example.osama.retrofit.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by osama on 10/26/2017.
 */

public interface ApiInterface {

    @GET("contacts.php")
    Call<List<Contact>> getContacts();
}
