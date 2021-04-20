package com.techease.groupiiapplication.ui.fragment.trip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.ActiveTripAdapter;
import com.techease.groupiiapplication.adapter.UnPublishTripAdapter;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.fragment.TripFragment.activeList;


public class UnPublishFragment extends Fragment {


    LinearLayoutManager linearLayoutManager;
    UnPublishTripAdapter unPublishTripAdapter;
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;

    @BindView(R.id.tvNoUnPublishTripFound)
    TextView tvNoUnPublishTripFound;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_un_publish, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


//    private void initAdapter() {
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        activeTripDetailAdapter = new ActiveTripAdapter(getActivity(), activeList);
//        rvTripDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
//        rvTripDetail.setAdapter(activeTripDetailAdapter);
//        rvTripDetail.setNestedScrollingEnabled(true);
//        Collections.reverse(activeList);
//        activeTripDetailAdapter.notifyDataSetChanged();
//
//        if (activeList.size()==0){
//            tvNoActiveTripFound.setVisibility(View.VISIBLE);
//        }else {
//            tvNoActiveTripFound.setVisibility(View.GONE);
//        }
//    }

}