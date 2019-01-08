package com.example.yu.bigproject.list.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.list.adapter.ListItemAdapter;
import com.example.yu.bigproject.list.bean.ListBean;
import com.example.yu.bigproject.shopcar.adapter.ShopCarAdapter;
import com.example.yu.bigproject.shopcar.bean.ShopCarBean;

import java.util.List;

public class CustomViewNum extends LinearLayout {

    private TextView add;
    private TextView cut;
    private TextView num;
    private int nums;
    private ListItemAdapter itemAdapter;
    private List<ListBean.Result.Detail> list;
    private int position;

    public CustomViewNum(Context context) {
        super(context);
        initView(context);
    }

    public CustomViewNum(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view=View.inflate(context,R.layout.custom_view_num,null);
        add = view.findViewById(R.id.shopcar_add);
        cut = view.findViewById(R.id.shopcar_cut);
        num = view.findViewById(R.id.shopcar_num);
        addView(view);
        //点击加号
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nums++;
                if(nums>1){
                    num.setTextColor(Color.parseColor("#666666"));
                }
                list.get(position).setCommodityCount(nums);
                num.setText(nums+"");
                callBackListener.callback();
                itemAdapter.notifyItemChanged(position);
            }
        });
        //点击减号
        cut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nums>0&&nums!=1){
                    nums--;
                    list.get(position).setCommodityCount(nums);
                    num.setText(nums+"");
                    callBackListener.callback();
                    itemAdapter.notifyItemChanged(position);
                }else{
                    num.setTextColor(Color.parseColor("#dddd"));
                }
            }
        });
    }
    public void setData(ListItemAdapter itemAdapter, List<ListBean.Result.Detail> list, int i){
        this.list=list;
        this.itemAdapter=itemAdapter;
        position=i;
        nums=list.get(position).getCommodityCount();
        num.setText(nums+"");
        if(nums==1){
            cut.setTextColor(Color.parseColor("#dddd"));
        }
    }

    private CallBackListener callBackListener;

    public void setOnCallBack(CallBackListener listener){
        callBackListener=listener;
    }
    public interface CallBackListener{
        void callback();
    }
}
