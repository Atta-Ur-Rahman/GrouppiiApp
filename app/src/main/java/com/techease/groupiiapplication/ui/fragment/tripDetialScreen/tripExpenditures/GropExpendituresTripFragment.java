package com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.TripExpendituresAdapter;
import com.techease.groupiiapplication.adapter.tripes.ActiveTripAdapter;
import com.techease.groupiiapplication.dataModel.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.getPaymentExpenses.GroupExpendituresItem;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.utils.AppRepository;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techease.groupiiapplication.ui.fragment.tripes.TripFragment.activeList;

public class GropExpendituresTripFragment extends Fragment {


    @BindView(R.id.rvGroupExpenditureTrip)
    RecyclerView rvGroupExpenditure;

    @BindView(R.id.tvNoTripFound)
    TextView tvNoTripFound;
    LinearLayoutManager linearLayoutManager;
    TripExpendituresAdapter tripExpendituresAdapter;
    List<GroupExpendituresItem> groupExpendituresItems=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grop_expenditures_trip, container, false);
        ButterKnife.bind(this,view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        tripExpendituresAdapter = new TripExpendituresAdapter(getActivity(), groupExpendituresItems);
        rvGroupExpenditure.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvGroupExpenditure.setAdapter(tripExpendituresAdapter);
        Collections.reverse(groupExpendituresItems);


        if (groupExpendituresItems.size()==0){
            tvNoTripFound.setVisibility(View.VISIBLE);
        }else {
            tvNoTripFound.setVisibility(View.GONE);
        }
        getPaymentExpenses();

        return view;
    }


//
    private void getPaymentExpenses() {
//        dialog.show();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripId(getActivity()), AppRepository.mUserID(getActivity()));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
//                    dialog.dismiss();
                    assert response.body() != null;
//                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
//                    tripExpendituresAdapter.notifyDataSetChanged();


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
}