package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.forgot.ForgotResponse;
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

public class ResedPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etAgainPassword)
    EditText etAgainPassword;
    @BindView(R.id.btnResetPassword)
    Button btnReset;

    @BindView(R.id.tilNewPassword)
    TextInputLayout tilNewPassword;
    @BindView(R.id.tilAgainPassword)
    TextInputLayout tilAgainPassword;


    private String strNewPassword, strAgainPassword;
    Dialog alertDialog;

    boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resed_password);

        ButterKnife.bind(this);
        getSupportActionBar().hide();
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

        Call<ForgotResponse> changePasswordResponseCall = BaseNetworking.ApiInterface().resetPassword(strAgainPassword, CodeVerificationActivity.strVerityCode);
        changePasswordResponseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.isSuccessful()) {
                    alertDialog.dismiss();
                    finishAffinity();
                    startActivity(new Intent(ResedPasswordActivity.this, LoginActivity.class));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(ResedPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                alertDialog.dismiss();
            }
        });
    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strNewPassword = etNewPassword.getText().toString();
        strAgainPassword = etAgainPassword.getText().toString();


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


            }
        }
    }
}