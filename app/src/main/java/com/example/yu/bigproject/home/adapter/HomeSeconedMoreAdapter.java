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
import com.example.yu.bigproject.home.bean.HomeSeconedBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSeconedMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeSeconedBean.ResultBean> list;

    public HomeSeconedMoreAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomeSeconedBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<HomeSeconedBean.ResultBean> result) {
        if(result!=null){
            this.list.addAll(result);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_more_recycler_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomeSeconedBean.ResultBean resultBean = list.get(i);
        holder.price.setText("¥"+resultBean.getPrice());
        holder.title.setText(resultBean.getCommodityName());
        holder.salenum.setText("已售"+resultBean.getSaleNum()+"件");
        Uri uri=Uri.parse(resultBean.getMasterPic());
        holder.simpleDraweeView.setImageURI(uri);
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

        @BindView(R.id.home_more_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.more_recycler_price)
        TextView price;
        @BindView(R.id.more_recycler_salenum)
        TextView salenum;
        @BindView(R.id.more_recycler_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.more_recycler_title)
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
