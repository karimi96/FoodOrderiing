package com.example.foodorderiing.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.orderDetail.OrderDetail;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.CustomerDao;
import com.example.foodorderiing.database.dao.OrderDao;
import com.example.foodorderiing.model.Order;

import java.util.List;


public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private Context context ;
    private List<Order> list ;
    DatabaseHelper db = DatabaseHelper.getInstance(context);


    public ListOrderAdapter(Context context, List<Order> list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_list_order,parent , false);
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
        ImageView edit;

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
//            edit = itemView.findViewById(R.id.edit_listOrder);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(context)
                            .setTitle("حذف")
                            .setMessage("ایا می خواهید این سفارش را حذف کنید؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    OrderDao dao = db.orderDao();
                                    dao.deleteOrder(list.get(getAdapterPosition()));
                                    list.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyItemRangeChanged(getAdapterPosition(),list.size());
                                    notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }
            });

//            edit.setOnClickListener(v -> {
//                Intent edit = new Intent(context,Order.class);
//                edit.putExtra("edit" , list.get(getAdapterPosition()).code);
//                context.startActivity(edit);
//            });


            itemView.setOnClickListener(this);
//            itemView.setClickable(true);

        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context , OrderDetail.class);
            intent.putExtra("code" , list.get(getAdapterPosition()).code);
            intent.putExtra("customerid" , list.get(getAdapterPosition()).customerID);
            intent.putExtra("name" , list.get(getAdapterPosition()).name);
            intent.putExtra("total" , list.get(getAdapterPosition()).total);
            context.startActivity(intent);

        }
    }
}
