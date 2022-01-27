package com.techease.groupiiapplication.adapter.addTrip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
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
import com.techease.groupiiapplication.api.ApiClass;
import com.techease.groupiiapplication.api.TripUserDeleteCallback;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;

import java.util.List;

public class AddTripParticipaintsAdapter extends RecyclerView.Adapter<AddTripParticipaintsAdapter.MyViewHolder> {

    private Context context;
    private List<AddTripDataModel> addTripDataModels;


    public AddTripParticipaintsAdapter(Context context, List<AddTripDataModel> addTripDataModel) {
        this.addTripDataModels = addTripDataModel;
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
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        AddTripDataModel addTripDataModel = addTripDataModels.get(position);

        holder.tvTitle.setText(String.valueOf(addTripDataModel.getName()));
        holder.tvParticipaintPhoneNumber.setText(String.valueOf(addTripDataModel.getPhone()));

        if (String.valueOf(addTripDataModel.getName()).equals("null")) {
            holder.tvTitle.setText(R.string.not_registered_user);
        }
        Glide.with(context).load(addTripDataModel.getPicture()).placeholder(R.drawable.user).into(holder.ivUser);

        holder.tvParticipaintPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


        if (addTripDataModel.getSharedCost() == 1) {
            holder.cbShareTripCost.setChecked(true);
            holder.cbShareTripCost.setText("Share trip cost");
        } else {
            holder.cbShareTripCost.setChecked(false);
            holder.cbShareTripCost.setText("Doesn't share trip cost");
        }

        if (String.valueOf(addTripDataModel.getType()).equals("notregistered")) {
            holder.tvNotRegesterUser.setVisibility(View.VISIBLE);
            holder.tvNotRegesterUser.setText("You invited " + addTripDataModel.getName() + " to the group");
            holder.materialCardView.setVisibility(View.GONE);

        } else {
            holder.tvNotRegesterUser.setVisibility(View.GONE);
            holder.materialCardView.setVisibility(View.VISIBLE);

        }


        holder.ivParticipantEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.ivParticipantEdit);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_delete:
                                @SuppressLint("NotifyDataSetChanged") TripUserDeleteCallback userDeleteCallback = success -> {
                                    if (success) {
                                        addTripDataModels.remove(position);
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "some went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                };

                                ApiClass.ApiCallForDeleteTripUser(userDeleteCallback, "" + addTripDataModel.getTripid(), "" + addTripDataModel.getUserid());

                                return true;


                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();


//                Intent intent = new Intent(context, EditParticipantActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("userId", String.valueOf(addTripDataModel.getUserid()));
//                bundle.putString("name", addTripDataModel.getName());
//                bundle.putString("email", addTripDataModel.getEmail());
//                bundle.putString("phone", addTripDataModel.getPhone());
//                bundle.putString("shared_cost", String.valueOf(addTripDataModel.getSharedCost()));
//                bundle.putString("trip_id", String.valueOf(addTripDataModel.getTripid()));
//                bundle.putBoolean("aBooleanIsTripDetailScreen", false);
//                intent.putExtras(bundle);
//                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return addTripDataModels.size();
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
}