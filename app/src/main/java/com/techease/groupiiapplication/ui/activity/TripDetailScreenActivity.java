

package com.techease.groupiiapplication.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.TabsViewPagerAdapter;
import com.techease.groupiiapplication.adapter.UserTripCircleImagesAdapter;
import com.techease.groupiiapplication.dataModel.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.AllTripDayFragment;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.PaymentsFragment;
import com.techease.groupiiapplication.ui.fragment.trip.PastFragment;
import com.techease.groupiiapplication.ui.fragment.trip.UpcomingFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DatePickerClass;
import com.techease.groupiiapplication.utils.GeneralUtills;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techease.groupiiapplication.adapter.ActiveTripAdapter.userList;

public class TripDetailScreenActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.tvTripTypeName)
    TextView tvTripTypeName;
    @BindView(R.id.ivTripImage)
    ImageView ivTripImage;
    @BindView(R.id.tvTripTitle)
    TextView tvTripTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;

    @BindView(R.id.rvImages)
    RecyclerView rvImages;

    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.tvMore)
    TextView tvMore;

    boolean valid = true;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llActivityMoreBottomSheet;
    LinearLayout llBottomSheetAddDayActivity;
    BottomSheetBehavior bottomSheetBehavior, addActivityBottomSheetBehavior;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack;
    TextView tvAddActivity;
    SwitchCompat switchCompatGroupActivity;

    String strActivityTitle, strActivityDate, strActivityTime, strActivityNote, strActivityGroup;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_trip_detail_screen);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String image = bundle.getString("image");


        Picasso.get().load(image).into(ivTripImage);
        tvTripTitle.setText(title);
        tvTripTypeName.setText(bundle.getString("trip_type"));
        tvDescription.setText(bundle.getString("description"));
        tvLocation.setText(bundle.getString("location"));


        rvImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvImages.addItemDecoration(new GeneralUtills.OverlapDecoration());
        rvImages.setHasFixedSize(true);
        rvImages.setAdapter(new UserTripCircleImagesAdapter(this, userList));


        initializeBottomSheet();


    }


    private void actionMenu() {
        View view = getLayoutInflater().inflate(R.layout.custom_menu_bottom_sheet, null);
        final Dialog mBottomSheetDialog = new Dialog(com.techease.groupiiapplication.ui.activity.TripDetailScreenActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

    }


    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivMenu, R.id.tvMore, R.id.ivMore})

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivMenu:
                actionMenu();
                break;
            case R.id.ivMore:
            case R.id.tvMore:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case R.id.ivAddActivity:
                addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.tvActivityAdd:
                if (isValid()) {
                    ApiCallForAddDayActivity();
                }
                break;
            case R.id.ivAddActivityBack:
                addActivityBottomSheetBehavior.setHideable(true);
                addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.etAddActivityDate:
                DatePickerClass.GetDatePickerDialog(etActivityDate, this);
                break;
            case R.id.etActivityTime:

                DatePickerClass.GetTimeDialog(etActivityTime, this);
                break;

        }
    }


    private void initializeBottomSheet() {
        llBottomSheetAddDayActivity = llActivityMoreBottomSheet.findViewById(R.id.bottom_sheet_add_activity);

        bottomSheetBehavior = BottomSheetBehavior.from(llActivityMoreBottomSheet);
        addActivityBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddDayActivity);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int i) {
                // do something when state changes
            }

            @Override
            public void onSlide(View view, float v) {
                // do something when slide happens
            }
        });
        // set the bottom sheet callback state to hidden when you just start your app
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        tabLayout = llActivityMoreBottomSheet.findViewById(R.id.tabs);
        viewPager = llActivityMoreBottomSheet.findViewById(R.id.viewpager);
        ivAddActivity = llActivityMoreBottomSheet.findViewById(R.id.ivAddActivity);


        ivAddActivityBack = llBottomSheetAddDayActivity.findViewById(R.id.ivAddActivityBack);

        tillActivityTitle = llBottomSheetAddDayActivity.findViewById(R.id.tilActivityTitle);
        tillDate = llBottomSheetAddDayActivity.findViewById(R.id.tillAddActivityDate);
        tillTime = llBottomSheetAddDayActivity.findViewById(R.id.tillActivitTime);
        tillActivityNote = llBottomSheetAddDayActivity.findViewById(R.id.tilAddActivityNote);

        etActivityTitle = llBottomSheetAddDayActivity.findViewById(R.id.etActivityTitle);
        etActivityDate = llBottomSheetAddDayActivity.findViewById(R.id.etAddActivityDate);
        etActivityTime = llBottomSheetAddDayActivity.findViewById(R.id.etActivityTime);
        etActivityNote = llBottomSheetAddDayActivity.findViewById(R.id.etAddActivityNote);

        tvAddActivity = llBottomSheetAddDayActivity.findViewById(R.id.tvActivityAdd);
        switchCompatGroupActivity = llBottomSheetAddDayActivity.findViewById(R.id.swAddGroupActivity);


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Days Plan");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.days_plan_selected, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Reservs");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.reserv_selected, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Payments");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.payment_selected, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Photos");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.photos_selected, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);


        ivAddActivityBack.setOnClickListener(this);
        ivAddActivity.setOnClickListener(this);
        tvAddActivity.setOnClickListener(this);

        etActivityDate.setOnClickListener(this);
        etActivityTime.setOnClickListener(this);

    }

    private void ApiCallForAddDayActivity() {
        dialog.show();
        Call<AddTripDayResponse> addTripDayResponseCall = BaseNetworking.ApiInterface().addTripDay(strActivityTitle, strActivityNote, strActivityDate, strActivityTime, "21", AppRepository.mUserID(this));
        addTripDayResponseCall.enqueue(new Callback<AddTripDayResponse>() {
            @Override
            public void onResponse(Call<AddTripDayResponse> call, Response<AddTripDayResponse> response) {
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    Log.d("zma", String.valueOf(response.message()));

                    AllTripDayFragment.ApiCallAllTirp(AppRepository.mUserID(TripDetailScreenActivity.this));

                    addActivityBottomSheetBehavior.setHideable(true);
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    Toast.makeText(TripDetailScreenActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(TripDetailScreenActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

                    etActivityDate.setText("");
                    etActivityNote.setText("");
                    etActivityTime.setText("");
                    etActivityTitle.setText("");

                }

            }

            @Override
            public void onFailure(Call<AddTripDayResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(TripDetailScreenActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strActivityTitle = etActivityTitle.getText().toString();
        strActivityDate = etActivityDate.getText().toString();
        strActivityTime = etActivityTime.getText().toString();
        strActivityNote = etActivityNote.getText().toString();


        if (strActivityTitle.isEmpty()) {
            tillActivityTitle.setErrorEnabled(true);
            tillActivityTitle.setError(getString(R.string.plesase_write_your_title));
            valid = false;
        } else {
            tillActivityTitle.setError(null);
        }
        if (strActivityNote.isEmpty()) {
            valid = false;
            tillActivityNote.setErrorEnabled(true);
            tillActivityNote.setError(getString(R.string.plesase_write_your_description));

        } else {
            tillActivityNote.setError(null);
            tillActivityNote.setErrorEnabled(false);
        }


        if (strActivityDate.isEmpty()) {
            valid = false;
            tillDate.setErrorEnabled(true);
            tillDate.setError(getString(R.string.plesase_write_start_date));

        } else {
            tillDate.setError(null);
            tillDate.setErrorEnabled(false);
        }
        if (strActivityTime.isEmpty()) {
            valid = false;
            tillTime.setErrorEnabled(true);
            tillTime.setError(getString(R.string.plesase_write_end_date));

        } else {
            tillTime.setError(null);
            tillTime.setErrorEnabled(false);
        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    private void setupViewPager(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllTripDayFragment(), "Days Plan");
        adapter.addFragment(new PastFragment(), "Reservs");
        adapter.addFragment(new PaymentsFragment(), "Payments");
        adapter.addFragment(new UpcomingFragment(), "Photos");
        viewPager.setAdapter(adapter);
    }

}
