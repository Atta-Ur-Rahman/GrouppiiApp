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
import com.techease.groupiiapplication.BuildConfig;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.activity.ContactUsActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.ChangePasswordActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.thefinestartist.finestwebview.FinestWebView;
import com.thefinestartist.finestwebview.listeners.WebViewListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    View view;
    @BindView(R.id.rlSignOut)
    RelativeLayout cvSignOut;
    @BindView(R.id.rlChangePassword)
    RelativeLayout rlChangePassword;
    @BindView(R.id.rlContactUs)
    RelativeLayout rlContactUs;
    @BindView(R.id.rlShare)
    RelativeLayout rlShare;
    @BindView(R.id.rlPrivacyPolicy)
    RelativeLayout rlPrivacyPolicy;


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
            Picasso.get().load(AppRepository.mUserProfileImage(getActivity())).placeholder(R.drawable.user).into(ivProfilePicture);
        }
    }

    @OnClick({R.id.rlSignOut, R.id.ivProfilePicture, R.id.rlChangePassword, R.id.rlContactUs, R.id.rlShare, R.id.rlPrivacyPolicy})
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

                break;
            case R.id.rlChangePassword:
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                break;

            case R.id.rlShare:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = "\nGroupii Application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=ff" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose)));
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            case R.id.rlContactUs:
                startActivity(new Intent(getActivity(), ContactUsActivity.class));

                break;
            case R.id.rlPrivacyPolicy:

                new FinestWebView.Builder(getActivity())
                        .titleDefault("Groupii Privacy Policy")
                        .theme(R.style.FinestWebViewTheme)
                        .showUrl(false)
                        .addWebViewListener(new WebViewListener() {
                            @Override
                            public void onPageFinished(String url) {
                                super.onPageFinished(url);

                            }
                        })
                        .show("https://www.freeprivacypolicy.com/live/6151174f-2367-4f51-bec5-0b0763d4a600");
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileImage();
    }
}