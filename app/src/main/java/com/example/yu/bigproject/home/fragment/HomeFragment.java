package com.example.yu.bigproject.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseFragment;
import com.example.yu.bigproject.home.activity.DetailActivity;
import com.example.yu.bigproject.home.adapter.HomeDesignAdapter;
import com.example.yu.bigproject.home.adapter.HomeMagicAdapter;
import com.example.yu.bigproject.home.adapter.HomeMoreAdapter;
import com.example.yu.bigproject.home.adapter.HomePopAdapter;
import com.example.yu.bigproject.home.adapter.HomePopSAdapter;
import com.example.yu.bigproject.home.adapter.HomeQualityAdapter;
import com.example.yu.bigproject.home.adapter.HomeSearchAdapter;
import com.example.yu.bigproject.home.adapter.HomeSeconedMoreAdapter;
import com.example.yu.bigproject.home.bean.HomeBannerBean;
import com.example.yu.bigproject.home.bean.HomeComBean;
import com.example.yu.bigproject.home.bean.HomeMoreBean;
import com.example.yu.bigproject.home.bean.HomePopBean;
import com.example.yu.bigproject.home.bean.HomePopSBean;
import com.example.yu.bigproject.home.bean.HomeSearchBean;
import com.example.yu.bigproject.home.bean.HomeSeconedBean;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YU
 * 首页
 */
public class HomeFragment extends BaseFragment implements Iview {

    private HomeMagicAdapter magicAdapter;
    private HomeQualityAdapter qualityAdapter;
    private HomeDesignAdapter designAdapter;
    private HomeSearchAdapter searchAdapter;
    private HomeMoreAdapter moreAdapter;
    private HomePopAdapter popAdapter;
    private HomePopSAdapter popSAdapter;
    private HomeSeconedMoreAdapter seconedMoreAdapter;
    private final int mSpanSize=2;
    private int sPage,mPage,rxxpId,pzshId,mlssId,MoreId;
    private IpresenterImpl ipresenter;
    private String search_content,categoryId;
    private boolean top=true;
    @BindView(R.id.home_search)
    ImageView home_search;
    @BindView(R.id.home_search_text)
    TextView home_search_text;
    @BindView(R.id.home_edit_text)
    EditText home_edit_text;
    @BindView(R.id.home_quality_recyclerview)
    RecyclerView quality_recyclerview;
    @BindView(R.id.home_magic_recyclerview)
    RecyclerView magic_recyclerview;
    @BindView(R.id.home_search_recycler)
    XRecyclerView search_recyclerview;
    @BindView(R.id.home_design_recyclerview)
    RecyclerView design_recyclerview;
    @BindView(R.id.home_banner)
    XBanner home_banner;
    @BindView(R.id.home_top_pop)
    RelativeLayout home_top_pop;
    @BindView(R.id.home_scrollview)
    ScrollView scrollView;
    @BindView(R.id.home_design_more)
    ImageView home_design_more;
    @BindView(R.id.home_magic_more)
    ImageView home_magic_more;
    @BindView(R.id.home_quality_more)
    ImageView home_quality_more;
    @BindView(R.id.home_more)
    RelativeLayout home_more;
    @BindView(R.id.home_more_bg)
    ImageView hom_more_bg;
    @BindView(R.id.home_more_text)
    TextView home_more_text;
    @BindView(R.id.home_more_recyclerview)
    XRecyclerView more_recyclerview;
    @BindView(R.id.none_product)
    RelativeLayout none_product;
    @BindView(R.id.home_pop_recycler)
    RecyclerView pop_recycler;
    @BindView(R.id.home_pop_recycler_seconed)
    RecyclerView pop_recycler_seconed;
    @BindView(R.id.home_pop_seconed_more)
    XRecyclerView pop_seconed_more;

    @Override
    protected void initDate() {
        //加载首页显示数据
        getData();
        //跳转路径
        final Intent intent=new Intent(getContext(),DetailActivity.class);
        //搜索 显示搜索出来的商品
        search_recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                sPage=1;
                getSearchData(sPage,search_content);
            }

