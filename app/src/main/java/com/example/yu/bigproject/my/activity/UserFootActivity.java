package com.example.yu.bigproject.my.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.my.adapter.UserFootAdapter;
import com.example.yu.bigproject.my.bean.UserFootBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFootActivity extends BaseActivity implements Iview {

    @BindView(R.id.user_foot_recycler)
    XRecyclerView foot_recycler;
    final int mSpan=2;
    private UserFootAdapter footAdapter;
    private int fPager;
    private IpresenterImpl ipresenter;

    @Override
    protected void initData() {
        fPager=1;
        getFootData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(mSpan,StaggeredGridLayoutManager.VERTICAL);
        foot_recycler.setLayoutManager(staggeredGridLayoutManager);
        footAdapter = new UserFootAdapter(this);
        foot_recycler.setAdapter(footAdapter);
        foot_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                fPager=1;
                getFootData();
            }

            @Override
            public void onLoadMore() {
                getFootData();
            }
        });


    }

    @Override
    protected int getResId() {
        return R.layout.activity_user_foot;
    }

    public void getFootData(){
        ipresenter.onGetStartRequest(String.format(Apis.USER_FOOT,fPager),UserFootBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof UserFootBean){
            UserFootBean footBean= (UserFootBean) data;
            if(fPager==1){
                footAdapter.setList(footBean.getResult());
            }else{
                footAdapter.addList(footBean.getResult());
            }
            fPager++;
            foot_recycler.refreshComplete();
            foot_recycler.loadMoreComplete();
        }
    }

    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }
}
