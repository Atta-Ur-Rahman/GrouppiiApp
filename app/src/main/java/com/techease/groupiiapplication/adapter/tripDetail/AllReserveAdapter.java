package com.techease.groupiiapplication.adapter.tripDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.ActiveTripAdapter;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.api.ApiClass;
import com.techease.groupiiapplication.dataModel.deleteRsvp.DeleteRsvpResponse;
import com.techease.groupiiapplication.dataModel.getReserveModel.GetReserveDataModel;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.AddReservsActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.GeneralUtills;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllReserveAdapter extends RecyclerView.Adapter<AllReserveAdapter.MyViewHolder> {


    private List<GetReserveDataModel> getReserveData;
    private Context context;

    public AllReserveAdapter(Context context, List<GetReserveDataModel> getReserveData) {
        this.getReserveData = getReserveData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_reserve_layout, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GetReserveDataModel getReserveDataModel = getReserveData.get(position);
        Log.d("zmahotelImage", getReserveDataModel.getImage());
        Glide.with(context).load(getReserveDataModel.getImage()).placeholder(R.drawable.image_thumbnail).into(holder.ivHotelImage);
        holder.tvHotelTitle.setText(getReserveDataModel.getTitle());
        holder.tvHotelLocation.setText(getReserveDataModel.getAddress());
        holder.tvStartEndDate.setText(DateUtills.getRsvpDateFormate(getReserveDataModel.getFromdate()) + "-" + DateUtills.getRsvpDateFormate(getReserveDataModel.getTodate()));

//        if (getReserveDataModel.getIsDone() == 1) {
//            holder.tvConfirmed.setText("Confirmed");
//        } else {
//            holder.tvConfirmed.setText("Not Confirmed");
//        }


        holder.tvConfirmed.setText(getReserveDataModel.getConfirmation());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TripDetailScreenActivity.aBooleanIsCreatedBy) {

                    PopupMenu popup = new PopupMenu(context, holder.itemView);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.edit_delete_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().toString().equals("Delete")) {

                                BottomSheetMaterialDialog mBottomSheetDialogd = new BottomSheetMaterialDialog.Builder((Activity) context)
                                        .setTitle("Delete RSVP?")
                                        .setMessage("Are you sure you want to this RSVP?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", (dialogInterface, which) -> {
                                            ApiCallForDeleteRsvp(position, String.valueOf(getReserveDataModel.getId()));
                                            dialogInterface.dismiss();
                                        })
                                        .setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss())
                                        .build();

                                // Show Dialog
                                mBottomSheetDialogd.show();

                            }


                            if (item.getTitle().toString().equals("Edit")) {

                                Intent intent = new Intent(context, AddReservsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("rsvpID", String.valueOf(getReserveDataModel.getId()));
                                bundle.putString("image", getReserveDataModel.getImage());
                                bundle.putString("title", getReserveDataModel.getTitle());
                                bundle.putString("start_date", getReserveDataModel.getFromdate());
                                bundle.putString("end_date", getReserveDataModel.getTodate());
                                bundle.putString("status", String.valueOf(getReserveDataModel.getConfirmation()));
                                bundle.putBoolean("edit", true);
                                bundle.putString("location", getReserveDataModel.getAddress());
                                intent.putExtras(bundle);
                                context.startActivity(intent);
//                            } else {
//                                Toast.makeText(this, getString(R.string.admin_can_edit_trip_settings), Toast.LENGTH_SHORT).show();
//                            }

                            }


                            return true;
                        }
                    });

                    popup.show(); //showing popup menu


                } else {
                    Toast.makeText(context, "Rsvp update and delete admin only", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void ApiCallForDeleteRsvp(int itemPosition, String strRsvpId) {

        Call<DeleteRsvpResponse> deleteRsvpResponseCall = BaseNetworking.ApiInterface().deleteReserve(strRsvpId);
        deleteRsvpResponseCall.enqueue(new Callback<DeleteRsvpResponse>() {
            @Override
            public void onResponse(Call<DeleteRsvpResponse> call, Response<DeleteRsvpResponse> response) {
                if (response.isSuccessful()) {
                    getReserveData.remove(itemPosition);
                    notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<DeleteRsvpResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return getReserveData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivHotelImage;
        TextView tvHotelTitle, tvHotelLocation, tvStartEndDate, tvConfirmed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivHotelImage = itemView.findViewById(R.id.ivHotelImage);
            tvHotelTitle = itemView.findViewById(R.id.tvHotelTitle);
            tvHotelLocation = itemView.findViewById(R.id.tvHotelLocation);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmedStatus);
            tvStartEndDate = itemView.findViewById(R.id.tvStartEndDate);


        }
    }
}
