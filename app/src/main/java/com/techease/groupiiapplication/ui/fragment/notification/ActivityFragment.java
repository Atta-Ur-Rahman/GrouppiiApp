package com.techease.groupiiapplication.ui.fragment.notification;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.notification.NotificationAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.notifications.GetNotificationsResponse;
import com.techease.groupiiapplication.dataModel.notifications.NotificationsDataItem;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragment extends Fragment {

    @BindView(R.id.rvNotificationList)
    RecyclerView rvNotificationList;

    @BindView(R.id.tvNotificationsNotFound)
    TextView tvNotificationsNotFound;


    LinearLayoutManager linearLayoutManager;
    NotificationAdapter notificationAdapter;
    Dialog dialog;

    ArrayList<NotificationsDataItem> notificationsDataItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this, view);

        initAdapter();
        getNotifications();


        return view;
    }

    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        dialog = AlertUtils.createProgressDialog(getActivity());
        notificationAdapter = new NotificationAdapter((getActivity()), notificationsDataItems);
        rvNotificationList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvNotificationList.setAdapter(notificationAdapter);
    }


    private void getNotifications() {
        Call<GetNotificationsResponse> getNotificationsResponseCall = BaseNetworking.ApiInterface().getAllNotification("users/getnotifications/" + AppRepository.mUserID(getActivity()));
        getNotificationsResponseCall.enqueue(new Callback<GetNotificationsResponse>() {
            @Override
            public void onResponse(Call<GetNotificationsResponse> call, Response<GetNotificationsResponse> response) {
                if (response.isSuccessful()) {

                    notificationsDataItems.clear();
                    notificationsDataItems.addAll(response.body().getData());
                    Collections.reverse(notificationsDataItems);
                    notificationAdapter.notifyDataSetChanged();

                    if (notificationsDataItems.size() == 0) {
                        tvNotificationsNotFound.setVisibility(View.VISIBLE);
                    } else {
                        tvNotificationsNotFound.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNotificationsResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getNotifications();
    }
}