package com.techease.groupiiapplication.adapter.tripes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.techease.groupiiapplication.dataModel.getAllTrip.Unpublish;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.ui.activity.AddTrip.AddNewTripThreeHotelActivity;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.ArrayList;
import java.util.List;

public class UnPublishTripAdapter extends RecyclerView.Adapter<UnPublishTripAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Unpublish> unpublishList;
    private List<Unpublish> unpublishListFilter;
    public static ArrayList<User> userList = new ArrayList<>();
    private List<String> stringArrayList = new ArrayList<>();


    public UnPublishTripAdapter(Context context, List<Unpublish> unpublishes) {
        this.unpublishList = unpublishes;
        this.unpublishListFilter = unpublishes;
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
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Unpublish data = unpublishListFilter.get(position);
        Glide.with(context).load(data.getCoverimage()).placeholder(R.drawable.image_thumbnail).into(holder.ivImage);
        holder.tvTitle.setText(data.getTitle());
        holder.tvStartEndDate.setText(DateUtills.getDateFormate(data.getFromdate()));
        holder.tvLocation.setText(data.getLocation());
        holder.tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(data.getFromdate())) + " days left");


        holder.rvUsers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvUsers.addItemDecoration(new GeneralUtills.OverlapDecoration());
        holder.rvUsers.setHasFixedSize(true);
        holder.rvUsers.setAdapter(new UserTripCircleImagesAdapter(context, data.getUsers()));


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


        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppRepository.mPutValue(context).putString("tripID", String.valueOf(data.getTripid())).commit();
                AppRepository.mPutValue(context).putString("tripIDForUpdation", String.valueOf(data.getId())).commit();

                if (data.getTitle().equals("unpublished")) {
//                    Log.d("zma tripid", String.valueOf(data.getId()));
                    context.startActivity(new Intent(context, NewTripStepTwoAddDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
                    } else {
                    AppRepository.mPutValue(context).putString(data.getTitle(), "trip_title_name").commit();
                    context.startActivity(new Intent(context, AddNewTripThreeHotelActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());

//                    TripFragment.userList = data.getUsers();
//                    Intent intent = new Intent(context, TripDetailScreenActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("image", data.getCoverimage());
//                    bundle.putString("title", data.getTitle());
//                    bundle.putString("trip_type", "Past Trip");
//                    bundle.putString("description", data.getDescription());
//                    bundle.putString("location", data.getLocation());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//
//                    TripFragment.userList = data.getUsers();
//                    Log.d("zma tripid", String.valueOf(data.getId()));


                }
            }

        });
    }

    public void removeAt(int position) {
        unpublishListFilter.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, unpublishListFilter.size());
    }

    @Override
    public int getItemCount() {
        return unpublishListFilter.size();
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
                    unpublishListFilter = unpublishList;
                } else {
                    List<Unpublish> filteredList = new ArrayList<>();
                    for (Unpublish row : unpublishList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    unpublishListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = unpublishListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                unpublishListFilter = (ArrayList<Unpublish>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}