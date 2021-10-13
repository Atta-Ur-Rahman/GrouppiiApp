package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.MapViewActivity;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.gallery.Connect;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.adapter.tripes.UserTripCircleImagesAdapter;
import com.techease.groupiiapplication.adapter.tripDetail.TripParticipantsAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.GroupExpenditure;
import com.techease.groupiiapplication.dataModel.payments.getPaymentsExpenses.PersonalExpenditure;
import com.techease.groupiiapplication.dataModel.tripDetial.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.interfaceClass.AddActivityBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.AddPaymentOnBackListener;
import com.techease.groupiiapplication.interfaceClass.ClickPartiallyPaidTripListener;
import com.techease.groupiiapplication.interfaceClass.ClickRecentTransactionListener;
import com.techease.groupiiapplication.interfaceClass.EditActivityDayPlanListener;
import com.techease.groupiiapplication.interfaceClass.ParticipantBackListener;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.fragment.payment.AddPaymentsTabsFragment;
import com.techease.groupiiapplication.ui.fragment.payment.ParticipantCostsTabsFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.AddAndEditDayPlaneFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.GropExpendituresTripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.PersonalExpendituresFragment;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.AllTripDayPlanFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PhotosFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.ReservesFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AnimationRVUtill;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.FileUtils;
import com.techease.groupiiapplication.utils.GeneralUtills;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
import com.techease.groupiiapplication.utils.NumberFormatUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailScreenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AddPaymentOnBackListener, AddActivityBackListener, EditActivityDayPlanListener, AddPaymentCallBackListener, ParticipantBackListener, ClickRecentTransactionListener, ClickPartiallyPaidTripListener {

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
    @BindView(R.id.tvGroupView)
    TextView tvGroupView;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;

    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.ivChat)
    ImageView ivChat;
    @BindView(R.id.tvMore)
    TextView tvMore;

    @BindView(R.id.llDayPlan)
    LinearLayout llDayPlan;

    @BindView(R.id.llPayment)
    LinearLayout llPayments;

    @BindView(R.id.llReservs)
    LinearLayout llReservs;

    @BindView(R.id.llPhoto)
    LinearLayout llPhoto;

    @BindView(R.id.cvMenu)
    LinearLayout cvMenu;


    LinearLayout llBottomSheetDayPlan, llBottomSheetReservs, llBottomSheetPayments, llBottomSheetPhotos;
    ImageView ivBottomSheetDayPlan, ivBottomSheetReservs, ivBottomSheetPayments, ivBottomSheetPhotos;
    TextView tvBottomSheetDayPlan, tvBottomSheetReservs, tvBottomSheetPayments, tvBottomSheetPhotos;

    public static List<GroupExpenditure> groupExpendituresItems = new ArrayList<>();
    public static List<PersonalExpenditure> personalExpendituresItems = new ArrayList<>();

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llActivityMoreBottomSheet;

    @BindView(R.id.llParticipantsBottomSheetBehaviorId)
    LinearLayout llParticipantsBottomSheetBehaviorId;

    LinearLayout llBottomSheetAddDayActivity, llBottomSheetAddPayment, llBottomSheetPartiallyPaidTrip, llParticipantsCosts;
    BottomSheetBehavior bottomSheetBehavior, addActivityBottomSheetBehavior, addPaymentBottomSheetBehavior, partiallyPaidTripBottomSheetBehavior, participantsBottomSheet, participantCostsBottomSheetBehaior;

    public static TripParticipantsAdapter tripParticipantsAdapter;
    public static ArrayList<AddTripDataModel> userParticipaintsList = new ArrayList<>();
    public static ArrayList<AddTripDataModel> paymentUserParticipaintsList = new ArrayList<>();
    public static ArrayList<AddTripDataModel> userParticipaintsCircleList = new ArrayList<>();


    RecyclerView rvTripParticipants;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public static boolean aBooleanResfreshGetUserTrip = false;
    @BindView(R.id.tvDaysLeft)
    TextView tvDaysLeft;
    boolean aBooleanAddImage = true, aBooleanIsCreatedBy = false;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_PICTURE = 3;

    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack, ivActivityType;
    TextView tvAddActivity;
    SwitchCompat switchCompatGroupActivity;


    ViewPager viewpagerExpenditures;
    TabLayout tabsPartiallyPaid;


    TextView tvPartiallyPaid, tvPayDate, tvPayDaysLeft, tvParticipantsCostsCount, tvPartiallyPaidPercentage, tvNoActiveTripFound;
    CircularSeekBar circularSeekBar;
    ImageView ivBackPartiallyPaid, ivAddTripParticipant;
    int anIntViewPagerPosition = 0;
    File sourceFile;
    Dialog addPhotoDialog;

    String strTripDate, strTitle, strPhoto, strPaymentUser;
    Dialog dialog;

    EditText etPhotoName;
    ImageView ivGalleryPhoto, ivCloseParticipant;
    Button btnAddPhoto;

    @BindView(R.id.spUserName)
    Spinner spUserName;


    String tripID;
    int userID;

    public static UserTripCircleImagesAdapter userTripCircleImagesAdapter;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_trip_detail_screen);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setupWindowAnimations();
        dialog = AlertUtils.createProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        strTitle = bundle.getString("title");
        strPhoto = bundle.getString("image");
        strTripDate = bundle.getString("date");

        aBooleanIsCreatedBy = bundle.getBoolean("is_createdby");
        tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(strTripDate)) + " days left");

        AppRepository.mPutValue(this).putString("trip_start_date", DateUtills.changeDateTripStartDateFormate(strTripDate)).commit();

        userID = AppRepository.mUserID(this);
        tripID = AppRepository.mTripIDForUpdation(this);


