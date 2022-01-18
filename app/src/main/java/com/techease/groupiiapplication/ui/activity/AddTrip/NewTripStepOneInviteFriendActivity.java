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
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.cardview.widget.CardView;
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
import com.techease.groupiiapplication.adapter.addTrip.AddTripParticipaintsAdapter;
import com.techease.groupiiapplication.adapter.addTrip.MyContactsAdapter;
import com.techease.groupiiapplication.adapter.gallery.RecyclerViewClickListener;
import com.techease.groupiiapplication.dataModel.addTrips.ContactDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripDataModel;
import com.techease.groupiiapplication.dataModel.addTrips.addTrip.AddTripResponse;
import com.techease.groupiiapplication.dataModel.addTrips.createTrip.CreateTripResponse;
import com.techease.groupiiapplication.dataModel.addTrips.tripDelete.DeleteTripResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.Constants;
import com.techease.groupiiapplication.utils.KeyBoardUtils;
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

    @BindView(R.id.cvName)
    CardView cvName;

    @BindView(R.id.rvInviteFriend)
    RecyclerView rvInviteFriend;

    boolean valid;
    String strName="nulll", strEmail, strPhoneNumber, strShareCost = "0";

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

    @BindView(R.id.tvEditParticipant)
    TextView tvInviteFriend;


    @BindView(R.id.tvSendInviteFriend)
    TextView tvSendInviteFriend;

    @BindView(R.id.cbShareTripCost)
    CheckBox cbShareCost;

    public static ArrayList<AddTripDataModel> addTripDataModels = new ArrayList<>();

    boolean contactPermissiongBoolean = true;

    Dialog dialog;

    @BindView(R.id.tvInviteFriendNotFound)
    TextView tvInviteFriendNotFound;


    LinearLayoutManager linearLayoutManager;
    public static AddTripParticipaintsAdapter addTripAdapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ivDoneContact)
    ImageView ivDoneContact;

    @BindView(R.id.btnNext)
    Button btnNext;

    private List<ContactDataModel> contactDataModelList = new ArrayList<>();
    Cursor cursor;
    private String name, phoneNumber;
    MyContactsAdapter contactsAdapter;
    RecyclerViewClickListener listener;
    @BindView(R.id.rvMyContact)
    RecyclerView rvMyContact;
    String strTripID;

    public ArrayList<AddTripDataModel> userListRegister = new ArrayList<>();
    public ArrayList<AddTripDataModel> userListNotRegister = new ArrayList<>();


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
        ProcessBarAnimation();

        strTripID = AppRepository.mTripIDForUpdation(this);


        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (contactPermissiongBoolean) {
                    String text = editable.toString();
                    Log.d("zma text", text);
                    contactsAdapter.getFilter().filter(text);
                    if (tilEmail.getVisibility() == View.VISIBLE) {
                    }
                }
            }
        });
