package com.example.yu.bigproject.shopcar.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseFragment;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.shopcar.adapter.ShopCarAdapter;
import com.example.yu.bigproject.shopcar.bean.ShopCarBean;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YU
 * @date 2019/01/06
 */
public class ShopCartFragment extends BaseFragment implements Iview {

    IpresenterImpl ipresenter;
    @BindView(R.id.shopcar_recycler)
    RecyclerView shopcar_recyclerView;
    @BindView(R.id.check_all)
    CheckBox shop_car_checkBox;
    @BindView(R.id.shop_car_price)
    TextView shop_car_price;
    private ShopCarAdapter carAdapter;
    private List<ShopCarBean.Result> results;

    @Override
    protected void initDate() {
        getCarData();
        shop_car_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalPrice=0;
                for (int i=0;i<results.size();i++){
                    ShopCarBean.Result result = results.get(i);
                    result.setChecked(shop_car_checkBox.isChecked());
                    totalPrice+=result.getPrice()*result.getCount();
                }
                if(shop_car_checkBox.isChecked()){
                    shop_car_price.setText("¥"+totalPrice);
                }else{
                    shop_car_price.setText("¥0.0");
                }
                carAdapter.notifyDataSetChanged();
            }
        });
        carAdapter.setListener(new ShopCarAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopCarBean.Result> list) {
                double totalPrice=0;
                int num=0;
                for(int i=0;i<list.size();i++){
                    ShopCarBean.Result result = list.get(i);
                    if(result.isChecked()){
                        totalPrice+=result.getPrice()*result.getCount();
                        num++;
                    }
                }
                if(num<list.size()){
                    shop_car_checkBox.setChecked(false);
                }else{
                    shop_car_checkBox.setChecked(true);
                }
                shop_car_price.setText("¥"+totalPrice);
            }
        });
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
        ipresenter=new IpresenterImpl(this);
        shopcar_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        carAdapter = new ShopCarAdapter(getContext());
        shopcar_recyclerView.setAdapter(carAdapter);
    }

    //查询购物车
    public void getCarData(){
        ipresenter.onGetStartRequest(Apis.SHOPCAR_SEL,ShopCarBean.class);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_show_shop_car;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof ShopCarBean){
            ShopCarBean carBean= (ShopCarBean) data;
            results = carBean.getResult();
            carAdapter.setList(carBean.getResult());
        }
    }

    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
    }
}
