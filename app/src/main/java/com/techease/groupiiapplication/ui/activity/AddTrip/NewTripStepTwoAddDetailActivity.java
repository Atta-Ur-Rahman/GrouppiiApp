package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTripDetail.AddTripDetailResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DatePickerClass;
import com.techease.groupiiapplication.utils.FileUtils;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;
import com.techease.groupiiapplication.utils.StringHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class NewTripStepTwoAddDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnNext)
    Button btnNext;


    @BindView(R.id.tilTripTitle)
    TextInputLayout tilTripTitle;
    @BindView(R.id.etTripTitle)
    EditText etTripTitle;

    @BindView(R.id.tilTripDescription)
    TextInputLayout tilTripDescription;
    @BindView(R.id.etDescription)
    EditText etDescription;


    @BindView(R.id.tillLocation)
    TextInputLayout tillLocation;
    @BindView(R.id.etLocation)
    EditText etLocation;

    @BindView(R.id.etTripStartDate)
    EditText etTripStartDate;
    @BindView(R.id.etTripEndtDate)
    EditText etTripEndDate;
    @BindView(R.id.etTripPayByDate)
    EditText etTripPayByDate;

    @BindView(R.id.tillStartDate)
    TextInputLayout tillStartDate;
    @BindView(R.id.tillEndDate)
    TextInputLayout tillEndDate;
    @BindView(R.id.tillPayByDate)
    TextInputLayout tillPayByDate;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    String strTripTitle, strDescription, strLocation, strStartDate, strEndDate, strPayByDate;


    @BindView(R.id.ivCover)
    ImageView ivCoverImage;


    @BindView(R.id.llCoverImage)
    RelativeLayout rlCoverImage;


    private static final int REQUEST_CODE_SELECT_PICTURE = 3;
    File sourceFile;


    Dialog dialog;

    boolean valid;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_two);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        ProcessBarAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.btnNext, R.id.ivBack, R.id.etTripStartDate, R.id.etTripEndtDate, R.id.etTripPayByDate, R.id.llCoverImage})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:

                if (isValid()) {
                    apiCallForCoverImage();
                }
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.etTripStartDate:
                DatePickerClass.GetDatePickerDialog(etTripStartDate, this);
                break;
            case R.id.etTripEndtDate:
                DatePickerClass.GetDatePickerDialog(etTripEndDate, this);
                break;
            case R.id.etTripPayByDate:
                DatePickerClass.GetDatePickerDialog(etTripPayByDate, this);
                break;
            case R.id.llCoverImage:
                checkImagePermission();
                break;
        }
    }

    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 25, 50);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
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

            }
        }).check();
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

        Intent chooserIntent = Intent.createChooser(pickIntent, getString(R.string.profile_photo));
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


    void finishWithResult() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_SELECT_PICTURE:
                if (checkActionType(data)) { // Camera
                    Uri imageUri = getPickImageResultUri(data);
                    ivCoverImage.setVisibility(View.VISIBLE);
                    Glide.with(this).load(imageUri).into(ivCoverImage);
                     sourceFile = new File(imageUri.getPath());

                    sourceFile = FileUtils.compressImage(this, sourceFile);

                } else {  // Gallery
                    if (data.getData() != null) {
                        Uri uri = data.getData();
                         sourceFile = FileUtils.getFile(this, uri);
//                        sourceFile = FileUtils.compressImage(this, originFile);
                        ivCoverImage.setVisibility(View.VISIBLE);
                        Glide.with(this).load(uri).into(ivCoverImage);

                    }
                }

                break;
        }


    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strTripTitle = etTripTitle.getText().toString();
        strDescription = etDescription.getText().toString();
        strLocation = etLocation.getText().toString();
        strStartDate = etTripStartDate.getText().toString();
        strEndDate = etTripEndDate.getText().toString();
        strPayByDate = etTripPayByDate.getText().toString();


        if (strTripTitle.isEmpty()) {
            tilTripTitle.setErrorEnabled(true);
            tilTripTitle.setError(getString(R.string.plesase_write_your_title));
            valid = false;
        } else {
            tilTripTitle.setError(null);
        }
        if (strDescription.isEmpty()) {
            valid = false;
            tilTripDescription.setErrorEnabled(true);
            tilTripDescription.setError(getString(R.string.plesase_write_your_description));

        } else {
            tilTripDescription.setError(null);
            tilTripDescription.setErrorEnabled(false);
        }
        if (strLocation.isEmpty()) {
            valid = false;
            tillLocation.setErrorEnabled(true);
            tillLocation.setError(getString(R.string.plesase_write_your_location));

        } else {
            tillLocation.setError(null);
            tillLocation.setErrorEnabled(false);
        }

        if (strStartDate.isEmpty()) {
            valid = false;
            tillStartDate.setErrorEnabled(true);
            tillStartDate.setError(getString(R.string.plesase_write_start_date));

        } else {
            tillStartDate.setError(null);
            tillStartDate.setErrorEnabled(false);
        }
        if (strEndDate.isEmpty()) {
            valid = false;
            tillEndDate.setErrorEnabled(true);
            tillEndDate.setError(getString(R.string.plesase_write_end_date));

        } else {
            tillEndDate.setError(null);
            tillEndDate.setErrorEnabled(false);
        }

        if (strPayByDate.isEmpty()) {
            valid = false;
            tillPayByDate.setErrorEnabled(true);
            tillPayByDate.setError(getString(R.string.plesase_write_pay_date));

        } else {
            tillPayByDate.setError(null);
            tillPayByDate.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        if (sourceFile == null) {
            valid = false;

            Toast.makeText(this, "cover image missing", Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

    private void apiCallForCoverImage() {

        dialog.show();

        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("coverimage", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyTitle = RequestBody.create(strTripTitle, MediaType.parse("multipart/form-data"));
        RequestBody BodyDescription = RequestBody.create(strDescription, MediaType.parse("multipart/form-data"));
        RequestBody BodyLocation = RequestBody.create(strLocation, MediaType.parse("multipart/form-data"));
        RequestBody BodyStartDate = RequestBody.create(strStartDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyEndDate = RequestBody.create(strEndDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyPayByDate = RequestBody.create(strPayByDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripId(this), MediaType.parse("multipart/form-data"));

        Call<AddTripDetailResponse> addTripDetailResponseCall = BaseNetworking.ApiInterface().addTripDetail(BodyTripId, BodyTitle, BodyDescription, BodyLocation, BodyStartDate, BodyEndDate, BodyPayByDate, CoverImage, BodyName);
        addTripDetailResponseCall.enqueue(new Callback<AddTripDetailResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<AddTripDetailResponse> call, Response<AddTripDetailResponse> response) {
                if (response.isSuccessful()) {

                    finish();
                    startActivity(new Intent(NewTripStepTwoAddDetailActivity.this, AddNewTripThreeHotelActivity.class), ActivityOptions.makeSceneTransitionAnimation(NewTripStepTwoAddDetailActivity.this).toBundle());
                    dialog.dismiss();
                    Log.d("zma uploddImage", String.valueOf(sourceFile));
                }
            }

            @Override
            public void onFailure(Call<AddTripDetailResponse> call, Throwable t) {
                Toast.makeText(NewTripStepTwoAddDetailActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.d("zma error", String.valueOf(t.getMessage()));


            }
        });


    }


}