package com.techease.groupiiapplication.ui.activity.profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.DataAdapter;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GeneralUtills;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.techease.groupiiapplication.adapter.ActiveTripAdapter.userList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;

    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.tvProfileEmail)
    TextView tvProfileEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setProfileImageAndName();


    }


    private void setProfileImageAndName() {
        if (AppRepository.mUserName(this).length() > 0) {
            tvProfileName.setText(AppRepository.mUserName(this));
        }

        if (AppRepository.mEmail(this).length() > 0) {
            tvProfileEmail.setText(AppRepository.mEmail(this));
        }
        if (AppRepository.mUserProfileImage(this).length() > 0) {
            Picasso.get().load(AppRepository.mUserProfileImage(this)).placeholder(R.drawable.dubai).into(ivProfilePicture);
        }
    }

    @OnClick({R.id.ivBack, R.id.ivProfilePicture})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEdit:

                break;
        }
    }
}
