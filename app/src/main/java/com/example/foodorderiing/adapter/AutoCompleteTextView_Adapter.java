package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Grouping;

import java.util.ArrayList;
import java.util.List;


public class AutoCompleteTextView_Adapter extends ArrayAdapter<Grouping> {

    private List<Grouping> list;
    private Context mContext;
    private int itemLayout;
    private List<Grouping> dataListAllItems;
    private ListFilter listFilter = new ListFilter();


    public AutoCompleteTextView_Adapter (Context context, List<Grouping> lists) {
        super(context, 0 , lists);
        list = lists;
        mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Grouping getItem(int position) {
        return list.get(position);
    }

    class ViewHolder {
        private TextView textView;
    }


//    @Override
//    public View getView (int position, View convertView,  ViewGroup parent){
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            convertView = inflater.inflate(itemLayout, parent, false);
//
//            viewHolder.textView = convertView.findViewById(R.id.tv_list_item_AutoComplete);
//
//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.textView.setText(list.get(position).name);
//
//
//        return convertView;
//    }




    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_auto_complete_textview, parent, false);
        }
//        view.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        TextView strName = view.findViewById(R.id.tv_list_item_AutoComplete);
        strName.setText(getItem(position).name);
        return view;
    }


    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<>(list);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();
                ArrayList<Grouping> matchValues = new ArrayList<>();
                for (Grouping dataItem : dataListAllItems) {
                    if (dataItem.name.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                list = (List<Grouping>) results.values;
            } else {
                list = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}