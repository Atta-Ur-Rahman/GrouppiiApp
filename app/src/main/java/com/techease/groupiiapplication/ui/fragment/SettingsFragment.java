package com.techease.groupiiapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.utils.AppRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    View view;
    @BindView(R.id.rlSignOut)
    RelativeLayout cvSignOut;


    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setProfileImage();

        return view;
    }


    private void setProfileImage() {

        if (AppRepository.mUserProfileImage(getActivity()).length() > 0) {
            Picasso.get().load(AppRepository.mUserProfileImage(getActivity())).into(ivProfilePicture);
        }
    }

    @OnClick({R.id.rlSignOut,R.id.ivProfilePicture})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rlSignOut:
                AppRepository.mPutValue(getActivity()).putBoolean("loggedIn", false).commit();
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.ivProfilePicture:
                startActivity(new Intent(getActivity(), ProfileActivity.class));



        }

    }

}