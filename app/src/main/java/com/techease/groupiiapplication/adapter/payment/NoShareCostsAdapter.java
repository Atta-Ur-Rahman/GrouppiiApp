package com.techease.groupiiapplication.adapter.payment;

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

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.SharesNoCost;

import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class NoShareCostsAdapter extends RecyclerView.Adapter<NoShareCostsAdapter.MyViewHolder> {

    private Context context;
    private List<SharesNoCost> sharesNoCosts;


    public NoShareCostsAdapter(Context context, List<SharesNoCost> groupExpendituresItems) {
        this.sharesNoCosts = groupExpendituresItems;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_participaints_costs_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        SharesNoCost sharesNoCost = this.sharesNoCosts.get(position);

        holder.circularSeekBar.setEnabled(false);

        holder.tvTitileName.setText(sharesNoCost.getName());


        if (sharesNoCost.getName() == null) {
            holder.tvTitileName.setText("$" + sharesNoCost.getAmount() + "-Not Registered User");

        }
        holder.tvDate.setText("Don't Share Cost");
//        holder.circularSeekBar.setProgress(recentTransaction);

        holder.cbShareTripCost.setChecked(false);
        holder.cbShareTripCost.setText("Doesn't share trip cost");

    }

    @Override
    public int getItemCount() {
        return sharesNoCosts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvPersentage, tvDate, tvPaymentType, tvTitileName;
        ImageView ivUser, ivType;
        CircularSeekBar circularSeekBar;

        CheckBox cbShareTripCost;

        MyViewHolder(View view) {
            super(view);
            tvPersentage = view.findViewById(R.id.tvPercentage);
            tvDate = view.findViewById(R.id.tvDate);
            tvPaymentType = view.findViewById(R.id.tvPaymentType);
            tvTitileName = view.findViewById(R.id.tvTitleName);

            ivUser = view.findViewById(R.id.ivUser);
            circularSeekBar = view.findViewById(R.id.csPayment);

            cbShareTripCost = view.findViewById(R.id.cbShareTripCost);


        }
    }
}