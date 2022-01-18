package com.techease.groupiiapplication.ui.activity.tripDetailScreen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.addTrip.MyContactsAdapter;
import com.techease.groupiiapplication.adapter.gallery.RecyclerViewClickListener;
import com.techease.groupiiapplication.dataModel.addTrips.ContactDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.getAllTrip.User;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.fragment.chat.AllUsersChatFragment;
import com.techease.groupiiapplication.ui.fragment.tripes.TripFragment;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
import com.techease.groupiiapplication.utils.PhoneNumberValidator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserTripParticpantActivity extends AppCompatActivity implements View.OnClickListener, MyContactsAdapter.ContactsAdapterListener {


    String strName = "null", strEmail = "", strPhoneNumber, strShareCost = "0";

    @BindView(R.id.tilName)
    TextInputLayout tilName;
    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.etPhone)
    EditText etPhone;

    @BindView(R.id.cvName)
    CardView cvName;

    @BindView(R.id.ivDoneContact)
    ImageView ivDoneContact;


    @BindView(R.id.btnAddUserTripParticipants)
    Button btnAddUserTripParticipants;

    @BindView(R.id.cbShareTripCost)
    CheckBox cbShareCost;

    boolean valid;


    private List<ContactDataModel> contactDataModelList = new ArrayList<>();
    Cursor cursor;
    private String name, phoneNumber;
    public static final int RequestPermissionCode = 1;

    MyContactsAdapter contactsAdapter;
    RecyclerViewClickListener listener;
    @BindView(R.id.rvMyContact)
    RecyclerView rvMyContact;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_user_trip_particpant);
        ButterKnife.bind(this);
        dialog = AlertUtils.createProgressDialog(this);
        initContactAdapter();
        ContactGetAndCheckPermission();


        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

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
//                if (tilEmail.getVisibility() == View.VISIBLE) {
//                    ContactLayoutVisible();
//                }
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

    @SuppressLint("ResourceType")
    private boolean isValid() {
        valid = true;
//        strName = etName.getText().toString();
//        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString();
        strPhoneNumber = strPhoneNumber.replace(" ", "");


        if (strPhoneNumber.equals(AppRepository.mPhoneNumber(this))) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.please_write_your_phone_number) + " not admin phone");
        } else

//        if (strPhoneNumber.length() < 1) {
//            if (strEmail.length() < 1) {
//                valid = false;
//            }
//        }

            if (strPhoneNumber.length() > 0) {
                if (!PhoneNumberValidator.isValidPhoneNumber(strPhoneNumber)) {
                    valid = false;
                    tilPhone.setErrorEnabled(true);
                    tilPhone.setError(getString(R.string.please_write_your_phone_number));

                } else {
                    tilPhone.setError(null);
                    tilPhone.setErrorEnabled(false);
                }
            }

