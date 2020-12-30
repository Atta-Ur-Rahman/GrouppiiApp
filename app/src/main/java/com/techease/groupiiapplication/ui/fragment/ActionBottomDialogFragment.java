package com.techease.groupiiapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.techease.groupiiapplication.R;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    View view;

    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_action_bottom_dialog, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        view.findViewById(R.id.tvCancel).setOnClickListener(this);
//        view.findViewById(R.id.textView2).setOnClickListener(this);
//        view.findViewById(R.id.textView3).setOnClickListener(this);
//        view.findViewById(R.id.textView4).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        dismiss();
    }

}