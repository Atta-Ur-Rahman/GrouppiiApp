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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getAllTrip.Active;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.AnimationRVUtill;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

public class ActiveTripAdapter extends RecyclerView.Adapter<ActiveTripAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Active> activeList;
    private List<Active> activeListFilter;


    public ActiveTripAdapter(Context context, List<Active> actives) {
        this.activeList = actives;
        this.activeListFilter = actives;
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
        Active data = activeListFilter.get(position);
        Glide.with(context).load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);

        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(DateUtills.getDateFormate(data.getFromdate()));
        holder.tvLocation.setText(data.getLocation());
        holder.tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(data.getFromdate())) + " days left");

        if (data.getUsers() != null) {
            TripFragment.userList.addAll(data.getUsers());
        }
        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, data.getUsers()));

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRepository.mPutValue(context).putString("getFromdate", String.valueOf(data.getFromdate())).commit();
                AppRepository.mPutValue(context).putString("tripID", String.valueOf(data.getTripid())).commit();

                if (data.isIsCreatedby()) {
                    AppRepository.mPutValue(context).putString("tripIDForUpdation", String.valueOf(data.getId())).commit();
                } else {
                    AppRepository.mPutValue(context).putString("tripIDForUpdation", String.valueOf(data.getTripid())).commit();

                }

                TripFragment.userList = data.getUsers();


                Intent intent = new Intent(context, TripDetailScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("image", data.getCoverimage());
                bundle.putString("title", data.getTitle());
                bundle.putString("trip_type", "Active Trip");
                bundle.putString("start_date", data.getFromdate());
                bundle.putString("end_date", data.getTodate());
                bundle.putString("pay_date", data.getPayDate());
                bundle.putString("description", data.getDescription());
                bundle.putString("location", data.getLocation());
                bundle.putBoolean("is_createdby", data.isIsCreatedby());
                intent.putExtras(bundle);
                context.startActivity(intent);
                Animatoo.animateFade(context);

            }
        });


    }


    @Override
    public int getItemCount() {
        return activeListFilter.size();
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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    activeListFilter = activeList;
                } else {
                    List<Active> filteredList = new ArrayList<>();
                    for (Active row : activeList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    activeListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = activeListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                activeListFilter = (ArrayList<Active>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}