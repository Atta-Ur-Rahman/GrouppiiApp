package com.techease.groupiiapplication.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.techease.groupiiapplication.R;


public class DialogBuilder {
    public static AlertDialog dialog;
    public static AlertDialog dialogBuilder(Context context, String label) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
        dialogBuilder.setView(dialogView);


        TextView textView = dialogView.findViewById(R.id.tv_dialog_text);
        textView.setText(label);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }



    public static void showSingleChoiceDialog(Context context) {
        String[] singleChoiceItems = context.getResources().getStringArray(R.array.dialog_single_choice_array);
        int itemSelected = 0;
        new AlertDialog.Builder(context)
                .setTitle("Select your gender")
                .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {

                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void showAlertDialog(Context context,String strId) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.CustomDialogTheme)
                .setTitle("Invitation sent")
                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending bombs to earth");


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Aborting mission");
                    }
                })
//                .setNeutralButton("Neutral", null)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
