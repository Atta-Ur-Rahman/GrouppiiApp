package com.techease.groupiiapplication.ui.fragment.payment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.payment.FullyPaidAdapter;
import com.techease.groupiiapplication.adapter.payment.NoShareCostsAdapter;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DontShareCostsParticipantFragment extends Fragment {


    @BindView(R.id.rvDontShareCosts)
    RecyclerView rvDontShareCosts;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    NoShareCostsAdapter noShareCostsAdapter;


    public static DontShareCostsParticipantFragment newInstance() {
        DontShareCostsParticipantFragment fragment = new DontShareCostsParticipantFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_dont_share_costs_participant, container, false);
        ButterKnife.bind(this, parentView);

        noShareCostsAdapter = new NoShareCostsAdapter(getActivity(), PaymentsFragment.sharesNoCostArrayList);
        rvDontShareCosts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvDontShareCosts.setAdapter(noShareCostsAdapter);
        noShareCostsAdapter.notifyDataSetChanged();



        if (PaymentsFragment.fullPaidArrayList.size() == 0) {
            tvNotFound.setTransitionVisibility(View.VISIBLE);
        } else {
            tvNotFound.setVisibility(View.GONE);
        }


        return parentView;
    }
}