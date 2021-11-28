package com.example.foodorderiing.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.model.OrderDetail;
import com.example.foodorderiing.model.Product;

import java.util.List;


public class OrdringDetailAdapter extends RecyclerView.Adapter<OrdringDetailAdapter.ViewHolder> {
    Context context;
//    List<OrderDetail> list;


//    public OrdringDetailAdapter(List<OrderDetail> list, Context context ) {
//        this.list = list;
//        this.context = context;
//    }

    public OrdringDetailAdapter( Context context ) {
        this.context = context;
    }

    @Override
    public OrdringDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_order_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrdringDetailAdapter.ViewHolder holder, int position) {
//        OrderDetail orderDetail = list.get(position);
//        holder.name_detail.setText(orderDetail.name);
//        holder.category_detail.setText(orderDetail.category);
//        holder.price_detail.setText(orderDetail.price);

    }


    @Override
    public int getItemCount() {
        return 12;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_detail;
        TextView category_detail;
        TextView price_detail;


        public ViewHolder(View itemView) {
            super(itemView);
           name_detail = itemView.findViewById(R.id.name_detail);
           category_detail = itemView.findViewById(R.id.category_detail);
           price_detail = itemView.findViewById(R.id.price_detail);
        }
    }

}