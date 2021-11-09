package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fredporciuncula.phonemoji.PhonemojiFlagTextView;
import com.fredporciuncula.phonemoji.PhonemojiTextInputEditText;
import com.fredporciuncula.phonemoji.PhonemojiTextInputLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.loginSignup.signUp.SignUpResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.GPSTracker;
import com.techease.groupiiapplication.utils.GeneralUtills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    boolean valid;
    String strName, strEmail, strPassword, strPhoneNumber, strAgainPassword;

    @BindView(R.id.tilName)
    TextInputLayout tilName;
    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tilAgainPassword)
    TextInputLayout tilAgainPassword;
    @BindView(R.id.etAgainPassword)
    EditText etAgainPassword;


    @BindView(R.id.tilPhonemojiTextInputLayout)
    PhonemojiTextInputLayout tilPhonemojiTextInputLayout;
    @BindView(R.id.phonemojiTextInputEditText)
    PhonemojiTextInputEditText phonemojiTextInputEditText;


    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    Dialog alertDialog;


    String fcmToken;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    protected LocationManager locationManager;
    String strLatitude, strLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        alertDialog = AlertUtils.createProgressDialog(this);
        initTextWatcher();
        getCurrentLocationPermission();


    }


    @OnClick({R.id.btnSignUp, R.id.tvSignIn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                getCurrentLocationPermission();
                if (isValid()) {
                    alertDialog.show();
                    ApiCallForSignUp();
                }
                break;
            case R.id.tvSignIn:

                Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                SignUpActivity.this.startActivity(mainIntent);
                SignUpActivity.this.finish();

//                finish();
//                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
        }
    }

    private void ApiCallForSignUp() {
        try {
            Call<SignUpResponse> signUpResponseCall = BaseNetworking.ApiInterface().signUp(strName, strEmail, strPassword, strPhoneNumber,"android", "user", strLatitude, strLongitude);
            signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                    Log.d("zma signup response", String.valueOf(response));

                    if (response.code() == 400) {
                        Toast.makeText(SignUpActivity.this, "The email or phone number has already been taken", Toast.LENGTH_SHORT).show();

                    }

                    if (response.isSuccessful()) {
                        alertDialog.dismiss();
                        finishAffinity();
                        Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) SignUpActivity.this).toBundle());

//                    AppRepository.mPutValue(SignUpActivity.this).putInt("userID",response.body().getData().getId());
//                    AppRepository.mPutValue(SignUpActivity.this).putString("mUserName", String.valueOf(response.body().getData().getName())).commit();
//                    AppRepository.mPutValue(SignUpActivity.this).putString("mProfilePicture", String.valueOf(response.body().getData().ge())).commit();


                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    alertDialog.dismiss();
                    Log.d("zma signup error", String.valueOf(t.getMessage()));
                    Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
        strName = etName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        strAgainPassword = etAgainPassword.getText().toString();
        strPhoneNumber = phonemojiTextInputEditText.getText().toString();
        if (strName.isEmpty()) {
            valid = false;
            tilName.setErrorEnabled(true);
            tilName.setError(getString(R.string.plesase_write_your_name));

        } else {
            tilName.setError(null);
            tilName.setErrorEnabled(false);
        }

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
            tilEmail.setErrorEnabled(false);

        }


        if (strPhoneNumber.isEmpty() || strPhoneNumber.length() < 5) {
            tilPhonemojiTextInputLayout.setErrorEnabled(true);
            tilPhonemojiTextInputLayout.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilPhonemojiTextInputLayout.setError(null);
            tilPhonemojiTextInputLayout.setErrorEnabled(false);

        }


        if (strPassword.isEmpty() || strPassword.length() < 6) {
            valid = false;
            tilPassword.setErrorEnabled(true);
            tilPassword.setError(getString(R.string.password_validation));

        } else {
            tilPassword.setError(null);
            tilPassword.setErrorEnabled(false);

        }
        if (!strAgainPassword.equals(strPassword)) {
            tilAgainPassword.setErrorEnabled(true);
            tilAgainPassword.setError(getString(R.string.password_dont_match_validation));
            valid = false;
        } else {
            tilAgainPassword.setError(null);
            tilAgainPassword.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strLongitude)) {
            valid = false;
            Toast.makeText(this, "location not fond wait!", Toast.LENGTH_SHORT).show();
        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        try {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                valid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return valid;
    }

    private void initTextWatcher() {

        etName.addTextChangedListener(new GenericTextWatcher(etName));
        etEmail.addTextChangedListener(new GenericTextWatcher(etEmail));
        etPassword.addTextChangedListener(new GenericTextWatcher(etPassword));
        etAgainPassword.addTextChangedListener(new GenericTextWatcher(etAgainPassword));
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
                case R.id.etName:
                    tilName.setErrorEnabled(false);
                    break;
                case R.id.etEmail:
                    tilEmail.setErrorEnabled(false);
                    break;
                case R.id.etPassword:
                    tilPassword.setErrorEnabled(false);
                    break;
                case R.id.etAgainPassword:
                    tilAgainPassword.setErrorEnabled(false);
                    break;
            }
        }
    }


    private void getCurrentLocationPermission() {
        valid = false;
        Dexter.withContext(this).withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                GPSTracker gpsTracker = new GPSTracker(SignUpActivity.this);

                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    double latitude = gpsTracker.latitude;
                    double longitude = gpsTracker.longitude;

                    strLatitude = String.valueOf(latitude);
                    strLongitude = String.valueOf(longitude);


                    valid = true;


                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

            }
        }).check();
    }


//
//    @Override
//    public void onLocationChanged(Location location) {
//        strLatitude = String.valueOf(location.getLatitude());
//        strLongitude = String.valueOf(location.getLongitude());
//        Log.d("zma", strLatitude + "  " + strLongitude);
//
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        Log.d("Latitude", "disable");
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Log.d("Latitude", "enable");
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude", "status");
//    }


}