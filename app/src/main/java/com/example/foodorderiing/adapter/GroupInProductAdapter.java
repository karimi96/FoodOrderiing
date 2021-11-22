package com.example.foodorderiing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderiing.R;
import com.example.foodorderiing.database.DatabaseHelper;
import com.example.foodorderiing.database.dao.ProductDao;
import com.example.foodorderiing.model.Grouping;

import java.util.List;


public class GroupInProductAdapter extends RecyclerView.Adapter<GroupInProductAdapter.ViewHolder> {
    List<Grouping> list;
  int[] image ;
    Context context;
    public String category_grouping;
    ProductDao dao;
    public ProductAdapter productAdapter;


    public GroupInProductAdapter(List<Grouping> list, Context context ) {
        this.list = list;
        this.context = context;
    }
//        public GroupInProductAdapter() {
//
//    }


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
              category_grouping = grouping.name;
              holder.linearLayout.setBackground(context.getDrawable(R.drawable.border_linear));
//                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
//                DatabaseHelper db = DatabaseHelper.getInstance(context.getApplicationContext());
//                dao = db.productDao();
//                productAdapter =new ProductAdapter(dao.get_product_by_category(category_grouping),context);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_groupInproduct ;
        LinearLayout linearLayout;
//        CircleImageView img ;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_groupInproduct = itemView.findViewById(R.id.tv_groupInproduct);
            linearLayout = itemView.findViewById(R.id.linear_g_p);
//            img = itemView.findViewById(R.id.circle_groupinproduct);
        }
    }




}