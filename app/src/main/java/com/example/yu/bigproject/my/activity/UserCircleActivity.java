package com.example.yu.bigproject.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCircleActivity extends BaseActivity {

    @BindView(R.id.user_circle_recyclerview)
    XRecyclerView circle_recycler;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        circle_recycler.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected int getResId() {
        return R.layout.activity_user_circle;
    }
}
