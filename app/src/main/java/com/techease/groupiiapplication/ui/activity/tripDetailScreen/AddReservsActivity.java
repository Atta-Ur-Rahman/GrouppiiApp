package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.addTrip.AutoCompleteCitiesAdapter;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.HotelCityIdData;
import com.techease.groupiiapplication.dataModel.reserveModel.AddReserveResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.DateUtills;
import com.techease.groupiiapplication.utils.KeyBoardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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

public class AddReservsActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnSaveReserve)
    Button btnSaveReserve;


    @BindView(R.id.tilTripTitle)
    TextInputLayout tilTripTitle;
    @BindView(R.id.etTripTitle)
    EditText etTripTitle;


    @BindView(R.id.tilTripConfirmation)
    TextInputLayout tilTripConfirmation;
    @BindView(R.id.etTripConfirmaton)
    EditText etTripConfirmaton;


    @BindView(R.id.tillLocation)
    TextInputLayout tillLocation;
//    @BindView(R.id.etLocation)
//    EditText etLocation;

    @BindView(R.id.etTripStartDate)
    EditText etTripStartDate;
    @BindView(R.id.etTripEndtDate)
    EditText etTripEndDate;

    @BindView(R.id.tillStartDate)
    TextInputLayout tillStartDate;
    @BindView(R.id.tillEndDate)
    TextInputLayout tillEndDate;


    String strTripTitle, strDescription, strLocation, strStartDate, strEndDate, strConfirmStatus = "", strPhoto, strId;


    @BindView(R.id.places_recycler_view)
    RecyclerView recyclerView;

    double latitude = 0.0;
    double longitude = 0.0;
    private StringBuilder mResult;

    @BindView(R.id.ivCover)
    ImageView ivCoverImage;

    @BindView(R.id.tvReserveTitle)
    TextView tvReserveTitle;

    @BindView(R.id.llCoverImage)
    RelativeLayout rlCoverImage;

    File sourceFile;

    AutoCompleteCitiesAdapter autoCompleteCitiesAdapter;

    int cityID = 2994;

    Dialog dialog;


    @BindView(R.id.cbTripConfirm)
    CheckBox cbTripConfirm;
    @BindView(R.id.autocompleteCity)
    AutoCompleteTextView autoCompleteTextView;
    List<HotelCityIdData> hotelCityIdDataList = new ArrayList<>();


    boolean valid;
    Uri imageUri;

    boolean aBooleanEdit = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_reservs);
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        Places.initialize(AddReservsActivity.this, getResources().getString(R.string.google_maps_key));

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(AddReservsActivity.this);
        // Create a new Places client instance.

        Collections.reverse(hotelCityIdDataList);
        autoCompleteCitiesAdapter = new AutoCompleteCitiesAdapter(AddReservsActivity.this, hotelCityIdDataList);
        autoCompleteTextView.setAdapter(autoCompleteCitiesAdapter);


        try {
            latitude = Double.parseDouble(AppRepository.mLat(AddReservsActivity.this));
            longitude = Double.parseDouble(AppRepository.mLng(AddReservsActivity.this));
        } catch (Exception e) {
            latitude = 33.6969485;
            longitude = 72.9692551;
        }


        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                Toast.makeText(NewTripStepTwoAddDetailActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
                // and once again when the user makes a selection (for example when calling fetchPlace()).
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
                // Create a RectangularBounds object.

                RectangularBounds bounds = RectangularBounds.newInstance(
                        new LatLng(latitude, longitude), //app lat/lng
                        new LatLng(latitude, longitude));
                // Use the builder to create a FindAutocompletePredictionsRequest.
                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        // Call either setLocationBias() OR setLocationRestriction().
                        .setLocationBias(bounds)
                        .setCountries(Constants.COUNTRIES)
                        //.setLocationRestriction(bounds)
                        .setSessionToken(token)
                        .setQuery(autoCompleteTextView.getText().toString())
                        .build();


                placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
                    mResult = new StringBuilder();
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        HotelCityIdData hotelCityIdData = new HotelCityIdData();
//                        hotelCityIdData.setCityId(Integer.parseInt(prediction.getPlaceId()));
                        hotelCityIdData.setCityName(String.valueOf(prediction.getFullText(null)));
                        hotelCityIdDataList.add(hotelCityIdData);
                        autoCompleteCitiesAdapter.notifyDataSetChanged();
                        mResult.append(" ").append(prediction.getFullText(null) + "\n");
                        Log.i("TAG", prediction.getPlaceId());
                        Log.i("TAG", prediction.getPrimaryText(null).toString());
//                        Toast.makeText(NewTripStepTwoAddDetailActivity.this, prediction.getPrimaryText(null) + "-" + prediction.getSecondaryText(null), Toast.LENGTH_SHORT).show();
                    }
