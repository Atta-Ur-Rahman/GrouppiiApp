package com.techease.groupiiapplication.ui.activity.AddTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.deleteTripUser.DeleteTripUserResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;

import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditParticipantActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tilName)
    TextInputLayout tilName;
    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.tvEditParticipant)
    TextView tvEditParticipant;

    @BindView(R.id.tvEditParticipantTitle)
    TextView tvEditParticipantTitle;
    @BindView(R.id.tvSteps)
    TextView tvSteps;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tvUpdateParticipant)
    TextView tvUpdateParticipant;

    @BindView(R.id.cbShareTripCost)
    CheckBox cbShareCost;
    boolean valid, aBooleanIsTripDetailScreen;

    String strUserID,strName, strEmail, strPhoneNumber, strTripId, strSharedCost = "0";
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_participant);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        strUserID = bundle.getString("userId");
        strName = bundle.getString("name");
        strEmail = bundle.getString("email");
        strPhoneNumber = bundle.getString("phone");
        strTripId = bundle.getString("trip_id");
        strSharedCost = bundle.getString("shared_cost");
        aBooleanIsTripDetailScreen = bundle.getBoolean("aBooleanIsTripDetailScreen");

        etName.setText(strName);
        etEmail.setText(strEmail);
        etPhone.setText(strPhoneNumber);
        if (strSharedCost.equals("1")) {
            cbShareCost.setChecked(true);
        } else {
            cbShareCost.setChecked(false);

        }
        if (aBooleanIsTripDetailScreen) {
            tvEditParticipant.setVisibility(View.GONE);
            tvSteps.setVisibility(View.GONE);
            tvEditParticipantTitle.setText(R.string.edit_participant);
            progressBar.setVisibility(View.GONE);
        }

        cbShareCost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strSharedCost = "1";
                } else {
                    strSharedCost = "0";
                }
            }
        });


    }

    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
        strName = etName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString();

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    private void ApiCallForEditParticipants() {
        dialog.show();
        NewTripStepOneInviteFriendActivity.addTripDataModels.clear();
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTrip(strName, strEmail, strPhoneNumber, strSharedCost,
                strTripId, AppRepository.mUserID(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma response", String.valueOf(response.message()));
                    Toast.makeText(EditParticipantActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        etEmail.setText("");
                        etPhone.setText("");
                        etName.setText("");
                        cbShareCost.setChecked(false);

                        if (aBooleanIsTripDetailScreen) {
                            TripDetailScreenActivity.userParticipaintsList.clear();
                            TripDetailScreenActivity.userParticipaintsList.addAll(response.body().getData());
                            TripDetailScreenActivity.tripParticipantsAdapter.notifyDataSetChanged();
                            finish();
                        } else {
                            NewTripStepOneInviteFriendActivity.addTripDataModels.addAll(response.body().getData());
                            NewTripStepOneInviteFriendActivity.addTripAdapter.notifyDataSetChanged();
                            finish();
                        }
                    }

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(EditParticipantActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.tvUpdateParticipant, R.id.tvDeleteParticipants, R.id.ivBack})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvUpdateParticipant:

                if (isValid()) {
                    ApiCallForEditParticipants();
                }
                break;
            case R.id.tvDeleteParticipants:
                ApiCallForTripUser();
                break;

            case R.id.ivBack:
                finish();
                break;
        }
    }

    private void ApiCallForTripUser() {
        dialog.show();
        Call<DeleteTripUserResponse> deleteTripUserResponseCall = BaseNetworking.ApiInterface().deleteTripUser(strTripId, strUserID);
        deleteTripUserResponseCall.enqueue(new Callback<DeleteTripUserResponse>() {
            @Override
            public void onResponse(Call<DeleteTripUserResponse> call, Response<DeleteTripUserResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    ApiCallGetUserTrip();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteTripUserResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void ApiCallGetUserTrip() {
        dialog.show();
        Call<AddTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripId(this));
        getGalleryPhotoResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    if (aBooleanIsTripDetailScreen) {
                        TripDetailScreenActivity.userParticipaintsList.clear();
                        TripDetailScreenActivity.userParticipaintsList.addAll(response.body().getData());
                        TripDetailScreenActivity.tripParticipantsAdapter.notifyDataSetChanged();
                        finish();
                    } else {

                        NewTripStepOneInviteFriendActivity.addTripDataModels.clear();
                        NewTripStepOneInviteFriendActivity.addTripDataModels.addAll(response.body().getData());
                        NewTripStepOneInviteFriendActivity.addTripAdapter.notifyDataSetChanged();
                        finish();
                    }


                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(EditParticipantActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}