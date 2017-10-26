package com.example.osama.retrofit.Contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.osama.retrofit.R;

/**
 * Created by osama on 10/26/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView Name,Email;


    public MyViewHolder(View itemView) {
        super(itemView);
        Name  = (TextView) itemView.findViewById(R.id.txt_name);
        Email = (TextView) itemView.findViewById(R.id.txt_email);
    }
}