//                    autoCompleteTextView.setText(String.valueOf(mResult));
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("TAG", "Place not found: " + apiException.getStatusCode());
                    }
                });

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {

                KeyBoardUtils.closeKeyboard(AddReservsActivity.this);
                KeyBoardUtils.hideKeyboard(AddReservsActivity.this);
                cityID = hotelCityIdDataList.get(itemPosition).getCityId();
                strLocation = hotelCityIdDataList.get(itemPosition).getCityName();

                AppRepository.mPutValue(AddReservsActivity.this).putInt("cityID", cityID).commit();
                AppRepository.mPutValue(AddReservsActivity.this).putString("cityName", strLocation).commit();


            }
        });


        cbTripConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strConfirmStatus = "1";

                } else {
                    strConfirmStatus = "0";
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        try {
            aBooleanEdit = bundle.getBoolean("edit");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (aBooleanEdit) {


            tvReserveTitle.setText("Edit RSVP");
            btnSaveReserve.setText("Update");

            strId = bundle.getString("rsvpID");
            strTripTitle = bundle.getString("title");
            strPhoto = bundle.getString("image");
            strStartDate = DateUtills.getEditDateFormate(bundle.getString("start_date"));
            strEndDate = DateUtills.getEditDateFormate(bundle.getString("end_date"));
            strLocation = bundle.getString("location");
            strConfirmStatus = bundle.getString("status");


            ivCoverImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(strPhoto).into(ivCoverImage);
            etTripTitle.setText(strTripTitle);
            etTripTitle.selectAll();
            autoCompleteTextView.setText(strLocation);

            etTripStartDate.setText((strStartDate));
            etTripEndDate.setText((strEndDate));
            etTripConfirmaton.setText(strConfirmStatus);
//            if (strConfirmStatus.equals("1")) {
//                cbTripConfirm.setChecked(true);
//            } else {
//                cbTripConfirm.setChecked(false);
//
//            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.btnSaveReserve, R.id.ivBack, R.id.etTripStartDate, R.id.etTripEndtDate, R.id.llCoverImage})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveReserve:
                if (isValid()) {
                    if (aBooleanEdit) {
                        apiCallForUpdateReserve(strId);
                    } else {
                        apiCallForAddRSVP();
                    }
                }
                break;
            case R.id.ivBack:
                AddReservsActivity.this.onBackPressed();
                break;
            case R.id.etTripStartDate:
                DateUtills.GetDatePickerDialog(etTripStartDate, AddReservsActivity.this);
                break;
            case R.id.etTripEndtDate:
                DateUtills.GetDatePickerDialog(etTripEndDate, AddReservsActivity.this);
                break;

            case R.id.llCoverImage:
                checkImagePermission();
                break;
        }
    }

    private void checkImagePermission() {
        Dexter.withContext(AddReservsActivity.this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {


                Log.d("zmapermissions", report.getDeniedPermissionResponses().size() + "   " + report.getGrantedPermissionResponses().size());
                if (report.getGrantedPermissionResponses().size() == 4) {
                    chooseAction();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken
                    token) {

            }
        }).check();
    }


    void chooseAction() {
        Constants.aBooleanGetImageForReserve = true;
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

            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            sourceFile = new File(returnValue.get(0));

            ivCoverImage.setImageURI(Uri.parse(returnValue.get(0)));
            ivCoverImage.setVisibility(View.VISIBLE);

        }
    }

    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
        strTripTitle = etTripTitle.getText().toString();
        strLocation = autoCompleteTextView.getText().toString();
        strStartDate = etTripStartDate.getText().toString();
        strEndDate = etTripEndDate.getText().toString();
        strConfirmStatus = etTripConfirmaton.getText().toString();
        if (cbTripConfirm.isChecked()) {
            strDescription = "1";
        } else {
            strDescription = "0";
        }

        if (strTripTitle.isEmpty()) {
            tilTripTitle.setErrorEnabled(true);
            tilTripTitle.setError(getString(R.string.plesase_write_your_title));
            valid = false;
        } else {
            tilTripTitle.setError(null);
        }

