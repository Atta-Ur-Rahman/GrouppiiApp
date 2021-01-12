

package com.techease.groupiiapplication.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.techease.groupiiapplication.utils.GeneralUtills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    boolean valid = true;

    @BindView(R.id.llBottomSheetBehaviorId)
    LinearLayout llActivityMoreBottomSheet;
    LinearLayout llBottomSheetAddDayActivity;
    BottomSheetBehavior bottomSheetBehavior, addActivityBottomSheetBehavior;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    BottomNavigationView bottomNavigationView;

    ArrayList<String> stringArrayList = new ArrayList<>();


    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote;
    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack;
    TextView tvAddActivity;
    SwitchCompat switchCompatGroupActivity;


    int anIntViewPagerPosition = 0;


    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;
    File sourceFile;


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

    }


    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivMenu, R.id.tvMore, R.id.ivMore, R.id.llDayPlan, R.id.llPayment})

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
                if (anIntViewPagerPosition == 0) {
                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (anIntViewPagerPosition == 3) {
                    checkImagePermission();
                }
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
            case R.id.llDayPlan:
                startActivity(new Intent(this, BottomSheetViewFragmentActivity.class));
                break;
            case R.id.llPayment:

                break;

        }
    }


    @SuppressLint("SetTextI18n")
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


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.days_plan);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.days_plan_selected, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.reserves);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.reserv_selected, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.payments);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.payment_selected, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(R.string.photos);
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
        adapter.addFragment(new ReservesFragment(), "Reserves");
        adapter.addFragment(new PaymentsFragment(), "Payments");
        adapter.addFragment(new PhotosFragment(), "Photos");
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                cameraBuilder();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

            }
        }).check();
    }

    public void cameraIntent() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    //open camera view
    public void cameraBuilder() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Open");
        String[] pictureDialogItems = {
                "\tGallery",
                "\tCamera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                galleryIntent();

                                break;
                            case 1:
                                cameraIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            sourceFile = new File(imagepath);
            ApiCallForUpdatePic();

        } else if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE && data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();


            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


            sourceFile = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            sourceFile = new File(sourceFile,
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                sourceFile.createNewFile();
                fo = new FileOutputStream(sourceFile);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            sourceFile.getAbsoluteFile();

            ApiCallForUpdatePic();

        }
    }


    @SuppressLint("SetTextI18n")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        return cursor.getString(column_index);

    }


    private void ApiCallForUpdatePic() {

        dialog.show();

        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part picture = MultipartBody.Part.createFormData("photo", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripId(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTitle = RequestBody.create(AppRepository.mUserID(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyTime = RequestBody.create(AppRepository.mUserID(this), MediaType.parse("multipart/form-data"));
        RequestBody BodyDate = RequestBody.create(AppRepository.mUserID(this), MediaType.parse("multipart/form-data"));


        Call<AddPhotoToGalleryResponse> addPhotoToGalleryResponseCall = BaseNetworking.ApiInterface().addPhotoToGallery(BodyTripId, BodyTitle, BodyTime, BodyDate, picture, BodyName);
        addPhotoToGalleryResponseCall.enqueue(new Callback<AddPhotoToGalleryResponse>() {
            @Override
            public void onResponse(Call<AddPhotoToGalleryResponse> call, Response<AddPhotoToGalleryResponse> response) {
                if (response.isSuccessful()) {

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
