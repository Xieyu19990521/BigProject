package com.example.yu.bigproject.home.adapter;

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
import com.example.yu.bigproject.home.bean.HomeComBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeQualityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeComBean.ResultBean.PzshBean.CommodityListBeanX> list;

    public HomeQualityAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomeComBean.ResultBean.PzshBean.CommodityListBeanX> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<HomeComBean.ResultBean.PzshBean.CommodityListBeanX> commodityList) {
        list.addAll(commodityList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_quality_recycler_item_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ViewHolder viewHolder1= (ViewHolder) viewHolder;
        viewHolder1.title.setText(list.get(i).getCommodityName());
        viewHolder1.price.setText("¥"+list.get(i).getPrice());
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder1.imageView);
        viewHolder1.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("commodityId",list.get(i).getCommodityId()+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.home_quality_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.quality_image)
        ImageView imageView;
        @BindView(R.id.quality_title)
        TextView title;
        @BindView(R.id.quality_price)
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
