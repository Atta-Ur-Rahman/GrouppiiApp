package com.techease.groupiiapplication.adapter.chatAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
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
import com.techease.groupiiapplication.dataModel.getUserTrip.GetUserTripData;

import java.util.List;

public class TripParticipantsAdapter extends RecyclerView.Adapter<TripParticipantsAdapter.MyViewHolder> {

    private Context context;
    private List<GetUserTripData> userList;


    public TripParticipantsAdapter(Context context, List<GetUserTripData> userList) {
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

        GetUserTripData user = userList.get(position);

        holder.tvTitle.setText(String.valueOf(user.getName()));
        holder.tvEmail.setText(String.valueOf(user.getEmail()));
        Glide.with(context).load(user.getPicture()).into(holder.ivUser);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvEmail;
        ImageView ivAddTripTick, ivUser;
        CheckBox cbShareTripCost;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitleName);
            tvEmail = view.findViewById(R.id.tvEmail);
            cbShareTripCost = view.findViewById(R.id.cbShareTripCost);
            ivUser = view.findViewById(R.id.ivUser);

        }
    }
}