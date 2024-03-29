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
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.api.TripEditedCallback;
import com.techease.groupiiapplication.dataModel.getSingleTrip.GetSingleTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getAllTripDay.AllTripDayDataModel;
import com.techease.groupiiapplication.mapView.MapViewActivity;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.gallery.Connect;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.adapter.tripes.UserTripCircleImagesAdapter;
import com.techease.groupiiapplication.adapter.tripDetail.TripParticipantsAdapter;
import com.techease.groupiiapplication.api.ApiCallback;
import com.techease.groupiiapplication.api.ApiClass;
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
import com.techease.groupiiapplication.interfaceClass.EditPaymentCallBackListener;
import com.techease.groupiiapplication.interfaceClass.ParticipantBackListener;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.AddTrip.NewTripStepTwoAddDetailActivity;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.fragment.payment.AddPaymentFragment;
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
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.FileUtils;
import com.techease.groupiiapplication.utils.GeneralUtills;
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
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import me.tankery.lib.circularseekbar.CircularSeekBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailScreenActivity extends AppCompatActivity implements View.OnClickListener, AddPaymentOnBackListener, AddActivityBackListener, EditActivityDayPlanListener, AddPaymentCallBackListener, ParticipantBackListener, ClickRecentTransactionListener, ClickPartiallyPaidTripListener, EditPaymentCallBackListener {

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
    public static ArrayList<AddTripDataModel> userList = new ArrayList<>();
    public static ArrayList<AddTripDataModel> paymentUserParticipaintsList = new ArrayList<>();
    public static ArrayList<AddTripDataModel> userParticipaintsCircleList = new ArrayList<>();

    public ArrayList<AddTripDataModel> userListRegister = new ArrayList<>();
    public ArrayList<AddTripDataModel> userListNotRegister = new ArrayList<>();


    RecyclerView rvTripParticipants;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public static boolean aBooleanResfreshGetUserTrip = false;
    @BindView(R.id.tvDaysLeft)
    TextView tvDaysLeft;

    TextView tvPaymentsDaysLeft;
    public static boolean aBooleanAddImage = true, aBooleanIsCreatedBy = false;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_PICTURE = 3;

    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack, ivActivityType;
    TextView tvAddActivity;
    SwitchCompat switchCompatGroupActivity;

    ViewPager viewpagerExpenditures;
    TabLayout tabsPartiallyPaid;


    TextView tvPartiallyPaidPaymente, tvPayDate, tvPayDaysLeft, tvParticipantsCostsCount, tvPartiallyPaidPercentage, tvNoActiveTripFound;
    CircularSeekBar circularSeekBar;
    ImageView ivBackPartiallyPaid, ivAddTripParticipant;
    int anIntViewPagerPosition = 0;
    File sourceFile;
    Dialog addPhotoDialog;

    String strTitle, strTripDescription, strLocation, strTripType, strTripStartDate, strTripEndDate, strTripPayDate, strPhoto;
    Dialog dialog;

    EditText etPhotoName;
    ImageView ivGalleryPhoto, ivCloseParticipant;
    Button btnAddPhoto;

    @BindView(R.id.spUserName)
    Spinner spUserName;

    String tripID;
    int userID;

    boolean aBooleanEditScreen;
    ArrayList<String> chatImageFiles = new ArrayList<>();

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

        //boolean use in Add new trip step four activity
        AppRepository.mPutValue(this).putBoolean("add_payment_on_step_four", false).commit();
        Bundle bundle = getIntent().getExtras();
        strTitle = bundle.getString("title");
        strTripDescription = bundle.getString("description");
        strPhoto = bundle.getString("image");
        strTripStartDate = bundle.getString("start_date");
        strTripEndDate = bundle.getString("end_date");
        strTripPayDate = bundle.getString("pay_date");
        strLocation = bundle.getString("location");
        strTripType = bundle.getString("trip_type");
        aBooleanEditScreen = bundle.getBoolean("edit");
        aBooleanIsCreatedBy = bundle.getBoolean("is_createdby");

        Constants.TRIP_NAME = strTitle;
        AppRepository.mPutValue(this).putString("trip_start_date", DateUtills.changeDateTripStartDateFormate(strTripStartDate)).commit();
        AppRepository.mPutValue(this).putString("trip_end_date", DateUtills.changeDateTripStartDateFormate(strTripEndDate)).commit();

        userID = AppRepository.mUserID(this);
        tripID = AppRepository.mTripIDForUpdation(this);


//        Log.d("zma user id", "" + userID);
//        Log.d("zma trip id", "" + tripID);


        Glide.with(this).load(strPhoto).into(ivTripImage);
        tvTripTitle.setText(strTitle);
        tvTripTypeName.setText(strTripType);
        tvDescription.setText(strTripDescription);
        tvLocation.setText(strLocation);
        tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(strTripStartDate)) + " days left");

        if (strTripType.equals("Past Trip")) {
            tvDaysLeft.setText("Complete trip");
        }
        stringArrayList = bundle.getStringArrayList("users");
        userTripCircleImagesAdapter = new UserTripCircleImagesAdapter(TripDetailScreenActivity.this, TripFragment.userList);
        rvImages.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvImages.addItemDecoration(new GeneralUtills.OverlapDecoration());
        rvImages.setHasFixedSize(true);
        rvImages.setAdapter(userTripCircleImagesAdapter);


        getPaymentExpenses();
        initializeBottomSheet();
        ApiCallGetUserTrip();


        bottomSheetBehavior.setHideable(true);


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
        tvCancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());

        TextView tvCancelTrip = mBottomSheetDialog.findViewById(R.id.tvCancelTrip);
        tvCancelTrip.setOnClickListener(v -> {


            if (aBooleanIsCreatedBy) {
                BottomSheetMaterialDialog mBottomSheetDialogd = new BottomSheetMaterialDialog.Builder(this)
                        .setTitle("Cancel Trip?")
                        .setMessage("Are you sure want to cancel this trip?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialogInterface, which) -> {
                            ApiCallback apiCallback = success -> {
                                TripDetailScreenActivity.this.finish();
                                ConnectChatResfresh.setMyBoolean(true);
                                Toast.makeText(TripDetailScreenActivity.this, "Trip cancel successfully", Toast.LENGTH_SHORT).show();
                                return false;
                            };
                            ApiClass.apiCallForTripDelete(TripDetailScreenActivity.this, apiCallback, tripID);
                            dialogInterface.dismiss();
                        })
                        .setNegativeButton("No", (dialogInterface, which) -> dialogInterface.dismiss())
                        .build();

                // Show Dialog
                mBottomSheetDialogd.show();
            } else {
                Toast.makeText(TripDetailScreenActivity.this, "Trip cancel admin only", Toast.LENGTH_SHORT).show();
            }

        });


        TextView tvShareTrip = mBottomSheetDialog.findViewById(R.id.tvShareTrip);
        tvShareTrip.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "\n" + AppRepository.mUserName(this) + " you wanted the upcoming trip\n\n";
                shareMessage = shareMessage + "https://www.grouppii.com/GrouppiiApp?tripid=" + AppRepository.mTripIDForUpdation(this);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        });


        TextView tvEditTrip = mBottomSheetDialog.findViewById(R.id.tvEditTrip);
        tvEditTrip.setOnClickListener(v -> {
            Constants.aBooleanRefreshAllTripApi = true;

            if (aBooleanIsCreatedBy) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(this, NewTripStepTwoAddDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("image", strPhoto);
                bundle.putString("title", strTitle);
                bundle.putString("trip_type", strTripType);
                bundle.putString("start_date", strTripStartDate);
                bundle.putString("end_date", strTripEndDate);
                bundle.putString("pay_date", strTripPayDate);
                bundle.putBoolean("edit", true);
                bundle.putString("description", strTripDescription);
                bundle.putString("location", strLocation);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.admin_can_edit_trip_settings), Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvParticipants = mBottomSheetDialog.findViewById(R.id.tvManageParticipants);
        tvParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                participantsBottomSheet.setDraggable(false);
                participantsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);

                linearLayoutManager = new LinearLayoutManager(TripDetailScreenActivity.this);
                tripParticipantsAdapter = new TripParticipantsAdapter((TripDetailScreenActivity.this), userList, aBooleanIsCreatedBy, tvNoActiveTripFound);
                rvTripParticipants.setLayoutAnimation(AnimationRVUtill.RecylerViewAnimation(TripDetailScreenActivity.this));
                rvTripParticipants.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, RecyclerView.VERTICAL, false));
                rvTripParticipants.setAdapter(tripParticipantsAdapter);

                if (userList.size() == 0) {
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

//                addActivityBottomSheetBehavior.setDraggable(false);
//                bottomSheetBehavior.setDraggable(false);

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
                    addActivityBottomSheetBehavior.setDraggable(false);
                    bottomSheetBehavior.setDraggable(false);
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.containerActivity, AddAndEditDayPlaneFragment.newInstance(null, "add"))
                            .commitNow();
                } else if (anIntViewPagerPosition == 1) {
                    if (aBooleanIsCreatedBy) {

                        startActivity(new Intent(this, AddReservsActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());


//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, AddReservsFragment.newInstance())
//                                .commitNow();
//                        addPaymentBottomSheetBehavior.setDraggable(false);
//                        bottomSheetBehavior.setDraggable(false);
//                        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.containerPayment, AddPaymentsTabsFragment.newInstance())
//                                .commitNow();
                    } else {
                        Toast.makeText(this, "Rsvp add only admin", Toast.LENGTH_SHORT).show();
                    }

                } else if (anIntViewPagerPosition == 2) {
                    if (aBooleanIsCreatedBy) {
                        addPaymentBottomSheetBehavior.setDraggable(false);
                        bottomSheetBehavior.setDraggable(false);
                        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containerPayment, AddPaymentFragment.newInstance("null", "null"))
                                .commitNow();
                    } else {
                        Toast.makeText(this, "payment add only admin", Toast.LENGTH_SHORT).show();
                    }

                } else if (anIntViewPagerPosition == 3) {

                    checkImagePermission();

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
        tvPartiallyPaidPaymente = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPartiallyPaid);


        tvPayDate = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPayDate);
        tvPayDaysLeft = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvDaysLeft);
        tvParticipantsCostsCount = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvParticipantsCostsCount);
        tvPaymentsDaysLeft = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvDaysLeft);

        viewpagerExpenditures = llBottomSheetPartiallyPaidTrip.findViewById(R.id.viewpagerExpenditures);
        tabsPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tabsPartiallyPaid);
        setupViewPagerForTabsPartiallyPaidTrip(viewpagerExpenditures);
        tabsPartiallyPaid.setupWithViewPager(viewpagerExpenditures);

        circularSeekBar.setEnabled(false);
        ivBackPartiallyPaid.setOnClickListener(this);

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


                if (position == 0 || position == 1 || position == 2 || position == 3) {
                    ivAddActivity.setVisibility(View.VISIBLE);
                } else {
//                    ivAddActivity.setVisibility(View.GONE);
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
                if (report.getGrantedPermissionResponses().size() == 4) {
                    chooseAction();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                aBooleanAddImage = true;
            }
        }).check();
    }


    void chooseAction() {

        ArrayList<String> uris = new ArrayList<>();
        Options options = Options.init()
                .setRequestCode(100)                                           //Request code for activity results
                .setCount(1)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start
                .setPreSelectedUrls(uris)                               //Pre selected Image Urls
                .setSpanCount(1)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/Grouppii/files");                                       //Custom Path For media Storage

        Pix.start(this, options);
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

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            chatImageFiles.clear();
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            chatImageFiles.addAll(returnValue);
            sourceFile = new File(chatImageFiles.get(0));
//
            ApiCallForAddPhotoToGallery();


        } else if (resultCode == RESULT_OK) {

            Uri imageUrdi = getPickImageResultUri(data);

            try {
                ivGalleryPhoto.setImageURI(imageUrdi);
                sourceFile = new File(imageUrdi.getPath());

            } catch (Exception e) {

            }


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

        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripIDForUpdation(this), AppRepository.mUserID(this));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    try {
                        tvPartiallyPaidPaymente.setText("$" + NumberFormatUtil.PaymentFormat(response.body().getData().getRecievedpayment()) + " / " + "$" + NumberFormatUtil.PaymentFormat(response.body().getData().getTotalpayment()));

                        tvPartiallyPaidPercentage.setText(NumberFormatUtil.FormatPercentage(response.body().getData().getPaidPercent()));
                        circularSeekBar.setProgress(Float.parseFloat(NumberFormatUtil.FormatPercentageShowCircle(response.body().getData().getPaidPercent())));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (response.body().getData().getRecievedpayment().equals("0.0"))
//                    tvPartiallyPaid.setText(response.body().getData().getPartialPaidUsers() + "/" + response.body().getData().getTotalUsers());
                        tvPayDate.setText(DateUtills.getDateFormate(response.body().getData().getTripdate()));
                    tvPaymentsDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(response.body().getData().getTripdate())) + " days left");

                    tvParticipantsCostsCount.setText(response.body().getData().getFullyPaidUsers() + " Paid," + response.body().getData().getPartialPaidUsers() + " Partially," + response.body().getData().getNotPaidUsers() + " Not");

                    groupExpendituresItems.clear();
                    personalExpendituresItems.clear();

                    groupExpendituresItems.addAll(response.body().getData().getGroupExpenditures());
                    personalExpendituresItems.addAll(response.body().getData().getPersonalExpenditures());
                    ConnectExpenditures.setMyBooleanListener(true);

                }
            }

            @Override
            public void onFailure(Call<GetPaymentExpensesResponse> call, Throwable t) {
            }
        });
    }


    private void ApiCallForAddPhotoToGallery() {

        dialog.show();
        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("photo", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripIDForUpdation(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTitle = RequestBody.create(sourceFile.getAbsoluteFile().getName(), MediaType.parse("multipart/form-data"));
        RequestBody BodyTime = RequestBody.create(DateUtills.getCurrentDate("hh:mm"), MediaType.parse("multipart/form-data"));
        RequestBody BodyDate = RequestBody.create(DateUtills.getCurrentDate("yyyy-MM-dd"), MediaType.parse("multipart/form-data"));

        Call<AddPhotoToGalleryResponse> addPhotoToGalleryResponseCall = BaseNetworking.ApiInterface().addPhotoToGallery(BodyTripId, BodyTitle, BodyTime, BodyDate, CoverImage, BodyName);
        addPhotoToGalleryResponseCall.enqueue(new Callback<AddPhotoToGalleryResponse>() {
            @Override
            public void onResponse(Call<AddPhotoToGalleryResponse> call, Response<AddPhotoToGalleryResponse> response) {

                if (response.isSuccessful()) {
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
        userList.clear();
        userParticipaintsCircleList.clear();
        paymentUserParticipaintsList.clear();

        try {
            AddTripDataModel addTripDataModel = new AddTripDataModel();
            addTripDataModel.setEmail(AppRepository.mEmail(TripDetailScreenActivity.this));
            addTripDataModel.setTripid(Long.valueOf(AppRepository.mTripIDForUpdation(this)));
            addTripDataModel.setUserid((long) userID);
            addTripDataModel.setName(AppRepository.mUserName(TripDetailScreenActivity.this));
            addTripDataModel.setPicture(AppRepository.mUserProfileImage(TripDetailScreenActivity.this));
            addTripDataModel.setLatitude(AppRepository.mLat(TripDetailScreenActivity.this));
            addTripDataModel.setLongitude(AppRepository.mLng(TripDetailScreenActivity.this));

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
                    userList.addAll(response.body().getData());
                    userParticipaintsCircleList.addAll(response.body().getData());


                    userListNotRegister.clear();
                    userListRegister.clear();
                    for (AddTripDataModel addTripDataModel : userList) {
                        if (addTripDataModel.getType().equals("registered")) {
                            userListRegister.add(addTripDataModel);
                        }
                        if (addTripDataModel.getType().equals("notregistered")) {

                            userListNotRegister.add(addTripDataModel);
                        }
                    }


                    userList.clear();
                    userList.addAll(userListRegister);
                    userList.addAll(userListNotRegister);

                    for (AddTripDataModel addTripDataModel : userList) {
                        Log.d("zmanotlist", addTripDataModel.getName());

                    }

                    ArrayList<AddTripDataModel> addTripDataModelsMain = new ArrayList<>();
                    for (AddTripDataModel userParticipaintsList : response.body().getData()) {
                        if (userParticipaintsList.getSharedCost() == 1) {
                            AddTripDataModel addTripDataModel = new AddTripDataModel();

                            if (userParticipaintsList.getName() == null) {
                                addTripDataModel.setName(userParticipaintsList.getPhone());
                            } else {
                                addTripDataModel.setName(userParticipaintsList.getName());
                            }
                            addTripDataModel.setEmail(userParticipaintsList.getEmail());
                            addTripDataModel.setTripid(userParticipaintsList.getTripid());
                            addTripDataModel.setLatitude(userParticipaintsList.getLatitude());
                            addTripDataModel.setLongitude(userParticipaintsList.getLongitude());
                            addTripDataModel.setUserid(userParticipaintsList.getUserid());
                            addTripDataModelsMain.add(addTripDataModel);
                        }
                    }

                    paymentUserParticipaintsList.addAll(addTripDataModelsMain);


                    CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(TripDetailScreenActivity.this, userList);
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
        if (Constants.aBooleanDetailTripScreenRefresh) {
            Constants.aBooleanDetailTripScreenRefresh = false;

            try {

                TripEditedCallback tripEditedCallback = response -> {
                    if (response.isSuccessful()) {
                        Glide.with(TripDetailScreenActivity.this).load(response.body().getData().getCoverimage()).into(ivTripImage);
                        tvTripTitle.setText(response.body().getData().getTitle());
                        tvDescription.setText(response.body().getData().getDescription());
                        tvLocation.setText(response.body().getData().getLocation());
                        tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(response.body().getData().getFromdate())) + " days left");

                        strTripStartDate = response.body().getData().getFromdate();
                        strTripEndDate = response.body().getData().getTodate();
                        strTripPayDate = response.body().getData().getPayDate();

                        AppRepository.mPutValue(TripDetailScreenActivity.this).putString("getFromdate", strTripStartDate).commit();
                    }
                    return false;
                };
                ApiClass.GetTripById(AppRepository.mTripIDForUpdation(this), this, dialog, tripEditedCallback);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (aBooleanResfreshGetUserTrip) {
            aBooleanResfreshGetUserTrip = false;
            tripParticipantsAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onAddActivityBack() {
        addActivityBottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setDraggable(true);
        addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onPaymentBack() {
        bottomSheetBehavior.setDraggable(true);
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void goEditActivityDayPlan(AllTripDayDataModel allTripDayDataModel) {
        addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerActivity, AddAndEditDayPlaneFragment.newInstance(allTripDayDataModel, "Update"))
                .commitNow();
    }

    @Override
    public void onPaymentAdddCallBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        getPaymentExpenses();
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

    @Override
    public void onEditPaymentCallBack() {
        addPaymentBottomSheetBehavior.setHideable(true);
        addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        getPaymentExpenses();

    }


}

