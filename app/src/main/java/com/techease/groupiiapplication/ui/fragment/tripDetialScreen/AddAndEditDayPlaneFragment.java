package com.techease.groupiiapplication.ui.fragment.tripDetialScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.tripDetial.addTripDay.AddTripDayResponse;
import com.techease.groupiiapplication.interfaceClass.AddActivityBackListener;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.Connectivity;
import com.techease.groupiiapplication.utils.DateUtills;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAndEditDayPlaneFragment extends Fragment implements View.OnClickListener {


    EditText etActivityTitle, etActivityDate, etActivityTime, etActivityNote;
    ImageView ivAddActivity, ivAddActivityBack, ivActivityType, ivAddPaymentBack, ivType;
    TextInputLayout tillActivityTitle, tillDate, tillTime, tillActivityNote, tillPaymentTitle, tillPaymentDate, tillPaymentAmount, tillShortDescription;
    TextView tvAddActivity, tvAddActivityTitle;
    SwitchCompat switchCompatGroupActivity;
    Dialog addActivityTypeDialog, dialog;
    String strActivityType;

    boolean valid = true;
    LinearLayout llTaxi, llBus, llReserv, llFlight;

    String tripID;
    int userID;
    AddActivityBackListener addActivityBackListener;


    String strTripDate, strActivityTitle, strActivityDate, strActivityTime, strActivityNote;

    public static AddAndEditDayPlaneFragment newInstance() {
        AddAndEditDayPlaneFragment fragment = new AddAndEditDayPlaneFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_add_and_edit_day_plane, container, false);
        ButterKnife.bind(this, parentView);
        dialog = AlertUtils.createProgressDialog(getActivity());


        if (getActivity() instanceof AddActivityBackListener)
            addActivityBackListener = (AddActivityBackListener) getActivity();


        userID = AppRepository.mUserID(getActivity());
        tripID = AppRepository.mTripIDForUpdation(getActivity());

        //add activity
        ivAddActivityBack = parentView.findViewById(R.id.ivAddActivityBack);
        ivActivityType = parentView.findViewById(R.id.ivActivityType);

        tillActivityTitle = parentView.findViewById(R.id.tilActivityTitle);
        tillDate = parentView.findViewById(R.id.tillAddActivityDate);
        tillTime = parentView.findViewById(R.id.tillActivitTime);
        tillActivityNote = parentView.findViewById(R.id.tilAddActivityNote);

        etActivityTitle = parentView.findViewById(R.id.etActivityTitle);
        etActivityDate = parentView.findViewById(R.id.etAddActivityDate);
        etActivityTime = parentView.findViewById(R.id.etActivityTime);
        etActivityNote = parentView.findViewById(R.id.etAddActivityNote);


        tvAddActivityTitle = parentView.findViewById(R.id.tvAddActivityTitle);

        ivType = parentView.findViewById(R.id.ivActivityType);

        tvAddActivity = parentView.findViewById(R.id.tvActivityAdd);
        switchCompatGroupActivity = parentView.findViewById(R.id.swAddGroupActivity);


        if (AppRepository.mEditDayPlanActivity(getActivity())) {
            AppRepository.mPutValue(getActivity()).putBoolean("mEditDayPlanActivity", false).commit();
            tvAddActivityTitle.setText("Edit Activity");
            tvAddActivity.setText("Save");

        }

        return parentView;
    }

    @OnClick({R.id.tvActivityAdd, R.id.ivActivityType, R.id.ivAddActivityBack, R.id.etAddActivityDate, R.id.etActivityTime})
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvActivityAdd:

                if (isValid()) {
                    ApiCallForAddDayActivity();
                }
                break;

            case R.id.ivAddActivityBack:
                addActivityBackListener.onAddActivityBack();
                break;
            case R.id.ivActivityType:
                activityTripTypeDialog();
                break;
            case R.id.etAddActivityDate:
//                KeyBoardUtils.closeKeyboard(getActivity());
//                KeyBoardUtils.hideKeyboard(Objects.requireNonNull(getActivity()));
                DateUtills.GetDatePickerDialog(etActivityDate, getActivity());
                break;
            case R.id.etActivityTime:
//                KeyBoardUtils.closeKeyboard(getActivity());
//                KeyBoardUtils.hideKeyboard(Objects.requireNonNull(getActivity()));
                DateUtills.GetTimeDialog(etActivityTime, getActivity());
                break;
            case R.id.llTaxi:
                ivActivityType.setImageResource(R.drawable.taxi_wheel);
                ivType.setImageResource(R.drawable.taxi_wheel);
                strActivityType = "Taxi";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llFlight:
                ivActivityType.setImageResource(R.drawable.flight);
                ivType.setImageResource(R.drawable.flight);
                strActivityType = "Flight";
                addActivityTypeDialog.dismiss();
                break;
            case R.id.llBus:
                ivActivityType.setImageResource(R.drawable.transfer);
                ivType.setImageResource(R.drawable.transfer);
                strActivityType = "Bus";
                addActivityTypeDialog.dismiss();

                break;
            case R.id.llReserv:
                ivActivityType.setImageResource(R.drawable.reserv_selected);
                ivActivityType.setImageResource(R.drawable.reserv_selected);
                strActivityType = "Hotel";
                addActivityTypeDialog.dismiss();
                break;
        }
    }


    void activityTripTypeDialog() {
        addActivityTypeDialog = new Dialog(getActivity());
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
        if (!Connectivity.isConnected(getActivity())) {
            valid = false;
            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }


        return valid;
    }

    private void ApiCallForAddDayActivity() {
        dialog.show();
        Call<AddTripDayResponse> addTripDayResponseCall = BaseNetworking.ApiInterface().addTripDay(strActivityTitle, strActivityNote, strActivityDate, strActivityTime, tripID, userID, strActivityType);
        addTripDayResponseCall.enqueue(new Callback<AddTripDayResponse>() {
            @Override
            public void onResponse(Call<AddTripDayResponse> call, Response<AddTripDayResponse> response) {
                if (response.isSuccessful()) {

                    dialog.dismiss();
//                    Log.d("zma", String.valueOf(response.message()));

                    AllTripDayPlanFragment.ApiCallAllTirp(AppRepository.mTripIDForUpdation(getActivity()));


                    //add activity bottom sheet
//                    addActivityBottomSheetBehavior.setHideable(true);
//                    addActivityBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


                    Toast.makeText(getActivity(), "successful", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });


    }


}