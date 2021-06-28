package com.techease.groupiiapplication.adapter.tripes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.dataModel.getAllTrip.Past;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PastTripAdapter extends RecyclerView.Adapter<PastTripAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Past> pastList;
    private List<Past> pastListFiltered;
    public static List<User> userList = new ArrayList<>();
    private ArrayList<String> stringArrayList = new ArrayList<>();
    Dialog dialog;


    public PastTripAdapter(Context context, List<Past> pasts) {
        this.pastList = pasts;
        this.pastListFiltered = pasts;
        this.context = context;
        dialog = AlertUtils.createProgressDialog((Activity) context);

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
        Past data = pastListFiltered.get(position);

        if (data.getCoverimage() != null) {
            Glide.with(context).load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);
        }
        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(data.getFromdate());
        holder.tvLocation.setText(data.getLocation());
        holder.tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(data.getFromdate())) + " days left");


        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, data.getUsers()));


        if (data.getTitle().equals("unpublished")) {

            holder.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    String strTripId = String.valueOf(data.getId());
                    if (GeneralUtills.PopupMenuDelete(new ApiCallback() {
                        @Override
                        public boolean onResponse(boolean success) {
                            if (success) {
                                removeAt(position);

                            }
                            return true;
                        }
                    }, context, holder.ivImage, strTripId)) {


                    }


                    return false;
                }
            });
        }

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRepository.mPutValue(context).putString("getFromdate", String.valueOf(data.getFromdate())).commit();
                AppRepository.mPutValue(context).putString("tripID", String.valueOf(data.getId())).commit();

                if (data.getTitle().equals("unpublished")) {
                    context.startActivity(new Intent(context, NewTripStepTwoAddDetailActivity.class));
                } else {

                    stringArrayList.clear();
                    for (int i = 0; i < userList.size(); i++) {
                        stringArrayList.add(String.valueOf(userList.get(i).getPicture()));
                    }

                    dialog.show();
                    new Thread() {

                        public void run() {

                            TripFragment.userList = data.getUsers();
                            Intent intent = new Intent(context, TripDetailScreenActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("image", data.getCoverimage());
                            bundle.putString("title", data.getTitle());
                            bundle.putString("trip_type", "Past Trip");
                            bundle.putString("description", data.getDescription());
                            bundle.putString("date", data.getFromdate());
                            bundle.putString("location", data.getLocation());
                            bundle.putStringArrayList("users", stringArrayList);
                            intent.putExtras(bundle);
                            context.startActivity(intent);

                            dialog.dismiss();
                        }

                    }.start();



                }
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return pastListFiltered.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvStartEndDate, tvDaysLeft, tvLocation;
        ImageView ivImage;
        RecyclerView rvUsers;

        MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.ivTripDetail);
            tvTitle = view.findViewById(R.id.tvTripTitle);
            tvStartEndDate = view.findViewById(R.id.tvStartEndDate);
            tvLocation = view.findViewById(R.id.tvLocation);
            rvUsers = view.findViewById(R.id.rvUsers);
            tvDaysLeft = view.findViewById(R.id.tvDaysLeft);


        }
    }

    public void removeAt(int position) {
        pastList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pastList.size());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pastListFiltered = pastList;
                } else {
                    List<Past> filteredList = new ArrayList<>();
                    for (Past row : pastList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    pastListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pastListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pastListFiltered = (ArrayList<Past>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}