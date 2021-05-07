package com.techease.groupiiapplication.adapter.tripDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayDataModel;
import com.techease.groupiiapplication.utils.DatePickerClass;

import java.util.List;

public class AllTripDayAdapter extends RecyclerView.Adapter<AllTripDayAdapter.MyViewHolder> {

    private Context context;
    private List<AllTripDayDataModel> allTripDayDataModels;


    public AllTripDayAdapter(Context context, List<AllTripDayDataModel> allTripDayDataModel) {
        this.allTripDayDataModels = allTripDayDataModel;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_all_trip_add_activity_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        AllTripDayDataModel allTripDayDataModel = allTripDayDataModels.get(position);

        holder.tvTitle.setText(String.valueOf(allTripDayDataModel.getTitle()));
        holder.tvDescription.setText(String.valueOf(allTripDayDataModel.getDescription()));
        holder.tvActivityTime.setText(DatePickerClass.timeFormated(allTripDayDataModel.getTime()));
        holder.tvUserName.setText(allTripDayDataModel.getUsername());


        switch (allTripDayDataModel.getType()) {
            case "Taxi":
                Glide.with(context).load(R.mipmap.taxi_wheel).into(holder.ivType);
                break;
            case "Bus":
                Glide.with(context).load(R.mipmap.transfer).into(holder.ivType);
                break;
            case "Reserv":
                Glide.with(context).load(R.mipmap.reserv_selected).into(holder.ivType);
                break;
            case "Flight":
                Glide.with(context).load(R.mipmap.flight).into(holder.ivType);
                break;
        }
        Log.d("zma type", String.valueOf(allTripDayDataModel.getType()));


    }

    @Override
    public int getItemCount() {
        return allTripDayDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvDescription, tvActivityTime, tvUserName;
        ImageView ivAddTripTick, ivType;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvActivityTitleName);
            tvDescription = view.findViewById(R.id.tvActivityDescription);
            tvActivityTime = view.findViewById(R.id.tvActivityTime);
            tvUserName = view.findViewById(R.id.tvUserName);
            ivType = view.findViewById(R.id.ivType);


        }
    }
}