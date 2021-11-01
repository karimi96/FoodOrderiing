package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> item;
    Context context;

//    public ProductAdapter(ArrayList<Product> item, Context context) {
//        this.item = item;
//        this.context = context;
//    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ProductAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                tv_name_food,
                tv_name_category,
                tv_price,
                tv_add_to_card,
                tv_numberOfOrder;
        ImageView
                img_food,
                img_InCrease,
                img_Dicrease;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_food = itemView.findViewById(R.id.tv_name_food);
            tv_name_category = itemView.findViewById(R.id.tv_name_category);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_add_to_card = itemView.findViewById(R.id.tv_add_to_card);
            tv_numberOfOrder = itemView.findViewById(R.id.tv_number_of_order);

            img_food = itemView.findViewById(R.id.img_food_product);
            img_InCrease = itemView.findViewById(R.id.img_inCrease);
            img_Dicrease = itemView.findViewById(R.id.img_diCrease);

        }
    }
}
