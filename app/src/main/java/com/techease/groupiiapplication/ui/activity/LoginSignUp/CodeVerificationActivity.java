package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.techease.groupiiapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnVerify)
    Button btnVerifyCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.pinView)
    PinView pinView;

    public static String strVerityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

    }


    @OnClick({R.id.ivBack, R.id.btnVerify})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerify:
                strVerityCode = pinView.getText().toString();
                if (strVerityCode.length() < 6) {
                    Toast.makeText(this, "Invalid code", Toast.LENGTH_SHORT).show();
//                    pinView.setError("Invalid code");
                } else {
                    startActivity(new Intent(this, ResedPasswordActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());
                }
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}