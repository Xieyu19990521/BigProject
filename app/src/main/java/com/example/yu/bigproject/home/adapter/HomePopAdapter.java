package com.example.yu.bigproject.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.home.bean.HomePopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YU
 * 一级类目适配器
 */
public class HomePopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomePopBean.ResultBean> list;

    public HomePopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<HomePopBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.home_pop_recycler_item_first,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        final HomePopBean.ResultBean resultBean = list.get(i);
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

        @BindView(R.id.home_pop_item)
        RelativeLayout relativeLayout;
        @BindView(R.id.home_pop_recycler_text)
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
