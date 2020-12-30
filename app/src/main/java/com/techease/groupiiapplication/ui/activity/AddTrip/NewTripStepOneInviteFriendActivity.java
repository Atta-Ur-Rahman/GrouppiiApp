package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.AddTripAdapter;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTripStepOneInviteFriendActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.clInviteFriend)
    ConstraintLayout clInviteFriend;
    @BindView(R.id.clAddInvite)
    ConstraintLayout clAddInvite;
    @BindView(R.id.ivAddUserTrip)
    ImageView ivAddUserTrip;


    @BindView(R.id.rvInviteFriend)
    RecyclerView rvInviteFriend;

    boolean valid;
    String strEmail, strPhoneNumber, strShareCost = "0";

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.tvSendInviteFriend)
    TextView tvSendInviteFriend;

    @BindView(R.id.cbShareTripCost)
    CheckBox cbShareCost;

    List<AddTripDataModel> addTripDataModels = new ArrayList<>();

    Dialog dialog;

    @BindView(R.id.tvInviteFriendNotFound)
    TextView tvInviteFriendNotFound;


    LinearLayoutManager linearLayoutManager;
    AddTripAdapter addTripAdapter;


    @BindView(R.id.btnNext)
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_one);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        tvInviteFriendNotFound.setVisibility(View.VISIBLE);


        apiCallGetTripID();

        cbShareCost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strShareCost = "1";
                } else {
                    strShareCost = "0";
                }
            }
        });

    }

    private void apiCallGetTripID() {
        dialog.show();
        Call<CreateTripResponse> createTripResponseCall = BaseNetworking.ApiInterface().getTripID(AppRepository.mUserID(this));
        createTripResponseCall.enqueue(new Callback<CreateTripResponse>() {
            @Override
            public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("zma tripid", String.valueOf(response.body().getTripid()));
                    dialog.dismiss();
                    AppRepository.mPutValue(NewTripStepOneInviteFriendActivity.this).putString("tripID", String.valueOf(response.body().getTripid())).commit();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CreateTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.ivBack, R.id.ivAddUserTrip, R.id.clAddInvite, R.id.clInviteFriend, R.id.btnNext, R.id.tvSendInviteFriend})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:
                if (clInviteFriend.getVisibility() == View.VISIBLE) {
                    clAddInvite.setVisibility(View.VISIBLE);
                    clInviteFriend.setVisibility(View.GONE);

                } else {
                    onBackPressed();
                    apiCallForTripDelete();
                }

                break;

            case R.id.tvSendInviteFriend:

                if (isValid()) {
                    ApiCallForAddInviteFriend();
                }
                break;
            case R.id.ivAddUserTrip:
                clAddInvite.setVisibility(View.GONE);
                clInviteFriend.setVisibility(View.VISIBLE);
                break;
            case R.id.btnNext:
                TripFragment.aBooleanRefreshApi = false;
                startActivity(new Intent(this, NewTripStepTwoAddDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation(NewTripStepOneInviteFriendActivity.this).toBundle());
                this.finish();

                break;

        }
    }

    private void ApiCallForAddInviteFriend() {
        dialog.show();
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTrip(strEmail, strPhoneNumber, strShareCost, AppRepository.mTripId(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma response", String.valueOf(response.message()));
                    Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        clAddInvite.setVisibility(View.VISIBLE);
                        clInviteFriend.setVisibility(View.GONE);
                        etEmail.setText("");
                        etPhone.setText("");
                        cbShareCost.setChecked(false);
                        addTripDataModels.addAll(response.body().getData());
                        initAdapter();
                    }

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString();

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
        }
        if (strPhoneNumber.isEmpty()) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.plesase_write_your_phone_number));

        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        apiCallForTripDelete();
    }

    private void apiCallForTripDelete() {
        Call<DeleteTripResponse> deleteTripResponseCall = BaseNetworking.ApiInterface().deleteTrip(AppRepository.mTripId(this));
        deleteTripResponseCall.enqueue(new Callback<DeleteTripResponse>() {
            @Override
            public void onResponse(Call<DeleteTripResponse> call, Response<DeleteTripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("zma trip", "delete sho");
//                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<DeleteTripResponse> call, Throwable t) {

                Log.d("zma trip", "delete " + t.getMessage());
//                onBackPressed();

            }
        });
    }


    private void initAdapter() {

        linearLayoutManager = new LinearLayoutManager(this);
        dialog = AlertUtils.createProgressDialog(this);
        addTripAdapter = new AddTripAdapter((this), addTripDataModels);
        rvInviteFriend.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvInviteFriend.setAdapter(addTripAdapter);
        rvInviteFriend.setNestedScrollingEnabled(true);
        Collections.reverse(addTripDataModels);
        addTripAdapter.notifyDataSetChanged();
        tvInviteFriendNotFound.setVisibility(View.GONE);
    }
}