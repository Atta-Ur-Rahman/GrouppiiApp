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
import com.techease.groupiiapplication.dataModel.login.LogInResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.HomeActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        dialog = AlertUtils.createProgressDialog(this);
        initTextWatcher();


    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
        strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
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
                finish();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;

            case R.id.tvForgotPassword:

                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;

        }
    }

    private void ApiCallForSignIn() {


        Call<LogInResponse> logInResponseCall = BaseNetworking.ApiInterface().login(strEmail, strPassword);
        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma login", String.valueOf(response.message()));
                    if (response.body().getSuccess()) {

                        AppRepository.mPutValue(LoginActivity.this).putString("mUserPassword", strPassword).commit();
                        AppRepository.mPutValue(LoginActivity.this).putInt("userID", Integer.parseInt(response.body().getData().getId().toString())).commit();
                        AppRepository.mPutValue(LoginActivity.this).putString("mUserName", String.valueOf(response.body().getData().getName())).commit();
                        AppRepository.mPutValue(LoginActivity.this).putString("mUserEmail", String.valueOf(response.body().getData().getEmail())).commit();
                        AppRepository.mPutValue(LoginActivity.this).putString("mProfilePicture", String.valueOf(response.body().getData().getPicture())).commit();

                        AppRepository.mPutValue(LoginActivity.this).putBoolean("loggedIn", true).commit();

                        finishAffinity();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Toast.makeText(LoginActivity.this, String.valueOf(response.message()), Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                Log.d("zma login error", String.valueOf(t.getMessage()));
                Toast.makeText(LoginActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

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

}