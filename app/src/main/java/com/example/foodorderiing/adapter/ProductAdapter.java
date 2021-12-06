package com.example.foodorderiing.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.product.AddNewProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import org.intellij.lang.annotations.JdkConstants;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable{
    Context context;
    List<Product> list;
    Listener listener;
    List<Product> list_search;
    DatabaseHelper database;
    ProductDao dao;

    public ProductAdapter(List<Product> list, Context context, Listener listener ) {
        this.context = context;
        this.list_search = list;
        this.listener = listener;
        this.list = new ArrayList<>(list_search);
    }

    public interface Listener{
        void onClick(Product product , int pos , String name );
    }


    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.getAdapterPosition();
        return viewHolder;
    }


    @Override
    public void onBindViewHolder( ProductAdapter.ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.tv_name_food.setText(product.name);
        holder.tv_name_category.setText(product.category);
        holder.tv_price.setText(product.price);
//        holder.tv_price.setText(decimalFormat.format(product.price));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    showDialogSheet(position , list.get(position).name);
                    listener.onClick(product , position , list.get(position).name );

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_food, tv_name_category , tv_price;
        ImageView img_food_bg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_food = itemView.findViewById(R.id.tv_name_food_product);
            tv_name_category = itemView.findViewById(R.id.tv_kind_of_food);
            tv_price = itemView.findViewById(R.id.tv_price_product);
            img_food_bg = itemView.findViewById(R.id.img_back_product);
        }
    }


    public void showDialogSheet(int pos , String name){
        final Dialog dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_product);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_p);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_p);
        TextView title = dialog_sheet.findViewById(R.id.name_sheet_p);
        title.setText(name);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewProductActivity.class);
                intent.putExtra("product",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                dialog_sheet.dismiss();

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("ایا مایلید این مورد را حذف کنید؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = DatabaseHelper.getInstance(context.getApplicationContext());
                                dao = database.productDao();
                                dao.deleteProduct(list.get(pos));
                                list.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos,list.size());
                                notifyDataSetChanged();
                                dialog_sheet.dismiss();
                                Toast.makeText(context, "با موفقیت حذف شد 😉 ", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog_sheet.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        dialog_sheet.show();
        dialog_sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSheet;
        dialog_sheet.getWindow().setGravity(Gravity.BOTTOM);

    }


    //  For search
    @Override
    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Product> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Product product : list_search){

                    if(product.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(product);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterdNewList;
            results.count = filterdNewList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };


    public  void addList(List<Product> arryList){
        list_search.clear();
        list_search.addAll(arryList);
        list = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }

    public  void clear(){
        list_search.clear();
        list.clear();
        notifyDataSetChanged();
    }

    public int count(){
        return list.size();
    }

}