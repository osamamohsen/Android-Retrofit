package com.example.osama.retrofit.Contact;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osama on 10/26/2017.
 */

public class Contact {

    @SerializedName("name")
    private String Name;

    @SerializedName("email")
    private String Email;

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}
