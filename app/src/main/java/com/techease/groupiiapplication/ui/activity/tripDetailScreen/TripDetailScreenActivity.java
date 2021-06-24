package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
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

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.gallery.Connect;
import com.techease.groupiiapplication.adapter.tripDetail.CustomSpinnerAdapter;
import com.techease.groupiiapplication.adapter.tripes.TabsViewPagerAdapter;
import com.techease.groupiiapplication.adapter.tripes.UserTripCircleImagesAdapter;
import com.techease.groupiiapplication.adapter.tripDetail.TripParticipantsAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPaymentExpenses.AddPaymentResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GetPaymentExpensesResponse;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.GroupExpendituresItem;
import com.techease.groupiiapplication.dataModel.tripDetial.getPaymentExpenses.PersonalExpendituresItem;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.ChatsActivity;
import com.techease.groupiiapplication.ui.activity.Map.MapViewActivity;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.getExpenditureExpensesListener.ConnectExpenditures;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.paymentClickInterface.ConnectPaymentClick;
import com.techease.groupiiapplication.ui.activity.tripDetailScreen.paymentClickInterface.ConnectionBooleanClickChangedListener;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.GropExpendituresTripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.tripExpenditures.PersonalExpendituresFragment;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.AllTripDayFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PaymentsFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.PhotosFragment;
import com.techease.groupiiapplication.ui.fragment.tripDetialScreen.ReservesFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.FileUtils;
import com.techease.groupiiapplication.utils.GeneralUtills;

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

public class TripDetailScreenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


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

    LinearLayout llTaxi, llBus, llReserv, llFlight;

    LinearLayout llBottomSheetDayPlan, llBottomSheetReservs, llBottomSheetPayments, llBottomSheetPhotos;
    ImageView ivBottomSheetDayPlan, ivBottomSheetReservs, ivBottomSheetPayments, ivBottomSheetPhotos;
    TextView tvBottomSheetDayPlan, tvBottomSheetReservs, tvBottomSheetPayments, tvBottomSheetPhotos;

    public static List<GroupExpendituresItem> groupExpendituresItems = new ArrayList<>();
    public static List<PersonalExpendituresItem> personalExpendituresItems = new ArrayList<>();


    boolean valid = true;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llActivityMoreBottomSheet;

    @BindView(R.id.llParticipantsBottomSheetBehaviorId)
    LinearLayout llParticipantsBottomSheetBehaviorId;

    LinearLayout llBottomSheetAddDayActivity, llBottomSheetAddPayment, llBottomSheetPartiallyPaidTrip, llPaymentMethod;
    BottomSheetBehavior bottomSheetBehavior, addActivityBottomSheetBehavior, addPaymentBottomSheetBehavior, partiallyPaidTripBottomSheetBehavior, participantsBottomSheet;

    public static TripParticipantsAdapter tripParticipantsAdapter;
    public static ArrayList<AddTripDataModel> userParticipaintsList = new ArrayList<>();

    RecyclerView rvTripParticipants;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @BindView(R.id.tvDaysLeft)
    TextView tvDaysLeft;
    boolean aBooleanAddImage = true;
    BottomNavigationView bottomNavigationView;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_PICTURE = 3;


    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote, tillPaymentTitle, tillPaymentDate, tillPaymentAmount, tillShortDescription;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote, etPaymentTitle, etPaymentDate, etPaymentAmount, etShortDescription;
    ImageView ivAddActivity, ivAddActivityBack, ivActivityType, ivAddPaymentBack, ivType;
    ImageView ivVisa, ivMasterCard, ivAmericanCard, ivJcb;
    TextView tvAddActivity, tvAddPayment;
    SwitchCompat switchCompatGroupActivity, swAddGroupPayment;


    TextView tvPartiallyPaid, tvPayDate, tvPayDaysLeft, tvParticipantsCostsCount, tvPartiallyPaidPercentage;
    CircularSeekBar circularSeekBar;
    ImageView ivBackPartiallyPaid, ivPartiallyPaid,ivAddTripParticipant;
    TabLayout tabsPartiallyPaid;
    ViewPager viewpagerExpenditures;