//
//        etPhone.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, @SuppressLint("ClickableViewAccessibility") MotionEvent motionEvent) {
//
//                if (rvMyContact.getVisibility()==View.VISIBLE){
//                    ContactLayoutGone();
//                    KeyBoardUtils.closeKeyboard(NewTripStepOneInviteFriendActivity.this);
//                    KeyBoardUtils.hideKeyboard(NewTripStepOneInviteFriendActivity.this);
//                }else {
//
//                    ContactLayoutVisible();
//                }
//                return false;
//            }
//        });
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
                    strTripID = response.body().getTripid() + "";
                    AppRepository.mPutValue(NewTripStepOneInviteFriendActivity.this).putString("tripIDForUpdation", strTripID).commit();
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
    @OnClick({R.id.ivBack, R.id.ivAddUserTrip, R.id.clAddInvite, R.id.clInviteFriend, R.id.btnNext, R.id.tvSendInviteFriend, R.id.etPhone, R.id.ivDoneContact})
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ivBack:
                if (tilEmail.getVisibility() == View.VISIBLE) {
//                    if (clInviteFriend.getVisibility() == View.VISIBLE) {
//                        clAddInvite.setVisibility(View.VISIBLE);
//                        clInviteFriend.setVisibility(View.GONE);
//                    } else {
                    onBackPressed();
                    apiCallForTripDelete();
//                    }
                } else {
                    ContactLayoutGone();
                }

                break;

            case R.id.tvSendInviteFriend:


                if (isValid()) {

                    for (int i = 0; i < addTripDataModels.size(); i++) {
                        Log.d("zmadata", addTripDataModels.get(i).getEmail() + "   " + addTripDataModels.get(i).getPhone());
                    }
                    boolean emailExist = true;
                    for (int i = 0; i < addTripDataModels.size(); i++) {
                        if (strEmail.equals(addTripDataModels.get(i).getEmail())) {
                            Toast.makeText(this, "email exist", Toast.LENGTH_SHORT).show();
                            emailExist = false;
                        }
                    }
                    for (int i = 0; i < addTripDataModels.size(); i++) {
                        if (strPhoneNumber.equals(addTripDataModels.get(i).getPhone())) {
                            Toast.makeText(this, "phone number exist", Toast.LENGTH_SHORT).show();
                            emailExist = false;
                        }
                    }
                    if (emailExist) {
                        if (strPhoneNumber.length() > 1 && strEmail.length() < 1) {
                            ApiCallForAddInviteFriendWithPhone();
                        }
//                        if (strPhoneNumber.length() > 1) {
//                            if (strEmail.length() > 1) {
//                                ApiCallForAddInviteFriendWithGmailAndPhone();
//                            }
//                        }
//                        if (strEmail.length() < 1) {
//                            if (strPhoneNumber.length() < 1) {
//                                ApiCallForAddInviteFriendWithGmailAndPhone();
//                            }
//                        }


//                        if (strEmail.length() > 1 && strPhoneNumber.length() < 1) {
//                            ApiCallForAddInviteFriendWithGmail();
//                        }
                    }
                }
                break;
            case R.id.ivAddUserTrip:
                ContactGetAndCheckPermission();
                clAddInvite.setVisibility(View.GONE);
                clInviteFriend.setVisibility(View.VISIBLE);
                ContactLayoutGone();
                ContactLayoutVisible();


                tvSendInviteFriend.setVisibility(View.VISIBLE);


                break;
            case R.id.btnNext:
//                if (addTripDataModels.size() != 0) {
//                    TripFragment.aBooleanRefreshAllTripApi = true;
                startActivity(new Intent(this, NewTripStepTwoAddDetailActivity.class), ActivityOptions.makeSceneTransitionAnimation(NewTripStepOneInviteFriendActivity.this).toBundle());
//                    this.finish();
//                } else {
//                    Toast.makeText(this, "Please Add Trip Participants", Toast.LENGTH_SHORT).show();
//                }
                break;

            case R.id.ivDoneContact:
//                ContactLayoutGone();
        }
    }


    private void ApiCallForAddInviteFriendWithPhone() {
        dialog.show();
        addTripDataModels.clear();
        if (strName.equals("null")) {
            strName = strPhoneNumber;
        }
        Call<AddTripResponse> addTripResponseCall = BaseNetworking.ApiInterface().addTripWithPhone(strName, strPhoneNumber.trim(), strShareCost, strTripID, AppRepository.mUserID(this));
        addTripResponseCall.enqueue(new Callback<AddTripResponse>() {
            @Override
            public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d("zma response", String.valueOf(response.message()));
                    Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    if (response.message().equals("OK")) {
                        strName = "null";
                        clAddInvite.setVisibility(View.VISIBLE);
                        clInviteFriend.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
                        etEmail.setText("");
                        etPhone.setText("");
                        etName.setText("");
                        cbShareCost.setChecked(false);
                        addTripDataModels.addAll(response.body().getData());
                        btnNext.setVisibility(View.VISIBLE);
                        Collections.reverse(addTripDataModels);

                        userListNotRegister.clear();
                        userListRegister.clear();
                        for (AddTripDataModel addTripDataModel : addTripDataModels) {
                            if (addTripDataModel.getType().equals("registered")) {
                                userListRegister.add(addTripDataModel);
                            }
                            if (addTripDataModel.getType().equals("notregistered")) {
                                userListNotRegister.add(addTripDataModel);
                            }
                        }


                        addTripDataModels.clear();
                        addTripDataModels.addAll(userListRegister);
                        addTripDataModels.addAll(userListNotRegister);
                        addTripAdapter.notifyDataSetChanged();

                        tvInviteFriendNotFound.setVisibility(View.GONE);

                        Log.d("zma user", "" + addTripDataModels);
                    }

                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddTripResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(NewTripStepOneInviteFriendActivity.this, "phone number exist", Toast.LENGTH_SHORT).show();

//                Toast.makeText(NewTripStepOneInviteFriendActivity.this, String.valueOf("error " + t), Toast.LENGTH_SHORT).show();
                Log.d("zma", t.getMessage());
            }
        });

    }


    private boolean isValid() {
        valid = true;
        strEmail = etEmail.getText().toString();
        strPhoneNumber = etPhone.getText().toString().trim();
        strPhoneNumber = strPhoneNumber.replace(" ", "");


        if (strPhoneNumber.equals(AppRepository.mPhoneNumber(this))) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.please_write_your_phone_number) + " not admin phone");
        } else if (!android.util.Patterns.PHONE.matcher(strPhoneNumber).matches()) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.please_write_your_phone_number));

        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
        }


     /*   if (!PhoneNumberValidator.isValidPhoneNumber(strPhoneNumber)) {
            valid = false;
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.plesase_write_your_phone_number));

        } else {
            tilPhone.setError(null);
            tilPhone.setErrorEnabled(false);
        }

        if (strName.isEmpty()) {
            valid = false;
            tilName.setErrorEnabled(true);
            tilName.setError(getString(R.string.plesase_write_participant_name));

        } else {
            tilName.setError(null);
            tilName.setErrorEnabled(false);
        }*/