//        Log.d("zma user id", "" + userID);
//        Log.d("zma trip id", "" + tripID);


        Glide.with(this).load(strPhoto).into(ivTripImage);
        tvTripTitle.setText(strTitle);
        tvTripTypeName.setText(bundle.getString("trip_type"));
        tvDescription.setText(bundle.getString("description"));
        tvLocation.setText(bundle.getString("location"));

        stringArrayList = bundle.getStringArrayList("users");
        userTripCircleImagesAdapter = new UserTripCircleImagesAdapter(TripDetailScreenActivity.this, TripFragment.userList);
        rvImages.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvImages.addItemDecoration(new GeneralUtills.OverlapDecoration());
        rvImages.setHasFixedSize(true);
        rvImages.setAdapter(userTripCircleImagesAdapter);


        getPaymentExpenses();
        initializeBottomSheet();
        ApiCallGetUserTrip();


    }


    private void actionMenu() {
        View view = getLayoutInflater().inflate(R.layout.custom_menu_bottom_sheet, null);
        final Dialog mBottomSheetDialog = new Dialog(TripDetailScreenActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        TextView tvCancel = mBottomSheetDialog.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        TextView tvParticipants = mBottomSheetDialog.findViewById(R.id.tvManageParticipants);
        tvParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                participantsBottomSheet.setDraggable(false);
                participantsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

//                Log.d("zma user", String.valueOf(TripFragment.userList));
                linearLayoutManager = new LinearLayoutManager(TripDetailScreenActivity.this);
                tripParticipantsAdapter = new TripParticipantsAdapter((TripDetailScreenActivity.this), userParticipaintsList, aBooleanIsCreatedBy);
                rvTripParticipants.setLayoutAnimation(AnimationRVUtill.RecylerViewAnimation(TripDetailScreenActivity.this));
                rvTripParticipants.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, RecyclerView.VERTICAL, false));
                rvTripParticipants.setAdapter(tripParticipantsAdapter);

                if (userParticipaintsList.size() == 0) {
                    tvNoActiveTripFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoActiveTripFound.setVisibility(View.GONE);
                }

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivMenu, R.id.tvLocation, R.id.tvGroupView, R.id.tvMore, R.id.ivMore, R.id.cvMenu, R.id.ivChat, R.id.llDayPlan, R.id.llReservs, R.id.llPayment, R.id.llPhoto})

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivMenu:
                actionMenu();
                break;
            case R.id.ivChat:
                Intent intent = new Intent(this, ChatsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title_name", tvTripTitle.getText().toString());
                bundle.putString("tripId", AppRepository.mTripIDForUpdation(this));
                bundle.putString("toUserId", "" + AppRepository.mUserID(this));
                bundle.putString("type", "group");
                bundle.putString("picture", strPhoto);


                intent.putExtras(bundle);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;
            case R.id.tvLocation:
            case R.id.tvGroupView:
                startActivity(new Intent(TripDetailScreenActivity.this, MapViewActivity.class));
                break;
            case R.id.ivMore:
            case R.id.tvMore:
                bottomSheetBehavior.setHideable(false);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llDayPlan:
                bottomSheetBehavior.setHideable(false);
                viewPager.setCurrentItem(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llReservs:
                bottomSheetBehavior.setHideable(false);
                viewPager.setCurrentItem(1);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llPayment:
                bottomSheetBehavior.setHideable(false);
                viewPager.setCurrentItem(2);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llPhoto:
                bottomSheetBehavior.setHideable(false);
                viewPager.setCurrentItem(3);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case R.id.ivAddActivity:
                if (anIntViewPagerPosition == 0) {
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.containerActivity, AddAndEditDayPlaneFragment.newInstance())
                            .commitNow();
                } else if (anIntViewPagerPosition == 2) {

                    if (aBooleanIsCreatedBy) {
                        addPaymentBottomSheetBehavior.setDraggable(false);
                        bottomSheetBehavior.setDraggable(false);
                        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containerPayment, AddPaymentsTabsFragment.newInstance())
                                .commitNow();
                    } else {
                        Toast.makeText(this, "payment add only admin", Toast.LENGTH_SHORT).show();
                    }

                } else if (anIntViewPagerPosition == 3) {

                    if (aBooleanAddImage) {
                        addPhotoDialog();
                        aBooleanAddImage = false;
                    }
                }
                break;

            case R.id.btnAddPhoto:

                if (etPhotoName.getText().toString().length() < 2) {
                    etPhotoName.setError("enter photo name");

                } else if (sourceFile == null) {
                    Toast.makeText(this, "select photo", Toast.LENGTH_SHORT).show();
                } else {
                    ApiCallForAddPhotoToGallery();

                }
                break;
            case R.id.ivPhoto:
                checkImagePermission();
                break;

            case R.id.llBottomSheeDayPlan:
                changeTextColdrTab(R.color.purple_500, R.color.gry, R.color.gry, R.color.gry);
                changeImageColdrTab(R.mipmap.days_plan_selected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos);
                viewPager.setCurrentItem(0);
                break;
            case R.id.llBottomSheeReservs:
                changeTextColdrTab(R.color.gry, R.color.purple_500, R.color.gry, R.color.gry);
                changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reserv_selected, R.mipmap.payment, R.mipmap.photos);
                viewPager.setCurrentItem(1);

                break;
            case R.id.llBottomSheePayment:
                changeTextColdrTab(R.color.gry, R.color.gry, R.color.purple_500, R.color.gry);
                changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reservs, R.mipmap.payment_selected, R.mipmap.photos);
                viewPager.setCurrentItem(2);

                break;
            case R.id.llBottomSheePhotos:
                changeTextColdrTab(R.color.gry, R.color.gry, R.color.gry, R.color.purple_500);
                changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos_selected);
                viewPager.setCurrentItem(3);
                break;
            case R.id.ivCloseParticipants:
                participantsBottomSheet.setHideable(true);
                participantsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                actionMenu();
                break;
            case R.id.ivAddTripParticipant:
                if (aBooleanIsCreatedBy) {
                    startActivity(new Intent(this, AddUserTripParticpantActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());
                } else {
                    Toast.makeText(this, "Participant add only admin", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.ivPartiallyBack:
                bottomSheetBehavior.setDraggable(true);
                partiallyPaidTripBottomSheetBehavior.setHideable(true);
                partiallyPaidTripBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
        }
    }


    private void addTripDayBottomSheet() {

        //add activity
        ivAddActivityBack = llBottomSheetAddDayActivity.findViewById(R.id.ivAddActivityBack);
        ivActivityType = llBottomSheetAddDayActivity.findViewById(R.id.ivActivityType);

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

        //main bottom sheet
        llBottomSheetDayPlan = llActivityMoreBottomSheet.findViewById(R.id.llBottomSheeDayPlan);
        llBottomSheetReservs = llActivityMoreBottomSheet.findViewById(R.id.llBottomSheeReservs);
        llBottomSheetPayments = llActivityMoreBottomSheet.findViewById(R.id.llBottomSheePayment);
        llBottomSheetPhotos = llActivityMoreBottomSheet.findViewById(R.id.llBottomSheePhotos);

        ivBottomSheetDayPlan = llActivityMoreBottomSheet.findViewById(R.id.ivBottomSheeDayPlan);
        ivBottomSheetReservs = llActivityMoreBottomSheet.findViewById(R.id.ivBottomSheeReservs);
        ivBottomSheetPayments = llActivityMoreBottomSheet.findViewById(R.id.ivBottomSheePayment);
        ivBottomSheetPhotos = llActivityMoreBottomSheet.findViewById(R.id.ivBottomSheePhotos);

        tvBottomSheetDayPlan = llActivityMoreBottomSheet.findViewById(R.id.tvBottomSheeDayPlan);
        tvBottomSheetReservs = llActivityMoreBottomSheet.findViewById(R.id.tvBottomSheeReservs);
        tvBottomSheetPayments = llActivityMoreBottomSheet.findViewById(R.id.tvBottomSheePayment);
        tvBottomSheetPhotos = llActivityMoreBottomSheet.findViewById(R.id.tvBottomSheePhotos);


        llBottomSheetDayPlan.setOnClickListener(this);
        llBottomSheetReservs.setOnClickListener(this);
        llBottomSheetPayments.setOnClickListener(this);
        llBottomSheetPhotos.setOnClickListener(this);
        ivActivityType.setOnClickListener(this);

        //add activity
        ivAddActivityBack.setOnClickListener(this);
        ivAddActivity.setOnClickListener(this);
        tvAddActivity.setOnClickListener(this);
        etActivityDate.setOnClickListener(this);
        etActivityTime.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTextColdrTab(int dayPlan, int reservs, int payment, int photos) {
        tvBottomSheetDayPlan.setTextColor(getColor(dayPlan));
        tvBottomSheetReservs.setTextColor(getColor(reservs));
        tvBottomSheetPayments.setTextColor(getColor(payment));
        tvBottomSheetPhotos.setTextColor(getColor(photos));
    }

    private void changeImageColdrTab(int my_trip_unselected, int reservs, int payment, int photos) {
        ivBottomSheetDayPlan.setImageResource(my_trip_unselected);
        ivBottomSheetReservs.setImageResource(reservs);
        ivBottomSheetPayments.setImageResource(payment);
        ivBottomSheetPhotos.setImageResource(photos);
    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void initializeBottomSheet() {
        llBottomSheetAddDayActivity = llActivityMoreBottomSheet.findViewById(R.id.bottom_sheet_add_activity);
        llBottomSheetAddPayment = llActivityMoreBottomSheet.findViewById(R.id.bottom_sheet_add_payment);
        llBottomSheetPartiallyPaidTrip = llActivityMoreBottomSheet.findViewById(R.id.bottom_sheet_partially_paid_trip);

        llParticipantsCosts = llActivityMoreBottomSheet.findViewById(R.id.bottom_sheet_participants_costs);

        bottomSheetBehavior = BottomSheetBehavior.from(llActivityMoreBottomSheet);
        participantsBottomSheet = BottomSheetBehavior.from(llParticipantsBottomSheetBehaviorId);

        addActivityBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddDayActivity);
        addPaymentBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddPayment);
        partiallyPaidTripBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetPartiallyPaidTrip);
        participantCostsBottomSheetBehaior = BottomSheetBehavior.from(llParticipantsCosts);


        // set the bottom sheet callback state to hidden when you just start your app
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//        participantsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
//        participantsBottomSheet.setHideable(true);


        rvTripParticipants = llParticipantsBottomSheetBehaviorId.findViewById(R.id.rvTripParticipants);
        ivCloseParticipant = llParticipantsBottomSheetBehaviorId.findViewById(R.id.ivCloseParticipants);
        ivAddTripParticipant = llParticipantsBottomSheetBehaviorId.findViewById(R.id.ivAddTripParticipant);
        tvNoActiveTripFound = llParticipantsBottomSheetBehaviorId.findViewById(R.id.tvNoActiveTripFound);

        ivCloseParticipant.setOnClickListener(this);
        ivAddTripParticipant.setOnClickListener(this);


        tabLayout = llActivityMoreBottomSheet.findViewById(R.id.tabs);
        viewPager = llActivityMoreBottomSheet.findViewById(R.id.viewpager);
        ivAddActivity = llActivityMoreBottomSheet.findViewById(R.id.ivAddActivity);

        addTripDayBottomSheet();

        partiallyPaidBottomSheet();

        setupViewPagerForTabs(viewPager);


    }


    private void partiallyPaidBottomSheet() {

        ivBackPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.ivPartiallyBack);
        circularSeekBar = llBottomSheetPartiallyPaidTrip.findViewById(R.id.csPayment);
        tvPartiallyPaidPercentage = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPercentage);
        tvPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPartiallyPaid);


        tvPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPartiallyPaid);
        tvPayDate = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPayDate);
        tvPayDaysLeft = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvDaysLeft);
        tvParticipantsCostsCount = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvParticipantsCostsCount);

        viewpagerExpenditures = llBottomSheetPartiallyPaidTrip.findViewById(R.id.viewpagerExpenditures);
        tabsPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tabsPartiallyPaid);
        setupViewPagerForTabsPartiallyPaidTrip(viewpagerExpenditures);
        tabsPartiallyPaid.setupWithViewPager(viewpagerExpenditures);


        circularSeekBar.setEnabled(false);

        ivBackPartiallyPaid.setOnClickListener(this);

    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        spUserName.setSelection(position);
        Toast.makeText(TripDetailScreenActivity.this, userParticipaintsList.get(position).getUserid() + "", Toast.LENGTH_LONG).show();

        strPaymentUser = String.valueOf(userParticipaintsList.get(position).getUserid());

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "nothing select", Toast.LENGTH_SHORT).show();
    }


    private void setupViewPagerForTabs(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllTripDayPlanFragment(), "Days Plan");
        adapter.addFragment(new ReservesFragment(), "Rsvp");
        adapter.addFragment(new PaymentsFragment(), "Payments");
        adapter.addFragment(new PhotosFragment(), "Photos");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                if (position == 0 || position == 2 || position == 3) {
                    ivAddActivity.setVisibility(View.VISIBLE);
                } else {
                    ivAddActivity.setVisibility(View.GONE);
                }
                if (position == 0) {
                    changeTextColdrTab(R.color.purple_500, R.color.gry, R.color.gry, R.color.gry);
                    changeImageColdrTab(R.mipmap.days_plan_selected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos);
                }
                if (position == 1) {
                    changeTextColdrTab(R.color.gry, R.color.purple_500, R.color.gry, R.color.gry);
                    changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reserv_selected, R.mipmap.payment, R.mipmap.photos);
                }
                if (position == 2) {
                    changeTextColdrTab(R.color.gry, R.color.gry, R.color.purple_500, R.color.gry);
                    changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reservs, R.mipmap.payment_selected, R.mipmap.photos);
                }
                if (position == 3) {
                    changeTextColdrTab(R.color.gry, R.color.gry, R.color.gry, R.color.purple_500);
                    changeImageColdrTab(R.mipmap.day_plane_gry, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos_selected);
                }

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

    private void setupViewPagerForTabsPartiallyPaidTrip(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getSupportFragmentManager());
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

    private void checkImagePermission() {
        Dexter.withContext(this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                chooseAction();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                aBooleanAddImage = true;
            }
        }).check();
    }


    void addPhotoDialog() {
        addPhotoDialog = new Dialog(this);
        addPhotoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPhotoDialog.setCancelable(true);
        addPhotoDialog.setContentView(R.layout.custom_add_photo_layout);
        ivGalleryPhoto = addPhotoDialog.findViewById(R.id.ivPhoto);
        etPhotoName = addPhotoDialog.findViewById(R.id.etPhotoName);
        btnAddPhoto = addPhotoDialog.findViewById(R.id.btnAddPhoto);
        ivGalleryPhoto.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);

        addPhotoDialog.show();
        AlertUtils.doKeepDialog(addPhotoDialog);
        addPhotoDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        addPhotoDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                aBooleanAddImage = true;
            }
        });

    }


    void chooseAction() {
        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
//        File dir = FileUtils.getDiskCacheDir(this, "temp");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        String name = StringHelper.getDateRandomString() + ".png";
//        sourceFile = new File(dir, name);
//        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sourceFile));
//
//
//        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        pickIntent.setType("image/*");
//
//        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.choose_photo));
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{captureImageIntent});
//
//        startActivityForResult(chooserIntent, REQUEST_CODE_SELECT_PICTURE);
    }


    boolean checkActionType(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            if ((data.getData() == null) && (data.getClipData() == null)) {
                isCamera = true;
            } else {
                isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }
        return isCamera;
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? Uri.fromFile(sourceFile) : data.getData();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        aBooleanAddImage = true;
        if (resultCode == RESULT_OK) {

            Uri imageUrdi = getPickImageResultUri(data);

            ivGalleryPhoto.setImageURI(imageUrdi);
            sourceFile = new File(imageUrdi.getPath());


            switch (requestCode) {


                case REQUEST_CODE_SELECT_PICTURE:
                    if (checkActionType(data)) { // Camera
                        Uri imageUri = getPickImageResultUri(data);
                        File originFile = new File(imageUri.getPath());
                        sourceFile = FileUtils.compressImage(this, originFile);

                    } else {  // Gallery
                        if (data.getData() != null) {
                            Uri uri = data.getData();
                            sourceFile = FileUtils.getFile(this, uri);
//                            sourceFile = FileUtils.compressImage(this, originFile);

                        }
                    }

                    ivGalleryPhoto.setImageURI(Uri.fromFile(sourceFile));

                    Log.d("zma image file", "" + sourceFile);


                    break;
            }
        }
    }


    private void getPaymentExpenses() {
        dialog.show();

        groupExpendituresItems.clear();
        personalExpendituresItems.clear();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripIDForUpdation(this), AppRepository.mUserID(this));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
