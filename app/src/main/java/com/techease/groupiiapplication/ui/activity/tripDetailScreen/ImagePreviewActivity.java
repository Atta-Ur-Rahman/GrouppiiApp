package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.chatAdapter.ChatAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImagePreviewActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    private Toolbar toolbar;
    private String strFileUrl;
//    boolean aBooleanImageCheck = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_preview);
        initVariable();

    }

    void initVariable() {

        Bundle bundle = getIntent().getExtras();


        strFileUrl = bundle.getString("file");
//        byte[] aByte = bundle.getByteArray("image");
////
//
//        try {
//            if (strFileUrl == "null") {
////                aBooleanImageCheck = false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ButterKnife.bind(this);
//        if (strFileUrl.equals("base64")) {
//            Glide.with(this).load(aByte).placeholder(R.drawable.progress_animation).into(ivPhoto);
//        } else {
            Glide.with(this).load(strFileUrl).placeholder(R.drawable.progress_animation).into(ivPhoto);

//        }


    }

    @OnClick({R.id.ivBack})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
}