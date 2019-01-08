package com.example.yu.bigproject.circle.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.circle.bean.CircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CircleBean.ResultBean> resultBeans;


    public CircleRecyclerViewAdapter(Context context) {
        this.context=context;
        resultBeans=new ArrayList<>();
    }

    public void setResultBeans(List<CircleBean.ResultBean> resultBeans) {
        this.resultBeans = resultBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.circle_recycler_item_view,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder holder= (ViewHolder) viewHolder;
        final CircleBean.ResultBean resultBean = resultBeans.get(i);
        Uri uri=Uri.parse(resultBean.getHeadPic());
        holder.simpleDraweeView.setImageURI(uri);
        holder.name.setText(resultBean.getNickName());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(
                new java.util.Date(resultBean.getCreateTime()));
        holder.time.setText(date);
        Glide.with(context).load(resultBean.getImage()).into(holder.imageView);
        if(resultBean.getWhetherGreat()==1){
            holder.prise.setImageResource(R.mipmap.common_btn_prise_s);
        }else{
            holder.prise.setImageResource(R.mipmap.common_btn_prise_n);
        }
        holder.content.setText(resultBean.getContent());
        holder.num.setText(resultBean.getGreatNum()+"");
        holder.prise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                        listener.onClick(resultBeans.get(i).getWhetherGreat(), i, resultBeans.get(i).getId());
                }
            }
        });
    }
    //点赞的方法
    public void getGivePraise(int position) {
        resultBeans.get(position).setWhetherGreat(1);
        resultBeans.get(position).setGreatNum(resultBeans.get(position).getGreatNum() + 1);
        notifyDataSetChanged();
    }
    //取消点赞的方法
    public void getCancelPraise(int position) {
        resultBeans.get(position).setWhetherGreat(2);
        resultBeans.get(position).setGreatNum(resultBeans.get(position).getGreatNum() - 1);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.circle_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.circle_time)
        TextView time;
        @BindView(R.id.circle_name)
        TextView name;
        @BindView(R.id.circle_image)
        ImageView imageView;
        @BindView(R.id.circle_content)
        TextView content;
        @BindView(R.id.circle_num)
        TextView num;
        @BindView(R.id.circle_prise)
        ImageView prise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    private OnClickListener listener;
    public void setOnclickListener(OnClickListener listener){
        this.listener=listener;
    }

    public interface OnClickListener{
        void onClick(int whetherGreat, int i, int id);
    }
}
