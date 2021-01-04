package com.techease.groupiiapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.dataModel.tripDetail.Past;
import com.techease.groupiiapplication.dataModel.tripDetail.User;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

public class PastTripAdapter extends RecyclerView.Adapter<PastTripAdapter.MyViewHolder> {

    private Context context;
    private List<Past> pastList;
    private List<User> userList = new ArrayList<>();


    public PastTripAdapter(Context context, List<Past> pasts) {
        this.pastList = pasts;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_trip_card_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Past data = pastList.get(position);
        Picasso.get().load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);
        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(data.getFromdate());
        holder.tvLocation.setText(data.getLocation());


        userList.addAll(data.getUsers());

        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, userList));


        if (data.getTitle().equals("unpublished")) {

            holder.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    Log.d("zma tripid", String.valueOf(data.getId()));

                    String strTripId = String.valueOf(data.getId());

                    if (GeneralUtills.PopupMenuDelete(new ApiCallback() {
                        @Override
                        public boolean onResponse(boolean success) {
                            if (success){
                                removeAt(position);

                            }
                            return true;
                        }
                    },context, holder.ivImage, strTripId)) {


                    }


                    return false;
                }
            });
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data.getTitle().equals("unpublished")) {
                    Log.d("zma tripid", String.valueOf(data.getId()));
                    AppRepository.mPutValue(context).putString(String.valueOf(data.getId()), "tripID").commit();
                    context.startActivity(new Intent(context, NewTripStepTwoAddDetailActivity.class));
                } else {

                    Intent intent = new Intent(context, TripDetailScreenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("image", data.getCoverimage());
                    bundle.putString("title", data.getTitle());
                    bundle.putString("trip_type", "Past Trip");
                    bundle.putString("description", data.getDescription());
                    bundle.putString("location", data.getLocation());
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                    Log.d("zma tripid", String.valueOf(data.getId()));


                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return pastList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate, tvLocation;
        ImageView ivImage;
        RecyclerView rvUsers;

        MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivTripDetail);
            tvTitle = view.findViewById(R.id.tvTripTitle);
            tvStartEndDate = view.findViewById(R.id.tvStartEndDate);
            tvLocation = view.findViewById(R.id.tvLocation);
            rvUsers = view.findViewById(R.id.rvUsers);


        }
    }

    public void removeAt(int position) {
        pastList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pastList.size());
    }
}