package com.techease.groupiiapplication.ui.fragment.payment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.GropExpendituresTripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.PersonalExpendituresFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartiallyPaidTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartiallyPaidTripFragment extends Fragment {


    @BindView(R.id.viewpagerExpenditures)
    ViewPager viewpagerExpenditures;

    @BindView(R.id.tabsPartiallyPaid)
    TabLayout tabsPartiallyPaid;


    public static PartiallyPaidTripFragment newInstance() {
        PartiallyPaidTripFragment fragment = new PartiallyPaidTripFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_partially_paid_trip, container, false);
        ButterKnife.bind(this, parentView);
        init();

        return parentView;
    }

    private void init() {
        setupViewPagerForTabsPartiallyPaidTrip(viewpagerExpenditures);
    }


    private void setupViewPagerForTabsPartiallyPaidTrip(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new GropExpendituresTripFragment(), "Group");
        adapter.addFragment(new PersonalExpendituresFragment(), "Personal");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}