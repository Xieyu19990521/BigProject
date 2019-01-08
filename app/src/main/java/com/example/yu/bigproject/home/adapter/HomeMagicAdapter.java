package com.example.yu.bigproject.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * @author YU
 * 魔力时尚界面
 */
public class HomeMagicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeComBean.ResultBean.MlssBean.CommodityListBeanXX> list;

    public HomeMagicAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomeComBean.ResultBean.MlssBean.CommodityListBeanXX> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<HomeComBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList) {
        list.addAll(commodityList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_magic_recycler_item_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomeComBean.ResultBean.MlssBean.CommodityListBeanXX mlssBean = list.get(i);
        Glide.with(context).load(mlssBean.getMasterPic()).into(holder.imageView);
        holder.title.setText(mlssBean.getCommodityName());
        holder.price.setText("¥"+mlssBean.getPrice());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("commodityId",mlssBean.getCommodityId()+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.home_magic_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.magic_image)
        ImageView imageView;
        @BindView(R.id.magic_title)
        TextView title;
        @BindView(R.id.magic_price)
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
