package com.dan2you.dizzy.seniorproject;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dan2you.dizzy.seniorproject.adapters.EmployeeAdapter;
import com.dan2you.dizzy.seniorproject.objects.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewEmployeeActivity extends AppCompatActivity {

    // Log.i TAG variable
    private static final String TAG = ViewEmployeeActivity.class.getName();

    // OKHTTP Variables
    private OkHttpClient post_client = new OkHttpClient();
    private RequestBody post;
    private Request request;
    private String URL_Employees = "http://www.dan2you.com/JSON/GetEmployees.php";

    // Passed Intent Variables (From MainActivity)
    private String id;
    private String job;

    // Passing Intent Variable
    public final static String JOBID = "JOBID";
    public final static String EMPID = "EMPID";

    // UI Object instantiation
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private EditText empIdET;
    private Button hoursButton;

    // Employee ArrayList
    ArrayList<Employee> empList = new ArrayList<Employee>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        // UI Objects initialization
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Acquire ID from MainActivity
        id = getIntent().getStringExtra(MainActivity.ID);
        job = getIntent().getStringExtra(MainActivity.JOB);

        // Create POST
        post = new FormBody.Builder()
                .add("user", id)
                .build();
        request = new Request.Builder()
                .url(URL_Employees)
                .post(post)
                .build();
        //get data from server
        post_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData = response.body().string();

                try {
                    Log.i(TAG, jsonData);
                    // Use the POST response
                    if (response.isSuccessful()){
                        getEmployeeData(jsonData);
                        // Move supervisor data out to the UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new EmployeeAdapter(empList, this);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private Employee getEmployeeData(String jsonString) throws JSONException{
        // Local Temp Variables
        String employeeID = "";
        String fname = "";
        String lname = "";

        try {
            // Feed JSONArray the JSON string
            JSONArray jsonarray = new JSONArray(jsonString);

            //For-loop to add employee objects to an employee array list
            for (int i = 0; i < jsonarray.length(); i++) {
                // Create a temp Employee object
                Employee tempEmp = new Employee(employeeID, fname, lname);
                JSONObject jsonObject = jsonarray.getJSONObject(i);
                /// Acquire the employee attributes for i employees
                employeeID = jsonObject.getString("employee");
                fname = jsonObject.getString("firstname");
                lname = jsonObject.getString("lastname");
                // Set the attribute values to those values in the tempEmp
                tempEmp.setEmployeeID(employeeID);
                tempEmp.setFirstname(fname);
                tempEmp.setLastname(lname);
                // Add the Employees the the global scope empList arraylist of employee objects
                empList.add(tempEmp);
            }
        } catch (JSONException e) {
            Log.e("log tag:", "Error parsing data" + e.toString());
        }
        return null;
    }
}


