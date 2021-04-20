package com.techease.groupiiapplication.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.Connect;
import com.techease.groupiiapplication.adapter.TabsViewPagerAdapter;
import com.techease.groupiiapplication.adapter.UserTripCircleImagesAdapter;
import com.techease.groupiiapplication.dataModel.addPhotoToGallery.AddPhotoToGalleryResponse;
import com.techease.groupiiapplication.dataModel.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.AllTripDayFragment;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.PaymentsFragment;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.PhotosFragment;
import com.techease.groupiiapplication.ui.fragment.bottomSheetFragment.ReservesFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DatePickerClass;
import com.techease.groupiiapplication.utils.FileUtils;
import com.techease.groupiiapplication.utils.GeneralUtills;
import com.techease.groupiiapplication.utils.StringHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @BindView(R.id.llDayPlan)
    LinearLayout llDayPlan;

    @BindView(R.id.llPayment)
    LinearLayout llPayments;

    @BindView(R.id.cvMenu)
    LinearLayout cvMenu;


    LinearLayout llBottomSheetDayPlan, llBottomSheetReservs, llBottomSheetPayments, llBottomSheetPhotos;
    ImageView ivBottomSheetDayPlan, ivBottomSheetReservs, ivBottomSheetPayments, ivBottomSheetPhotos;
    TextView tvBottomSheetDayPlan, tvBottomSheetReservs, tvBottomSheetPayments, tvBottomSheetPhotos;


    boolean valid = true;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llActivityMoreBottomSheet;
    LinearLayout llBottomSheetAddDayActivity;
    BottomSheetBehavior bottomSheetBehavior, addActivityBottomSheetBehavior;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    boolean aBooleanAddImage = true;

    BottomNavigationView bottomNavigationView;

    ArrayList<String> stringArrayList = new ArrayList<>();


    private static final int REQUEST_CODE_SELECT_PICTURE = 3;


    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack;
    TextView tvAddActivity;
    SwitchCompat switchCompatGroupActivity;


    int anIntViewPagerPosition = 0;
    File sourceFile;


    Dialog addPhotoDialog;

    String strActivityTitle, strActivityDate, strActivityTime, strActivityNote, strActivityGroup, strPhotoName;

    Dialog dialog;

    String photoName;
    EditText etPhotoName;
    ImageView ivGalleryPhoto;
    Button btnAddPhoto;

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


        stringArrayList = bundle.getStringArrayList("users");
        Log.d("zma array", String.valueOf(stringArrayList));

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

        TextView tvCancel=mBottomSheetDialog.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivMenu, R.id.tvMore, R.id.ivMore, R.id.llDayPlan, R.id.llPayment, R.id.cvMenu})

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
            case R.id.cvMenu:

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case R.id.ivAddActivity:
                if (anIntViewPagerPosition == 0) {
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
            case R.id.llDayPlan:
//                startActivity(new Intent(this, BottomSheetViewFragmentActivity.class));
                break;
            case R.id.llPayment:

                break;

            case R.id.llBottomSheeDayPlan:
                changeTextColdrTab(R.color.purple_500, R.color.gry, R.color.gry, R.color.gry);
                changeImageColdrTab(R.mipmap.my_trips_selected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos);
                viewPager.setCurrentItem(0);
                break;
            case R.id.llBottomSheeReservs:
                changeTextColdrTab(R.color.gry, R.color.purple_500, R.color.gry, R.color.gry);
                changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reserv_selected, R.mipmap.payment, R.mipmap.photos);
                viewPager.setCurrentItem(1);

                break;
            case R.id.llBottomSheePayment:
                changeTextColdrTab(R.color.gry, R.color.gry, R.color.purple_500, R.color.gry);
                changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reservs, R.mipmap.payment_selected, R.mipmap.photos);
                viewPager.setCurrentItem(2);

                break;
            case R.id.llBottomSheePhotos:
                changeTextColdrTab(R.color.gry, R.color.gry, R.color.gry, R.color.purple_500);
                changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos_selected);
                viewPager.setCurrentItem(3);

                break;


        }
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

        bottomSheetBehavior = BottomSheetBehavior.from(llActivityMoreBottomSheet);
        addActivityBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetAddDayActivity);

        bottomNavigationView = llActivityMoreBottomSheet.findViewById(R.id.navigation);


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


        setupViewPagerForTabs(viewPager);


