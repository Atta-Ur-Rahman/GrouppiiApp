package com.techease.groupiiapplication.ui.fragment.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.ActiveTripAdapter;
import com.techease.groupiiapplication.interfaceClass.ConnectSearch;
import com.techease.groupiiapplication.interfaceClass.ConnectionSearchChangedListener;
import com.techease.groupiiapplication.ui.fragment.TripFragment;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.fragment.TripFragment.activeList;

public class ActiveFragment extends Fragment {

    View view;
    LinearLayoutManager linearLayoutManager;
    ActiveTripAdapter activeTripDetailAdapter;
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;

    @BindView(R.id.tvNoActiveTripFound)
    TextView tvNoActiveTripFound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active, container, false);
        ButterKnife.bind(this, view);
        initAdapter();

        return view;
    }


    private void initAdapter() {


        linearLayoutManager = new LinearLayoutManager(getActivity());
        activeTripDetailAdapter = new ActiveTripAdapter(getActivity(), activeList);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvTripDetail.setAdapter(activeTripDetailAdapter);
        rvTripDetail.setNestedScrollingEnabled(true);
        Collections.reverse(activeList);
        activeTripDetailAdapter.notifyDataSetChanged();

        ConnectSearch.addGalleryPhotoListener(new ConnectionSearchChangedListener() {
            @Override
            public void OnMySearching() {
                activeTripDetailAdapter.getFilter().filter(ConnectSearch.getMySearch());

            }
        });



        if (activeList.size()==0){
            tvNoActiveTripFound.setVisibility(View.VISIBLE);
        }else {
            tvNoActiveTripFound.setVisibility(View.GONE);
        }
    }


}