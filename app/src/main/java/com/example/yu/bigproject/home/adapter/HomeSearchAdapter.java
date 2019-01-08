package com.example.yu.bigproject.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.home.activity.DetailActivity;
import com.example.yu.bigproject.home.bean.HomeSearchBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeSearchBean.ResultBean> list;

    public HomeSearchAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomeSearchBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<HomeSearchBean.ResultBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_search_recycler_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomeSearchBean.ResultBean resultBean = list.get(i);
        Uri uri = Uri.parse(resultBean.getMasterPic());
        holder.simpleDraweeView.setImageURI(uri);
        holder.title.setText(resultBean.getCommodityName());
        holder.price.setText("¥"+resultBean.getPrice());
        holder.salenum.setText("已售"+resultBean.getSaleNum()+"件");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(context,DetailActivity.class);
            intent.putExtra("commodityId",resultBean.getCommodityId()+"");
            context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.home_search_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.home_recycler_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.home_recycler_title)
        TextView title;
        @BindView(R.id.home_recycler_price)
        TextView price;
        @BindView(R.id.home_recycler_salenum)
        TextView salenum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
