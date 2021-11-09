package com.techease.groupiiapplication.chat.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.potyvideo.library.AndExoPlayerView;
import com.techease.groupiiapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerActivity extends AppCompatActivity {


    @BindView(R.id.andExoPlayerView)
    AndExoPlayerView andExoPlayerView;

    ArrayList<String> chatImagesList = new ArrayList<>();
    String strFileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        strFileUrl = bundle.getString("file");
        chatImagesList = bundle.getStringArrayList("images");


        HashMap<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("foo", "bar");
        andExoPlayerView.setSource(strFileUrl, extraHeaders);

    }
}