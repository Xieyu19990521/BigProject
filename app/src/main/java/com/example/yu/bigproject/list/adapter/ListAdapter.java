package com.example.yu.bigproject.list.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.list.bean.ListBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ListBean.Result> list;

    public ListAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ListBean.Result> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<ListBean.Result> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_recycler_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        ListBean.Result result = list.get(i);
        holder.code.setText(result.getOrderId());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(result.getOrderTime()));
        holder.time.setText(date);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_code)
        TextView code;
        @BindView(R.id.list_item_time)
        TextView time;
        @BindView(R.id.list_item_recycler)
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
