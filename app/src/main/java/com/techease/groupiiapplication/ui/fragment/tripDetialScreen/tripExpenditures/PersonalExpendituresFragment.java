package com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalExpendituresFragment extends Fragment {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvPersonalExpenditureTrip)
    RecyclerView rvPersonalExpenditureTrip;


    @BindView(R.id.tvNoTripFound)
    TextView tvNoTripFound;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_expenditures, container, false);
        ButterKnife.bind(this,view);

//        if (PaymentsFragment.groupExpendituresItems.size()==0){
            tvNoTripFound.setVisibility(View.VISIBLE);
//        }else {
//            tvNoTripFound.setVisibility(View.GONE);
//        }

        return view;
    }
}