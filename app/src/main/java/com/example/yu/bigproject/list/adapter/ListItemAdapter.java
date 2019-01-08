package com.example.yu.bigproject.list.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.list.bean.ListBean;
import com.example.yu.bigproject.views.CustomViewNum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ListBean.Result.Detail> list;

    public ListItemAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ListBean.Result.Detail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item_recycler_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        ListBean.Result.Detail detail = list.get(i);
        holder.title.setText(detail.getCommodityName());
        holder.price.setText(detail.getCommodityPrice()+"");
        Uri uri=Uri.parse(detail.getCommodityPic());
        holder.simpleDraweeView.setImageURI(uri);
        holder.customViewNum.setData(this,list,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_item_item_image)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.list_item_item_price)
        TextView price;
        @BindView(R.id.list_item_item_title)
        TextView title;
        @BindView(R.id.custom_view_num)
        CustomViewNum customViewNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
