package com.techease.groupiiapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.techease.groupiiapplication.dataModel.tripDetail.Active;
import com.techease.groupiiapplication.dataModel.tripDetail.User;
import com.techease.groupiiapplication.ui.activity.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

public class UnPublishTripAdapter extends RecyclerView.Adapter<UnPublishTripAdapter.MyViewHolder> {

    private Context context;
    private List<Active> activeList;
    public static List<User> userList = new ArrayList<>();
    private List<String> stringArrayList = new ArrayList<>();


    public UnPublishTripAdapter(Context context, List<Active> actives) {
        this.activeList = actives;
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
        Active data = activeList.get(position);
        Picasso.get().load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);
        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(data.getFromdate());
        holder.tvLocation.setText(data.getLocation());
//        holder.tvDaysLeft.setText(data.);


        if (data.getUsers() != null) {
            userList.addAll(data.getUsers());
        }

//                    Log.d("userpic", data.getUsers().toString());



        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, userList));

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppRepository.mPutValue(context).putString("tripID", String.valueOf(data.getId())).commit();

                Intent intent = new Intent(context, TripDetailScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("image", data.getCoverimage());
                bundle.putString("title", data.getTitle());
                bundle.putString("trip_type", "Active Trip");
                bundle.putString("description", data.getDescription());
                bundle.putString("location", data.getLocation());
                bundle.putStringArrayList("user", (ArrayList<String>) stringArrayList);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }
        });


    }


    @Override
    public int getItemCount() {
        return activeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate, tvLocation, tvDaysLeft;
        ImageView ivImage;
        RecyclerView rvUsers;

        MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivTripDetail);
            tvTitle = view.findViewById(R.id.tvTripTitle);
            tvStartEndDate = view.findViewById(R.id.tvStartEndDate);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvDaysLeft = view.findViewById(R.id.tvDaysLeft);
            rvUsers = view.findViewById(R.id.rvUsers);

        }
    }
}