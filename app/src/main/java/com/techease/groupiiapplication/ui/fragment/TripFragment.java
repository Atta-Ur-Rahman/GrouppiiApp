package com.techease.groupiiapplication.ui.fragment;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.TabsViewPagerAdapter;
import com.techease.groupiiapplication.dataModel.tripDetail.Active;
import com.techease.groupiiapplication.dataModel.tripDetail.Past;
import com.techease.groupiiapplication.dataModel.tripDetail.TripDetailResponse;
import com.techease.groupiiapplication.dataModel.tripDetail.Upcoming;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.ui.fragment.trip.ActiveFragment;
import com.techease.groupiiapplication.ui.fragment.trip.PastFragment;
import com.techease.groupiiapplication.ui.fragment.trip.UpcomingFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TripFragment extends Fragment implements View.OnClickListener {

    View view;
    Dialog dialog;
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public static boolean aBooleanRefreshApi = true;
    public static ViewPager viewPagerTrip;
    public static List<Active> activeList = new ArrayList<>();
    public static List<Past> pastList = new ArrayList<>();
    public static List<Upcoming> upcomingList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip, container, false);
        dialog = AlertUtils.createProgressDialog(getActivity());
        ButterKnife.bind(this, view);
        viewPagerTrip = viewPager;

        aBooleanRefreshApi = true;

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search a trip...");

        setProfileImageAndName();

//        apiCallGetTripDetail();

        return view;
    }

    private void setProfileImageAndName() {
        if (AppRepository.mUserName(getActivity()).length() > 0) {
            tvProfileName.setText("Hi, " + AppRepository.mUserName(getActivity()));
        }

        if (AppRepository.mUserProfileImage(getActivity()).length() > 0) {
            Picasso.get().load(AppRepository.mUserProfileImage(getActivity())).placeholder(R.drawable.dubai).into(ivProfile);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new ActiveFragment(), "Active");
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        adapter.addFragment(new PastFragment(), "Past");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileImageAndName();
        if (aBooleanRefreshApi) {
            apiCallGetTripDetail();
            aBooleanRefreshApi = false;
        }
    }


    private void apiCallGetTripDetail() {
        dialog.show();
        activeList.clear();
        pastList.clear();
        upcomingList.clear();
        Call<TripDetailResponse> call = BaseNetworking.ApiInterface().getTripDetail(AppRepository.mUserID(getActivity()));
        call.enqueue(new Callback<TripDetailResponse>() {
            @Override
            public void onResponse(Call<TripDetailResponse> call, Response<TripDetailResponse> response) {

//                Log.d("zma", String.valueOf(response));
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    activeList.addAll(response.body().getData().getActive());
                    upcomingList.addAll(response.body().getData().getUpcoming());
                    pastList.addAll(response.body().getData().getPast());
//                    Collections.reverse(activeList);
//                    Collections.reverse(upcomingList);
                    Collections.reverse(pastList);
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TripDetailResponse> call, Throwable t) {
                dialog.dismiss();

                Log.d("zma response", String.valueOf(t.getMessage()));

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.ivProfile})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivProfile:
                startActivity(new Intent(getActivity(), ProfileActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
        }
    }

}