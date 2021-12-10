package com.techease.groupiiapplication.ui.fragment.payment;

import android.annotation.SuppressLint;
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
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnBackListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPaymentsTabsFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.tabsAddPaymentTypes)
    TabLayout tabsAddPaymentTypes;
    @BindView(R.id.viewpagerAddPaymentsTypes)
    ViewPager viewpagerAddPaymentsTypes;
    public static int anIntViewPagerPosition = 0;
    private AddPaymentOnBackListener addPaymentOnBackListener;

    public static AddPaymentsTabsFragment newInstance() {
        AddPaymentsTabsFragment fragment = new AddPaymentsTabsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_add_payments_tabs, container, false);
        ButterKnife.bind(this, parentView);
        init();
        return parentView;
    }


    private void init() {

        if (getActivity() instanceof AddPaymentOnBackListener)
            addPaymentOnBackListener = (AddPaymentOnBackListener) getActivity();
        setupViewPagerForTabsPaymentsTypes(viewpagerAddPaymentsTypes);
        tabsAddPaymentTypes.setupWithViewPager(viewpagerAddPaymentsTypes);
        tabsAddPaymentTypes.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //do stuff here
                anIntViewPagerPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void setupViewPagerForTabsPaymentsTypes(ViewPager viewPager) {


        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new AddPaymentFragment(), "Add Payments");
        adapter.addFragment(new AddPaymentFragment(), "Add Expenses");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                anIntViewPagerPosition = position;
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

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                anIntViewPagerPosition = 0;
                addPaymentOnBackListener.onPaymentBack();
                break;
        }
    }

}