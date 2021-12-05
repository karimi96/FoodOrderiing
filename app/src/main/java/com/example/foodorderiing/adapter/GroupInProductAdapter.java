package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.model.Grouping;
import java.util.List;


public class GroupInProductAdapter extends RecyclerView.Adapter<GroupInProductAdapter.ViewHolder> {
    Context context;
    List<Grouping> list;
    Listener listener;


    public GroupInProductAdapter(List<Grouping> list, Context context , Listener listener ) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener{
        void onClick(String name);
    }

    @Override
    public GroupInProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_group_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupInProductAdapter.ViewHolder holder, int position) {
        Grouping grouping = list.get(position);
        holder.tv_name_groupInproduct.setText(grouping.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
//              category_grouping = grouping.name;
              listener.onClick(list.get(position).name);
////              holder.linearLayout.setBackground(context.getDrawable(R.drawable.border_linear));
////                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
////                DatabaseHelper db = DatabaseHelper.getInstance(context.getApplicationContext());
////                dao = db.productDao();
////                productAdapter =new ProductAdapter(dao.get_product_by_category(category_grouping),context);
//
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_groupInproduct ;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_groupInproduct = itemView.findViewById(R.id.tv_groupInproduct);
        }
    }

}