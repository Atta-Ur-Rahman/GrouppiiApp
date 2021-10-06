package com.techease.groupiiapplication.ui.fragment.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.groupiiapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartiallyPaidParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartiallyPaidParticipantFragment extends Fragment {

    public static PartiallyPaidParticipantFragment newInstance(String param1, String param2) {
        PartiallyPaidParticipantFragment fragment = new PartiallyPaidParticipantFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partially_paid_participant, container, false);
    }
}