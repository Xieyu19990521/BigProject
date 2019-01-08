package com.example.yu.bigproject.circle.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseFragment;
import com.example.yu.bigproject.circle.adapter.CircleRecyclerViewAdapter;
import com.example.yu.bigproject.circle.bean.CircleBean;
import com.example.yu.bigproject.circle.bean.CircleGreatBean;
import com.example.yu.bigproject.login.LoginActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YU
 * 圈子页面
 */
public class CircleFragment extends BaseFragment implements Iview {

    @BindView(R.id.circle_recycler)
    RecyclerView circle_recyclerView;
    private CircleRecyclerViewAdapter circleAdapter;
    private IpresenterImpl ipresenter;
    private int position;

    @Override
    protected void initDate() {
        getCircleData();
        circleAdapter.setOnclickListener(new CircleRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(int whetherGreat, int i, int id) {
                position=i;
                if(whetherGreat==1){
                    cancelGreatData(id);
                }else{
                    getGreatData(id);
                }
            }
        });
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
        circle_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        circleAdapter = new CircleRecyclerViewAdapter(getContext());
        circle_recyclerView.setAdapter(circleAdapter);
        ipresenter=new IpresenterImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_show_circle;
    }

    //获取圈子信息
    public void getCircleData(){
        ipresenter.onGetStartRequest(String.format(Apis.CIRCLE_LIST),CircleBean.class);
    }
    //获取点赞信息 点赞
    public void getGreatData(int id){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",id+"");
        ipresenter.onPostStartRequest(Apis.CIRCLE_ADDGREAT,map,CircleGreatBean.class);
    }
    //取消点赞
    public void cancelGreatData(int id){
        ipresenter.onDeleteStartRequest(String.format(Apis.CIRCLE_CANCELFREAT,id),CircleGreatBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof CircleBean){
            CircleBean circleBean= (CircleBean) data;
            circleAdapter.setResultBeans(circleBean.getResult());
        }
        if(data instanceof CircleGreatBean){
            CircleGreatBean circleGreatBean= (CircleGreatBean) data;
            if(circleGreatBean.getMessage().equals("请先登陆")){
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }else if(circleGreatBean.getMessage().equals("点赞成功")){
                Toast.makeText(getContext(), circleGreatBean.getMessage(), Toast.LENGTH_SHORT).show();
                //getCircleData();
                circleAdapter.getGivePraise(position);
            }else if(circleGreatBean.getMessage().equals("取消成功")){
                circleAdapter.getCancelPraise(position);
                Toast.makeText(getContext(), circleGreatBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }
}
