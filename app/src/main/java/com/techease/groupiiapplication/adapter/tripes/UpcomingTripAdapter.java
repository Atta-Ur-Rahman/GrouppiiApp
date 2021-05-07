package com.techease.groupiiapplication.adapter.tripes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.Upcoming;
import com.techease.groupiiapplication.dataModel.tripDetail.User;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

public class UpcomingTripAdapter extends RecyclerView.Adapter<UpcomingTripAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Upcoming> upcomingList;
    private List<Upcoming> upcomingListFilter;
    private List<User> userList = new ArrayList<>();
    private List<String> stringArrayList = new ArrayList<>();


    public UpcomingTripAdapter(Context context, List<Upcoming> upcomings) {
        this.upcomingList = upcomings;
        this.upcomingListFilter = upcomings;
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
        Upcoming data = upcomingListFilter.get(position);
        Picasso.get().load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);
        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(data.getFromdate());
        holder.tvLocation.setText(data.getLocation());

        userList.addAll(data.getUsers());


        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, userList));


        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TripFragment.userList = data.getUsers();

                AppRepository.mPutValue(context).putString("tripID", String.valueOf(data.getId())).commit();

                if (data.getTitle().equals("unpublished")) {
                    Log.d("zma tripid", String.valueOf(data.getId()));
                    context.startActivity(new Intent(context, NewTripStepTwoAddDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

                } else {
                    Intent intent = new Intent(context, TripDetailScreenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("image", data.getCoverimage());
                    bundle.putString("title", data.getTitle());
                    bundle.putString("trip_type", "Upcoming Trip");
                    bundle.putString("description", data.getDescription());
                    bundle.putString("location", data.getLocation());
                    bundle.putStringArrayList("user", (ArrayList<String>) stringArrayList);

                    intent.putExtras(bundle);
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return upcomingListFilter.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    upcomingListFilter = upcomingList;
                } else {
                    List<Upcoming> filteredList = new ArrayList<>();
                    for (Upcoming row : upcomingList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    upcomingListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = upcomingListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                upcomingListFilter = (ArrayList<Upcoming>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}