//        BroadcastReceiver broadcastReceiver;
//        IntentFilter intentFilter=new IntentFilter();
//
//        intentFilter.addAction(Intent.ACTION_TIME_TICK);
//        broadcastReceiver=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(context, "show tost time", Toast.LENGTH_SHORT).show();
//            }
//        };
//        registerReceiver(broadcastReceiver,intentFilter);


        ivAddActivityBack.setOnClickListener(this);
        ivAddActivity.setOnClickListener(this);
        tvAddActivity.setOnClickListener(this);

        etActivityDate.setOnClickListener(this);
        etActivityTime.setOnClickListener(this);


    }

    private void ApiCallForAddDayActivity() {
        dialog.show();
        Call<AddTripDayResponse> addTripDayResponseCall = BaseNetworking.ApiInterface().addTripDay(strActivityTitle, strActivityNote, strActivityDate, strActivityTime, AppRepository.mTripId(this), AppRepository.mUserID(this));
        addTripDayResponseCall.enqueue(new Callback<AddTripDayResponse>() {
            @Override
            public void onResponse(Call<AddTripDayResponse> call, Response<AddTripDayResponse> response) {
                if (response.isSuccessful()) {

                    dialog.dismiss();
                    Log.d("zma", String.valueOf(response.message()));

                    AllTripDayFragment.ApiCallAllTirp(AppRepository.mTripId(TripDetailScreenActivity.this));

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

                if (position == 0) {
                    changeTextColdrTab(R.color.purple_500, R.color.gry, R.color.gry, R.color.gry);
                    changeImageColdrTab(R.mipmap.my_trips_selected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos);
                }
                if (position == 1) {
                    changeTextColdrTab(R.color.gry, R.color.purple_500, R.color.gry, R.color.gry);
                    changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reserv_selected, R.mipmap.payment, R.mipmap.photos);
                }
                if (position == 2) {
                    changeTextColdrTab(R.color.gry, R.color.gry, R.color.purple_500, R.color.gry);
                    changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reservs, R.mipmap.payment_selected, R.mipmap.photos);
                }
                if (position == 3) {
                    changeTextColdrTab(R.color.gry, R.color.gry, R.color.gry, R.color.purple_500);
                    changeImageColdrTab(R.mipmap.my_trip_unselected, R.mipmap.reservs, R.mipmap.payment, R.mipmap.photos_selected);
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


    private void checkImagePermission() {
        Dexter.withActivity(this).withPermissions(
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
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

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
        File dir = FileUtils.getDiskCacheDir(this, "temp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = StringHelper.getDateRandomString() + ".png";
        sourceFile = new File(dir, name);
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sourceFile));

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.choose_photo));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{captureImageIntent});

        startActivityForResult(chooserIntent, REQUEST_CODE_SELECT_PICTURE);
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


    private void ApiCallForAddPhotoToGallery() {

        dialog.show();

        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("photo", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripId(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTitle = RequestBody.create(etPhotoName.getText().toString(), MediaType.parse("multipart/form-data"));
        RequestBody BodyTime = RequestBody.create(DatePickerClass.getCurrentDate("hh:mm"), MediaType.parse("multipart/form-data"));
        RequestBody BodyDate = RequestBody.create(DatePickerClass.getCurrentDate("yyyy-MM-dd"), MediaType.parse("multipart/form-data"));


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
                    Toast.makeText(TripDetailScreenActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

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


}

