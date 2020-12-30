package com.techease.groupiiapplication.ui.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetail.Active;
import com.techease.groupiiapplication.dataModel.tripDetail.Past;
import com.techease.groupiiapplication.dataModel.tripDetail.TripDetailResponse;
import com.techease.groupiiapplication.dataModel.tripDetail.Upcoming;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepOneInviteFriendActivity;
import com.techease.groupiiapplication.ui.fragment.SettingsFragment;
import com.techease.groupiiapplication.ui.fragment.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    @BindView(R.id.llTrip)
    LinearLayout llTrip;

    @BindView(R.id.ivSettings)
    ImageView ivSettings;
    @BindView(R.id.tvSettings)
    TextView tvSettings;


    @BindView(R.id.ivTrip)
    ImageView ivTrip;
    @BindView(R.id.tvTrip)
    TextView tvTrip;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    Dialog dialog;



    final Fragment fragmentTrip = new TripFragment();
    final Fragment fragmentSettings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        fm.beginTransaction().add(R.id.container, fragmentSettings, "2").hide(fragmentSettings).commit();
        fm.beginTransaction().add(R.id.container, fragmentTrip, "1").commit();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.llSettings, R.id.llTrip, R.id.fab})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.llTrip:

                fm.beginTransaction().hide(active).show(fragmentTrip).commit();
                active = fragmentTrip;

                tvTrip.setTextColor(getColor(R.color.purple_500));
                Picasso.get().load(R.mipmap.my_trips_selected).into(ivSettings);
                tvSettings.setTextColor(getColor(R.color.gry));
                Picasso.get().load(R.mipmap.settings).into(ivSettings);
                break;

            case R.id.llSettings:

                tvSettings.setTextColor(getColor(R.color.purple_500));
                Picasso.get().load(R.mipmap.settings_selected).into(ivSettings);
                tvTrip.setTextColor(getColor(R.color.gry));

                fm.beginTransaction().hide(active).show(fragmentSettings).commit();
                active = fragmentSettings;
                break;

            case R.id.fab:
                startActivity(new Intent(HomeActivity.this, NewTripStepOneInviteFriendActivity.class), ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle());

                break;

        }
    }







}