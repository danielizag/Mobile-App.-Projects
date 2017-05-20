package com.dan2you.dizzy.seniorproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dan2you.dizzy.seniorproject.MainActivity;
import com.dan2you.dizzy.seniorproject.R;
import com.dan2you.dizzy.seniorproject.objects.Employee;

import java.util.ArrayList;

/**
 * Created by Dizzy on 4/17/2017.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private ArrayList<Employee> emplist;
    private Context context;
    private Runnable runnable;

    private static final String TAG = EmployeeAdapter.class.getName();

    public EmployeeAdapter(ArrayList<Employee> emp, Runnable runnable) {
        this.emplist = emp;
        this.runnable = runnable;
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee_recycler, parent,false);
        return new EmployeeAdapter.ViewHolder(v);
    }

    public EmployeeAdapter(ArrayList<Employee> emp, Context context){
        this.emplist = emp;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.ViewHolder holder, int position) {
        final Employee empitem = emplist.get(position);

        holder.empidTextView.setText(empitem.getEmployeeID());
        holder.nameTextView.setText(empitem.getFullname());

        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return emplist.size();
    }

    //Toast.makeText(context, "You Clicked this", Toast.LENGTH_LONG).show();

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView empidTextView;
        public TextView nameTextView;
        public LinearLayout linearlayout;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            empidTextView = (TextView) itemView.findViewById(R.id.empidTextView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    Toast.makeText(context, "You Clicked this", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
