package com.techease.groupiiapplication.adapter.tripDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.PersonalExpenditure;

import java.util.List;

public class PersonalExpendituresAdapter extends RecyclerView.Adapter<PersonalExpendituresAdapter.MyViewHolder> {

    private Context context;
    private List<PersonalExpenditure> groupExpendituresItems;


    public PersonalExpendituresAdapter(Context context, List<PersonalExpenditure> groupExpendituresItems) {
        this.groupExpendituresItems = groupExpendituresItems;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_trip_expenditures_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        PersonalExpenditure groupExpendituresItem = groupExpendituresItems.get(position);

        holder.tvExpenditureTitle.setText("$"+groupExpendituresItem.getAmount()+" - "+groupExpendituresItem.getName());
        holder.tvShortDescription.setText(String.valueOf(groupExpendituresItem.getShortDesc()));

        if (groupExpendituresItem.getTypeImage() != null) {
            switch (groupExpendituresItem.getTypeImage()) {
                case "taxi":
                    Glide.with(context).load(R.mipmap.taxi_wheel).into(holder.ivType);
                    break;
                case "bus":
                    Glide.with(context).load(R.mipmap.transfer).into(holder.ivType);
                    break;
                case "reserv":
                    Glide.with(context).load(R.mipmap.reserv_selected).into(holder.ivType);
                    break;
                case "flight":
                    Glide.with(context).load(R.mipmap.flight).into(holder.ivType);
                    break;
            }
        }
        Log.d("zma type", String.valueOf(groupExpendituresItem.getTypeImage()));


    }

    @Override
    public int getItemCount() {
        return groupExpendituresItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvExpenditureTitle, tvShortDescription;
        ImageView ivMenuDot, ivType;

        MyViewHolder(View view) {
            super(view);
            tvExpenditureTitle = view.findViewById(R.id.tvExpenditureTitle);
            tvShortDescription = view.findViewById(R.id.tvShortDescription);
            ivMenuDot = view.findViewById(R.id.ivMenuDot);
            ivType = view.findViewById(R.id.ivType);


        }
    }
}