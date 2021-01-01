package com.techease.groupiiapplication.ui.fragment.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.PastTripAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.fragment.TripFragment.pastList;


public class PastFragment extends Fragment {


    LinearLayoutManager linearLayoutManager;
    PastTripAdapter pastTripDetailAdapter;
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    View view;

    @BindView(R.id.tvNoPastTripFound)
    TextView tvNoPastTripFound;

    //region newInstance
    public static Fragment newInstance() {
        return new PastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_past, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }


    private void initAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        pastTripDetailAdapter = new PastTripAdapter(getActivity(), pastList);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvTripDetail.setAdapter(pastTripDetailAdapter);
        pastTripDetailAdapter.notifyDataSetChanged();

        if (pastList.size()==0){
            tvNoPastTripFound.setVisibility(View.VISIBLE);
        }else {
            tvNoPastTripFound.setVisibility(View.GONE);
        }
    }

}

