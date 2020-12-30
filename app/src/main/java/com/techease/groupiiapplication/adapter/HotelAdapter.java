package com.techease.groupiiapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.hotel.HotelDataModel;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {

    private Context context;
    private List<HotelDataModel> hotelDataModels;


    public HotelAdapter(Context context, List<HotelDataModel> hotelDataModelList) {
        this.hotelDataModels = hotelDataModelList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_trip_detail_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        HotelDataModel data = hotelDataModels.get(position);
        Picasso.get().load(data.getHotel().getMedia().get(0).getUri()).placeholder(R.drawable.progress_animation).into(holder.ivImage);
//        holder.ivImage.setImageURI(Uri.parse(data.getHotel().getMedia().get(0).getUri()));
        holder.tvTitle.setText(data.getHotel().getName());
        holder.tvRoom.setText(data.getHotel().getType());
        holder.tvHotelPrice.setText(data.getHotel().getRating());

        Log.d("zma image",String.valueOf(data.getHotel().getMedia().get(0).getUri()));



    }

    @Override
    public int getItemCount() {
        return hotelDataModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvRoom,tvDescription, tvHotelPrice;
        ImageView ivImage;

        MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivTripDetail);
            tvTitle = view.findViewById(R.id.tvTripTitle);
            tvRoom = view.findViewById(R.id.tvRoom);
            tvHotelPrice =view.findViewById(R.id.tvHotelPrice);

        }
    }
}