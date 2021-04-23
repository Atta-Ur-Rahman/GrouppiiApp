package com.techease.groupiiapplication.adapter.chatAdapter;

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

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.User;

import java.util.List;

public class TripParticipantsAdapter extends RecyclerView.Adapter<TripParticipantsAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;


    public TripParticipantsAdapter(Context context, List<User> userList) {
        this.userList = userList;
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

        User user = userList.get(position);

        holder.tvTitle.setText(String.valueOf(user.getName()));
        holder.tvStartEndDate.setText(String.valueOf(user.getCreatedAt()));
        Glide.with(context).load(user.getPicture()).into(holder.ivUser);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate;
        ImageView ivAddTripTick, ivUser;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitleName);
            tvStartEndDate = view.findViewById(R.id.tvEmail);
            ivAddTripTick = view.findViewById(R.id.ivTick);
            ivUser = view.findViewById(R.id.ivUser);

        }
    }
}