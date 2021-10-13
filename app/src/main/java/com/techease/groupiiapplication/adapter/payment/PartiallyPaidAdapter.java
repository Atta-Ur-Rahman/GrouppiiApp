package com.techease.groupiiapplication.adapter.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.PartialPaid;
import com.techease.groupiiapplication.utils.NumberFormatUtil;

import java.util.List;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class PartiallyPaidAdapter extends RecyclerView.Adapter<PartiallyPaidAdapter.MyViewHolder> {

    private Context context;
    private List<PartialPaid> partialPaidList;


    public PartiallyPaidAdapter(Context context, List<PartialPaid> partialPaids) {
        this.partialPaidList = partialPaids;
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

        PartialPaid partialPaid = this.partialPaidList.get(position);
        holder.circularSeekBar.setEnabled(false);
        holder.tvTitileName.setText("$" + partialPaid.getAmount() + "-"+ partialPaid.getName());
        holder.tvDate.setText("Partially Paid");
        holder.circularSeekBar.setProgress(partialPaid.getPercent());
        holder.tvPersentage.setText(NumberFormatUtil.FormatPercentage(Double.valueOf(partialPaid.getPercent())));


    }

    @Override
    public int getItemCount() {
        return partialPaidList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvPersentage, tvDate, tvPaymentType, tvTitileName;
        ImageView ivUser, ivType;
        CircularSeekBar circularSeekBar;

        MyViewHolder(View view) {
            super(view);
            tvPersentage = view.findViewById(R.id.tvPercentage);
            tvDate = view.findViewById(R.id.tvDate);
            tvPaymentType = view.findViewById(R.id.tvPaymentType);
            tvTitileName = view.findViewById(R.id.tvTitleName);

            ivUser = view.findViewById(R.id.ivUser);
            circularSeekBar = view.findViewById(R.id.csPayment);


        }
    }
}