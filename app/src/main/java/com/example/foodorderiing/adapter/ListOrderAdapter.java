package com.example.foodorderiing.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.activity.orderDetail.OrderDetail;
import com.example.foodorderiing.model.Order;

import java.util.List;


public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private Context context ;
    private List<Order> list ;

    public ListOrderAdapter(Context context, List<Order> list ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_record_ordring,parent , false);
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

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name_record);
            status = itemView.findViewById(R.id.status_record);
            total = itemView.findViewById(R.id.total_record);
            explain = itemView.findViewById(R.id.explain_record);

            itemView.setOnClickListener(this);
//            itemView.setClickable(true);

        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context , OrderDetail.class);
            intent.putExtra("code" , list.get(getAdapterPosition()).code);
            context.startActivity(intent);

        }
    }
}
