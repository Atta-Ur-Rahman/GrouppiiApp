package com.techease.groupiiapplication.ui.fragment.tripes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.PastTripAdapter;
import com.techease.groupiiapplication.interfaceClass.addGalleryPhoto.ConnectSearch;
import com.techease.groupiiapplication.interfaceClass.addGalleryPhoto.ConnectionSearchChangedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.techease.groupiiapplication.ui.fragment.tripes.TripFragment.activeList;
import static com.techease.groupiiapplication.ui.fragment.tripes.TripFragment.pastList;


public class PastFragment extends Fragment {


    LinearLayoutManager linearLayoutManager;
    PastTripAdapter pastTripDetailAdapter;
    @BindView(R.id.rvTripDetail)
    RecyclerView rvTripDetail;
    View view;

    SearchView searchView;
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
        rvTripDetail.setItemViewCacheSize(pastList.size());
        rvTripDetail.setAdapter(pastTripDetailAdapter);
        pastTripDetailAdapter.notifyDataSetChanged();
        rvTripDetail.setItemViewCacheSize(pastList.size());
        ConnectSearch.addGalleryPhotoListener(new ConnectionSearchChangedListener() {
            @Override
            public void OnMySearching() {
                pastTripDetailAdapter.getFilter().filter(ConnectSearch.getMySearch());

            }
        });

        if (pastList.size() == 0) {
            tvNoPastTripFound.setVisibility(View.VISIBLE);
        } else {
            tvNoPastTripFound.setVisibility(View.GONE);
        }
    }
}

