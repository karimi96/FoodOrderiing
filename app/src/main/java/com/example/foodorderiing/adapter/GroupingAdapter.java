package com.example.foodorderiing.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.grouping.AddNewGroupingActivity;
import com.example.foodorderiing.activity.product.AddNewProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.GroupingDao;
import com.example.foodorderiing.design.BlureImage;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import java.util.List;

public class GroupingAdapter extends RecyclerView.Adapter<GroupingAdapter.ViewHolder> {
    List<Grouping> list;
    Context context;
    DatabaseHelper database;
    GroupingDao dao;
    Grouping grouping;

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
        grouping = list.get(position);
        holder.tv_name_category.setText(grouping.name);
//        holder.tv_name_category.setSelected(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSheet(position , list.get(position).name);
            }
        });
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


    private void showDialogSheet(int pos , String name){

        final Dialog dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_grouping);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_g);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_g);
        TextView title = dialog_sheet.findViewById(R.id.name_sheet_g);
        title.setText(name);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , AddNewGroupingActivity.class);
                intent.putExtra("grouping",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                dialog_sheet.dismiss();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder( context )
                        .setTitle("حذف")
                        .setMessage("ایا مایلید این مورد را حذف کنید؟")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = DatabaseHelper.getInstance(context.getApplicationContext());
                                dao = database.groupingDao();
                                dao.deleteGrouping(grouping);
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


    public void addList(List<Grouping> arraylist){
        list.clear();
        list.addAll(arraylist);
        notifyDataSetChanged();
    }

}
