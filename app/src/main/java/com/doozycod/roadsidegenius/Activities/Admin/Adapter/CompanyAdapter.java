package com.doozycod.roadsidegenius.Activities.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.roadsidegenius.Model.Company.Company;
import com.doozycod.roadsidegenius.R;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.RecyclerHolder> {

    Context context;
    List<Company> companyList = new ArrayList<>();

    public CompanyAdapter(Context context, List<Company> companyList) {
        this.context = context;
        this.companyList = companyList;
    }


    @NonNull
    @Override
    public CompanyAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_recyclerview, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.RecyclerHolder holder, int position) {

        holder.companyID.setText(companyList.get(position).getId());
        holder.companyName.setText(companyList.get(position).getCompanyName());
        holder.companyAddress.setText(companyList.get(position).getCompanyAddress());
        holder.companyEmail.setText(companyList.get(position).getCompanyEmail());
        holder.companyNumber.setText(companyList.get(position).getCompanyNumber());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView companyID, companyName, companyNumber, companyEmail, companyAddress;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            companyEmail = itemView.findViewById(R.id.companyEmail);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            companyNumber = itemView.findViewById(R.id.companyNumber);
            companyName = itemView.findViewById(R.id.companyTXT);
            companyID = itemView.findViewById(R.id.companyID);
        }
    }
}
