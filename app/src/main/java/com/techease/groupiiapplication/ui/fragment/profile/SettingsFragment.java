package com.techease.groupiiapplication.ui.fragment.profile;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.BuildConfig;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.ui.activity.profile.ContactUsActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.ChangePasswordActivity;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.utils.AppRepository;
import com.thefinestartist.finestwebview.FinestWebView;
import com.thefinestartist.finestwebview.listeners.WebViewListener;

import java.util.List;

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

    @OnClick({R.id.rlSignOut, R.id.ivProfilePicture, R.id.rlChangePassword, R.id.rlContactUs, R.id.rlShare, R.id.rlPrivacyPolicy, R.id.ivBrowser, R.id.ivFacebook, R.id.ivTwitter, R.id.ivinstagram})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rlSignOut:

                SharedPreferences settings = getActivity().getSharedPreferences("com.techease.grouppii", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.ivProfilePicture:
                startActivity(new Intent(getActivity(), ProfileActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

                break;
            case R.id.rlChangePassword:
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;

            case R.id.rlShare:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = "\nGrouppii Application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=ff" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose)));
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            case R.id.rlContactUs:
//                setAnimation();
                startActivity(new Intent(getActivity(), ContactUsActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

                break;
            case R.id.rlPrivacyPolicy:

                new FinestWebView.Builder(getActivity())
                        .titleDefault("Grouppii Privacy Policy")
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

            case R.id.ivBrowser:
                String url = "http://www.google.com";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.ivFacebook:
                launchFacebook();
                break;
            case R.id.ivTwitter:
                launchTwitter();
                break;
            case R.id.ivinstagram:
                launchInstagram();
                break;
        }

    }

    private void launchTwitter() {
        Intent intent = null;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=USERID"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Groupii"));
        }
        this.startActivity(intent);
    }

    private void launchInstagram() {
        Uri uri = Uri.parse("https://www.instagram.com/grouppiiapp/");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/grouppiiapp/")));
        }
    }

    public final void launchFacebook() {
        final String urlFb = "fb://page/" + "426253597411506";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/" + "426253597411506";
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileImage();
    }

}