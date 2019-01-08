package com.example.yu.bigproject.shopcar.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.shopcar.bean.ShopCarBean;
import com.example.yu.bigproject.shopcar.view.CustomViewNum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ShopCarBean.Result> list;

    public ShopCarAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ShopCarBean.Result> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.shopcar_sel_recycler_item_view,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        ShopCarBean.Result result = list.get(i);
        holder.title.setText(result.getCommodityName());
        holder.price.setText("¥"+result.getPrice());
        Uri uri=Uri.parse(result.getPic());
        holder.image.setImageURI(uri);
        holder.radioButton.setChecked(result.isChecked());
        holder.customViewNum.setData(this,list,i);
        holder.customViewNum.setOnCallBack(new CustomViewNum.CallBackListener() {
            @Override
            public void callback() {
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack(list);
                }
            }
        });
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setChecked(isChecked);
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack(list);
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.shopcar_relative)
        RelativeLayout relativeLayout;
        @BindView(R.id.shopcar_title)
        TextView title;
        @BindView(R.id.shopcar_image)
        SimpleDraweeView image;
        @BindView(R.id.shopcar_price)
        TextView price;
        @BindView(R.id.shopcar_radio)
        CheckBox radioButton;
        @BindView(R.id.shopcar_custom_view_num)
        CustomViewNum customViewNum;
        @BindView(R.id.shop_car_del)
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<ShopCarBean.Result> list);
    }
}
