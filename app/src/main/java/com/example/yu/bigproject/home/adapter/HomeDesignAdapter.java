package com.example.yu.bigproject.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YU
 * 主页面 品质生活适配器
 */
public class HomeDesignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeComBean.ResultBean.RxxpBean.CommodityListBean> list;

    public HomeDesignAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomeComBean.ResultBean.RxxpBean.CommodityListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<HomeComBean.ResultBean.RxxpBean.CommodityListBean> commodityListBeans) {
        list.addAll(commodityListBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_design_recycler_item_view,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomeComBean.ResultBean.RxxpBean.CommodityListBean mlssBean = list.get(i);
        Uri uri=Uri.parse(mlssBean.getMasterPic());
        holder.imageView.setImageURI(uri);
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

        @BindView(R.id.home_design_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.design_image)
        SimpleDraweeView imageView;
        @BindView(R.id.design_title)
        TextView title;
        @BindView(R.id.design_price)
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
