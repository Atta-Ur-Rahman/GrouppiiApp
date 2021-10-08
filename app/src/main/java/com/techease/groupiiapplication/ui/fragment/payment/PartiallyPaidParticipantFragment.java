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
import com.techease.groupiiapplication.adapter.payment.PartiallyPaidAdapter;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartiallyPaidParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartiallyPaidParticipantFragment extends Fragment {


    @BindView(R.id.rvPartiallyPiad)
    RecyclerView rvPartiallyPaid;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    PartiallyPaidAdapter partiallyPaidAdapter;

    public static PartiallyPaidParticipantFragment newInstance(String param1, String param2) {
        PartiallyPaidParticipantFragment fragment = new PartiallyPaidParticipantFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_partially_paid_participant, container, false);
        ButterKnife.bind(this, parentView);


        partiallyPaidAdapter = new PartiallyPaidAdapter(getActivity(), PaymentsFragment.partialPaidArrayList);
        rvPartiallyPaid.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvPartiallyPaid.setAdapter(partiallyPaidAdapter);
        partiallyPaidAdapter.notifyDataSetChanged();


        if (PaymentsFragment.fullPaidArrayList.size() == 0) {
            tvNotFound.setTransitionVisibility(View.VISIBLE);
        } else {
            tvNotFound.setVisibility(View.GONE);
        }


        return parentView;
    }
}