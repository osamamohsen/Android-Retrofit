package com.example.osama.retrofit.Contact;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osama.retrofit.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by osama on 10/26/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Contact> contacts;

    public RecycleAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Name.setText(contacts.get(position).getName());
        holder.Email.setText(contacts.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

}