//        if (strConfirmStatus.isEmpty()) {
//            tilTripConfirmation.setErrorEnabled(true);
//            tilTripConfirmation.setError(getString(R.string.plesase_write_your_title));
//            valid = false;
//        } else {
//            tilTripConfirmation.setError(null);
//        }

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


        if (!Connectivity.isConnected(AddReservsActivity.this)) {
            valid = false;
            Toast.makeText(AddReservsActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        if (sourceFile == null) {
            sourceFile = null;
        }


        return valid;
    }


    private void apiCallForAddRSVP() {
        dialog.show();
        RequestBody BodyTitle = RequestBody.create(strTripTitle, MediaType.parse("multipart/form-data"));
        RequestBody BodyConfirmation = RequestBody.create(strConfirmStatus, MediaType.parse("multipart/form-data"));
        RequestBody BodyLocation = RequestBody.create(strLocation, MediaType.parse("multipart/form-data"));
        RequestBody BodyStartDate = RequestBody.create(strStartDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyEndDate = RequestBody.create(strEndDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyIsDone = RequestBody.create("0", MediaType.parse("multipart/form-data"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripIDForUpdation(AddReservsActivity.this), MediaType.parse("multipart/form-data"));

        if (sourceFile != null) {
            RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
            final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("image", sourceFile.getAbsoluteFile().getName(), requestFile);
            RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
            Call<AddReserveResponse> addTripDetailResponseCall = BaseNetworking.ApiInterface().addReserveDetail(BodyTripId, BodyConfirmation, BodyTitle, BodyLocation, BodyStartDate, BodyEndDate, BodyIsDone, CoverImage, BodyName);
            addTripDetailResponseCall.enqueue(new Callback<AddReserveResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<AddReserveResponse> call, Response<AddReserveResponse> response) {
                    Log.d("zma response", String.valueOf(response));

                    if (response.isSuccessful()) {
                        Constants.aBooleanGetImageForReserve = true;
                        AddReservsActivity.this.finish();
                        Toast.makeText(AddReservsActivity.this, "Reserve Added successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AddReserveResponse> call, Throwable t) {
                    Toast.makeText(AddReservsActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Log.d("zma error", String.valueOf(t.getMessage()));


                }
            });
        }


    }

    private void apiCallForUpdateReserve(String rsvpID) {
        dialog.show();
        RequestBody BodyTitle = RequestBody.create(strTripTitle, MediaType.parse("multipart/form-data"));
        RequestBody BodyLocation = RequestBody.create(strLocation, MediaType.parse("multipart/form-data"));
        RequestBody BodyConfirmation = RequestBody.create(strConfirmStatus, MediaType.parse("multipart/form-data"));
        RequestBody BodyStartDate = RequestBody.create(strStartDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyEndDate = RequestBody.create(strEndDate, MediaType.parse("multipart/form-data"));
        RequestBody BodyIsDone = RequestBody.create("0", MediaType.parse("multipart/form-data"));
        RequestBody BodyRsvpId = RequestBody.create(rsvpID, MediaType.parse("multipart/form-data"));
        RequestBody BodyTripId = RequestBody.create(AppRepository.mTripIDForUpdation(AddReservsActivity.this), MediaType.parse("multipart/form-data"));

        if (sourceFile != null) {
            RequestBody requestFile = RequestBody.create(sourceFile.getAbsoluteFile(), MediaType.parse("multipart/form-data"));
            final MultipartBody.Part CoverImage = MultipartBody.Part.createFormData("image", sourceFile.getAbsoluteFile().getName(), requestFile);
            RequestBody BodyName = RequestBody.create("upload-test", MediaType.parse("text/plain"));
            Call<AddReserveResponse> addTripDetailResponseCall = BaseNetworking.ApiInterface().editReserveDetail(BodyRsvpId, BodyConfirmation, BodyTripId, BodyTitle, BodyLocation, BodyStartDate, BodyEndDate, BodyIsDone, CoverImage, BodyName);
            addTripDetailResponseCall.enqueue(new Callback<AddReserveResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<AddReserveResponse> call, Response<AddReserveResponse> response) {
                    Log.d("zma response", String.valueOf(response));

                    if (response.isSuccessful()) {
                        Constants.aBooleanGetImageForReserve = true;
                        AddReservsActivity.this.finish();
                        Toast.makeText(AddReservsActivity.this, "Reserve updated successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AddReserveResponse> call, Throwable t) {
                    Toast.makeText(AddReservsActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Log.d("zma error", String.valueOf(t.getMessage()));


                }
            });
        } else {
            Call<AddReserveResponse> addTripDetailResponseCall = BaseNetworking.ApiInterface().editReserveWithImageDetail(BodyRsvpId, BodyConfirmation, BodyTripId, BodyTitle, BodyLocation, BodyStartDate, BodyEndDate, BodyIsDone);
            addTripDetailResponseCall.enqueue(new Callback<AddReserveResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<AddReserveResponse> call, Response<AddReserveResponse> response) {
                    Log.d("zma responsenull", String.valueOf(response));

                    if (response.isSuccessful()) {
                        Constants.aBooleanGetImageForReserve = true;
                        AddReservsActivity.this.finish();
                        Toast.makeText(AddReservsActivity.this, "Reserve updated successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AddReserveResponse> call, Throwable t) {
                    Toast.makeText(AddReservsActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Log.d("zma error", String.valueOf(t.getMessage()));


                }
            });

        }


    }
}