//        if (strEmail.length() < 1) {
//            if (strPhoneNumber.length() < 1) {
//                valid = false;
//            }
//        }
//
//        if (strEmail.length() > 0) {
//            if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
//                tilEmail.setErrorEnabled(true);
//                tilEmail.setError(getString(R.string.valid_email));
//                valid = false;
//            } else {
//                tilEmail.setError(null);
//            }
//        }

        if (!Connectivity.isConnected(this)) {
            valid = false;
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ivBack, R.id.btnAddUserTripParticipants, R.id.etPhone, R.id.ivDoneContact})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivBack:
                onBackPressed();
                break;

            case R.id.btnAddUserTripParticipants:
                if (isValid()) {

                    boolean emailExist = true;
                    for (int i = 0; i < TripDetailScreenActivity.userList.size(); i++) {
                        if (strEmail.equals(TripDetailScreenActivity.userList.get(i).getEmail())) {
                            Toast.makeText(this, "email exist", Toast.LENGTH_SHORT).show();
                            emailExist = false;
                        }
                    }

                    for (int i = 0; i < TripDetailScreenActivity.userList.size(); i++) {
                        if (strPhoneNumber.equals(TripDetailScreenActivity.userList.get(i).getPhone())) {
                            Toast.makeText(this, "phone number exist", Toast.LENGTH_SHORT).show();
                            emailExist = false;
                        }
                    }
                    if (emailExist) {
                        ApiCallForAddInviteFriend();
                    }

                }
                break;
            case R.id.ivAddUserTrip:
            case R.id.ivDoneContact:
                ContactLayoutGone();
                break;

        }


    }


    private void ApiCallForAddInviteFriend() {
        dialog.show();
        TripDetailScreenActivity.userList.clear();
        TripDetailScreenActivity.userParticipaintsCircleList.clear();
        TripDetailScreenActivity.paymentUserParticipaintsList.clear();

        try {
            AddTripDataModel addTripDataModel = new AddTripDataModel();
            addTripDataModel.setEmail(AppRepository.mEmail(AddUserTripParticpantActivity.this));
            addTripDataModel.setTripid(Long.valueOf(AppRepository.mTripIDForUpdation(this)));
            addTripDataModel.setUserid((long) AppRepository.mUserID(this));
            addTripDataModel.setName(AppRepository.mUserName(AddUserTripParticpantActivity.this));
            TripDetailScreenActivity.paymentUserParticipaintsList.add(addTripDataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ApiCAllForAddInviteFriendWithGmailAndPhone();


//
//        if (strPhoneNumber.length() > 1) {
//            if (strEmail.length() > 1) {
//                ApiCAllForAddInviteFriendWithGmailAndPhone();
//
////                Toast.makeText(this, "both", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (strEmail.length() < 1) {
//            if (strPhoneNumber.length() < 1) {
//                ApiCAllForAddInviteFriendWithGmailAndPhone();
////                Toast.makeText(this, "both", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        if (strPhoneNumber.length() > 1 && strEmail.length() < 1) {
//            ApiCAllForAddInviteFriendWithPhone();
////            Toast.makeText(this, "phone", Toast.LENGTH_SHORT).show();
//
//        }
//        if (strEmail.length() > 1 && strPhoneNumber.length() < 1) {
//            ApiCAllForAddInviteFriendWithGmail();
////            Toast.makeText(this, "gmail", Toast.LENGTH_SHORT).show();
//
//        }


    }

    private void ApiCAllForAddInviteFriendWithGmailAndPhone() {
        if (strName.equals("null")) {
            strName = strPhoneNumber;
        }
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTripWithGmailAndPhone(strName, strEmail, strPhoneNumber.trim(), strShareCost,
                AppRepository.mTripIDForUpdation(this), AppRepository.mUserID(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zmaAddUser response", String.valueOf(response.message() + " " + response.code()));
                    Toast.makeText(AddUserTripParticpantActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        try {
                            AllUsersChatFragment.aBooleanRefreshSocket = false;
                            ConnectChatResfresh.setMyBoolean(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            AllUsersChatFragment.aBooleanRefreshSocket = true;
//                            Log.d("zmaerror", e.getMessage());
                        }


                        TripDetailScreenActivity.aBooleanResfreshGetUserTrip = true;
                        TripDetailScreenActivity.userParticipaintsCircleList.addAll(response.body().getData());
                        TripDetailScreenActivity.userList.addAll(response.body().getData());
                        TripDetailScreenActivity.paymentUserParticipaintsList.addAll(response.body().getData());
                        TripDetailScreenActivity.tripParticipantsAdapter.notifyDataSetChanged();


                        TripFragment.userList.clear();
                        for (int i = 0; i < TripDetailScreenActivity.userList.size(); i++) {
                            User user = new User();
                            user.setName(TripDetailScreenActivity.userList.get(i).getName());
                            user.setPicture(String.valueOf(TripDetailScreenActivity.userList.get(i).getPicture()));
                            user.setTripid(TripDetailScreenActivity.userList.get(i).getTripid());
                            user.setUserid(TripDetailScreenActivity.userList.get(i).getUserid());
                            user.setSharedCost(TripDetailScreenActivity.userList.get(i).getSharedCost());
                            TripFragment.userList.add(user);
                        }
                        finish();

                    }

                } else {
                    Log.d("zmaAddUser eresponse", String.valueOf(response.message() + " " + response.code()));

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Log.d("zmaaddparticipant", t.getMessage());
//                Toast.makeText(AddUserTripParticpantActivity.this, "please add participant email", Toast.LENGTH_SHORT).show();
                Toast.makeText(AddUserTripParticpantActivity.this, "phone number already exist", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initContactAdapter() {

        contactDataModelList.clear();
        rvMyContact.setLayoutManager(new LinearLayoutManager(this));
        rvMyContact.setHasFixedSize(true);
        listener = (view, position) -> {
            etPhone.setText(contactDataModelList.get(position).getNumContact());
            strName = contactDataModelList.get(position).getNameContact();
            Toast.makeText(AddUserTripParticpantActivity.this, "", Toast.LENGTH_SHORT).show();
//            ContactLayoutGone();


        };

        contactsAdapter = new MyContactsAdapter(this, contactDataModelList, this);
        rvMyContact.setAdapter(contactsAdapter);


    }


    private void ContactGetAndCheckPermission() {
        Dexter.withContext(this).withPermissions(
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

    private void ContactLayoutVisible() {
        cvName.setVisibility(View.GONE);
        tilEmail.setVisibility(View.GONE);
        rvMyContact.setVisibility(View.VISIBLE);
        ivDoneContact.setVisibility(View.VISIBLE);
    }

    private void ContactLayoutGone() {
        cvName.setVisibility(View.VISIBLE);
        tilEmail.setVisibility(View.VISIBLE);
        rvMyContact.setVisibility(View.GONE);
        ivDoneContact.setVisibility(View.GONE);
        KeyBoardUtils.closeKeyboard(this);
        KeyBoardUtils.hideKeyboard(this);
    }

    @Override
    public void onContactSelected(ContactDataModel contact) {
        etPhone.setText(contact.getNumContact());
        strName = contact.getNameContact();
        etPhone.setSelection(etPhone.getText().length());

//        ContactLayoutGone();

    }
}