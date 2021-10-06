package com.techease.groupiiapplication.ui.fragment.payment;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.interfaceClass.ParticipantBackListener;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.GropExpendituresTripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.PersonalExpendituresFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipantCostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipantCostsFragment extends Fragment implements View.OnClickListener {


    public static ParticipantCostsFragment newInstance() {
        ParticipantCostsFragment fragment = new ParticipantCostsFragment();

        return fragment;
    }

    int anIntViewPagerPosition = 0;


    @BindView(R.id.tabsParticipantCosts)
    TabLayout tabsParticipantCosts;
    @BindView(R.id.viewpagerParticipantCosts)
    ViewPager viewpagerParticipantCosts;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    private ParticipantBackListener participantBackListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_participant_costs, container, false);
        ButterKnife.bind(this, parentView);
        init();


        return parentView;
    }

    private void init() {
        setupViewPagerForTabsParticipantCosts(viewpagerParticipantCosts);
        tabsParticipantCosts.setupWithViewPager(viewpagerParticipantCosts);


        if (getActivity() instanceof ParticipantBackListener)
            participantBackListener = (ParticipantBackListener) getActivity();


    }

    @SuppressLint("NonConstantResourceId")

    @OnClick({R.id.ivBack})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                participantBackListener.onParticipantBack();
                break;


        }
    }


    private void setupViewPagerForTabsParticipantCosts(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FullPaidParticipantFragment(), "Fully Paid");
        adapter.addFragment(new PartiallyPaidParticipantFragment(), "Partially Paid");
        adapter.addFragment(new DontShareCostsParticipantFragment(), "Don't Share Costs");

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                anIntViewPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}