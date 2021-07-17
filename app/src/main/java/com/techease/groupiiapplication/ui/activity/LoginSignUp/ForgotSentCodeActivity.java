package com.techease.groupiiapplication.ui.activity.LoginSignUp;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.utils.KeyBoardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotSentCodeActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tvCountDownTimer)
    TextView tvCountDownTimer;

    @BindView(R.id.btnResendCode)
    Button btnResendCode;
    @BindView(R.id.llResendConformationCode)
    LinearLayout llResendCodeConformation;

    @BindView(R.id.btnVerifyCode)
    Button btnVerifyCode;

    @BindView(R.id.et_code_num1)
    EditText et_num1;
    @BindView(R.id.et_code_num2)
    EditText et_num2;
    @BindView(R.id.et_code_num3)
    EditText et_num3;
    @BindView(R.id.et_code_num4)
    EditText et_num4;

    String strVerifycode, strForgotPasswordEmail;


    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_forgot_sent_code);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initiateListener();
    }

    private void initiateListener() {
        btnResendCode.setOnClickListener(this);
        btnVerifyCode.setOnClickListener(this);

        et_num1.addTextChangedListener(genraltextWatcher);
        et_num2.addTextChangedListener(genraltextWatcher);
        et_num3.addTextChangedListener(genraltextWatcher);
        et_num4.addTextChangedListener(genraltextWatcher);

        KeyBoardUtils.showKeyboardWithEditText(this, et_num1);


    }

    private void CountDownTimerClass() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDownTimer.setText("(" + millisUntilFinished / 1000 + ")");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                et_num1.setText("");
                et_num2.setText("");
                et_num3.setText("");
                et_num4.setText("");
                llResendCodeConformation.setVisibility(View.GONE);
                btnResendCode.setVisibility(View.VISIBLE);
                btnVerifyCode.setVisibility(View.GONE);


            }

        }.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnResendCode:
//                apiCallReSendCode();
                break;

            case R.id.btnVerifyCode:

                onDataInput();
                if (strVerifycode.length() == 4) {
//                    apiCallForgotVerifyCode();
                } else {
                    Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    private void onDataInput() {
        String num1 = et_num1.getText().toString();
        String num2 = et_num2.getText().toString();
        String num3 = et_num3.getText().toString();
        String num4 = et_num4.getText().toString();

        strVerifycode = num1 + num2 + num3 + num4;


    }


    private TextWatcher genraltextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

            onDataInput();
            if (strVerifycode.length() == 4) {
                ApiCallForgotVerifyCode();


            }

            if (et_num1.length() == 1) {

                et_num2.requestFocus();

            }
            if (et_num2.length() == 1) {

                et_num3.requestFocus();

            }
            if (et_num3.length() == 1) {

                et_num4.requestFocus();

            }
        }

    };

    private void ApiCallForgotVerifyCode() {


    }

}