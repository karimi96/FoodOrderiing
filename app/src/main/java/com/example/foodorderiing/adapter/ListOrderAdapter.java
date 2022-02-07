package com.example.foodorderiing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.orderDetail.OrderDetail;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.model.Order;
import com.google.gson.Gson;

import java.util.List;


public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private Context context;
    private List<Order> list;
    private DatabaseHelper db = App.getDatabase();
    private OrderDetailDao detailDao;
    private OrderDao orderDao;


    public ListOrderAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_list_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ListOrderAdapter.ViewHolder holder, int position) {
        Order order = list.get(position);
        holder.name.setText(order.name);
        holder.status.setText(String.valueOf(order.statusCustomer));
        holder.total.setText(order.total);
        holder.explain.setText(order.discrebtion);
        holder.date.setText(order.date);
        holder.time.setText(order.time);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView status;
        TextView total;
        TextView explain;
        TextView time;
        TextView date;
        TextView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name_record);
            status = itemView.findViewById(R.id.status_record);
            total = itemView.findViewById(R.id.total_record);
            explain = itemView.findViewById(R.id.explain_record);
            time = itemView.findViewById(R.id.time_listOrdring);
            date = itemView.findViewById(R.id.date_listOrdring);
            delete = itemView.findViewById(R.id.delete_listOrder);
            itemView.setOnClickListener(this);

            delete.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("ایا مایلید این سفارش را حذف کنید؟")
                        .setPositiveButton("بله",(dialog, which) -> {
                                initDataBase();
                                initDeleteOneOrder(getAdapterPosition(), list.get(getAdapterPosition()).name);
                        })
                        .setNegativeButton("خیر", (dialog, which) -> {
                                dialog.dismiss();
                        })
                        .create()
                        .show();
            });
        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context, OrderDetail.class);
            intent.putExtra("listOrder", new Gson().toJson(list.get(getAdapterPosition())));
            context.startActivity(intent);
        }
    }


    private void initDataBase() {
        orderDao = db.orderDao();
        detailDao = db.orderDetailDao();
    }


    private void initDeleteOneOrder(int pos, String name) {
        detailDao.deleteOneOrderDetail(list.get(pos).code);
        orderDao.deleteOrder(list.get(pos));
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, list.size());
        notifyDataSetChanged();
        Toast.makeText(context, " سفارش " + name + " با موفقیت حذف شد. ", Toast.LENGTH_SHORT).show();
    }
}
