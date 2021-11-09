package com.techease.groupiiapplication.chat.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.chat.view.animation.DepthTransformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatImagePreviewActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    ArrayList<String> chatImagesList = new ArrayList<>();
    String strFileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_image_preview);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_in);

        Bundle bundle = getIntent().getExtras();
        strFileUrl = bundle.getString("file");
        chatImagesList = bundle.getStringArrayList("images");
//        Collections.reverse(chatImagesList);


        initAdapter();

    }

    private int getCategoryPos(String category) {
        return chatImagesList.indexOf(category);
    }

    private void initAdapter() {
        DepthTransformation depthTransformation = new DepthTransformation();
        viewPager.setPageTransformer(true, depthTransformation);
        myViewPagerAdapter = new MyViewPagerAdapter(chatImagesList);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        myViewPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(getCategoryPos(strFileUrl));

    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

//            tvActionBarTitle.setText(strImageNameAndPath.substring(strImageNameAndPath.lastIndexOf("/") + 1));
            Log.d("position", String.valueOf(position));
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @OnClick({R.id.ivBack})
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
        }
    }


    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;
        private ArrayList<String> chatImageList = new ArrayList<>();

        public MyViewPagerAdapter(ArrayList<String> chatImageList) {
            this.chatImageList=chatImageList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) ChatImagePreviewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.chatimage_fullscreen_preview, container, false);

            TouchImageView photoView = view.findViewById(R.id.ivLockZoomageView);
            photoView.setClickable(true);
            Glide.with(ChatImagePreviewActivity.this).load(chatImagesList.get(position)).into(photoView);


            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return chatImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}