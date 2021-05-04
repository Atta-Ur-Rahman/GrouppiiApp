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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.forgot.ForgotResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    boolean valid;
    String strEmail;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;
    Dialog dialogl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialogl = AlertUtils.createProgressDialog(this);
        initTextWatcher();


    }

    @OnClick({R.id.ivBack, R.id.btnSentCode})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSentCode:

                if (isValid()) {
                    ApiCallForSentCode();

                }
                break;
        }
    }

    private void ApiCallForSentCode() {

        dialogl.show();
        Call<ForgotResponse> forgotResponseCall = BaseNetworking.ApiInterface().forgotPassword(strEmail);
        forgotResponseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.isSuccessful()) {
                    dialogl.dismiss();
                    finish();
//                    AppRepository.mPutValue(ForgotPasswordActivity.this).putString("password_token", String.valueOf(response.body().getData())).commit();
                    startActivity(new Intent(ForgotPasswordActivity.this, CheckEmailActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) ForgotPasswordActivity.this).toBundle());

                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                dialogl.dismiss();
            }
        });


    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strEmail = etEmail.getText().toString();
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


    private void initTextWatcher() {

        etEmail.addTextChangedListener(new GenericTextWatcher(etEmail));


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
            switch (view.getId()) {
                case R.id.etEmail:
                    tilEmail.setErrorEnabled(false);
                    break;


            }
        }
    }

}