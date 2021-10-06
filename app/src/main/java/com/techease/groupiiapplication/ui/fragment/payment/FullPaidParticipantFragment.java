package com.techease.groupiiapplication.ui.fragment.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.groupiiapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullPaidParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullPaidParticipantFragment extends Fragment {

    public static FullPaidParticipantFragment newInstance(String param1, String param2) {
        FullPaidParticipantFragment fragment = new FullPaidParticipantFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_paid_participant, container, false);
    }
}