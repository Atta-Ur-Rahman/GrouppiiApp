package com.techease.groupiiapplication.ui.fragment.tripes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.UnPublishTripAdapter;
import com.techease.groupiiapplication.interfaceClass.ConnectSearch;
import com.techease.groupiiapplication.interfaceClass.ConnectionSearchChangedListener;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        initAdapter();

        return view;
    }


    private void initAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        unPublishTripAdapter = new UnPublishTripAdapter(getActivity(), TripFragment.unpublishList);
        rvTripDetail.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvTripDetail.setAdapter(unPublishTripAdapter);
        rvTripDetail.setNestedScrollingEnabled(true);
        Collections.reverse(TripFragment.unpublishList);
        unPublishTripAdapter.notifyDataSetChanged();

        ConnectSearch.addGalleryPhotoListener(new ConnectionSearchChangedListener() {
            @Override
            public void OnMySearching() {
                unPublishTripAdapter.getFilter().filter(ConnectSearch.getMySearch());

            }
        });


        if (TripFragment.unpublishList.size() == 0) {
            tvNoUnPublishTripFound.setVisibility(View.VISIBLE);
        } else {
            tvNoUnPublishTripFound.setVisibility(View.GONE);
        }
    }

}