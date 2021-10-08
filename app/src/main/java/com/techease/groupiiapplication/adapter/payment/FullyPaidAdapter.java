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
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.FullPaid;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.RecentTransaction;
import com.techease.groupiiapplication.utils.DateUtills;

import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class FullyPaidAdapter extends RecyclerView.Adapter<FullyPaidAdapter.MyViewHolder> {

    private Context context;
    private List<FullPaid> recentTransaction;


    public FullyPaidAdapter(Context context, List<FullPaid> groupExpendituresItems) {
        this.recentTransaction = groupExpendituresItems;
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

        FullPaid recentTransaction = this.recentTransaction.get(position);

        holder.circularSeekBar.setEnabled(false);

        holder.tvTitileName.setText(recentTransaction.getName());
        holder.tvDate.setText("Fully Paid");
        holder.circularSeekBar.setProgress(100);
        holder.tvPersentage.setText("100%");

    }

    @Override
    public int getItemCount() {
        return recentTransaction.size();
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