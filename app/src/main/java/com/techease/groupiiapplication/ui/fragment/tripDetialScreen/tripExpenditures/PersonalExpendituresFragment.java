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

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.GroupExpendituresAdapter;
import com.techease.groupiiapplication.adapter.tripDetail.PersonalExpendituresAdapter;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectionBooleanExpendituresListener;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity.groupExpendituresItems;
import static com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity.personalExpendituresItems;

public class PersonalExpendituresFragment extends Fragment {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvPersonalExpenditureTrip)
    RecyclerView rvPersonalExpenditureTrip;


    LinearLayoutManager linearLayoutManager;
    PersonalExpendituresAdapter personalExpendituresAdapter;

    @BindView(R.id.tvNoTripFound)
    TextView tvNoTripFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_expenditures, container, false);
        ButterKnife.bind(this, view);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        personalExpendituresAdapter = new PersonalExpendituresAdapter(getActivity(), personalExpendituresItems);
        rvPersonalExpenditureTrip.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPersonalExpenditureTrip.setAdapter(personalExpendituresAdapter);
        Collections.reverse(personalExpendituresItems);




        ConnectExpenditures.addClickListener(new ConnectionBooleanExpendituresListener() {
            @Override
            public void OnMyBooleanListener() {
                personalExpendituresAdapter.notifyDataSetChanged();

                if (personalExpendituresItems.size() == 0) {
                    tvNoTripFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoTripFound.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}