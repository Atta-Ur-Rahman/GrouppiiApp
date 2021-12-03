package com.techease.groupiiapplication.ui.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.updateUserProfile.UpdateUserProfileResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepOneInviteFriendActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.SignUpActivity;
import com.techease.groupiiapplication.ui.activity.profile.EditProfileActivity;
import com.techease.groupiiapplication.ui.fragment.SearchFragment;
import com.techease.groupiiapplication.ui.fragment.notification.ActivityFragment;
import com.techease.groupiiapplication.ui.fragment.profile.SettingsFragment;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.ui.fragment.chat.AllUsersChatFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GPSTracker;
import com.techease.groupiiapplication.utils.StringHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.llTrip)
    LinearLayout llTrip;
    @BindView(R.id.ivTrip)
    ImageView ivTrip;
    @BindView(R.id.tvTrip)
    TextView tvTrip;

    @BindView(R.id.llChat)
    LinearLayout llChat;
    @BindView(R.id.ivChat)
    ImageView ivChat;
    @BindView(R.id.tvChat)
    TextView tvChat;

    @BindView(R.id.llActivity)
    LinearLayout llActivity;
    @BindView(R.id.ivActivity)
    ImageView ivActivity;
    @BindView(R.id.tvActivity)
    TextView tvActivity;


    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    @BindView(R.id.ivSettings)
    ImageView ivSettings;
    @BindView(R.id.tvSettings)
    TextView tvSettings;


    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    Dialog dialog;

    String strLatitude, strLongitude;

    final Fragment fragmentTrip = new TripFragment();
    final Fragment fragmentChatActivity = new AllUsersChatFragment();
    final Fragment fragmentActivity = new ActivityFragment();
    final Fragment fragmentSettings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentTrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);


//        TransformationCompat.onTransformationStartContainer(this);

        fm.beginTransaction().add(R.id.container, fragmentChatActivity, "4").hide(fragmentChatActivity).commit();
        fm.beginTransaction().add(R.id.container, fragmentActivity, "3").hide(fragmentActivity).commit();
        fm.beginTransaction().add(R.id.container, fragmentSettings, "2").hide(fragmentSettings).commit();
        fm.beginTransaction().add(R.id.container, fragmentTrip, "1").commit();

        try {
            if (AppRepository.mLat(this).equals("null") || AppRepository.mLng(this).equals("null")) {
                getCurrentLocationPermission();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getCurrentLocationPermission() {
        Dexter.withContext(this).withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                GPSTracker gpsTracker = new GPSTracker(HomeActivity.this);

                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    double latitude = gpsTracker.latitude;
                    double longitude = gpsTracker.longitude;
                    strLatitude = String.valueOf(latitude);
                    strLongitude = String.valueOf(longitude);
                    ApiCallForUpdateLatLng();

                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

            }
        }).check();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.llTrip, R.id.llChat, R.id.llActivity, R.id.llSettings, R.id.fab})
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
                tvChat.setTextColor(getColor(R.color.gry));
                tvActivity.setTextColor(getColor(R.color.gry));
                tvSettings.setTextColor(getColor(R.color.gry));

                ivTrip.setImageResource(R.mipmap.my_trips_selected);
                ivChat.setImageResource(R.mipmap.chats);
                ivActivity.setImageResource(R.mipmap.activity);
                ivSettings.setImageResource(R.mipmap.settings);


                break;

            case R.id.llChat:
                tvTrip.setTextColor(getColor(R.color.gry));
                tvChat.setTextColor(getColor(R.color.purple_500));
                tvActivity.setTextColor(getColor(R.color.gry));
                tvSettings.setTextColor(getColor(R.color.gry));

                ivTrip.setImageResource(R.mipmap.my_trip_unselected);
                ivChat.setImageResource(R.mipmap.chat_selected);
                ivActivity.setImageResource(R.mipmap.activity);
                ivSettings.setImageResource(R.mipmap.settings);


//                fm.beginTransaction().add(R.id.container, fragmentTrip, "4").hide(fragmentTrip).commit();
//                fm.beginTransaction().add(R.id.container, fragmentChatActivity, "3").commit();

                fm.beginTransaction().hide(active).show(fragmentChatActivity).commit();
                active = fragmentChatActivity;

                break;
            case R.id.llActivity:

                tvTrip.setTextColor(getColor(R.color.gry));
                tvChat.setTextColor(getColor(R.color.gry));
                tvActivity.setTextColor(getColor(R.color.purple_500));
                tvSettings.setTextColor(getColor(R.color.gry));

                ivTrip.setImageResource(R.mipmap.my_trip_unselected);
                ivChat.setImageResource(R.mipmap.chats);
                ivActivity.setImageResource(R.mipmap.activity_selected);
                ivSettings.setImageResource(R.mipmap.settings);


                fm.beginTransaction().hide(active).show(fragmentActivity).commit();
                active = fragmentActivity;

                break;
            case R.id.llSettings:

                tvTrip.setTextColor(getColor(R.color.gry));
                tvChat.setTextColor(getColor(R.color.gry));
                tvActivity.setTextColor(getColor(R.color.gry));
                tvSettings.setTextColor(getColor(R.color.purple_500));

                ivTrip.setImageResource(R.mipmap.my_trip_unselected);
                ivChat.setImageResource(R.mipmap.chats);
                ivActivity.setImageResource(R.mipmap.activity);
                ivSettings.setImageResource(R.mipmap.settings_selected);

                fm.beginTransaction().hide(active).show(fragmentSettings).commit();
                active = fragmentSettings;
                break;

            case R.id.fab:

                Intent intent = new Intent(HomeActivity.this, NewTripStepOneInviteFriendActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(this);

                break;

        }
    }

    private void ApiCallForUpdateLatLng() {
        dialog.show();
        Call<UpdateUserProfileResponse> updateProfilePicResponseCall = BaseNetworking.ApiInterface().updateProfileLatLng(AppRepository.mUserID(this) + "", AppRepository.mUserName(this), AppRepository.mEmail(this), "description", AppRepository.mPhoneNumber(this), strLatitude, strLongitude);
        updateProfilePicResponseCall.enqueue(new Callback<UpdateUserProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateUserProfileResponse> call, Response<UpdateUserProfileResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(HomeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("zmaerror", jsonObject.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserProfileResponse> call, Throwable t) {
                dialog.dismiss();
                Log.d("zmaerror", t.getMessage());
                Toast.makeText(HomeActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        StringHelper.checkFirebase = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StringHelper.checkFirebase = false;

    }
}