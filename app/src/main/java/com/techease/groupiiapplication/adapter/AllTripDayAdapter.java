package com.techease.groupiiapplication.adapter;

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
import com.techease.groupiiapplication.dataModel.getAllTripDay.AllTripDayDataModel;
import com.techease.groupiiapplication.utils.DatePickerClass;

import java.util.List;

public class AllTripDayAdapter extends RecyclerView.Adapter<AllTripDayAdapter.MyViewHolder> {

    private Context context;
    private List<AllTripDayDataModel> allTripDayDataModels ;


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


    }

    @Override
    public int getItemCount() {
        return allTripDayDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvDescription,tvActivityTime;
        ImageView ivAddTripTick;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvActivityTitleName);
            tvDescription = view.findViewById(R.id.tvActivityDescription);
            tvActivityTime=view.findViewById(R.id.tvActivityTime);

        }
    }
}