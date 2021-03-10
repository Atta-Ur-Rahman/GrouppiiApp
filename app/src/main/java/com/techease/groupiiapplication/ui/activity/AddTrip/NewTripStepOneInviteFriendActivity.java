package com.techease.groupiiapplication.ui.activity.AddTrip;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.AddTripAdapter;
import com.techease.groupiiapplication.adapter.MyContactsAdapter;
import com.techease.groupiiapplication.adapter.RecyclerViewClickListener;
import com.techease.groupiiapplication.dataModel.ContactDataModel;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.ProgressBarAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTripStepOneInviteFriendActivity extends AppCompatActivity implements View.OnClickListener, MyContactsAdapter.ContactsAdapterListener {


    @BindView(R.id.clInviteFriend)
    ConstraintLayout clInviteFriend;
    @BindView(R.id.clAddInvite)
    ConstraintLayout clAddInvite;
    @BindView(R.id.ivAddUserTrip)
    ImageView ivAddUserTrip;


    @BindView(R.id.rvInviteFriend)
    RecyclerView rvInviteFriend;

    boolean valid;
    String strEmail, strPhoneNumber, strShareCost = "0";

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.etPhone)
    EditText etPhone;


    @BindView(R.id.tvInviteFriend)
    TextView tvInviteFriend;


    @BindView(R.id.tvSendInviteFriend)
    TextView tvSendInviteFriend;

    @BindView(R.id.cbShareTripCost)
    CheckBox cbShareCost;

    List<AddTripDataModel> addTripDataModels = new ArrayList<>();

    Dialog dialog;

    @BindView(R.id.tvInviteFriendNotFound)
    TextView tvInviteFriendNotFound;


    LinearLayoutManager linearLayoutManager;
    AddTripAdapter addTripAdapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btnNext)
    Button btnNext;

    private List<ContactDataModel> contactDataModelList = new ArrayList<>();
    Cursor cursor;
    private String name, phoneNumber;
    public static final int RequestPermissionCode = 1;

    MyContactsAdapter contactsAdapter;

    RecyclerViewClickListener listener;


    @BindView(R.id.rvMyContact)
    RecyclerView rvMyContact;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_step_one);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);

        tvInviteFriendNotFound.setVisibility(View.VISIBLE);

        initAdapter();
        initContactAdapter();
        ApiCallGetTripID();
        ContactGetAndCheckPermission();
        ProcessBarAnimation();


        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                Log.d("zma text", text);
                contactsAdapter.getFilter().filter(text);
                if (tilEmail.getVisibility() == View.VISIBLE) {
                    ContactLayoutVisible();
                }
            }
        });

        etPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, @SuppressLint("ClickableViewAccessibility") MotionEvent motionEvent) {
                ContactLayoutVisible();
                return false;
            }
        });
        cbShareCost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    strShareCost = "1";
                } else {
                    strShareCost = "0";
                }
            }
        });

    }

    private void ProcessBarAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 1, 25);
        anim.setDuration(1000);
        progressBar.startAnimation(anim);
    }

    private void ApiCallGetTripID() {
        dialog.show();
        Call<CreateTripResponse> createTripResponseCall = BaseNetworking.ApiInterface().getTripID(AppRepository.mUserID(this));
        createTripResponseCall.enqueue(new Callback<CreateTripResponse>() {
            @Override
            public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("zma tripid", String.valueOf(response.body().getTripid()));
                    dialog.dismiss();
                    AppRepository.mPutValue(NewTripStepOneInviteFriendActivity.this).putString("tripID", String.valueOf(response.body().getTripid())).commit();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CreateTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.ivBack, R.id.ivAddUserTrip, R.id.clAddInvite, R.id.clInviteFriend, R.id.btnNext, R.id.tvSendInviteFriend, R.id.etPhone})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:

                if (tilEmail.getVisibility() == View.VISIBLE) {

                    if (clInviteFriend.getVisibility() == View.VISIBLE) {
                        clAddInvite.setVisibility(View.VISIBLE);
                        clInviteFriend.setVisibility(View.GONE);
                    } else {
                        onBackPressed();
                        apiCallForTripDelete();
                    }
                } else {
                    ContactLayoutGone();
                }

                break;

            case R.id.tvSendInviteFriend:

                if (isValid()) {
                    ApiCallForAddInviteFriend();
                }
                break;
            case R.id.ivAddUserTrip:
                clAddInvite.setVisibility(View.GONE);
                clInviteFriend.setVisibility(View.VISIBLE);
                ContactLayoutGone();

                break;
            case R.id.btnNext:
                TripFragment.aBooleanRefreshApi = false;
                startActivity(new Intent(this, NewTripStepTwoAddDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation(NewTripStepOneInviteFriendActivity.this).toBundle());
                this.finish();

                break;

            case R.id.etPhone:

                ContactLayoutVisible();


        }
    }


    private void ContactLayoutVisible() {
        tilEmail.setVisibility(View.GONE);
        tvSendInviteFriend.setVisibility(View.GONE);
        tvInviteFriend.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        rvMyContact.setVisibility(View.VISIBLE);
    }

    private void ApiCallForAddInviteFriend() {
        dialog.show();
        addTripDataModels.clear();
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTrip(strEmail, strPhoneNumber, strShareCost,
                AppRepository.mTripId(this), AppRepository.mUserID(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma response", String.valueOf(response.message()));
                    Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        clAddInvite.setVisibility(View.VISIBLE);
                        clInviteFriend.setVisibility(View.GONE);
                        etEmail.setText("");
                        etPhone.setText("");
                        cbShareCost.setChecked(false);
                        addTripDataModels.addAll(response.body().getData());
                        initAdapter();
                    }

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;

        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString();

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
        }
        if (strPhoneNumber.isEmpty()) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.plesase_write_your_phone_number));

        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
        }
        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    @Override
    public void onBackPressed() {
        if (tilEmail.getVisibility() == View.VISIBLE) {

            if (clInviteFriend.getVisibility() == View.VISIBLE) {
                clAddInvite.setVisibility(View.VISIBLE);
                clInviteFriend.setVisibility(View.GONE);

            } else {
                apiCallForTripDelete();
                super.onBackPressed();
            }
        } else {
            ContactLayoutGone();

        }


    }

    private void ContactLayoutGone() {
        tilEmail.setVisibility(View.VISIBLE);
        tvSendInviteFriend.setVisibility(View.VISIBLE);
        tvInviteFriend.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        rvMyContact.setVisibility(View.GONE);
    }

    private void apiCallForTripDelete() {
        Call<DeleteTripResponse> deleteTripResponseCall = BaseNetworking.ApiInterface().deleteTrip(AppRepository.mTripId(this));
        deleteTripResponseCall.enqueue(new Callback<DeleteTripResponse>() {
            @Override
            public void onResponse(Call<DeleteTripResponse> call, Response<DeleteTripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("zma trip", "delete sho");
                }
            }

            @Override
            public void onFailure(Call<DeleteTripResponse> call, Throwable t) {

                Log.d("zma trip", "delete " + t.getMessage());

            }
        });
    }


    private void initAdapter() {


        linearLayoutManager = new LinearLayoutManager(this);
        dialog = AlertUtils.createProgressDialog(this);
        addTripAdapter = new AddTripAdapter((this), addTripDataModels);
        rvInviteFriend.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvInviteFriend.setAdapter(addTripAdapter);
        rvInviteFriend.setNestedScrollingEnabled(true);
        Collections.reverse(addTripDataModels);
        addTripAdapter.notifyDataSetChanged();
        tvInviteFriendNotFound.setVisibility(View.GONE);

    }


    private void initContactAdapter() {

        contactDataModelList.clear();
        rvMyContact.setLayoutManager(new LinearLayoutManager(this));
        rvMyContact.setHasFixedSize(true);

        listener = (view, position) -> {

            etPhone.setText(contactDataModelList.get(position).getNumContact());
            ContactLayoutGone();
        };

        contactsAdapter = new MyContactsAdapter(this, contactDataModelList, this);
        rvMyContact.setAdapter(contactsAdapter);


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void GetContactsIntoArrayList() {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactDataModel number = new ContactDataModel();
            number.setNameContact(name);
            number.setNumContact(phoneNumber);


            if (!containsContactNumber(contactDataModelList, phoneNumber)) {
                contactDataModelList.add(number);
            }
//
//            Comparator<ContactDataModel> compareById = (ContactDataModel o1, ContactDataModel o2) -> o1.getNameContact().compareTo(o2.getNameContact());
//            Collections.sort(contactDataModelList, compareById);

            contactsAdapter.notifyDataSetChanged();

        }


        cursor.close();

    }

    boolean containsContactNumber(List<ContactDataModel> list, String phoneNumber) {
        for (ContactDataModel item : list) {
            if (item.getNumContact().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    private void ContactGetAndCheckPermission() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.READ_CONTACTS
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    GetContactsIntoArrayList();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }


    @Override
    public void onContactSelected(ContactDataModel contact) {
        etPhone.setText(contact.getNumContact());
        etPhone.setSelection(etPhone.getText().length());
    }
}