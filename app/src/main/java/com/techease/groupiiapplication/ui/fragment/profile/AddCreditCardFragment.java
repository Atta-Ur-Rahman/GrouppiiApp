package com.techease.groupiiapplication.ui.fragment.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addCriditModel.AddCreditCardResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.ui.activity.LoginSignUp.LoginActivity;
import com.techease.groupiiapplication.ui.activity.profile.ProfileActivity;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCreditCardFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.tilCVV)
    TextInputLayout tilCVV;
    @BindView(R.id.tilCardNumber)
    TextInputLayout tilCardNumber;
    @BindView(R.id.tilName)
    TextInputLayout tilName;
    @BindView(R.id.tilExpiry)
    TextInputLayout tilExpiry;
    @BindView(R.id.etCardNumber)
    TextInputEditText etCardNumber;
    @BindView(R.id.etCardHolderName)
    TextInputEditText etHolderName;
    @BindView(R.id.etExpiry)
    TextInputEditText etExpiry;
    @BindView(R.id.etCVV)
    TextInputEditText etCVV;
    @BindView(R.id.btnContinue)
    Button btnContinue;
    private String cardNumber, holderName, expiryYear, expiryMonth, cvv, cardType = "";
    private boolean valid = false;
    private View view;
    private static String VISA_CARD = "4";
    private static String MASTER_CARD = "5";
    private static String DISCOVER = "6";
    private static String AMERICAN_CARD = "37";
    private static String CARTE_BLANCHE_DINERS_CLUB = "38";
    private boolean isBackspaceClicked = false;

    Dialog dialog;

    public static AddCreditCardFragment newInstance() {
        AddCreditCardFragment fragment = new AddCreditCardFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_add_credit_card, container, false);
        ButterKnife.bind(this, parentView);
        dialog = AlertUtils.createProgressDialog(getActivity());


        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (after < count) {
                    isBackspaceClicked = true;
                } else if (after > count) {
                    isBackspaceClicked = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isBackspaceClicked) {
                    if (s.length() == 2) {
                        etExpiry.append("/");
                    }
                } else {

                    etExpiry.append("");
                }

            }
        });


        return parentView;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.btnContinue, R.id.ivBackArrow})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackArrow:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            case R.id.btnContinue:

                if (isValid()) {
                    ApiCallForAddCreditCard();
                }
                break;
        }
    }

    private void ApiCallForAddCreditCard() {

        dialog.show();
        String[] exp = expiryYear.split("/");
        expiryYear = "20" + exp[1];
        expiryMonth = exp[0];


        Log.d("zmaexpier", expiryYear + "   " + expiryMonth);


        Call<AddCreditCardResponse> addCreditCardResponseCall = BaseNetworking.ApiInterface().addCreditCard(AppRepository.mUserName(getActivity()), cardNumber, expiryMonth, expiryYear, cvv, "cus_KLo5BYjalkYKXY");
        addCreditCardResponseCall.enqueue(new Callback<AddCreditCardResponse>() {
            @Override
            public void onResponse(Call<AddCreditCardResponse> call, Response<AddCreditCardResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddCreditCardResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }


    private boolean isValid() {
        valid = true;
        cardNumber = etCardNumber.getText().toString();
        holderName = etHolderName.getText().toString();
        cvv = etCVV.getText().toString();
        expiryYear = etExpiry.getText().toString();
        valid = true;
        if (cardNumber.isEmpty() || cardNumber.length() < 16) {
            valid = false;
            tilCardNumber.setError("Please enter card number");
        } else {
            tilCardNumber.setError(null);
            if (cardNumber.substring(0, 1).equals(VISA_CARD)) {
                cardType = "VISA Card";
            } else if (cardNumber.substring(0, 1).equals(MASTER_CARD)) {
                cardType = "Master Card";
            } else if (cardNumber.substring(0, 1).equals(DISCOVER)) {
                cardType = "Discover";
            } else if (cardNumber.substring(0, 1).equals("3")) {
                if (cardNumber.substring(1, 2).equals("7")) {
                    cardType = "American Express";
                } else if (cardNumber.substring(1, 2).equals("8")) {
                    cardType = "Carte Blanche Diners Club";
                }
            }
        }

        if (holderName.isEmpty() || holderName.length() < 3) {
            tilName.setError("Please write a valid name");
            valid = false;
        } else {
            tilName.setError(null);
        }

        if (cvv.isEmpty() || cvv.length() < 3) {
            valid = false;
            tilCVV.setError("Invalid cvv");
        } else {
            tilCVV.setError(null);
        }
        if (expiryYear.isEmpty()) {
            tilExpiry.setError("Invalid card expiry");
            valid = false;
        } else if (Integer.parseInt(expiryYear.substring(0, 2)) > 12 ||
                Integer.parseInt(expiryYear.substring(0, 2)) == 0) {
            tilExpiry.setError("Invalid expiry month");
            valid = false;
        } else if (Integer.parseInt(expiryYear.substring(3, 5)) < 20) {
            tilExpiry.setError("Invalid expiry year");
            valid = false;
        } else {
            tilExpiry.setError(null);
        }
        return valid;
    }
}