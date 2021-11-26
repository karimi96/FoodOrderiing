package com.example.foodorderiing.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Product;
import java.util.List;


public class OrdringAdapter extends RecyclerView.Adapter<OrdringAdapter.ViewHolder> {
    Context context;
    Product product ;
    List<Product> list;


    public OrdringAdapter(List<Product> list, Context context ) {
        this.list = list;
        this.context = context;
    }


    @Override
    public OrdringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_ordering,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(OrdringAdapter.ViewHolder holder, int position) {
        product = list.get(position);
        holder.tv_name_food.setText(product.name);
        holder.tv_name_category.setText(product.category);
        holder.tv_price.setText( product.price );
        holder.tv_number_order.setText("1");

        holder.img_Increase.setOnClickListener(new View.OnClickListener() {
            int count = 1 ;
            int price = Integer.valueOf(holder.tv_price.getText().toString());

            @Override
            public void onClick(View v) {
                count++;
                holder.tv_number_order.setText(Integer.toString(count));
                int c = count * price ;
                holder.tv_price.setText(String.valueOf(c));
            }
        });


        holder.img_Dicrease.setOnClickListener(new View.OnClickListener() {
            int price1 = Integer.valueOf(holder.tv_price.getText().toString());

            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(holder.tv_number_order.getText().toString());
                if(num == 1){

                }else {
                    num-- ;
                    holder.tv_number_order.setText(Integer.toString(num));
                    int a  = price1 * num ;
                    holder.tv_price.setText(String.valueOf(a));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size() ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_food, tv_name_category , tv_price , tv_number_order;
        ImageView img_food_bg;
        ImageView img_Increase;
        ImageView img_Dicrease;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_food = itemView.findViewById(R.id.name_food_ordring);
            tv_name_category = itemView.findViewById(R.id.catagory_ordreing);
            tv_number_order = itemView.findViewById(R.id.tv_number_of_order);
            tv_price = itemView.findViewById(R.id.price_ordring);
            img_Increase = itemView.findViewById(R.id.img_inCrease);
            img_Dicrease = itemView.findViewById(R.id.img_diCrease);
        }
    }

    public  void addList(List<Product> arryList){
        list.clear();
        list.addAll(arryList);
        notifyDataSetChanged();
    }

}