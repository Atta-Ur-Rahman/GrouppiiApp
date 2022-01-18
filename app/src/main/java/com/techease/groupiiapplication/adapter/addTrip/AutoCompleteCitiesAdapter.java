package com.techease.groupiiapplication.adapter.addTrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.dataModel.addTrips.OgodaHotel.HotelCityIdData;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCitiesAdapter extends ArrayAdapter<HotelCityIdData> {
    private List<HotelCityIdData> hotelCityIdDataList;
    Context context;

    public AutoCompleteCitiesAdapter(@NonNull Context context, @NonNull List<HotelCityIdData> hotelCityIdData) {
        super(context, 0, hotelCityIdData);
        this.hotelCityIdDataList = hotelCityIdData;
        this.context = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_city_name_layout, parent, false
            );
        }

        TextView tvCityName = convertView.findViewById(R.id.tvAutocopleteCityName);
        HotelCityIdData countryItem = getItem(position);

        if (countryItem != null) {
            tvCityName.setText(countryItem.getCityName());
        }

        return convertView;
    }


    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<HotelCityIdData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(hotelCityIdDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (HotelCityIdData item : hotelCityIdDataList) {
                    if (item.getCityName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((HotelCityIdData) resultValue).getCityName();
        }
    };
}