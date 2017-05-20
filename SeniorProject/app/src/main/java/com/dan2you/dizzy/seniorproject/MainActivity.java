package com.dan2you.dizzy.seniorproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.dan2you.dizzy.seniorproject.objects.Supervisor;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // LOG.i TAG variable
    private static final String TAG = MainActivity.class.getName();

    // OKHTTP variables
    private OkHttpClient post_client = new OkHttpClient();
    private Request request;
    private RequestBody post;
    private String URL_Supervisor = "http://www.dan2you.com/JSON/GetSupervisor.php";

    // Passed intent variables
    private String id;
    public String id_pass;
    public String job_pass;

    // Values to pass to new intent
    public final static String ID = "ID";
    public final static String JOB = "JOB";

    // UI Variables
    private TextView welcomeTV;
    private ListView menuLV;

    // Supervisor Strings and Object instantiation
    private String firstname = "";
    private String lastname = "";
    private String job = "";
    private Supervisor supervisorData = new Supervisor(firstname,lastname, job);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI objects
        welcomeTV = (TextView) findViewById(R.id.welcomeTextView);
        menuLV = (ListView) findViewById(R.id.menuListView);
        String[] menuValues = new String[] {"Job Assignment","Your Team","Job Issues","Team Issues"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, menuValues);
        menuLV.setAdapter(adapter);

        // Get ID from LoginActivity
        id = getIntent().getStringExtra(LoginActivity.ID);

        // Create ID to pass
        id_pass = id;

        // Create POST
        post = new FormBody.Builder()
                .add("user", id)
                .build();
        request = new Request.Builder()
                .url(URL_Supervisor)
                .post(post)
                .build();


        // Get user data
        post_client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    Log.i(TAG, jsonData);
                    // Use the POST response
                    if (response.isSuccessful()){
                        getSupervisorData(jsonData);
                        // Move supervisor data out to the UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                firstname = supervisorData.getFirstname();
                                lastname = supervisorData.getLastname();
                                String welcome = "Welcome " + firstname + " " + lastname;
                                welcomeTV.setText(welcome);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // ListView Menu to new activities
        menuLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent currentJob = new Intent(getApplication(), CurrentJobActivity.class);
                    currentJob.putExtra(ID, id_pass);
                    startActivityForResult(currentJob, 0);
                }
                if (position==1){
                    job_pass = supervisorData.getJob_id();
                    Intent myEmployeesIntent = new Intent(getApplication(), ViewEmployeeActivity.class);
                    myEmployeesIntent.putExtra(ID, id_pass);
                    myEmployeesIntent.putExtra(JOB, job_pass);
                    startActivityForResult(myEmployeesIntent, 1);
                }
                if (position==2){
                    job_pass = supervisorData.getJob_id();
                    Intent jobIssueIntent = new Intent(getApplication(), JobIssueActivity.class);
                    jobIssueIntent.putExtra(ID, id_pass);
                    jobIssueIntent.putExtra(JOB, job_pass);
                    startActivityForResult(jobIssueIntent, 2);
                }
                if (position==3){
                    job_pass = supervisorData.getJob_id();
                    Intent empIssueIntent = new Intent(getApplication(), EmployeeIssueActivity.class);
                    empIssueIntent.putExtra(ID, id_pass);
                    empIssueIntent.putExtra(JOB, job_pass);
                    startActivityForResult(empIssueIntent, 3);
                }
            }
        });
    }

    // method to assign jsonarray values to Supervisor
    private Supervisor getSupervisorData(String jsonString) throws JSONException{
        String job_id ="";
        String fname = "";
        String lname = "";

        JSONArray jsonarray = new JSONArray(jsonString);
        for (int i = 0 ; i < jsonarray.length(); i++){
            JSONObject jsonObject = jsonarray.getJSONObject(i);
            fname = jsonObject.getString("firstname");
            lname = jsonObject.getString("lastname");
            job_id = jsonObject.getString("job");

            supervisorData.setFirstname(fname);
            supervisorData.setLastname(lname);
            supervisorData.setJob_id(job_id);
        }

        Log.i(TAG, supervisorData.getFirstname());
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