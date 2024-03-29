package com.techease.groupiiapplication.ui.fragment.tripes;

import static com.techease.groupiiapplication.utils.Constants.aBooleanRefreshAllTripApi;

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
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.dataModel.getAllTrip.Active;
import com.techease.groupiiapplication.dataModel.getAllTrip.Past;
import com.techease.groupiiapplication.dataModel.getAllTrip.TripDetailResponse;
import com.techease.groupiiapplication.dataModel.getAllTrip.Unpublish;
import com.techease.groupiiapplication.dataModel.getAllTrip.Upcoming;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.interfaceClass.addGalleryPhoto.ConnectSearch;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Constants;

import java.util.ArrayList;
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

    public static ViewPager viewPagerTrip;
    public static List<Active> activeList = new ArrayList<>();
    public static List<Past> pastList = new ArrayList<>();
    public static List<Upcoming> upcomingList = new ArrayList<>();
    public static List<Unpublish> unpublishList = new ArrayList<>();

    public static List<User> userList = new ArrayList<>();

    private MyClassListener mListener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip, container, false);
        dialog = AlertUtils.createProgressDialog(getActivity());
        ButterKnife.bind(this, view);
        viewPagerTrip = viewPager;


        aBooleanRefreshAllTripApi = true;
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search a trip...");
        setProfileImageAndName();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                ConnectSearch.setMySearch(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                if (queryString != null) {
                    ConnectSearch.setMySearch(queryString);
                }
                return false;
            }
        });


        return view;
    }

    public interface MyClassListener {
        public void onSomeEvent(String myString);
    }

    public void setMyClassListener(MyClassListener listener) {
        this.mListener = listener;
    }


    private void setProfileImageAndName() {
        if (AppRepository.mUserName(getActivity()).length() > 0) {
            tvProfileName.setText("Hi, " + AppRepository.mUserName(getActivity()));
        }

        if (AppRepository.mUserProfileImage(getActivity()).length() > 0) {
            Picasso.get().load(AppRepository.mUserProfileImage(getActivity())).placeholder(R.drawable.user).into(ivProfile);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new ActiveFragment(), "Active");
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        adapter.addFragment(new PastFragment(), "Past");
//        adapter.addFragment(new UnPublishFragment(), "G Rec");
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
        if (Constants.aBooleanAddedTripApi) {
            apiCallGetTripDetail();


        } else if (aBooleanRefreshAllTripApi) {
            apiCallGetTripDetail();
            aBooleanRefreshAllTripApi = false;
        }
    }


    private void apiCallGetTripDetail() {
        dialog.show();
        activeList.clear();
        pastList.clear();
        upcomingList.clear();
        unpublishList.clear();
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
                    unpublishList.addAll(response.body().getData().getUnpublish());
//                    Collections.reverse(activeList);
//                    Collections.reverse(upcomingList);
//                    Collections.reverse(pastList);
                    setupViewPager(viewPager);
                    tabLayout.setupWithViewPager(viewPager);



                    if (Constants.aBooleanAddedTripApi) {
                        Constants.aBooleanAddedTripApi = false;

                        if (upcomingList.size() > 0) {
                            viewPager.setCurrentItem(1);
                        }
                        ConnectChatResfresh.setMyBoolean(true);

                    }else {

                        if (upcomingList.size() > 0) {
                            viewPager.setCurrentItem(1);
                        }
                   }

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
//                setAnimation();
                startActivity(new Intent(getActivity(), ProfileActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
        }
    }

}