package com.techease.groupiiapplication.ui.fragment.payment;

import static com.techease.groupiiapplication.utils.Constants.IS_FROM_NEW_SET_SCREEN;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.interfaceClass.ParticipantBackListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipantCostsTabsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipantCostsTabsFragment extends Fragment implements View.OnClickListener {


    public static ParticipantCostsTabsFragment newInstance() {
        ParticipantCostsTabsFragment fragment = new ParticipantCostsTabsFragment();

        return fragment;
    }

    int anIntViewPagerPosition = 0;


    @BindView(R.id.tabsParticipantCosts)
    TabLayout tabsParticipantCosts;
    @BindView(R.id.viewpagerParticipantCosts)
    ViewPager viewpagerParticipantCosts;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.rlTop)
    RelativeLayout rlTop;
    @BindView(R.id.vMinusIcon)
    View vMinusIcon;
    private ParticipantBackListener participantBackListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_participant_costs, container, false);
        ButterKnife.bind(this, parentView);

        if (!IS_FROM_NEW_SET_SCREEN) {
            ivBack.setVisibility(View.GONE);
            vMinusIcon.setVisibility(View.GONE);
            IS_FROM_NEW_SET_SCREEN = true;
            rlTop.setVisibility(View.GONE);
        }
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
        adapter.addFragment(new DontShareCostsParticipantFragment(), getString(R.string.dosnotsharecost));

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