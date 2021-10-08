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
import com.techease.groupiiapplication.adapter.payment.RecentTransctionAdapter;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullPaidParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullPaidParticipantFragment extends Fragment {

    @BindView(R.id.rvFullyPaid)
    RecyclerView rvFullyPaid;
    @BindView(R.id.tvNotFound)
    TextView tvNotFound;
    FullyPaidAdapter fullyPaidAdapter;

    public static FullPaidParticipantFragment newInstance(String param1, String param2) {
        FullPaidParticipantFragment fragment = new FullPaidParticipantFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_full_paid_participant, container, false);
        ButterKnife.bind(this, parentView);


        fullyPaidAdapter = new FullyPaidAdapter(getActivity(), PaymentsFragment.fullPaidArrayList);
        rvFullyPaid.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvFullyPaid.setAdapter(fullyPaidAdapter);
        fullyPaidAdapter.notifyDataSetChanged();


        if (PaymentsFragment.fullPaidArrayList.size() == 0) {
            tvNotFound.setTransitionVisibility(View.VISIBLE);
        } else {
            tvNotFound.setVisibility(View.GONE);
        }


        return parentView;
    }
}