package com.dan2you.dizzy.seniorproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dan2you.dizzy.seniorproject.adapters.JobAdapter;
import com.dan2you.dizzy.seniorproject.objects.Job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CurrentJobActivity extends AppCompatActivity {

    // Log.i TAG variable
    private static final String TAG = ViewEmployeeActivity.class.getName();

    // OKHTTP Variables
    private OkHttpClient post_client = new OkHttpClient();
    private RequestBody post;
    private Request request;
    private String URL_CurrentJob = "http://www.dan2you.com/JSON/GetJob.php";

    // Passed Intent Variables (From MainActivity)
    private String id;
    private String pass_address;
    public final static String ADDRESS = "ADDRESS";

    // UI Object instantiation
    private ListView jobLV;

    private TextView jobIDTV;
    private TextView compTV;
    private TextView detailsTV;
    private TextView locationTV;
    private Button mapBut;


    // Job Data
    private Job currentJob = new Job();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job);

        id = getIntent().getStringExtra(MainActivity.ID);

        jobIDTV = (TextView) findViewById(R.id.job_idTextView);
        compTV = (TextView) findViewById(R.id.compTextView);
        detailsTV = (TextView) findViewById(R.id.jobdetailsTextView);
        locationTV = (TextView) findViewById(R.id.locationTextView);
        mapBut = (Button) findViewById(R.id.mapButton);

        post = new FormBody.Builder()
                .add("user", id)
                .build();
        request = new Request.Builder()
                .url(URL_CurrentJob)
                .post(post)
                .build();

        post_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData = response.body().string();

//                jobItems = new ArrayList<>();

                try {
                    Log.i(TAG, jsonData);
                    // Use the POST response
                    if (response.isSuccessful()){
                        getJobData(jsonData);
                        // Move supervisor data out to the UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                adapter = new JobAdapter(jobItems,this);
//                                recyclerView.setAdapter(adapter);
                                jobIDTV.setText(currentJob.getJob_Id());
                                compTV.setText(currentJob.getCompany());
                                detailsTV.setText(currentJob.getDetails());
                                locationTV.setText(currentJob.getAddress());
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mapBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass_address = currentJob.getAddress();
                Intent mapIntent = new Intent(getApplication(), MapsActivity.class);
                mapIntent.putExtra(ADDRESS, pass_address);
                startActivity(mapIntent);
            }
        });
    }
    private Job getJobData(String jsonString) throws JSONException{
        // Local Temp Variables
        String job_Id = "";
        String company = "";
        String street = "";
        String city = "";
        String state = "";
        String details = "";
        String complete = "";

//        try {
        // Feed JSONArray the JSON string
        JSONArray jsonarray = new JSONArray(jsonString);

        //For-loop to add employee objects to an employee array list
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonObject = jsonarray.getJSONObject(i);
            job_Id = jsonObject.getString("job");
            details = jsonObject.getString("details");
            company = jsonObject.getString("company");
            street = jsonObject.getString("street");
            city = jsonObject.getString("city");
            state = jsonObject.getString("state");
            complete = jsonObject.getString("complete");
        }

        currentJob.setJob_Id(job_Id);
        currentJob.setDetails(details);
        currentJob.setCompany(company);
        currentJob.setStreet(street);
        currentJob.setCity(city);
        currentJob.setState(state);
        currentJob.setComplete(complete);

        return null;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

}
