package com.example.foodorderiing.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.orderDetail.OrderDetail;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.database.dao.OrderDetailDao;
import com.example.foodorderiing.helper.App;
import com.example.foodorderiing.helper.CustomDialog;
import com.example.foodorderiing.model.Order;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Order> list_search;
    private List<Order> list;
    private DatabaseHelper db = App.getDatabase();
    private OrderDetailDao detailDao;
    private OrderDao orderDao;


    public ListOrderAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list_search = list;
        this.list = new ArrayList<>(list_search);
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
                android.app.AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("ایا مایلید این سفارش را حذف کنید؟")
                        .setPositiveButton("بله",(dialog1, which) -> {
                                initDataBase();
                                initDeleteOneOrder(getAdapterPosition(), list.get(getAdapterPosition()).name);
                        })
                        .setNegativeButton("خیر", (dialog1, which) -> {
                            dialog1.dismiss();
                        })
                        .show();
                CustomDialog.setTypeFaceAlertDialog(dialog, context);
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


    //  For search
    @Override
    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Order> filterdNewList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterdNewList.addAll(list_search);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Order order : list_search) {

                    if (order.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(order);
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

}
