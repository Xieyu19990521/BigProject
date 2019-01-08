package com.example.yu.bigproject.my.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.my.adapter.AddressAdapter;
import com.example.yu.bigproject.my.bean.MyAddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAddressActivity extends BaseActivity implements Iview {

    @BindView(R.id.add_recycler)
    RecyclerView recyclerView;
    private IpresenterImpl ipresenter;
    private AddressAdapter addressAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(this);
        recyclerView.setAdapter(addressAdapter);
    }
    @OnClick(R.id.add_add)
    public void setOnClick(View view){
        Intent intent=new Intent(this,AddAddressActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_user_address;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof MyAddressBean){
            MyAddressBean addressBean= (MyAddressBean) data;
            addressAdapter.setList(addressBean.getResult());
        }

    }

    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
    }
}
