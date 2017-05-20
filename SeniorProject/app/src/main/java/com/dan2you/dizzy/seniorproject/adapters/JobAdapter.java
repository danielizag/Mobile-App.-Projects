package com.dan2you.dizzy.seniorproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.dan2you.dizzy.seniorproject.R;
import com.dan2you.dizzy.seniorproject.objects.Job;

/**
 * Created by Dizzy on 4/16/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder>{

    private ArrayList<Job> joblist;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_current_job, parent,false);
        return new ViewHolder(v);
    }

    public JobAdapter(ArrayList<Job> job, Context context){
        this.joblist = job;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Job jobitem = joblist.get(position);

        holder.jobidTextView.setText(jobitem.getJob_Id());
        holder.companyTextView.setText(jobitem.getCompany());
        holder.jobdetailsTextView.setText(jobitem.getDetails());
        holder.addressTextView.setText(jobitem.getAddress());
    }

    @Override
    public int getItemCount() {
        return joblist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView jobidTextView;
        public TextView companyTextView;
        public TextView jobdetailsTextView;
        public TextView addressTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            jobidTextView = (TextView) itemView.findViewById(R.id.jobidTextView);
            companyTextView= (TextView) itemView.findViewById(R.id.companynameTextView);
            jobdetailsTextView= (TextView) itemView.findViewById(R.id.jobdetailsTextView);
            addressTextView= (TextView) itemView.findViewById(R.id.addressTextView);
        }
    }
}
