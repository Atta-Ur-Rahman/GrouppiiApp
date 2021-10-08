package com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures;

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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.GroupExpendituresAdapter;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectionBooleanExpendituresListener;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity.groupExpendituresItems;


public class GropExpendituresTripFragment extends Fragment {


    @BindView(R.id.rvGroupExpenditureTrip)
    RecyclerView rvGroupExpenditure;

    @BindView(R.id.tvNoTripFound)
    TextView tvNoTripFound;
    LinearLayoutManager linearLayoutManager;
    GroupExpendituresAdapter tripExpendituresAdapter;
//    public static List<GroupExpendituresItem> groupExpendituresItems = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grop_expenditures_trip, container, false);
        ButterKnife.bind(this, view);


        Log.d("zma expenditures", "" + groupExpendituresItems);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        tripExpendituresAdapter = new GroupExpendituresAdapter(getActivity(), groupExpendituresItems);
        rvGroupExpenditure.setLayoutManager(linearLayoutManager);
        rvGroupExpenditure.setAdapter(tripExpendituresAdapter);

        ConnectExpenditures.addClickListener(new ConnectionBooleanExpendituresListener() {
            @Override
            public void OnMyBooleanListener() {
                tripExpendituresAdapter.notifyDataSetChanged();

                if (groupExpendituresItems.size() == 0) {
                    tvNoTripFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoTripFound.setVisibility(View.GONE);
                }

            }
        });
        getPaymentExpenses();


        return view;

    }


    private void getPaymentExpenses() {

        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripId(getActivity()), AppRepository.mUserID(getActivity()));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
//                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
//                    Collections.reverse(groupExpendituresItems);
//                    tripExpendituresAdapter.notifyDataSetChanged();

//                    Log.d("zma payment response", "" + response.body().getData().getGroupExpenditures());

                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}