package com.example.foodorderiing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Grouping;
import java.util.List;


public class GroupInProductAdapter extends RecyclerView.Adapter<GroupInProductAdapter.ViewHolder> {
    Context context;
    List<Grouping> list;
    Listener listener;
    int row_index = 0;


    public GroupInProductAdapter(List<Grouping> list, Context context , Listener listener ) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener{
        void onClick(int position , Grouping grouping);
    }

    @Override
    public GroupInProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_group_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupInProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Grouping grouping = list.get(position);
        holder.tv_name_groupInproduct.setText(grouping.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position ;
                notifyDataSetChanged();
                listener.onClick(position , grouping);
            }
        });

            if(row_index == position){
                holder.cardView.setCardBackgroundColor(Color.parseColor("#ef4224"));
                holder.tv_name_groupInproduct.setTextColor(Color.parseColor("#f8f3f7"));
            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#f8f3f7"));
                holder.tv_name_groupInproduct.setTextColor(Color.parseColor("#676767"));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_groupInproduct ;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_groupInproduct = itemView.findViewById(R.id.tv_groupInproduct);
            cardView = itemView.findViewById(R.id.cardview_groupINproduct);
        }
    }

}