package com.doozycod.roadsidegenius.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.roadsidegenius.Activities.EditActivity;
import com.doozycod.roadsidegenius.Model.AdminRegisterModel;
import com.doozycod.roadsidegenius.Model.DriverList.Driver;
import com.doozycod.roadsidegenius.Model.ServiceList.Service;
import com.doozycod.roadsidegenius.R;
import com.doozycod.roadsidegenius.Service.ApiService;
import com.doozycod.roadsidegenius.Utils.CustomProgressBar;
import com.doozycod.roadsidegenius.Utils.SharedPreferenceMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.RecyclerHolder> {
    Context context;
    List<Service> services = new ArrayList<>();
    ApiService apiService;
    SharedPreferenceMethod sharedPreferenceMethod;
    CustomProgressBar customProgressBar;

    public ServiceListAdapter(Context context, List<Service> services, ApiService apiService) {
        this.context = context;
        this.services = services;
        customProgressBar = new CustomProgressBar(context);
        this.apiService = apiService;
        sharedPreferenceMethod = new SharedPreferenceMethod(context);

    }

    @NonNull
    @Override
    public ServiceListAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_recyclerview, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.RecyclerHolder holder, int position) {
        Service service = services.get(position);
        holder.serviceCost.setText(service.getServiceCost());
        holder.serviceTypeTXT.setText(service.getServiceType());
        holder.serviceID.setText(service.getServiceId());
        holder.descriptionTxt.setText(service.getServiceDescription());
        holder.deleteDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDriverDialog(position, service.getId());
            }
        });
    }

    private void deleteDriverDialog(int position, String id) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_driver_record_dialog);
        Button yesButton = dialog.findViewById(R.id.yesButton);
        Button noButton = dialog.findViewById(R.id.cancelDeletion);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                services.remove(position);
                deleteDriver(id);

            }
        });
        dialog.show();
    }

    void deleteDriver(String id) {
        customProgressBar.showProgress();
        apiService.deleteDriver(sharedPreferenceMethod.getJWTToken(), id).enqueue(new Callback<AdminRegisterModel>() {
            @Override
            public void onResponse(Call<AdminRegisterModel> call, Response<AdminRegisterModel> response) {
                customProgressBar.hideProgress();

                if (response.body().getResponse().getStatus().equals("Success")) {
                    notifyDataSetChanged();
                    Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AdminRegisterModel> call, Throwable t) {
                customProgressBar.hideProgress();

            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView serviceID, serviceTypeTXT, serviceCost, descriptionTxt;
        ImageView editDriverButton, deleteDriverButton;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
            serviceCost = itemView.findViewById(R.id.serviceCost);
            serviceTypeTXT = itemView.findViewById(R.id.serviceTypeTXT);
            serviceID = itemView.findViewById(R.id.serviceID);
            deleteDriverButton = itemView.findViewById(R.id.deleteDriverButton);

        }
    }
}