//                    assert response.body() != null;
                    try {
                        tvPartiallyPaidPercentage.setText(NumberFormatUtil.FormatPercentage(response.body().getData().getPaidPercent()));
                        circularSeekBar.setProgress(Float.parseFloat(NumberFormatUtil.FormatPercentageShowCircle(response.body().getData().getPaidPercent())));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tvPartiallyPaid.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());
                    tvPayDate.setText(DateUtills.getDateFormate(response.body().getData().getTripdate()));
                    tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(strTripDate)) + " days left");


                    tvParticipantsCostsCount.setText(response.body().getData().getFullyPaidUsers() + " Paid," + response.body().getData().getPartialPaidUsers() + " Partially," + response.body().getData().getNotPaidUsers() + " Not");
                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
                    personalExpendituresItems.addAll(response.body().getData().getPersonalExpenditures());
                    ConnectExpenditures.setMyBooleanListener(true);

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void ApiCallForAddPhotoToGallery() {
        dialog.show();
        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("photo", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripIDForUpdation(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTitle = RequestBody.create(etPhotoName.getText().toString(), MediaType.parse("multipart/form-data"));
        RequestBody BodyTime = RequestBody.create(DateUtills.getCurrentDate("hh:mm"), MediaType.parse("multipart/form-data"));
        RequestBody BodyDate = RequestBody.create(DateUtills.getCurrentDate("yyyy-MM-dd"), MediaType.parse("multipart/form-data"));

        Call<AddPhotoToGalleryResponse> addPhotoToGalleryResponseCall = BaseNetworking.ApiInterface().addPhotoToGallery(BodyTripId, BodyTitle, BodyTime, BodyDate, CoverImage, BodyName);
        addPhotoToGalleryResponseCall.enqueue(new Callback<AddPhotoToGalleryResponse>() {
            @Override
            public void onResponse(Call<AddPhotoToGalleryResponse> call, Response<AddPhotoToGalleryResponse> response) {

                if (response.isSuccessful()) {
                    addPhotoDialog.dismiss();
                    sourceFile = null;
                    Connect.setMyBoolean(true);
                    dialog.dismiss();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(TripDetailScreenActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddPhotoToGalleryResponse> call, Throwable t) {
                Toast.makeText(TripDetailScreenActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void ApiCallGetUserTrip() {
        dialog.show();
        userParticipaintsList.clear();
        userParticipaintsCircleList.clear();
        paymentUserParticipaintsList.clear();

        try {
            AddTripDataModel addTripDataModel = new AddTripDataModel();
            addTripDataModel.setEmail(AppRepository.mEmail(TripDetailScreenActivity.this));
            addTripDataModel.setTripid(Long.valueOf(AppRepository.mTripIDForUpdation(this)));
            addTripDataModel.setUserid((long) userID);
            addTripDataModel.setName(AppRepository.mUserName(TripDetailScreenActivity.this));
            addTripDataModel.setPicture(AppRepository.mUserProfileImage(TripDetailScreenActivity.this));
            paymentUserParticipaintsList.add(addTripDataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripIDForUpdation(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userParticipaintsList.addAll(response.body().getData());
                    userParticipaintsCircleList.addAll(response.body().getData());


                    for (AddTripDataModel addTripDataModel : response.body().getData()) {
//                        paymentUserParticipaintsList
                    }
                    paymentUserParticipaintsList.addAll(response.body().getData());

                    Log.d("zmaUserSecond", userParticipaintsList.size() + "");

                    CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(TripDetailScreenActivity.this, userParticipaintsList);
                    spUserName.setAdapter(customAdapter);


                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(TripDetailScreenActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aBooleanResfreshGetUserTrip) {
            aBooleanResfreshGetUserTrip = false;
            tripParticipantsAdapter.notifyDataSetChanged();
            userTripCircleImagesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddActivityBack() {
        addActivityBottomSheetBehavior.setHideable(true);
        addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onPaymentBack() {
        bottomSheetBehavior.setDraggable(true);
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void goEditActivityDayPlan() {
        addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerActivity, AddAndEditDayPlaneFragment.newInstance())
                .commitNow();
    }

    @Override
    public void onPaymentAdddCallBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        getPaymentExpenses();


        Toast.makeText(this, "paymentresrsh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onParticipantBack() {
        bottomSheetBehavior.setDraggable(true);
        participantCostsBottomSheetBehaior.setHideable(true);
        participantCostsBottomSheetBehaior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void goClickRecentTransaction() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_participantCosts, ParticipantCostsTabsFragment.newInstance())
                .commitNow();
        participantCostsBottomSheetBehaior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setDraggable(false);
        participantCostsBottomSheetBehaior.setDraggable(false);

    }

    @Override
    public void goClickPartiallyPaidTrip() {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.containerPartiallyPaidTrip, PartiallyPaidTripFragment.newInstance())
//                .commitNow();
        partiallyPaidTripBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setDraggable(false);
        partiallyPaidTripBottomSheetBehavior.setDraggable(false);

    }


    @Override
    public void onBackPressed() {
        if (!bottomSheetBehavior.isHideable()) {
            bottomSheetBehavior.setHideable(true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
            Animatoo.animateZoom(TripDetailScreenActivity.this);
            finish();
        }
    }



    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }
}