//    Spinner spUserName;


    int anIntViewPagerPosition = 0;
    File sourceFile;


    Dialog addPhotoDialog, addActivityTypeDialog;

    String strTripDate, strActivityTitle, strActivityDate, strActivityTime, strActivityNote, strTitle, strPhoto, strPaymentTitle, strPaymentDate, strPaymentAmount, strPaymentShortDescription, strPaymentMethod = "VISA", strPaymentUser;

    Dialog dialog;

    String strIsPersonal = "0", strActivityType="Flight", strPersonal = "1";
    EditText etPhotoName;
    ImageView ivGalleryPhoto, ivCloseParticipant;
    Button btnAddPhoto;

    String strPartiallyPaidPercentage, strPartiallyPaid, strPayDate, strParticipantsCostsCount;
    int seekbarProgress;

    @BindView(R.id.spUserName)
    Spinner spUserName;


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
        dialog = AlertUtils.createProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        strTitle = bundle.getString("title");
        strPhoto = bundle.getString("image");
        strTripDate = bundle.getString("date");
        tvDaysLeft.setText(DateUtills.getTripDetailDayleft(DateUtills.changeDateFormate(strTripDate))+" days left");

        Log.d("zma user id", "" + AppRepository.mUserID(this));
        Log.d("zma trip id", "" + AppRepository.mTripId(this));


        Glide.with(this).load(strPhoto).into(ivTripImage);
        tvTripTitle.setText(strTitle);
        tvTripTypeName.setText(bundle.getString("trip_type"));
        tvDescription.setText(bundle.getString("description"));
        tvLocation.setText(bundle.getString("location"));

        stringArrayList = bundle.getStringArrayList("users");
        Log.d("zma array", String.valueOf(stringArrayList));

        rvImages.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvImages.addItemDecoration(new GeneralUtills.OverlapDecoration());
        rvImages.setHasFixedSize(true);
        rvImages.setAdapter(new UserTripCircleImagesAdapter(TripDetailScreenActivity.this, TripFragment.userList));


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
                tripParticipantsAdapter = new TripParticipantsAdapter((TripDetailScreenActivity.this), userParticipaintsList);
                rvTripParticipants.setLayoutManager(new LinearLayoutManager(TripDetailScreenActivity.this, RecyclerView.VERTICAL, false));
                rvTripParticipants.setAdapter(tripParticipantsAdapter);

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivMenu, R.id.tvLocation, R.id.tvMore, R.id.ivMore, R.id.cvMenu, R.id.ivChat, R.id.llDayPlan, R.id.llReservs, R.id.llPayment, R.id.llPhoto})

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
                bundle.putString("tripId", AppRepository.mTripId(this));
                bundle.putString("toUserId", "" + AppRepository.mUserID(this));
                bundle.putString("type", "group");
                bundle.putString("picture", strPhoto);


                intent.putExtras(bundle);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;
            case R.id.tvLocation:
                startActivity(new Intent(this, MapViewActivity.class));
                break;
            case R.id.ivMore:
            case R.id.tvMore:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llDayPlan:
                viewPager.setCurrentItem(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llReservs:
                viewPager.setCurrentItem(1);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llPayment:
                viewPager.setCurrentItem(2);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llPhoto:
                viewPager.setCurrentItem(3);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case R.id.ivAddActivity:
                if (anIntViewPagerPosition == 0) {
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (anIntViewPagerPosition == 2) {
                    addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (anIntViewPagerPosition == 3) {

                    if (aBooleanAddImage) {
                        addPhotoDialog();
                        aBooleanAddImage = false;
                    }
                }
                break;
            case R.id.tvActivityAdd:
                if (isValid()) {
                    ApiCallForAddDayActivity();
                }
                break;

            case R.id.tvPaymentAdd:
                if (isValidAddPayment()) {
                    ApiCallForAddPayment();
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

            case R.id.ivAddPaymentBack:
                addPaymentBottomSheetBehavior.setHideable(true);
                addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;

            case R.id.etAddPaymentDate:
                DateUtills.GetDatePickerDialog(etPaymentDate, this);
                break;

            case R.id.ivAddActivityBack:
                addActivityBottomSheetBehavior.setHideable(true);
                addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.etAddActivityDate:
                DateUtills.GetDatePickerDialog(etActivityDate, this);
                break;
            case R.id.etActivityTime:
                DateUtills.GetTimeDialog(etActivityTime, this);
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
                startActivity(new Intent(this, AddUserTripParticpantActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());
                break;
            case R.id.ivActivityType:
            case R.id.ivType:
                activityTripTypeDialog();
                break;
            case R.id.llTaxi:
                ivActivityType.setImageResource(R.mipmap.taxi_wheel);
                ivType.setImageResource(R.mipmap.taxi_wheel);
                strActivityType = "Taxi";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llFlight:
                ivActivityType.setImageResource(R.mipmap.flight);
                ivType.setImageResource(R.mipmap.flight);
                strActivityType = "Flight";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llBus:
                ivActivityType.setImageResource(R.mipmap.transfer);
                ivType.setImageResource(R.mipmap.transfer);
                strActivityType = "Bus";
                addActivityTypeDialog.dismiss();

                break;
            case R.id.llReserv:
                ivActivityType.setImageResource(R.mipmap.flight);
                ivActivityType.setImageResource(R.mipmap.reserv_selected);
                strActivityType = "Hotel";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.ivVisa:
                HighliteImage(ivVisa, ivMasterCard, ivJcb, ivAmericanCard);
                strPaymentMethod = "Visa";

                break;
            case R.id.ivMastercard:
                HighliteImage(ivMasterCard, ivVisa, ivJcb, ivAmericanCard);
                strPaymentMethod = "Mastercard";

                break;
            case R.id.ivJcb:
                HighliteImage(ivJcb, ivMasterCard, ivVisa, ivAmericanCard);
                strPaymentMethod = "JCB";

                break;
            case R.id.ivAmericanExpress:
                HighliteImage(ivAmericanCard, ivMasterCard, ivJcb, ivVisa);
                strPaymentMethod = "American Express";
                break;
            case R.id.ivPartiallyBack:
                bottomSheetBehavior.setDraggable(true);
                partiallyPaidTripBottomSheetBehavior.setHideable(true);
                partiallyPaidTripBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
        }
    }

    private void HighliteImage(ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4) {
        iv1.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        iv2.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv3.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);
        iv4.getDrawable().setColorFilter(0x00000000, PorterDuff.Mode.SRC_ATOP);

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

        bottomSheetBehavior = BottomSheetBehavior.from(llActivityMoreBottomSheet);
        participantsBottomSheet = BottomSheetBehavior.from(llParticipantsBottomSheetBehaviorId);

        addActivityBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddDayActivity);
        addPaymentBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddPayment);
        partiallyPaidTripBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetPartiallyPaidTrip);


        // set the bottom sheet callback state to hidden when you just start your app
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//        participantsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
//        participantsBottomSheet.setHideable(true);


        rvTripParticipants = llParticipantsBottomSheetBehaviorId.findViewById(R.id.rvTripParticipants);
        ivCloseParticipant = llParticipantsBottomSheetBehaviorId.findViewById(R.id.ivCloseParticipants);
        ivAddTripParticipant =llParticipantsBottomSheetBehaviorId.findViewById(R.id.ivAddTripParticipant);

        ivCloseParticipant.setOnClickListener(this);
        ivAddTripParticipant.setOnClickListener(this);


        tabLayout = llActivityMoreBottomSheet.findViewById(R.id.tabs);
        viewPager = llActivityMoreBottomSheet.findViewById(R.id.viewpager);
        ivAddActivity = llActivityMoreBottomSheet.findViewById(R.id.ivAddActivity);

        addTripDayBottomSheet();
        addPaymentBottomSheet();


        setupViewPagerForTabs(viewPager);


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

    private void addPaymentBottomSheet() {
        //add payment
        ivAddPaymentBack = llBottomSheetAddPayment.findViewById(R.id.ivAddPaymentBack);
        ivType = llBottomSheetAddPayment.findViewById(R.id.ivType);
        llPaymentMethod = llBottomSheetAddPayment.findViewById(R.id.llPaymentMethod);


        tillPaymentTitle = llBottomSheetAddPayment.findViewById(R.id.tilPaymentTitle);
        tillPaymentDate = llBottomSheetAddPayment.findViewById(R.id.tillAddPaymentDate);
        tillPaymentAmount = llBottomSheetAddPayment.findViewById(R.id.tillAmount);
        tillShortDescription = llBottomSheetAddPayment.findViewById(R.id.tilAddPaymentShortDescription);

        etPaymentTitle = llBottomSheetAddPayment.findViewById(R.id.etPaymentTitle);
        etPaymentDate = llBottomSheetAddPayment.findViewById(R.id.etAddPaymentDate);
        etPaymentAmount = llBottomSheetAddPayment.findViewById(R.id.etAmount);
        etShortDescription = llBottomSheetAddPayment.findViewById(R.id.etAddPaymentShortDescription);

        tvAddPayment = llBottomSheetAddPayment.findViewById(R.id.tvPaymentAdd);
        swAddGroupPayment = llBottomSheetAddPayment.findViewById(R.id.swAddGroupPayment);

//        spUserName = llBottomSheetAddPayment.findViewById(R.id.spUserName);
        spUserName.setOnItemSelectedListener(this);


        ivVisa = llBottomSheetAddPayment.findViewById(R.id.ivVisa);
        ivMasterCard = llBottomSheetAddPayment.findViewById(R.id.ivMastercard);
        ivJcb = llBottomSheetAddPayment.findViewById(R.id.ivJcb);
        ivAmericanCard = llBottomSheetAddPayment.findViewById(R.id.ivAmericanExpress);


        swAddGroupPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strIsPersonal = "0";
                } else {
                    strIsPersonal = "1";

                }
            }
        });


        ivAddPaymentBack.setOnClickListener(this);
        ivActivityType.setOnClickListener(this);
        tvAddPayment.setOnClickListener(this);
        etPaymentDate.setOnClickListener(this);
