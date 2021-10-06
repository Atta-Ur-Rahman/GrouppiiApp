package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import static com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity.personalExpendituresItems;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.payment.RecentTransctionAdapter;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GetPaymentExpensesData;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GroupExpendituresItem;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.participantsCostsClickInterface.ConnectParticipantCostsClick;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.paymentClickInterface.ConnectPaymentClick;
import com.techease.groupiiapplication.utils.AppRepository;

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

    @BindView(R.id.rvRecentTransaction)
    RecyclerView rvRecentTransaction;


    @BindView(R.id.tvRecentTransaction)
    TextView tvRecentTransaction;

    AddPaymentCallBackListener addPaymentCallBackListener;


    @BindView(R.id.tvPaymentPaid)
    TextView tvPaymentPaid;
    ArrayList<GetPaymentExpensesData> getPaymentExpensesData = new ArrayList<>();
    ArrayList<GroupExpendituresItem> groupExpendituresItems = new ArrayList<>();
    String strTripID;
    int strUserID;


    RecentTransctionAdapter recentTransctionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payments, container, false);
        strTripID = AppRepository.mTripIDForUpdation(getActivity());
        strUserID = AppRepository.mUserID(getActivity());

        ButterKnife.bind(this, view);
        circularSeekBar.setEnabled(false);
        getPaymentExpenses();

        recentTransctionAdapter = new RecentTransctionAdapter(getActivity(), personalExpendituresItems);
        rvRecentTransaction.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvRecentTransaction.setAdapter(recentTransctionAdapter);


        Log.d("zma user id", "" + AppRepository.mUserID(getActivity()));
        Log.d("zma trip id", "" + AppRepository.mTripId(getActivity()));
        return view;
    }

    public void getPaymentExpenses() {

        Log.d("zmaids", strTripID + "     " + strUserID);
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(strTripID, strUserID);
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    tvPercentage.setText(response.body().getData().getPaidPercent() + "%");
                    tvPaidNumber.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());
//                    tvBalance.setText("" + response.body().getData().getTotalpayment());
                    try {
                        circularSeekBar.setProgress(response.body().getData().getPaidPercent());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
                    tvPaymentPaid.setText("0 / " + "$ " + response.body().getData().getTotalpayment());

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

    @OnClick({R.id.mcvPayment, R.id.tvRecentTransaction})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mcvPayment:
                ConnectPaymentClick.setMyBoolean(true);
                break;
            case R.id.tvRecentTransaction:
                ConnectParticipantCostsClick.setMyBoolean(true);
                break;

        }

    }

    @Override
    public void onPaymentAdddCallBack() {
        getPaymentExpenses();
        Toast.makeText(getActivity(), "payment added", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            addPaymentCallBackListener = (AddPaymentCallBackListener) context;
//        } catch (Exception e) {
//
//        }
//
//    }
}