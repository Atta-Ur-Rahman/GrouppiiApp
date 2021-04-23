package com.techease.groupiiapplication.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.NotificationAdapter;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.utils.AlertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityFragment extends Fragment {

    @BindView(R.id.rvNotificationList)
    RecyclerView rvNotificationList;


    LinearLayoutManager linearLayoutManager;
    NotificationAdapter notificationAdapter;
    List<AddTripDataModel> addTripDataModels = new ArrayList<>();
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this, view);


        initAdapter();


        return view;
    }

    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        dialog = AlertUtils.createProgressDialog(getActivity());
        notificationAdapter = new NotificationAdapter((getActivity()), addTripDataModels);
        rvNotificationList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvNotificationList.setAdapter(notificationAdapter);
    }
}