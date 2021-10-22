package com.techease.groupiiapplication.ui.activity.payments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.fragment.payment.AddPaymentFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AddPaymentFragment.newInstance(bundle.getString("edit_payment_id"), bundle.getString("edit_payment_type")))
                    .commitNow();
        }

    }

    @OnClick({R.id.ivBack})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;


        }
    }
}