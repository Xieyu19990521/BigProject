package com.example.yu.bigproject.list.fragment;

import android.util.Log;
import android.view.View;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseFragment;
import com.example.yu.bigproject.list.bean.ListBean;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFragment extends BaseFragment implements Iview {

    private IpresenterImpl ipresenter;
    @BindView(R.id.list_recycler)
    XRecyclerView list_recycler;
    private int mPage;
    private int mType;

    @Override
    protected void initDate() {
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
        ipresenter=new IpresenterImpl(this);
        list_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getData(mPage,mType);
            }

            @Override
            public void onLoadMore() {
                getData(mPage,mType);
            }
        });
    }

    public void getData(int mPage,int mType){
        ipresenter.onGetStartRequest(String.format(Apis.LIST_URL,mType,mPage),ListBean.class);
    }
    @Override
    protected int getResId() {
        return R.layout.fragment_show_list;
    }
    @OnClick({R.id.list_linear_all,R.id.list_linear_obligation,R.id.list_linear_wait,R.id.list_linear_remain,R.id.list_linear_completed})
    public void setOnclick(View v){
        switch (v.getId()){
            case R.id.list_linear_all:
                //查询全部订单
                mType=0;
                mPage=1;
                getData(mPage,mType);
                break;
            case R.id.list_linear_obligation:
                //查询待付款订单
                mType=1;
                mPage=1;
                getData(mPage,mType);
                break;
            case R.id.list_linear_wait:
                //查询待收货订单
                mType=2;
                mPage=1;
                getData(mPage,mType);
                break;
            case R.id.list_linear_remain:
                //查询待评价订单
                mType=3;
                mPage=1;
                getData(mPage,mType);
                break;
            case R.id.list_linear_completed:
                //查询已完成订单
                mType=9;
                mPage=1;
                getData(mPage,mType);
                break;
            default:break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof ListBean){
            ListBean listBean= (ListBean) data;
            listBean.getResult();
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
