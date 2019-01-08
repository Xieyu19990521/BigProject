package com.example.yu.bigproject.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.home.activity.DetailActivity;
import com.example.yu.bigproject.my.bean.UserFootBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<UserFootBean.ResultBean> list;

    public UserFootAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<UserFootBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<UserFootBean.ResultBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.my_foot_recycler_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final UserFootBean.ResultBean resultBean = list.get(i);
        Glide.with(context).load(resultBean.getMasterPic()).into(holder.imageView);
        holder.title.setText(resultBean.getCommodityName());
        holder.price.setText("¥"+resultBean.getPrice());
        holder.num.setText("已浏览"+resultBean.getBrowseNum()+"次");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(resultBean.getBrowseTime()));
        holder.time.setText(date);
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
        @BindView(R.id.foot_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.foot_image)
        ImageView imageView;
        @BindView(R.id.foot_title)
        TextView title;
        @BindView(R.id.foot_price)
        TextView price;
        @BindView(R.id.foot_num)
        TextView num;
        @BindView(R.id.foot_time)
        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
