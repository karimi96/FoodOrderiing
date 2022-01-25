package com.example.foodorderiing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.helper.Tools;
import com.example.foodorderiing.model.Product;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<Product> list;
    public Listener listener ;


    public OrderAdapter(List<Product> list, Context context , Listener listener  ) {
        this.list = list;
        this.context = context;
        this.listener = listener ;
    }

    public interface Listener{
        void onAdded(int pos);
        void onRemove(int pos);
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_ordering,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);
        holder.tv_name_food.setText(product.name);
        holder.tv_name_category.setText(product.category);
        holder.tv_price.setText( Tools.getForamtPrice(Tools.convertToPrice(product.price) * product.amount+"") );
        holder.tv_number_order.setText(product.amount+"");

        holder.img_food_bg.setImageURI(Uri.parse(product.picture));

        try{
            final int takeFlags =  (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // Check for the freshest data.
            context.getContentResolver().takePersistableUriPermission(Uri.parse(product.picture), takeFlags);
            // convert uri to bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(product.picture));
            // set bitmap to imageview
            holder.img_food_bg.setImageBitmap(bitmap);
        }
        catch (Exception e){
            //handle exception
            e.printStackTrace();
        }





        holder.img_Increase.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                listener.onAdded(position);
            }
        });

        holder.img_Dicrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(position);
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
            img_food_bg = itemView.findViewById(R.id.img_ordring);
        }
    }


    public  void addList(List<Product> arryList){
        list.clear();
        list.addAll(arryList);
        notifyDataSetChanged();
    }

}