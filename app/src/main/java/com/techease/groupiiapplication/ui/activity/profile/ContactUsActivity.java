package com.techease.groupiiapplication.ui.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.profile.contactUs.ContactUsResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.etContact)
    EditText etContactUs;
    String strMessage;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        init();

    }

    private void init() {

    }

    @OnClick({R.id.btnSend, R.id.ivBack})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                strMessage = etContactUs.getText().toString();
                if (strMessage.length() < 5) {
                    Toast.makeText(this, "A valid message is required.", Toast.LENGTH_SHORT).show();
                } else {
                    ApiCallContactUs();
                }
                break;
        }
    }

    private void ApiCallContactUs() {
        dialog.show();
        Call<ContactUsResponse> contactUsResponseCall = BaseNetworking.ApiInterface().contactUs(strMessage, AppRepository.mUserID(this));
        contactUsResponseCall.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(ContactUsActivity.this, "Your message has been submitted. Thanks.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    dialog.dismiss();
                    Toast.makeText(ContactUsActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ContactUsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}