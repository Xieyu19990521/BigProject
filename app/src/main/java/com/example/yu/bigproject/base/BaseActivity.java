package com.example.yu.bigproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        initView(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getResId();
}
