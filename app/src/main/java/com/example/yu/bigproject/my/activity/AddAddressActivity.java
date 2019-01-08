package com.example.yu.bigproject.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;

import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_add_address;
    }
}
