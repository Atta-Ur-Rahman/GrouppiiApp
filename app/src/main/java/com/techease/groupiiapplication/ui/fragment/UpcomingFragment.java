package com.techease.groupiiapplication.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.UpcomingTripAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.fragment.TripFragment.upcomingList;


public class UpcomingFragment extends Fragment {


    LinearLayoutManager linearLayoutManager;
    UpcomingTripAdapter upcommingTripDetailAdapter;
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    View view;

    @BindView(R.id.tvNoUpComingTripFound)
    TextView tvNoUpComingTripFound;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcomming, container, false);
        ButterKnife.bind(this,view);
        initAdapter();
        return view;
    }
    private void initAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        upcommingTripDetailAdapter = new UpcomingTripAdapter(getActivity(), upcomingList);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvTripDetail.setAdapter(upcommingTripDetailAdapter);
        upcommingTripDetailAdapter.notifyDataSetChanged();

        if (upcomingList.size()==0){
            tvNoUpComingTripFound.setVisibility(View.VISIBLE);
        }else {
            tvNoUpComingTripFound.setVisibility(View.GONE);
        }
    }

}