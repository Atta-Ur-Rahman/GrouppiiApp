package com.techease.groupiiapplication.adapter.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripDataModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context context;
    private List<AddTripDataModel> addTripDataModels;


    public NotificationAdapter(Context context, List<AddTripDataModel> addTripDataModel) {
        this.addTripDataModels = addTripDataModel;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_notification_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        AddTripDataModel addTripDataModel = addTripDataModels.get(position);

//        holder.tvTitle.setText(String.valueOf(addTripDataModel.getPhone()));
//        holder.tvStartEndDate.setText(String.valueOf(addTripDataModel.getEmail()));


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate;
        ImageView ivAddTripTick;

        MyViewHolder(View view) {
            super(view);
//            tvTitle = view.findViewById(R.id.tvTitleName);
//            tvStartEndDate = view.findViewById(R.id.tvEmail);
//            ivAddTripTick = view.findViewById(R.id.ivTick);

        }
    }
}