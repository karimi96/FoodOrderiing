package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Grouping;

import java.util.ArrayList;
import java.util.List;

public class GroupingAdapter extends RecyclerView.Adapter<GroupingAdapter.ViewHolder> {
    List<Grouping> list;
    Context context;

    public GroupingAdapter(List<Grouping> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GroupingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_grouping,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupingAdapter.ViewHolder holder, int position) {
        Grouping grouping = list.get(position);
        holder.tv_name_category.setText(grouping.name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_category;
        ImageView img_food_grouping;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_category = itemView.findViewById(R.id.tv_name_of_category_group);
            img_food_grouping = itemView.findViewById(R.id.img_food_grouping);


        }
    }

    public void addList(List<Grouping> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
