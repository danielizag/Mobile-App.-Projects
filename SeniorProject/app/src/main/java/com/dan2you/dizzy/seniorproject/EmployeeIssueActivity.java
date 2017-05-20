package com.dan2you.dizzy.seniorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class EmployeeIssueActivity extends AppCompatActivity {

    // LOG.i TAG variable
    private static final String TAG = EmployeeIssueActivity.class.getName();

    // Passable intent values initialization
    private String super_id;
    private String job_id;

    //POST Variables initialization
    private String id;
    private String empID;
    private String job;
    private String issue_post;

    // UI object initializaton
    private EditText empidET;
    private EditText employeeIssueET;
    private Button sendEmpIssueBut;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Boolean success;
    private Boolean empEmpty;
    private Boolean issueEmpty;

    // OKHTTP Variables
    private OkHttpClient post_client = new OkHttpClient();
    private RequestBody post;
    private Request request;
    private String URL_Employees = "http://www.dan2you.com/JSON/GetEmployees.php";
    private String URL_Employee_Issue = "http://www.dan2you.com/JSON/ReportEmployeeIssue.php";

    // Employee ArrayList
    ArrayList<Employee> empList = new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_issue);

        empidET = (EditText) findViewById(R.id.empIDEditText);
        employeeIssueET = (EditText) findViewById(R.id.jobEmpEditText);
        sendEmpIssueBut = (Button) findViewById(R.id.sendEmpIssueButton);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        super_id = getIntent().getStringExtra(MainActivity.ID);
        job_id = getIntent().getStringExtra(MainActivity.JOB);

        // POST values
        id = super_id;
        job = job_id;

        // Create POST
        post = new FormBody.Builder()
                .add("user", id)
                .build();
        request = new Request.Builder()
                .url(URL_Employees)
                .post(post)
                .build();

        // Set Data to emplist for recyclerView
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
                                Log.i(TAG, jsonData);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //
        sendEmpIssueBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empID = empidET.getText().toString();
                issue_post = employeeIssueET.getText().toString();

                String checkEmp = empID;
                String checkIssue = issue_post;

                empEmpty = checkEmpty(checkEmp);
                issueEmpty = checkEmpty(checkIssue);

                // Toast successful input
                success = false;

                if (empEmpty == false && issueEmpty == false) {
                    // Create POST
                    post = new FormBody.Builder()
                            .add("user", id)
                            .add("employee", empID)
                            .add("job", job)
                            .add("issue", issue_post)
                            .build();
                    request = new Request.Builder()
                            .url(URL_Employee_Issue)
                            .post(post)
                            .build();
                    post_client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    employeeIssueET.setText("");
                                    //Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    success = true;
                                    employeeIssueET.setText("");
                                    checkSuccess(success);
                                }
                            });
                        }
                    });
                    success = false;
                } else {
                    Toast.makeText(getApplicationContext(), "Both Fields Are Required", Toast.LENGTH_LONG).show();
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

    private void checkSuccess(Boolean success){
        if (success == true){
            Toast.makeText(getApplicationContext(), "Successful Post", Toast.LENGTH_SHORT).show();
        }
    }
    private Boolean checkEmpty(String val){
        String empty = "";
        if (val.equals(empty)){
            return true;
        } else {
            return false;
        }
    }
}

