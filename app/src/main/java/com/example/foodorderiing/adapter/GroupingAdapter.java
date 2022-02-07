package com.example.foodorderiing.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.example.foodorderiing.activity.grouping.AddNewGroupingActivity;
import com.example.foodorderiing.activity.product.ProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.model.Grouping;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class GroupingAdapter extends RecyclerView.Adapter<GroupingAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Grouping> list;
    private List<Grouping> list_search;
    private DatabaseHelper database;
    private GroupingDao groupingDao;
    private ProductDao productDao;
    private int count;
    private String text;


    public GroupingAdapter(List<Grouping> list, Context context) {
        this.list_search = list;
        this.list = new ArrayList<>(list_search);
        this.context = context;
    }

    @Override
    public GroupingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_grouping, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Grouping grouping = list.get(position);
        holder.tv_name_category.setText(grouping.name);

        try {
            if(new File(grouping.picture).exists() && !grouping.picture.isEmpty()){
                Glide.with(context).load(new File(grouping.picture)).into(holder.img_food_grouping);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

//        try{
//            final int takeFlags =  (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            // Check for the freshest data.
//            context.getContentResolver().takePersistableUriPermission(Uri.fromFile(new File(grouping.picture)), takeFlags);
//            // convert uri to bitmap
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(new File(grouping.picture)));
//            // set bitmap to imageview
//            holder.img_food_grouping.setImageBitmap(bitmap);
//        }
//        catch (Exception e){
//            //handle exception
//            e.printStackTrace();
//        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener , View.OnClickListener{
        TextView tv_name_category;
        ImageView img_food_grouping;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_category = itemView.findViewById(R.id.tv_name_of_category_group);
            img_food_grouping = itemView.findViewById(R.id.img_food_grouping);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        
        @Override
        public boolean onLongClick(View v) {
            showButtonSheet(getAdapterPosition(), list.get(getAdapterPosition()).name);
            return true;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("groupingToproduct" , new Gson().toJson(list.get(getAdapterPosition())));
            context.startActivity(intent);
        }
    }


    private void showButtonSheet(int pos, String name) {
        final Dialog dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_grouping);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_g);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_g);
        TextView title = dialog_sheet.findViewById(R.id.name_sheet_g);
        title.setText(name);
        dialog_sheet.show();
        dialog_sheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSheet;
        dialog_sheet.getWindow().setGravity(Gravity.BOTTOM);

        edit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AddNewGroupingActivity.class);
                intent.putExtra("grouping", new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                dialog_sheet.dismiss();
        });

        delete.setOnClickListener(v -> {
            initDataBase(name);
            setTextAlertDialog();
            showAlertDialog(name, pos, dialog_sheet);

        });
    }


    private void initDataBase(String name){
        database = App.getDatabase();
        groupingDao = database.groupingDao();
        productDao = database.productDao();
        count = productDao.get_product_by_category(name).size();
    }


    private void setTextAlertDialog(){
        if(count >= 1 ){
            text =  " این دسته بندی "+ " ( "+ count + " ) "+ " محصول دارد ؛ ایا مایلید انرا حذف کنید؟ ";
        }else {
            text = " ایا مایلید این مورد را حذف کنید؟";
        }
    }


    private void showAlertDialog(String name , int pos , Dialog dialog_sheet){
        new AlertDialog.Builder(context)
                .setTitle("حذف")
                .setMessage(text)
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(count >= 1){
                            productDao.deleteProductByCategory(name);
                           deleteOneItem(pos, dialog_sheet, name);
                        }else {
                            deleteOneItem(pos, dialog_sheet, name);
                        }
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


    private void deleteOneItem(int pos, Dialog dialog_sheet, String name){
        groupingDao.deleteGrouping(list.get(pos));
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, list.size());
        notifyDataSetChanged();
        dialog_sheet.dismiss();
        Toast.makeText(context, name + " با موفقیت حذف شد", Toast.LENGTH_LONG).show();
    }


    public void addList(List<Grouping> arraylist) {
        list_search.clear();
        list_search.addAll(arraylist);
        list = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }


    //  For search
    @Override
    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Grouping> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Grouping grouping : list_search){

                    if(grouping.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(grouping);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdNewList;
            results.count = filterdNewList.size();
            if(results.count == 0 ) Toast.makeText(context, "موردی یافت نشد", Toast.LENGTH_SHORT).show();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}