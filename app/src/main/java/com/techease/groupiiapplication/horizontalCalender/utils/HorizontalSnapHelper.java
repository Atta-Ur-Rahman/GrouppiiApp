package com.techease.groupiiapplication.horizontalCalender.utils;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.horizontalCalender.HorizontalCalendar;
import com.techease.groupiiapplication.horizontalCalender.HorizontalCalendarView;

public class HorizontalSnapHelper extends LinearSnapHelper {

    private HorizontalCalendar horizontalCalendar;
    private HorizontalCalendarView calendarView;

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        View snapView = super.findSnapView(layoutManager);

        if (calendarView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {
            int selectedItemPosition;
            if (snapView == null) {
                // no snapping required
                selectedItemPosition = horizontalCalendar.getSelectedDatePosition();
                Log.d("zmascroll", "first"+selectedItemPosition);
            } else {

                Log.d("zmascroll", "snap");

                int[] snapDistance = calculateDistanceToFinalSnap(layoutManager, snapView);
                if ((snapDistance[0] != 0) || (snapDistance[1] != 0)) {
                    return snapView;
                }
                selectedItemPosition = layoutManager.getPosition(snapView);
                Log.d("zmascroll", "second"+selectedItemPosition);

            }

            notifyCalendarListener(layoutManager.getPosition(snapView));
        }

        return snapView;
    }

    private void notifyCalendarListener(int selectedItemPosition) {
        if (!horizontalCalendar.isItemDisabled(selectedItemPosition)) {
            horizontalCalendar.getCalendarListener()
                    .onDateSelected(horizontalCalendar.getDateAt(selectedItemPosition), selectedItemPosition);
        }
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        // Do nothing
    }

    public void attachToHorizontalCalendar(@Nullable HorizontalCalendar horizontalCalendar) throws IllegalStateException {
        this.horizontalCalendar = horizontalCalendar;
        this.calendarView = horizontalCalendar.getCalendarView();
        super.attachToRecyclerView(calendarView);
    }
}
