package com.techease.groupiiapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripDataModel;

import java.util.List;

public class AddTripParticipaintsAdapter extends RecyclerView.Adapter<AddTripParticipaintsAdapter.MyViewHolder> {

    private Context context;
    private List<AddTripDataModel> addTripDataModels;


    public AddTripParticipaintsAdapter(Context context, List<AddTripDataModel> addTripDataModel) {
        this.addTripDataModels = addTripDataModel;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_add_trip_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        AddTripDataModel addTripDataModel = addTripDataModels.get(position);

        holder.tvTitle.setText(String.valueOf(addTripDataModel.getName()));
        holder.tvStartEndDate.setText(String.valueOf(addTripDataModel.getEmail()));

        Glide.with(context).load(addTripDataModel.getPicture()).placeholder(R.drawable.user).into(holder.ivUser);

        Log.d("zma trip data", "" + addTripDataModel.getSharedCost());

        if (addTripDataModel.getSharedCost() == 1) {
            holder.cbShareTripCost.setChecked(true);
        } else {
            holder.cbShareTripCost.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return addTripDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate;
        ImageView ivUser;
        CheckBox cbShareTripCost;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitleName);
            tvStartEndDate = view.findViewById(R.id.tvEmail);
            cbShareTripCost = view.findViewById(R.id.cbShareTripCost);
            ivUser = view.findViewById(R.id.ivUser);

        }
    }
}