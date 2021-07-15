package com.techease.groupiiapplication.adapter.tripDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.api.ApiClass;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.tripDetial.getUserTrip.GetUserTripData;
import com.techease.groupiiapplication.ui.activity.AddTrip.EditParticipantActivity;

import java.util.List;

public class TripParticipantsAdapter extends RecyclerView.Adapter<TripParticipantsAdapter.MyViewHolder> {

    private Context context;
    private List<AddTripDataModel> userList;


    public TripParticipantsAdapter(Context context, List<AddTripDataModel> userList) {
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

        AddTripDataModel user = userList.get(position);

        holder.tvTitle.setText(String.valueOf(user.getName()));
        holder.tvEmail.setText(String.valueOf(user.getEmail()));
        Glide.with(context).load(user.getPicture()).into(holder.ivUser);

        Log.d("zmasharecost", user.getSharedCost() + "");

        if (String.valueOf(user.getSharedCost()).equals("1")) {
            holder.cbShareTripCost.setChecked(true);
        } else {
            holder.cbShareTripCost.setChecked(false);

        }

        holder.ivParticipantEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditParticipantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", user.getName());
                bundle.putString("email", user.getEmail()+"");
                bundle.putString("phone", user.getPhone()+"");
                bundle.putString("userId", String.valueOf(user.getUserid()));
                bundle.putString("shared_cost", String.valueOf(user.getSharedCost()));
                bundle.putString("trip_id", String.valueOf(user.getTripid()));
                bundle.putBoolean("aBooleanIsTripDetailScreen", true);
                intent.putExtras(bundle);
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle, tvEmail;
        ImageView ivAddTripTick, ivUser, ivParticipantEdit;
        CheckBox cbShareTripCost;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitleName);
            tvEmail = view.findViewById(R.id.tvEmail);
            cbShareTripCost = view.findViewById(R.id.cbShareTripCost);
            ivUser = view.findViewById(R.id.ivUser);
            ivParticipantEdit = view.findViewById(R.id.ivParticipantEdit);


        }
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