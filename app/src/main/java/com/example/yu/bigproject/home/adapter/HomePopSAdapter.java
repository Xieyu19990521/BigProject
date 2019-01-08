package com.example.yu.bigproject.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.home.bean.HomePopSBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePopSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomePopSBean.ResultBean> list;

    public HomePopSAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomePopSBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_pop_recycler_item_seconed,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomePopSBean.ResultBean resultBean = list.get(i);
        holder.imageView.setImageResource(R.mipmap.category_icon_coat_n);
        holder.textView.setText(resultBean.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.OnClick(resultBean.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.home_pop_item_seconed)
        RelativeLayout relativeLayout;
        @BindView(R.id.home_pop_image_seconed)
        ImageView imageView;
        @BindView(R.id.home_pop_text_seconed)
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private OnClickListener listener;
    public void setOnclickListener(OnClickListener listener){
        this.listener=listener;
    }

    public interface OnClickListener{
        void OnClick(String id);
    }
}
