package com.dan2you.dizzy.seniorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class JobIssueActivity extends AppCompatActivity {

    // LOG.i TAG variable
    private static final String TAG = JobIssueActivity.class.getName();

    // Passable intent values initialization
    private String super_id;
    private String job_id;

    //POST Variables initialization
    private String id;
    private String job;
    private String issue_post;

    // UI components initialization
    private EditText jobissueET;
    private Button sendIssueBut;
    private Boolean success;
    private Boolean jobEmpty;

    // OKHTTP connection objects initialization
    private OkHttpClient post_client = new OkHttpClient();
    private Request request;
    private RequestBody post;
    private String URL_JobIssue = "http://www.dan2you.com/JSON/ReportJobIssue.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_issue);

        // UI object instantiation
        jobissueET = (EditText) findViewById(R.id.jobIssueEditText);
        sendIssueBut = (Button) findViewById(R.id.sendEmpIssueButton);


        super_id = getIntent().getStringExtra(MainActivity.ID);
        job_id = getIntent().getStringExtra(MainActivity.JOB);

        id = super_id;
        job = job_id;

        sendIssueBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                issue_post = jobissueET.getText().toString();

                String checkJob = issue_post;

                jobEmpty = checkEmpty(checkJob);

                // Toast successful input
                success = false;

                if (jobEmpty == false) {
                    // OKHTTP post and request instantiation
                    post = new FormBody.Builder()
                            .add("user", id)
                            .add("job", job)
                            .add("issue", issue_post)
                            .build();
                    request = new Request.Builder()
                            .url(URL_JobIssue)
                            .post(post)
                            .build();
                    post_client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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
                                    jobissueET.setText("");
                                    checkSuccess(success);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Field is Required", Toast.LENGTH_LONG).show();
                }
            }
        });
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