//        etPaymentUser.setOnClickListener(this);
        llPaymentMethod.setOnClickListener(this);
        ivType.setOnClickListener(this);


        ivVisa.setOnClickListener(this);
        ivMasterCard.setOnClickListener(this);
        ivJcb.setOnClickListener(this);
        ivAmericanCard.setOnClickListener(this);


        Log.d("zma usr", "" + TripFragment.userList);


        ConnectPaymentClick.addClickListener(new ConnectionBooleanClickChangedListener() {
            @Override
            public void OnMyBooleanClickChanged() {
                partiallyPaidTripBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior.setDraggable(false);
                partiallyPaidTripBottomSheetBehavior.setDraggable(false);
            }

        });


        partiallyPaidBottomSheet();


    }

    private void partiallyPaidBottomSheet() {

        ivBackPartiallyPaid = llBottomSheetPartiallyPaidTrip.findViewById(R.id.ivPartiallyBack);
        circularSeekBar = llBottomSheetPartiallyPaidTrip.findViewById(R.id.csPayment);
        tvPartiallyPaidPercentage = llBottomSheetPartiallyPaidTrip.findViewById(R.id.tvPercentage);
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
//        Toast.makeText(TripDetailScreenActivity.this, userList.get(position).getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "nothing select", Toast.LENGTH_SHORT).show();
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


    @SuppressLint("ResourceType")
    private boolean isValidAddPayment() {
        valid = true;

        strPaymentTitle = etPaymentTitle.getText().toString();
        strPaymentDate = etPaymentDate.getText().toString();
        strPaymentAmount = etPaymentAmount.getText().toString();
        strPaymentShortDescription = etShortDescription.getText().toString();

        if (strPaymentTitle.isEmpty()) {
            tillPaymentTitle.setErrorEnabled(true);
            tillPaymentTitle.setError(getString(R.string.plesase_write_your_title));
            valid = false;
        } else {
            tillPaymentTitle.setError(null);
        }
        if (strPaymentShortDescription.isEmpty()) {
            valid = false;
            tillShortDescription.setErrorEnabled(true);
            tillShortDescription.setError(getString(R.string.plesase_write_your_description));

        } else {
            tillShortDescription.setError(null);
            tillShortDescription.setErrorEnabled(false);
        }


        if (strPaymentDate.isEmpty()) {
            valid = false;
            tillPaymentDate.setErrorEnabled(true);
            tillPaymentDate.setError(getString(R.string.plesase_write_date));

        } else {
            tillPaymentDate.setError(null);
            tillPaymentDate.setErrorEnabled(false);
        }
        if (strPaymentAmount.isEmpty()) {
            valid = false;
            tillPaymentAmount.setErrorEnabled(true);
            tillPaymentAmount.setError(getString(R.string.plesase_write_payment_amount));

        } else {
            tillPaymentAmount.setError(null);
            tillPaymentAmount.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    private void setupViewPagerForTabs(ViewPager viewPager) {
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllTripDayFragment(), "Days Plan");
        adapter.addFragment(new ReservesFragment(), "Reserves");
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
                anIntViewPagerPosition = position;
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

    void activityTripTypeDialog() {
        addActivityTypeDialog = new Dialog(this);
        addActivityTypeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addActivityTypeDialog.setCancelable(true);
        addActivityTypeDialog.setContentView(R.layout.custom_activity_type_layout);
        llTaxi = addActivityTypeDialog.findViewById(R.id.llTaxi);
        llBus = addActivityTypeDialog.findViewById(R.id.llBus);
        llFlight = addActivityTypeDialog.findViewById(R.id.llFlight);
        llReserv = addActivityTypeDialog.findViewById(R.id.llReserv);

        llTaxi.setOnClickListener(this);
        llBus.setOnClickListener(this);
        llFlight.setOnClickListener(this);
        llReserv.setOnClickListener(this);

        addActivityTypeDialog.show();
        AlertUtils.doKeepDialog(addActivityTypeDialog);
        addActivityTypeDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        addActivityTypeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

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


    private void ApiCallForAddDayActivity() {
        dialog.show();
        Call<AddTripDayResponse> addTripDayResponseCall = BaseNetworking.ApiInterface().addTripDay(strActivityTitle, strActivityNote, strActivityDate, strActivityTime, AppRepository.mTripId(this), AppRepository.mUserID(this), strActivityType);
        addTripDayResponseCall.enqueue(new Callback<AddTripDayResponse>() {
            @Override
            public void onResponse(Call<AddTripDayResponse> call, Response<AddTripDayResponse> response) {
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    Log.d("zma", String.valueOf(response.message()));

                    AllTripDayFragment.ApiCallAllTirp(AppRepository.mTripId(TripDetailScreenActivity.this));


                    //add activity bottom sheet
                    addActivityBottomSheetBehavior.setHideable(true);
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


                    Toast.makeText(TripDetailScreenActivity.this, "successful", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(TripDetailScreenActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

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

    private void ApiCallForAddPayment() {

        Log.d("zma date", strPaymentDate);
        dialog.show();
        Call<AddPaymentResponse> addPaymentResponseCall = BaseNetworking.ApiInterface().addPayment(AppRepository.mTripId(this), AppRepository.mUserID(this),
                strPaymentAmount, strActivityType, strTitle, strPaymentDate, strPaymentShortDescription, strIsPersonal, AppRepository.mUserID(this), strPaymentMethod);
        addPaymentResponseCall.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {

                Log.d("zma addpayment", String.valueOf(response));
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    //add payment bottom sheet
                    addPaymentBottomSheetBehavior.setHideable(true);
                    addPaymentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    etPaymentTitle.setText("");
                    etPaymentDate.setText("");
                    etPaymentAmount.setText("");
                    etShortDescription.setText("");


                    getPaymentExpenses();
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                Log.d("zma addpayment error", String.valueOf(t.getMessage()));

                Toast.makeText(TripDetailScreenActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getPaymentExpenses() {
        dialog.show();

        groupExpendituresItems.clear();
        personalExpendituresItems.clear();
        Call<GetPaymentExpensesResponse> getPaymentExpensesResponseCall = BaseNetworking.ApiInterface().getPaymentExpenses(AppRepository.mTripId(this), AppRepository.mUserID(this));
        getPaymentExpensesResponseCall.enqueue(new Callback<GetPaymentExpensesResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<GetPaymentExpensesResponse> call, Response<GetPaymentExpensesResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
//                    assert response.body() != null;
                    tvPartiallyPaidPercentage.setText(response.body().getData().getPaidPercent() + "%");
                    tvPartiallyPaid.setText(response.body().getData().getFullyPaidUsers() + "/" + response.body().getData().getTotalUsers());


                    tvPayDate.setText(DateUtills.getDateFormate(response.body().getData().getTripdate()));
                    circularSeekBar.setProgress(response.body().getData().getPaidPercent());
                    tvParticipantsCostsCount.setText(response.body().getData().getFullyPaidUsers() + " Paid," + response.body().getData().getFullyPaidUsers() + " Partially," + response.body().getData().getNotPaidUsers() + " Not");
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
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripId(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTitle = RequestBody.create(etPhotoName.getText().toString(), MediaType.parse("multipart/form-data"));
        RequestBody BodyTime = RequestBody.create(DateUtills.getCurrentDate("hh:mm"), MediaType.parse("multipart/form-data"));
        RequestBody BodyDate = RequestBody.create(DateUtills.getCurrentDate("yyyy-MM-dd"), MediaType.parse("multipart/form-data"));

        Call<AddPhotoToGalleryResponse> addPhotoToGalleryResponseCall = BaseNetworking.ApiInterface().addPhotoToGallery(BodyTripId, BodyTitle, BodyTime, BodyDate, CoverImage, BodyName);
        addPhotoToGalleryResponseCall.enqueue(new Callback<AddPhotoToGalleryResponse>() {
            @Override
            public void onResponse(Call<AddPhotoToGalleryResponse> call, Response<AddPhotoToGalleryResponse> response) {

                Log.d("zma response", String.valueOf(response));
                if (response.isSuccessful()) {

                    addPhotoDialog.dismiss();
                    sourceFile = null;
                    Connect.setMyBoolean(true);
                    dialog.dismiss();
//                    Toast.makeText(TripDetailScreenActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

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
        Call<AddTripResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().getUserTrip("trips/gettrip/" + AppRepository.mTripId(this));
        getGalleryPhotoResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userParticipaintsList.addAll(response.body().getData());

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

}

