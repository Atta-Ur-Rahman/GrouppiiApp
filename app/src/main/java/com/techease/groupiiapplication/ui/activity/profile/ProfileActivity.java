package com.techease.groupiiapplication.ui.activity.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;
import com.squareup.picasso.Picasso;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.profile.updateProfilePicture.UpdateProfilePicResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import org.json.JSONException;
import org.json.JSONObject;

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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;

    @BindView(R.id.ivAddProfilePicture)
    ImageView ivAddProfilePicture;

    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.tvProfileEmail)
    TextView tvProfileEmail;

    @BindView(R.id.rlPayments)
    RelativeLayout rlPayments;

    final int CAMERA_CAPTURE = 1;
    final int RESULT_LOAD_IMAGE = 2;
    File sourceFile;


    @BindView(R.id.tvCurrencyType)
    TextView tvCurrencyType;


    Dialog dialog;
    CurrencyPicker picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setAnimation();
        dialog = AlertUtils.createProgressDialog(this);
        setProfileImageAndName();

        picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener((name, code, symbol, flagDrawableResID) -> {
            // Implement your code here
            AppRepository.mPutValue(ProfileActivity.this).putString("mCurrencyType", symbol + code).commit();
            tvCurrencyType.setText(symbol + code);
            picker.dismiss();
        });


        if (!AppRepository.mCurrencyType(this).equals("")) {
            tvCurrencyType.setText(AppRepository.mCurrencyType(this));
        }

    }


    private void setProfileImageAndName() {
        if (AppRepository.mUserName(this).length() > 0) {
            tvProfileName.setText(AppRepository.mUserName(this));
        }

        if (AppRepository.mEmail(this).length() > 0) {
            tvProfileEmail.setText(AppRepository.mEmail(this));
        }
        if (AppRepository.mUserProfileImage(this).length() > 0) {
            Picasso.get().load(AppRepository.mUserProfileImage(this)).placeholder(R.drawable.user).into(ivProfilePicture);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.ivEdit, R.id.ivProfilePicture, R.id.ivAddProfilePicture, R.id.rlPayments, R.id.rlCurrencyPicker})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivProfilePicture:
            case R.id.ivEdit:
                startActivity(new Intent(this, EditProfileActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());

                break;
            case R.id.ivAddProfilePicture:
                checkImagePermission();
                break;
            case R.id.rlPayments:
                startActivity(new Intent(this, AddCreditCardActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) this).toBundle());
                break;
            case R.id.rlCurrencyPicker:

                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");


                break;
        }
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

            ivProfilePicture.setImageURI(selectedImageUri);

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

            Toast.makeText(this, String.valueOf(sourceFile.getAbsolutePath()), Toast.LENGTH_SHORT).show();


            ivProfilePicture.setVisibility(View.VISIBLE);
            ivProfilePicture.setImageBitmap(thumbnail);


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
        ivProfilePicture.setImageBitmap(BitmapFactory.decodeFile(filePath));
        return cursor.getString(column_index);

    }


    private void ApiCallForUpdatePic() {

        dialog.show();

        RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
        final MultipartBody.Part picture = MultipartBody.Part.createFormData("picture", sourceFile.getAbsoluteFile().getName(), requestFile);
        RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
        RequestBody BodyUserId = RequestBody.create(AppRepository.mUserID(this) + "", MediaType.parse("multipart/form-data"));


        Call<UpdateProfilePicResponse> updateProfilePicResponseCall = BaseNetworking.ApiInterface().updateProfilePic(BodyUserId, picture, BodyName);
        updateProfilePicResponseCall.enqueue(new Callback<UpdateProfilePicResponse>() {
            @Override
            public void onResponse(Call<UpdateProfilePicResponse> call, Response<UpdateProfilePicResponse> response) {
                if (response.isSuccessful()) {
                    AppRepository.mPutValue(ProfileActivity.this).putString("mProfilePicture", String.valueOf(response.body().getData().getPicture())).commit();
                    dialog.dismiss();
                    Toast.makeText(ProfileActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(ProfileActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfilePicResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ProfileActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfileImageAndName();
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(200);
            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

}
