package com.example.foodorderiing.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.product.AddNewProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.CustomDialog;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Product> list;
    private Listener listener;
    private List<Product> list_search;
    private DatabaseHelper database;
    private ProductDao dao;

    public ProductAdapter(List<Product> list, Context context, Listener listener) {
        this.context = context;
        this.list_search = list;
        this.listener = listener;
        this.list = new ArrayList<>(list_search);
    }


    public interface Listener {
        void onClick(Product product, int pos, String name);
    }


    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);
        holder.tv_name_food.setText(product.name);
        holder.tv_name_category.setText(product.category);
        holder.tv_price.setText(product.price);
//        holder.img_food_bg.setImageURI(Uri.parse(product.picture));

        try {
            if (new File(product.picture).exists() && !product.picture.isEmpty()) {
                Glide.with(context).load(new File(product.picture)).into(holder.img_food_bg);
            } else holder.img_food_bg.setImageDrawable(context.getDrawable(R.drawable.defult_pic));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name_food, tv_name_category, tv_price;
        ImageView img_food_bg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_food = itemView.findViewById(R.id.tv_name_food_product);
            tv_name_category = itemView.findViewById(R.id.tv_kind_of_food);
            tv_price = itemView.findViewById(R.id.tv_price_product);
            img_food_bg = itemView.findViewById(R.id.img_back_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product product = list.get(getAdapterPosition());
            listener.onClick(product, getAdapterPosition(), list.get(getAdapterPosition()).name);
        }
    }


    public void showButtonSheet(int pos, String name) {
        final Dialog dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_product);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_p);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_p);
        TextView title = dialog_sheet.findViewById(R.id.name_sheet_p);
        title.setText(name);
        dialog_sheet.show();
        dialog_sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSheet;
        dialog_sheet.getWindow().setGravity(Gravity.BOTTOM);

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddNewProductActivity.class);
            intent.putExtra("product", new Gson().toJson(list.get(pos)));
            context.startActivity(intent);
            dialog_sheet.dismiss();
        });

        delete.setOnClickListener(v -> {
            showAlertDialog(pos, dialog_sheet, name);
        });
    }


    private void showAlertDialog(int pos, Dialog dialog_sheet, String name) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("حذف")
                .setMessage("ایا مایلید این مورد را حذف کنید؟")
                .setPositiveButton("بله", (dialog1, which) -> {
                    database = DatabaseHelper.getInstance(context.getApplicationContext());
                    dao = database.productDao();
                    dao.deleteProduct(list.get(pos));
                    list.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, list.size());
                    notifyDataSetChanged();
                    dialog_sheet.dismiss();
                    Toast.makeText(context, name + " با موفقیت حذف شد ", Toast.LENGTH_LONG).show();

                })
                .setNegativeButton("خیر", (dialog1, which) -> {
                    dialog1.dismiss();
                    dialog_sheet.dismiss();

                })
                .show();
        CustomDialog.setTypeFaceAlertDialog(dialog, context);
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
            if (constraint == null || constraint.length() == 0) {
                filterdNewList.addAll(list_search);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product product : list_search) {

                    if (product.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(product);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdNewList;
            results.count = filterdNewList.size();
            if (results.count == 0)
                Toast.makeText(context, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


    public void addList(List<Product> arryList) {
        list_search.clear();
        list_search.addAll(arryList);
        list = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }


    public void add(Product product) {
        list.add(list.size(), product);
        notifyItemInserted(list.size());
    }

    public void update(Product product, int pos) {
//        list.get(0) = product;
        notifyItemChanged(pos, product);
    }

    public void remove(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


}