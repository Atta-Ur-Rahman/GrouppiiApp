package com.techease.groupiiapplication.adapter.tripDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.ui.activity.AddTrip.EditParticipantActivity;
import com.techease.groupiiapplication.utils.Constants;

import java.util.List;

public class TripParticipantsAdapter extends RecyclerView.Adapter<TripParticipantsAdapter.MyViewHolder> {

    private Context context;
    private List<AddTripDataModel> userList;
    private boolean isCreatedBy;
    TextView tvNotFound;

    public TripParticipantsAdapter(Context context, List<AddTripDataModel> userList, boolean IsCreateBy, TextView tvNotFount) {
        this.userList = userList;
        this.context = context;
        this.isCreatedBy = IsCreateBy;
        this.tvNotFound = tvNotFount;
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
        AddTripDataModel user = userList.get(position);

        holder.tvTitle.setText(String.valueOf(user.getName()));
        holder.tvParticipaintPhoneNumber.setText(formatNumber(String.valueOf(user.getPhone())));
        Glide.with(context).load(user.getPicture()).placeholder(R.drawable.user).into(holder.ivUser);

        if (String.valueOf(user.getType()).equals("notregistered")) {
            holder.tvNotRegesterUser.setVisibility(View.VISIBLE);
            holder.tvNotRegesterUser.setText("You invited "+user.getName() +" to the group" );
            holder.materialCardView.setVisibility(View.GONE);

        } else {
            holder.tvNotRegesterUser.setVisibility(View.GONE);
            holder.materialCardView.setVisibility(View.VISIBLE);

        }


        if (String.valueOf(user.getSharedCost()).equals("1")) {
            holder.cbShareTripCost.setChecked(true);
        } else {
            holder.cbShareTripCost.setChecked(false);
        }


        holder.ivParticipantEdit.setOnClickListener(v -> {
            if (isCreatedBy) {
                Intent intent = new Intent(context, EditParticipantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", user.getName());
                bundle.putString("email", user.getEmail() + "");
                bundle.putString("phone", user.getPhone() + "");
                bundle.putString("userId", String.valueOf(user.getUserid()));
                bundle.putString("shared_cost", String.valueOf(user.getSharedCost()));
                bundle.putString("trip_id", String.valueOf(user.getTripid()));
                bundle.putBoolean("aBooleanIsTripDetailScreen", true);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            } else {
                Toast.makeText(context, "Only admin can manage participant", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public int getItemCount() {
        if (userList.size() == 0) {
            tvNotFound.setVisibility(View.VISIBLE);
        } else {
            tvNotFound.setVisibility(View.GONE);

        }
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvParticipaintPhoneNumber, tvNotRegesterUser;
        ImageView ivUser, ivParticipantEdit;
        CheckBox cbShareTripCost;
        MaterialCardView materialCardView;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitleName);
            tvParticipaintPhoneNumber = view.findViewById(R.id.tvParticipaintPhoneNumber);
            cbShareTripCost = view.findViewById(R.id.cbShareTripCost);
            ivUser = view.findViewById(R.id.ivUser);
            ivParticipantEdit = view.findViewById(R.id.ivParticipantEdit);
            materialCardView = view.findViewById(R.id.cvTrip);
            tvNotRegesterUser = view.findViewById(R.id.tvNotRegesterUser);


        }
    }


    private String formatNumber(String unformattedNumber) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = "us";//tm.getSimCountryIso();

        String formattedNumber;
        if (Build.VERSION.SDK_INT >= 21) {
            formattedNumber = PhoneNumberUtils.formatNumberToE164(unformattedNumber, countryCode);
        } else {
            formattedNumber = PhoneNumberUtils.formatNumber(unformattedNumber);
        }
        if (formattedNumber == null) {
            formattedNumber = unformattedNumber.replaceAll("[-,+]", "");
        }
        return formattedNumber;
    }


    public static boolean PopupMenuDelete(Context context, ImageView ivEdit) {
        PopupMenu popup = new PopupMenu(context, ivEdit);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.bottom_edit_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(
//                        LoginSignupSelectionActivity.this,
//                        "You Clicked : " + item.getTitle(),
//                        Toast.LENGTH_SHORT
//                ).show();

//                tvLanguage.setText(item.getTitle().toString());
                if (item.getTitle().toString().equals("Delete")) {

                }


                return true;
            }
        });

        popup.show(); //showing popup menu


        return false;
    }
}