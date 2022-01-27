package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.CommonResponse;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.dataModel.loginSignup.login.LogInResponse;
import com.techease.groupiiapplication.dataModel.newLogin.LoginResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
import com.techease.groupiiapplication.ui.activity.SplashActivity;
import com.techease.groupiiapplication.ui.activity.profile.EditProfileActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.TripDetailScreenActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    boolean valid;
    String strEmail, strPassword;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;


    Dialog dialog;

    String fcmToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        dialog = AlertUtils.createProgressDialog(this);
        initTextWatcher();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("fetching_fcm_failed", String.valueOf(task.getException()));
                            return;
                        }

                        // Get new FCM registration token
                        fcmToken = task.getResult();

                        Log.d("zmatoken", fcmToken);

//                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();

        if (strEmail.isEmpty()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
        }
        if (strPassword.isEmpty() || strPassword.length() < 6) {
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getString(R.string.password_should_be_six));
            valid = false;
        } else {
            tilPassword.setError(null);
        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

    @OnClick({R.id.btnSignIn, R.id.tvSignUp, R.id.tvForgotPassword})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                if (isValid()) {
                    dialog.show();
                    ApiCallForSignIn();
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                break;
            case R.id.tvSignUp:
                Intent mainIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();

                break;

            case R.id.tvForgotPassword:

                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;

        }
    }

    private void ApiCallForSignIn() {

        Call<LoginResponse> logInResponseCall = BaseNetworking.ApiInterface().login(strEmail, strPassword, fcmToken);
        logInResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    if (response.body().isSuccess()) {
                        try {
                            AppRepository.mPutValue(LoginActivity.this).putString("mUserPassword", strPassword).commit();
                            AppRepository.mPutValue(LoginActivity.this).putInt("userID", Integer.parseInt(response.body().getData().getId() + "")).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("mUserName", String.valueOf(response.body().getData().getName())).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("mUserEmail", String.valueOf(response.body().getData().getEmail())).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("mProfilePicture", String.valueOf(response.body().getData().getPicture())).commit();
                            AppRepository.mPutValue(LoginActivity.this).putBoolean("loggedIn", true).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("lat", String.valueOf(response.body().getData().getLatitude())).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("lng", String.valueOf(response.body().getData().getLongitude())).commit();
                            AppRepository.mPutValue(LoginActivity.this).putString("mPhoneNumber", String.valueOf(response.body().getData().getPhone())).commit();


                            if (AppRepository.mShareTripId(LoginActivity.this).equals("linknotavailable")) {
                                Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                LoginActivity.this.startActivity(mainIntent);
                                LoginActivity.this.finishAffinity();
                            } else {
                                ApiCallAddUserToTrip(AppRepository.mShareTripId(LoginActivity.this));

                            }

                            Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.incorrect_password_email), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("zma login error", String.valueOf(t.getMessage()));
                Toast.makeText(LoginActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
    }

    private void ApiCallAddUserToTrip(String shareTripId) {
        Call<CommonResponse> addUserToTrip = BaseNetworking.ApiInterface().addUserToTrip("" + AppRepository.mUserID(this), shareTripId);
        addUserToTrip.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "You Added to this trip", Toast.LENGTH_SHORT).show();
                    GetTripById(shareTripId);
                    AppRepository.mPutValue(LoginActivity.this).putString("shareTripId", "linknotavailable").commit();

                } else {
                    Toast.makeText(LoginActivity.this, "Some went wrong please try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initTextWatcher() {

        etEmail.addTextChangedListener(new GenericTextWatcher(etEmail));
        etPassword.addTextChangedListener(new GenericTextWatcher(etPassword));

    }


    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.etEmail:
                    tilEmail.setErrorEnabled(false);
                    break;
                case R.id.etPassword:
                    tilPassword.setErrorEnabled(false);
                    break;


            }
        }
    }


    private void GetTripById(String strTripID) {
        Call<GetSingleTripResponse> getSingleTripResponseCall = BaseNetworking.ApiInterface().getTripById("trips/getsingletrip/" + strTripID);
        getSingleTripResponseCall.enqueue(new Callback<GetSingleTripResponse>() {
            @Override
            public void onResponse(Call<GetSingleTripResponse> call, Response<GetSingleTripResponse> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    Intent intent = new Intent(LoginActivity.this, TripDetailScreenActivity.class);
                    Bundle bundle = new Bundle();
                    AppRepository.mPutValue(LoginActivity.this).putString("tripIDForUpdation", String.valueOf(response.body().getData().getId())).commit();

                    bundle.putString("image", response.body().getData().getCoverimage());
                    bundle.putString("title", response.body().getData().getTitle());
                    bundle.putString("trip_type", response.body().getData().getStatus());
                    bundle.putString("start_date", response.body().getData().getFromdate());
                    bundle.putString("end_date", response.body().getData().getTodate());
                    bundle.putString("pay_date", response.body().getData().getPayDate());
                    bundle.putString("description", response.body().getData().getDescription());
                    bundle.putString("location", response.body().getData().getLocation());
                    bundle.putBoolean("is_createdby", false);
                    intent.putExtras(bundle);
                    LoginActivity.this.startActivity(intent);
                    LoginActivity.this.finish();

                }
            }

            @Override
            public void onFailure(Call<GetSingleTripResponse> call, Throwable t) {

            }
        });
    }


}