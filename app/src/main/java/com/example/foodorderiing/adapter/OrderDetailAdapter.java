package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.model.OrderDetail;

import java.util.List;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    Context context;
    List<OrderDetail> list;


    public OrderDetailAdapter(List<OrderDetail> list, Context context ) {
        this.list = list;
        this.context = context;
    }


    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_order_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderDetailAdapter.ViewHolder holder, int position) {
        OrderDetail orderDetail = list.get(position);
        holder.name_detail.setText(orderDetail.name);
        holder.category_detail.setText(orderDetail.category);
        holder.price_detail.setText(orderDetail.price);
        holder.amount_detail.setText(String.valueOf(orderDetail.amount));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_detail;
        TextView category_detail;
        TextView price_detail;
        TextView amount_detail;


        public ViewHolder(View itemView) {
            super(itemView);
           name_detail = itemView.findViewById(R.id.name_detail);
           category_detail = itemView.findViewById(R.id.category_detail1);
           price_detail = itemView.findViewById(R.id.price_detai1);
           amount_detail = itemView.findViewById(R.id.amount_detail);
        }
    }

}