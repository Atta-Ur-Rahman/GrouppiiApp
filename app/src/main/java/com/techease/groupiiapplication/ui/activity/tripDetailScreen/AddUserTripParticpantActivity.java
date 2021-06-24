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
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.interfaceClass.refreshChat.ConnectChatResfresh;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserTripParticpantActivity extends AppCompatActivity implements View.OnClickListener, MyContactsAdapter.ContactsAdapterListener {


    String strName, strEmail, strPhoneNumber, strShareCost = "0";

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
        strName = etName.getText().toString();
        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString();

        if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.valid_email));
            valid = false;
        } else {
            tilEmail.setError(null);
        }

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
                    for (int i = 0; i < TripDetailScreenActivity.userParticipaintsList.size(); i++) {
                        if (strEmail.equals(TripDetailScreenActivity.userParticipaintsList.get(i).getEmail())) {
                            Toast.makeText(this, "email exist", Toast.LENGTH_SHORT).show();
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
        TripDetailScreenActivity.userParticipaintsList.clear();
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTrip(strName, strEmail, strPhoneNumber, strShareCost,
                AppRepository.mTripId(this), AppRepository.mUserID(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma response", String.valueOf(response.message() + " " + response.code()));
                    Toast.makeText(AddUserTripParticpantActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        ConnectChatResfresh.setMyBoolean(true);
                        TripDetailScreenActivity.userParticipaintsList.addAll(response.body().getData());
                        TripDetailScreenActivity.tripParticipantsAdapter.notifyDataSetChanged();
                        finish();

                    }

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddUserTripParticpantActivity.this, "please add participant email", Toast.LENGTH_SHORT).show();
            }
        });

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
        etPhone.setSelection(etPhone.getText().length());
        ContactLayoutGone();

    }
}