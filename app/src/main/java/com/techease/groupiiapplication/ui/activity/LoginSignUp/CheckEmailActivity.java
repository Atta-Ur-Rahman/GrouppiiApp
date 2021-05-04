package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.techease.groupiiapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckEmailActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btnVerifyCode)
    Button btnVerifyCode;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


    }

    @OnClick({R.id.ivBack, R.id.btnVerifyCode})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerifyCode:
                startActivity(new Intent(this, CodeVerificationActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

}