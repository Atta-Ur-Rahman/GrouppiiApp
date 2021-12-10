package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.payment.RecentTransctionAdapter;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.FullPaid;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesDataModel;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GroupExpenditure;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.PartialPaid;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.RecentTransaction;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.SharesNoCost;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.ClickPartiallyPaidTripListener;
import com.techease.groupiiapplication.interfaceClass.ClickRecentTransactionListener;
import com.techease.groupiiapplication.interfaceClass.EditPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.backParticipantsCostsClickInterface.ConnectParticipantCostsBackClick;
import com.techease.groupiiapplication.interfaceClass.backParticipantsCostsClickInterface.ParticipantCostsBackClickChangedListener;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.payment.AddPaymentFragment;
import com.techease.groupiiapplication.utils.AnimationRVUtill;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
import com.techease.groupiiapplication.utils.NumberFormatUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentsFragment extends Fragment implements View.OnClickListener, AddPaymentCallBackListener {

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
    @BindView(R.id.tvNoRecentTransactionsFound)
    TextView tvNoRecentTransactionsFound;
    @BindView(R.id.rvRecentTransaction)
    RecyclerView rvRecentTransaction;


    @BindView(R.id.llRecentTransaction)
    RelativeLayout llRecentTransaction;

    @BindView(R.id.tvPaymentPaid)
    TextView tvPaymentPaid;
    ArrayList<GetPaymentExpensesDataModel> getPaymentExpensesData = new ArrayList<>();

    ArrayList<RecentTransaction> recentTransactions = new ArrayList<>();
    ArrayList<GroupExpenditure> groupExpendituresItems = new ArrayList<>();

    private EditPaymentCallBackListener editPaymentCallBackListener;

    ClickPartiallyPaidTripListener clickPartiallyPaidTripListener;


    public static ArrayList<FullPaid> fullPaidArrayList = new ArrayList<>();
    public static ArrayList<PartialPaid> partialPaidArrayList = new ArrayList<>();
    public static ArrayList<SharesNoCost> sharesNoCostArrayList = new ArrayList<>();
    String strTripID;
    int strUserID;
    RecentTransctionAdapter recentTransctionAdapter;
    ClickRecentTransactionListener clickParticipantCostsListener;


    public static PaymentsFragment newInstance() {
        PaymentsFragment fragment = new PaymentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payments, container, false);
        strTripID = AppRepository.mTripIDForUpdation(getActivity());
        strUserID = AppRepository.mUserID(getActivity());

        if (getActivity() instanceof ClickRecentTransactionListener)
            clickParticipantCostsListener = (ClickRecentTransactionListener) getActivity();

        if (getActivity() instanceof ClickPartiallyPaidTripListener)
            clickPartiallyPaidTripListener = (ClickPartiallyPaidTripListener) getActivity();

        if (getActivity() instanceof EditPaymentCallBackListener)
            editPaymentCallBackListener = (EditPaymentCallBackListener) getActivity();

        ButterKnife.bind(this, view);
        circularSeekBar.setEnabled(false);
        getPaymentExpenses();

        recentTransctionAdapter = new RecentTransctionAdapter(getActivity(), recentTransactions);
        rvRecentTransaction.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvRecentTransaction.setLayoutAnimation(AnimationRVUtill.RecylerViewAnimation(getActivity()));
        rvRecentTransaction.setAdapter(recentTransctionAdapter);


        ConnectParticipantCostsBackClick.addClickListener(() -> {
            editPaymentCallBackListener.onEditPaymentCallBack();
            getPaymentExpenses();
        });


        Log.d("zma user id", "" + AppRepository.mUserID(getActivity()));
        Log.d("zma trip id", "" + AppRepository.mTripIDForUpdation(getActivity()));
        return view;
    }

    public void getPaymentExpenses() {

        Log.d("zmaids", strTripID + "     " + strUserID);
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(strTripID, strUserID);
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    tvPaidNumber.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());

                    fullPaidArrayList.clear();
                    partialPaidArrayList.clear();
                    sharesNoCostArrayList.clear();
                    recentTransactions.clear();

                    fullPaidArrayList.addAll(response.body().getData().getFullPaid());
                    partialPaidArrayList.addAll(response.body().getData().getPartialPaid());
                    sharesNoCostArrayList.addAll(response.body().getData().getSharesNoCost());
                    recentTransactions.addAll(response.body().getData().getRecentTransaction());
                    recentTransctionAdapter.notifyDataSetChanged();

                    if (recentTransactions.size() == 0) {
                        tvNoRecentTransactionsFound.setTransitionVisibility(View.VISIBLE);
                    } else {
                        tvNoRecentTransactionsFound.setTransitionVisibility(View.GONE);
                    }

                    try {
                        tvPercentage.setText(NumberFormatUtil.FormatPercentage(response.body().getData().getPaidPercent()));
                        circularSeekBar.setProgress(Float.parseFloat(NumberFormatUtil.FormatPercentageShowCircle(response.body().getData().getPaidPercent())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
                    tvPaymentPaid.setText("$" + response.body().getData().getRecievedpayment() + " / " + "$" + response.body().getData().getTotalpayment());

                    Log.d("zma payment response", "" + response.body().getData().getGroupExpenditures());

                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
//                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.d("zma payment response", "" + t.getMessage());

            }
        });
    }

    @OnClick({R.id.mcvPayment, R.id.llRecentTransaction})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mcvPayment:
                clickPartiallyPaidTripListener.goClickPartiallyPaidTrip();
                break;
            case R.id.llRecentTransaction:
                clickParticipantCostsListener.goClickRecentTransaction();
                break;

        }

    }

    @Override
    public void onPaymentAdddCallBack() {
        getPaymentExpenses();
        Toast.makeText(getActivity(), "payment added", Toast.LENGTH_SHORT).show();
    }

}