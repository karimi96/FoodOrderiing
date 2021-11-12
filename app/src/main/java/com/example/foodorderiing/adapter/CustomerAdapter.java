package com.example.foodorderiing.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.foodorderiing.activity.customer.AddNewCustomerActivity;
import com.example.foodorderiing.activity.product.AddNewProductActivity;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.model.Customer;
import com.example.foodorderiing.model.Grouping;
import com.example.foodorderiing.model.Product;
import com.google.gson.Gson;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    List<Customer> list;
    Context context;
    DatabaseHelper database;
    CustomerDao dao;
    Customer customer;

    public CustomerAdapter(List<Customer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_customer,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.ViewHolder holder, int position) {
        customer = list.get(position);
        holder.tv_name_customer.setText(customer.name);
        holder.tv_phone.setText(customer.phone);
        holder.tv_address.setText(customer.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSheet(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_customer, tv_phone , tv_address;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_customer = itemView.findViewById(R.id.tv_name_customer);
            tv_phone = itemView.findViewById(R.id.tv_phone_customer);
            tv_address = itemView.findViewById(R.id.tv_address_customer);


        }
    }


    private void showDialogSheet(int pos){

        final Dialog dialog_sheet = new Dialog(context);
        dialog_sheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sheet.setContentView(R.layout.bottom_sheet_customer);

        LinearLayout edit = dialog_sheet.findViewById(R.id.linear_edit_c);
        LinearLayout delete = dialog_sheet.findViewById(R.id.linear_delete_c);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewCustomerActivity.class);
                intent.putExtra("customer",new Gson().toJson(list.get(pos)));
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
                                dao = database.customerDao();
                                dao.deleteCustomer(customer);
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

    public void addList(List<Customer> arraylist){
        list.clear();
        list.addAll(arraylist);
        notifyDataSetChanged();
    }



}
