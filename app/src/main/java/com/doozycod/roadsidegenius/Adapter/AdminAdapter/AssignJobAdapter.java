package com.doozycod.roadsidegenius.Adapter.AdminAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.roadsidegenius.Model.AssignedJobs.Job;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class AssignJobAdapter extends RecyclerView.Adapter<AssignJobAdapter.RecyclerHolder> {

    Context context;
    List<Job> jobs = new ArrayList<>();
    ApiService apiService;
    SharedPreferenceMethod sharedPreferenceMethod;

    public AssignJobAdapter(Context context, List<Job> jobs, ApiService apiService) {
        this.context = context;
        this.jobs = jobs;
        this.apiService = apiService;
        sharedPreferenceMethod = new SharedPreferenceMethod(context);
    }

    @NonNull
    @Override
    public AssignJobAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_job_recyclerview, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignJobAdapter.RecyclerHolder holder, int position) {
        holder.customerNameTxt.setText(jobs.get(position).getCustomerName());
        holder.phoneTxt.setText("+" + jobs.get(position).getCustomerNumber());
        holder.emailTxt.setText(jobs.get(position).getCustomerEmail());
        holder.dispatcherNameTxt.setText(jobs.get(position).getDispatcherName());
        holder.DriverNameTxt.setText(jobs.get(position).getDriver());
        holder.serviceTypeTxt.setText(jobs.get(position).getService());
        holder.pickupTxt.setText(jobs.get(position).getCustomerPickup());
        holder.dropoffTxt.setText(jobs.get(position).getCustomerDropoff());
        TimeZone tz = TimeZone.getDefault();
        Log.e("TAG", "onBindViewHolder: "+tz.getDisplayName(false, TimeZone.SHORT) );
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//    void startJobAPI(int pos) {
//        apiService.updateStatusForDriverJob(sharedPreferenceMethod.getJWTToken(),jobs.get(pos).getJobId(),"Started",
//                )
//    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView customerNameTxt, phoneTxt, emailTxt, dispatcherNameTxt, DriverNameTxt,
                serviceTypeTxt, amount_quoted, pickupTxt, dropoffTxt;
        Button startButton;
        CardView cardView;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            startButton = itemView.findViewById(R.id.startButton);
            dropoffTxt = itemView.findViewById(R.id.dropoffTxt);
            pickupTxt = itemView.findViewById(R.id.pickupTxt);
//            amount_quoted = itemView.findViewById(R.id.amount_quoted);
            serviceTypeTxt = itemView.findViewById(R.id.serviceTypeTxt);
            DriverNameTxt = itemView.findViewById(R.id.DriverNameTxt);
            dispatcherNameTxt = itemView.findViewById(R.id.dispatcherNameTxt);
            emailTxt = itemView.findViewById(R.id.emailTxt);
            phoneTxt = itemView.findViewById(R.id.phoneTxt);
            customerNameTxt = itemView.findViewById(R.id.customerNameTxt);
        }
    }
}