            @Override
            public void onLoadMore() {
                getSearchData(sPage,search_content);
            }
        });
        //更多数据加载
        more_recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getMoreData(mPage,MoreId);
            }
            @Override
            public void onLoadMore() {
                getMoreData(mPage,MoreId);
            }
        });

        pop_seconed_more.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getSeconedMore(categoryId,mPage);
            }
            @Override
            public void onLoadMore() {
                getSeconedMore(categoryId,mPage);
            }
        });

        //TODO 适配器添加监听
        popAdapter.setOnclickListener(new HomePopAdapter.OnClickListener() {
            @Override
            public void OnClick(String id) {
                getPopDataSeconed(id);
            }
        });

        popSAdapter.setOnclickListener(new HomePopSAdapter.OnClickListener() {
            @Override
            public void OnClick(String id) {
                categoryId=id;
                mPage=1;
                scrollView.setVisibility(View.GONE);
                search_recyclerview.setVisibility(View.GONE);
                home_top_pop.setVisibility(View.GONE);
                pop_seconed_more.setVisibility(View.VISIBLE);
                getSeconedMore(categoryId,mPage);
            }
        });
    }
    //TODO 点击事件
    @OnClick({R.id.home_search_text,R.id.home_search,R.id.home_menu,
            R.id.home_design_more,R.id.home_magic_more,R.id.home_quality_more})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.home_search_text:
                search_content = home_edit_text.getText().toString();
                if(!search_content.equals("")){
                    scrollView.setVisibility(View.GONE);
                    pop_seconed_more.setVisibility(View.GONE);
                    search_recyclerview.setVisibility(View.VISIBLE);
                    home_more.setVisibility(View.GONE);
                    home_top_pop.setVisibility(View.GONE);
                    sPage=1;
                    getSearchData(sPage, search_content);
                }else{
                    Toast.makeText(getContext(), "商品不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.home_search:
                v.setVisibility(View.GONE);
                home_edit_text.setVisibility(View.VISIBLE);
                home_search_text.setVisibility(View.VISIBLE);
                break;

            case R.id.home_menu:
                if(top) {
                    home_top_pop.setVisibility(View.VISIBLE);
                    getPopDataFirst();
                    getPopDataSeconed("1001002");
                }
                else{
                    home_top_pop.setVisibility(View.GONE);
                }
                top=!top;
                break;

            case R.id.home_design_more:
                scrollView.setVisibility(View.GONE);
                home_more.setVisibility(View.VISIBLE);
                hom_more_bg.setBackgroundResource(R.mipmap.home_design_bitmap);
                home_more_text.setText("热销新品");
                home_more_text.setTextColor(Color.parseColor("#ff7f57"));
                mPage = 1;
                MoreId=rxxpId;
                getMoreData(mPage,MoreId);
                break;

            case R.id.home_magic_more:
                scrollView.setVisibility(View.GONE);
                home_more.setVisibility(View.VISIBLE);
                hom_more_bg.setBackgroundResource(R.mipmap.home_magic_bitmap);
                home_more_text.setText("魔力时尚");
                home_more_text.setTextColor(Color.parseColor("#6699ff"));
                mPage = 1;
                MoreId=mlssId;
                getMoreData(mPage,MoreId);
                break;

            case R.id.home_quality_more:
                scrollView.setVisibility(View.GONE);
                home_more.setVisibility(View.VISIBLE);
                hom_more_bg.setBackgroundResource(R.mipmap.home_quality_bitmap);
                home_more_text.setText("品质生活");
                home_more_text.setTextColor(Color.parseColor("#ff6666"));
                mPage = 1;
                MoreId=pzshId;
                getMoreData(mPage,MoreId);
                break;
            default:break;
        }

    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
        ipresenter = new IpresenterImpl(this);
        //TODO 设置布局管理器 和适配器
        GridLayoutManager qualityManager=new GridLayoutManager(getContext(),mSpanSize);
        qualityManager.setOrientation(OrientationHelper.VERTICAL);
        quality_recyclerview.setLayoutManager(qualityManager);
        qualityAdapter=new HomeQualityAdapter(getContext());
        quality_recyclerview.setAdapter(qualityAdapter);

        LinearLayoutManager magicManager=new LinearLayoutManager(getContext());
        magicManager.setOrientation(OrientationHelper.VERTICAL);
        magic_recyclerview.setLayoutManager(magicManager);
        magicAdapter=new HomeMagicAdapter(getContext());
        magic_recyclerview.setAdapter(magicAdapter);

        LinearLayoutManager designManager=new LinearLayoutManager(getContext());
        designManager.setOrientation(OrientationHelper.HORIZONTAL);
        design_recyclerview.setLayoutManager(designManager);
        designAdapter = new HomeDesignAdapter(getContext());
        design_recyclerview.setAdapter(designAdapter);

        GridLayoutManager searchManager=new GridLayoutManager(getContext(),mSpanSize);
        searchManager.setOrientation(OrientationHelper.VERTICAL);
        search_recyclerview.setLayoutManager(searchManager);
        searchAdapter = new HomeSearchAdapter(getContext());
        search_recyclerview.setAdapter(searchAdapter);

        GridLayoutManager moreManager=new GridLayoutManager(getContext(),mSpanSize);
        moreManager.setOrientation(OrientationHelper.VERTICAL);
        more_recyclerview.setLayoutManager(moreManager);
        moreAdapter = new HomeMoreAdapter(getContext());
        more_recyclerview.setAdapter(moreAdapter);

        LinearLayoutManager popManager=new LinearLayoutManager(getContext());
        popManager.setOrientation(OrientationHelper.HORIZONTAL);
        pop_recycler.setLayoutManager(popManager);
        popAdapter = new HomePopAdapter(getContext());
        pop_recycler.setAdapter(popAdapter);

        LinearLayoutManager popSManager=new LinearLayoutManager(getContext());
        popSManager.setOrientation(OrientationHelper.HORIZONTAL);
        pop_recycler_seconed.setLayoutManager(popSManager);
        popSAdapter = new HomePopSAdapter(getContext());
        pop_recycler_seconed.setAdapter(popSAdapter);

        GridLayoutManager seconedMoreManager=new GridLayoutManager(getContext(),mSpanSize);
        seconedMoreManager.setOrientation(OrientationHelper.VERTICAL);
        pop_seconed_more.setLayoutManager(seconedMoreManager);
        seconedMoreAdapter = new HomeSeconedMoreAdapter(getContext());
        pop_seconed_more.setAdapter(seconedMoreAdapter);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_show_home;
    }

    //TODO 加载数据
    //加载首页显示数据数据
    private void getData() {
        ipresenter.onGetStartRequest(Apis.HOME_BANER,HomeBannerBean.class);
        ipresenter.onGetStartRequest(Apis.HOME_LIST,HomeComBean.class);
    }
    //搜索框 搜索数据
    private void getSearchData(int sPage, String search_content) {
        ipresenter.onGetStartRequest(String.format(Apis.HOME_SEARCH,search_content,sPage),HomeSearchBean.class);
    }
    //加载更多
    private void getMoreData(int mPage,int id){
        ipresenter.onGetStartRequest(String.format(Apis.HOME_MORE,id,mPage),HomeMoreBean.class);
    }

    //类目 一级 加载数据
    private void getPopDataFirst(){
        ipresenter.onGetStartRequest(Apis.HOME_POP_FIRST,HomePopBean.class);
    }
    //类目二级 加载数据
    private void getPopDataSeconed(String firstCategoryId){
        ipresenter.onGetStartRequest(String.format(Apis.HOME_POP_SECONED,firstCategoryId),HomePopSBean.class);
    }
    //根据二级条目 点击查询数据
    private void getSeconedMore(String categoryId,int mpage){
        ipresenter.onGetStartRequest(String.format(Apis.HOME_SECONED_MORE,categoryId,mpage),HomeSeconedBean.class);
    }
    //TODO 请求数据成功
    @Override
    public void onSuccess(Object data) {
        //头部 轮播图数据
        if(data instanceof HomeBannerBean) {
            HomeBannerBean bannerBean = (HomeBannerBean) data;
            home_banner.setData(bannerBean.getResult(), null);
            home_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    HomeBannerBean.ResultBean bean = (HomeBannerBean.ResultBean) model;
                    Glide.with(getActivity()).load(bean.getImageUrl()).into((ImageView) view);
                }
            });
        }else
        //下面三个板块的数据
        if(data instanceof HomeComBean){
            HomeComBean homeComBean = (HomeComBean) data;
            int Rx_size = homeComBean.getResult().getRxxp().size();
            int Ml_size = homeComBean.getResult().getMlss().size();
            int Pz_size = homeComBean.getResult().getPzsh().size();
            rxxpId = homeComBean.getResult().getRxxp().get(0).getId();
            pzshId = homeComBean.getResult().getPzsh().get(0).getId();
            mlssId = homeComBean.getResult().getMlss().get(0).getId();
            for (int i=0;i<Rx_size;i++){
                if(i==0){
                    qualityAdapter.setList(homeComBean.getResult().getPzsh().get(0).getCommodityList());
                }else{
                    qualityAdapter.addList(homeComBean.getResult().getPzsh().get(i).getCommodityList());
                }
            }
            for (int i=0;i<Ml_size;i++){
                if(i==0){
                    magicAdapter.setList(homeComBean.getResult().getMlss().get(0).getCommodityList());
                }else{
                    magicAdapter.addList(homeComBean.getResult().getMlss().get(i).getCommodityList());
                }
            }
            for (int i=0;i<Pz_size;i++){
                if(i==0){
                    designAdapter.setList(homeComBean.getResult().getRxxp().get(0).getCommodityList());
                }else{
                    designAdapter.addList(homeComBean.getResult().getRxxp().get(i).getCommodityList());
                }
            }
        }else
        //搜索框 搜索出来的数据
        if(data instanceof HomeSearchBean){
            HomeSearchBean searchBean= (HomeSearchBean) data;
            if(sPage==1){
                searchAdapter.setList(searchBean.getResult());
                if(searchBean.getResult().size()==0){
                    none_product.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }else{
                    none_product.setVisibility(View.GONE);
                }
            }else{
                searchAdapter.addList(searchBean.getResult());
                if(searchBean.getResult().size()<10){
                    Toast.makeText(getContext(), "没有更多商品了", Toast.LENGTH_SHORT).show();
                }
            }
            search_recyclerview.loadMoreComplete();
            search_recyclerview.refreshComplete();
            sPage++;

        }else
        //显示更多数据
        if(data instanceof HomeMoreBean){
            HomeMoreBean moreBean= (HomeMoreBean) data;
            if(mPage==1){
                if(moreBean.getResult().size()==0){
                    none_product.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }else{
                    none_product.setVisibility(View.GONE);
                }
                moreAdapter.setList(moreBean.getResult());
            }else{
                moreAdapter.addList(moreBean.getResult());
                if(moreBean.getResult().size()<10){
                    Toast.makeText(getContext(), "没有更多商品了", Toast.LENGTH_SHORT).show();
                }
            }
            more_recyclerview.loadMoreComplete();
            more_recyclerview.refreshComplete();
            mPage++;
        }else
        //一级类目数据
        if(data instanceof HomePopBean){
            HomePopBean popBean= (HomePopBean) data;
            popAdapter.setList(popBean.getResult());
        }else

        if(data instanceof HomePopSBean){
            HomePopSBean popSBean= (HomePopSBean) data;
            popSAdapter.setList(popSBean.getResult());
        }else
        //二级类目数据
        if(data instanceof HomeSeconedBean){
            HomeSeconedBean seconedBean= (HomeSeconedBean) data;
            if(mPage==1){
                if(seconedBean.getResult().size()==0){
                    none_product.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }else{
                    none_product.setVisibility(View.GONE);
                }
                seconedMoreAdapter.setList(seconedBean.getResult());
            }else{
                seconedMoreAdapter.addList(seconedBean.getResult());
                if(seconedBean.getResult().size()<10){
                    Toast.makeText(getContext(), "没有更多商品了", Toast.LENGTH_SHORT).show();
                }
            }
            mPage++;
            pop_seconed_more.refreshComplete();
            pop_seconed_more.loadMoreComplete();
        }
    }
    //TODO 请求数据失败
    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }

    //监听返回键

    public void getBackData(boolean back){
        if(back){
            search_recyclerview.setVisibility(View.GONE);
            home_edit_text.setVisibility(View.INVISIBLE);
            home_search.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            home_more.setVisibility(View.GONE);
            home_search_text.setVisibility(View.GONE);
            home_edit_text.setText("");
            home_search.setVisibility(View.VISIBLE);
            none_product.setVisibility(View.GONE);
            home_top_pop.setVisibility(View.GONE);
            pop_seconed_more.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
    }
}