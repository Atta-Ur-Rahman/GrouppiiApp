package com.techease.groupiiapplication.horizontalCalender.adapter;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.horizontalCalender.HorizontalCalendar;
import com.techease.groupiiapplication.horizontalCalender.model.HorizontalCalendarConfig;
import com.techease.groupiiapplication.horizontalCalender.utils.CalendarEventsPredicate;
import com.techease.groupiiapplication.horizontalCalender.utils.HorizontalCalendarPredicate;
import com.techease.groupiiapplication.horizontalCalender.utils.Utils;

import java.util.Calendar;
import java.util.List;

public class DaysAdapter extends HorizontalCalendarBaseAdapter<DateViewHolder, Calendar> {

    public DaysAdapter(HorizontalCalendar horizontalCalendar, Calendar startDate, Calendar endDate, HorizontalCalendarPredicate disablePredicate, CalendarEventsPredicate eventsPredicate) {
        super(R.layout.hc_item_calendar, horizontalCalendar, startDate, endDate, disablePredicate, eventsPredicate);
    }

    @Override
    protected DateViewHolder createViewHolder(View itemView, int cellWidth) {
        final DateViewHolder holder = new DateViewHolder(itemView);
        holder.layoutContent.setMinimumWidth(cellWidth);

        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        Calendar day = getItem(position);
        HorizontalCalendarConfig config = horizontalCalendar.getConfig();

        final Integer selectorColor = horizontalCalendar.getConfig().getSelectorColor();
        if (selectorColor != null) {
            holder.selectionView.setBackgroundColor(selectorColor);
        }




        holder.textMiddle.setText(DateFormat.format(config.getFormatMiddleText(), day));
        holder.textMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getSizeMiddleText());

        if (config.isShowTopText()) {
            holder.textTop.setText(DateFormat.format(config.getFormatTopText(), day));
            holder.textTop.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getSizeTopText());
        } else {
            holder.textTop.setVisibility(View.GONE);
        }

        if (config.isShowBottomText()) {
            holder.textBottom.setText(DateFormat.format(config.getFormatBottomText(), day));
            holder.textBottom.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getSizeBottomText());
        } else {
            holder.textBottom.setVisibility(View.GONE);
        }

        showEvents(holder, day);
        applyStyle(holder, day, position);

    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position, List<Object> payloads) {
        if ((payloads == null) || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }

        Calendar date = getItem(position);
        applyStyle(holder, date, position);
    }

    @Override
    public Calendar getItem(int position) throws IndexOutOfBoundsException {
        if (position >= itemsCount) {
            throw new IndexOutOfBoundsException();
        }

        int daysDiff = position - horizontalCalendar.getShiftCells();

        Calendar calendar = (Calendar) startDate.clone();
        calendar.add(Calendar.DATE, daysDiff);

        return calendar;
    }

    @Override
    protected int calculateItemsCount(Calendar startDate, Calendar endDate) {
        int days = Utils.daysBetween(startDate, endDate) + 1;
        return days + (horizontalCalendar.getShiftCells() * 2);
    }
}
