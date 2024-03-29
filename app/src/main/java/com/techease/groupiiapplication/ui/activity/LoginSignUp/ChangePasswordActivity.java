package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.loginSignup.genrelResetPassword.GeneralResetPassword;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.GeneralUtills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etAgainPassword)
    EditText etAgainPassword;
    @BindView(R.id.btnResetPassword)
    Button btnReset;

    @BindView(R.id.etCurrentPassword)
    EditText etCurrentPassword;

    @BindView(R.id.tilCurrentPassword)
    TextInputLayout tilCurrentPassword;
    @BindView(R.id.tilNewPassword)
    TextInputLayout tilNewPassword;
    @BindView(R.id.tilAgainPassword)
    TextInputLayout tilAgainPassword;


    private String strCurrentPassword, strNewPassword, strAgainPassword;
    Dialog alertDialog;

    boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        setAnimation();
        alertDialog = AlertUtils.createProgressDialog(this);
        initTextWatcher();

    }

    @OnClick({R.id.ivBack, R.id.btnResetPassword})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResetPassword:
                if (isValid()) {
                    alertDialog.show();
                    ApiCallForChangePassword();
                }
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    private void ApiCallForChangePassword() {

        Call<GeneralResetPassword> changePasswordResponseCall = BaseNetworking.ApiInterface().changePassword(strAgainPassword, AppRepository.mUserID(this));
        changePasswordResponseCall.enqueue(new Callback<GeneralResetPassword>() {
            @Override
            public void onResponse(Call<GeneralResetPassword> call, Response<GeneralResetPassword> response) {
                if (response.isSuccessful()) {
                    alertDialog.dismiss();
                    finishAffinity();
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GeneralResetPassword> call, Throwable t) {
                alertDialog.dismiss();
            }
        });
    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strNewPassword = etNewPassword.getText().toString();
        strAgainPassword = etAgainPassword.getText().toString();
        strCurrentPassword = etCurrentPassword.getText().toString();

        if (!GeneralUtills.isValidPassword(strCurrentPassword)) {
            tilCurrentPassword.setErrorEnabled(true);
            tilCurrentPassword.setError(getString(R.string.password_should_be_six));
            valid = false;

        } else if (AppRepository.mUserPassword(this).equals(strCurrentPassword)) {
            tilCurrentPassword.setErrorEnabled(true);
            tilCurrentPassword.setError(getString(R.string.incorrect_current_password));
            valid = false;

        } else {
            tilCurrentPassword.setError(null);
            tilCurrentPassword.setErrorEnabled(false);

        }


        if (!GeneralUtills.isValidPassword(strNewPassword)) {
            tilNewPassword.setErrorEnabled(true);
            tilNewPassword.setError(getString(R.string.password_should_be_six));
            valid = false;

        } else {
            tilNewPassword.setError(null);
            tilNewPassword.setErrorEnabled(false);

        }
        if (!strAgainPassword.equals(strNewPassword)) {
            tilAgainPassword.setErrorEnabled(true);
            tilAgainPassword.setError(getString(R.string.password_dont_match_validation));
            valid = false;
        } else {
            tilAgainPassword.setError(null);
            tilAgainPassword.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    private void initTextWatcher() {
        etNewPassword.addTextChangedListener(new GenericTextWatcher(etNewPassword));
        etAgainPassword.addTextChangedListener(new GenericTextWatcher(etAgainPassword));
        etCurrentPassword.addTextChangedListener(new GenericTextWatcher(etCurrentPassword));

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
                case R.id.etNewPassword:
                    tilNewPassword.setErrorEnabled(false);
                    break;
                case R.id.etAgainPassword:
                    tilAgainPassword.setErrorEnabled(false);
                    break;
                case R.id.etCurrentPassword:
                    tilCurrentPassword.setErrorEnabled(false);
                    break;


            }
        }
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.RIGHT);
            slide.setDuration(200);
            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
}