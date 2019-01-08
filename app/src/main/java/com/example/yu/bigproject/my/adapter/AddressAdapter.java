package com.example.yu.bigproject.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.my.bean.MyAddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyAddressBean.Result> list;

    public AddressAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<MyAddressBean.Result> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.my_address_recycler_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        MyAddressBean.Result result = list.get(i);
        holder.address.setText(result.getAddress());
        holder.name.setText(result.getRealName());
        if(result.getWhetherDefault()==1){
            holder.radioButton.setChecked(true);
        }else{
            holder.radioButton.setChecked(false);
        }
        holder.tel.setText(result.getPhone());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除地址
            }
        });
        holder.updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改地址
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.address_name)
        TextView name;
        @BindView(R.id.address_tel)
        TextView tel;
        @BindView(R.id.address_address)
        TextView address;
        @BindView(R.id.address_radio)
        RadioButton radioButton;
        @BindView(R.id.address_del)
        Button del;
        @BindView(R.id.address_updata)
        Button updata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