//
//        if (strPhoneNumber.length() < 1) {
//            if (strEmail.length() < 1) {
//                valid = false;
//            }
//        }
//        if (strEmail.length() < 1) {
//            if (strPhoneNumber.length() < 1) {
//                valid = false;
//            }
//        }
//
//        if (strPhoneNumber.length() > 0) {
//            if (!PhoneNumberValidator.isValidPhoneNumber(strPhoneNumber)) {
//                valid = false;
//                tilPhone.setErrorEnabled(true);
//                tilPhone.setError(getString(R.string.plesase_write_your_phone_number));
//
//            } else {
//                tilPhone.setError(null);
//                tilPhone.setErrorEnabled(false);
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

    @Override
    public void onBackPressed() {
        if (tilPhone.getVisibility() == View.VISIBLE) {
            if (clInviteFriend.getVisibility() == View.VISIBLE) {
                clAddInvite.setVisibility(View.VISIBLE);
                clInviteFriend.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
            } else {
                apiCallForTripDelete();
                super.onBackPressed();
            }
        } else {
            ContactLayoutGone();

        }


    }


    private void ContactLayoutVisible() {
        cvName.setVisibility(View.GONE);
        tilEmail.setVisibility(View.GONE);
//        tvSendInviteFriend.setVisibility(View.GONE);
        tvInviteFriend.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        rvMyContact.setVisibility(View.VISIBLE);
//        ivDoneContact.setVisibility(View.VISIBLE);


    }

    private void ContactLayoutGone() {
        cvName.setVisibility(View.VISIBLE);
        tilEmail.setVisibility(View.VISIBLE);
        tvSendInviteFriend.setVisibility(View.VISIBLE);
        tvInviteFriend.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        rvMyContact.setVisibility(View.GONE);
//        ivDoneContact.setVisibility(View.GONE);
        KeyBoardUtils.closeKeyboard(this);
        KeyBoardUtils.hideKeyboard(this);

    }

    private void apiCallForTripDelete() {
        Call<DeleteTripResponse> deleteTripResponseCall = BaseNetworking.ApiInterface().deleteTrip(strTripID);
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

        addTripDataModels.clear();

        linearLayoutManager = new LinearLayoutManager(this);
        dialog = AlertUtils.createProgressDialog(this);
        addTripAdapter = new AddTripParticipaintsAdapter((this), addTripDataModels);
        rvInviteFriend.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvInviteFriend.setAdapter(addTripAdapter);
        rvInviteFriend.setNestedScrollingEnabled(true);

//        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


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
        Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_CONTACTS
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (report.getGrantedPermissionResponses().size() == 1) {
                        etPhone.setInputType(InputType.TYPE_CLASS_TEXT);
                        GetContactsIntoArrayList();
                        contactPermissiongBoolean = true;
                    }

                    if (report.getDeniedPermissionResponses().size() == 1) {
                        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
                        contactPermissiongBoolean = false;

                    }
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
        etName.setText(contact.getNameContact());

        strName = contact.getNameContact();
        etPhone.setSelection(etPhone.getText().length());
//        ContactLayoutGone();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.aBooleanAddedTripApi) {
            finish();
        }
    }
}