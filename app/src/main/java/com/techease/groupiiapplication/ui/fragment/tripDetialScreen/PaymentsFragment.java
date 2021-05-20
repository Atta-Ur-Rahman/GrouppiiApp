package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.getPaymentExpenses.GroupExpendituresItem;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.PaymentClickInterface.ConnectPaymentClick;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentsFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.csPayment)
    CircularSeekBar circularSeekBar;

    @BindView(R.id.mcvPayment)
    MaterialCardView mcvPayment;

    @BindView(R.id.tvPercentage)
    TextView tvPercentage;
    @BindView(R.id.tvPartiallyPaid)
    TextView tvBalance;
    @BindView(R.id.tvPaidNumber)
    TextView tvPaidNumber;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dialog = AlertUtils.createProgressDialog(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payments, container, false);
        ButterKnife.bind(this, view);
        circularSeekBar.setEnabled(false);
        getPaymentExpenses();
        return view;
    }

    private void getPaymentExpenses() {
//        dialog.show();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripId(getActivity()), AppRepository.mUserID(getActivity()));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
//                    dialog.dismiss();
//                    paymentExpensesData.addAll(response.body().getData())

                    assert response.body() != null;
                    tvPercentage.setText(response.body().getData().getPaidPercent() + "%");
                    tvPaidNumber.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());
                    tvBalance.setText("" + response.body().getData().getTotalpayment());
                    circularSeekBar.setProgress(response.body().getData().getPaidPercent());


                } else {
//                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
//                dialog.dismiss();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.mcvPayment})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mcvPayment:
                ConnectPaymentClick.setMyBoolean(true);
                break;

        }

    }
}