### 1-	Add permission of internet in manifest
	<uses-permission android:name="android.permission.INTERNET"></uses-permission>

###2-	Add in your build gradle
	
	compile 'com.squareup.retrofit2:retrofit:2.3.0'
	compile 'com.squareup.retrofit2:converter-gson:2.3.0'

    compile 'com.android.support:cardview-v7:25.3.1' 
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.squareup.picasso:picasso:2.5.2' // add Picasso

###3-	Add Recycle View Xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.osama.retrofit.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycleView"
        android:scrollbars="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </android.support.v7.widget.RecyclerView>

</RelativeLayout>

###4-	Add card View Xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_weight="4"
    android:layout_height="80dp"
    >

    <TextView
        android:id="@+id/txt_name"
        android:textSize="18sp"
        android:textStyle="bold"
        android:text="Osama"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:gravity="center_vertical"
        android:layout_height="wrap_content" />

    <TextView
        android:id="@+id/txt_email"
        android:layout_width="0dp"
        android:layout_weight="3"
        android:textSize="18sp"
        android:textStyle="bold"
        android:text="osamamohsen1994#gmail.com"
        android:gravity="center_vertical"
        android:layout_marginRight="10dp"
        android:layout_height="wrap_content" />
    
</LinearLayout>

###5-	Add Contact Model

package com.example.osama.retrofit.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by osama on 10/26/2017.
 */

public class Contact {

/*name: column name is database that mean if variable name is name doesn’t necessary to make serialize*/

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



###6-	Add Client API
	public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.106:8000/contact/index";
    public static Retrofit retrofit= null;
    public static Retrofit getApiClient() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

###7-	Now time to make an Interface for responsible to call api
public interface ApiInterface {

    @POST("index")
    Call<List<Contact>> getContacts();
}


###8-	Create ViewHolder

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView Name,Email;


    public MyViewHolder(View itemView) {
        super(itemView);
        Name  = (TextView) itemView.findViewById(R.id.txt_name);
        Email = (TextView) itemView.findViewById(R.id.txt_email);
    }
}


###9-	Create RecycleAdapter
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

###10-	Go To Main Activity
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
private RecycleAdapter adapter;
private List<Contact> contacts;
private ApiInterface apiInterface;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.recycleView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);

    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    Call<List<Contact>> call = apiInterface.getContacts();

    call.enqueue(new Callback<List<Contact>>() {
        @Override
        public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
            Log.d("contacts",response.toString());
            contacts = response.body();
            adapter = new RecycleAdapter(contacts);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<List<Contact>> call, Throwable t) {
            Log.d("contacts","Failure");
            Log.d("contacts",t.getMessage());

        }
    });
}

