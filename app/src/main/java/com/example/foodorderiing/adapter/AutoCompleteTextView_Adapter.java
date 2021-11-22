package com.example.foodorderiing.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Grouping;

import java.util.ArrayList;
import java.util.List;


public class AutoCompleteTextView_Adapter extends ArrayAdapter<Grouping> {

    private Context context;
    private int resourceId;
    private List<Grouping> items, tempItems, suggestions;

    int selected_region_id = 0;

    public AutoCompleteTextView_Adapter(@NonNull Context context, int resourceId, ArrayList<Grouping> items, int region_id) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
        this.selected_region_id = region_id;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            Grouping grouping = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.title);
            if (grouping != null) {
                name.setText(grouping.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public Grouping getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return RegionFilter;
    }

    private Filter RegionFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Grouping Region = (Grouping) resultValue;
            return Region.name;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            synchronized (filterResults) {
                if (charSequence != null) {
                    // Clear and Retrieve the autocomplete results.
                    List<Grouping> resultList = getFilteredResults(charSequence);
                    suggestions.addAll(resultList);
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Grouping> tempValues = (ArrayList<Grouping>) filterResults.values;
            Log.e("qqqq", "publishResults: " + charSequence + " - " + filterResults.count);
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Grouping RegionObj : tempValues) {
                    add(RegionObj);
                    //notifyDataSetChanged();
                }
            } else {
                clear();
                addAll(tempItems);
                notifyDataSetChanged();
            }
        }
    };

    private List<Grouping> getFilteredResults(CharSequence charSequence) {
        List<Grouping> filteredResults = new ArrayList<>();
        for (Grouping Region: tempItems) {
            if (Region.name.toLowerCase().contains(charSequence.toString().toLowerCase()) ) {
                filteredResults.add(Region);
            }
        }
        return  filteredResults;
    }
}

