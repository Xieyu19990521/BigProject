package com.example.yu.bigproject.home.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.home.bean.HomeDetailBean;
import com.example.yu.bigproject.home.bean.ShopResultBean;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.shopcar.bean.ShopCarAddBean;
import com.example.yu.bigproject.shopcar.bean.ShopCarBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YU
 * @date 2019/1/3
 * 详情页面
 */
public class DetailActivity extends BaseActivity implements Iview {

    @BindView(R.id.detail_back)
    ImageView detail_back;
    @BindView(R.id.detail_banner)
    Banner detail_banner;
    @BindView(R.id.detail_title)
    TextView detail_title;
    @BindView(R.id.detail_weight)
    TextView detail_weight;
    @BindView(R.id.detail_price)
    TextView detail_price;
    @BindView(R.id.detail_salenum)
    TextView detail_salenum;
    @BindView(R.id.detail_webview)
    WebView detail_webView;
    IpresenterImpl ipresenter;
    private String commodityId;

    @Override
    protected void initData() {
        /*detail_webView.getSettings().setBuiltInZoomControls(true);
        detail_webView.getSettings().setSupportZoom(true);
        detail_webView.getSettings().setDisplayZoomControls(true);*/
        Intent intent=getIntent();
        commodityId = intent.getStringExtra("commodityId");
        getDetailData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        detail_banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        detail_banner.setImageLoader(new ImageLoaderInterface<SimpleDraweeView>() {

            @Override
            public void displayImage(Context context, Object path, SimpleDraweeView imageView) {
                Uri uri=Uri.parse((String) path);
                imageView.setImageURI(uri);
            }

            @Override
            public SimpleDraweeView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
                return simpleDraweeView;
            }
        });
    }
    //查询购物车
    private void selShopCar(){
        ipresenter.onGetStartRequest(Apis.SHOPCAR_SEL,ShopCarBean.class);
    }

    //获取详情页信息
    private void getDetailData() {
        ipresenter.onGetStartRequest(String.format(Apis.HOME_DETAIL,commodityId),HomeDetailBean.class);
    }
    //添加购物车
    private void addShopCar(List<ShopResultBean> list){
        String string="[";
        for (int i=0;i<list.size();i++){
            if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                int count = list.get(i).getCount();
                count++;
                list.get(i).setCount(count);
                break;
            }else if(i==list.size()-1){
                list.add(new ShopResultBean(Integer.valueOf(commodityId),1));
                break;
            }
        }
        for (ShopResultBean resultBean:list){
            string+="{\"commodityId\":"+resultBean.getCommodityId()+",\"count\":"+resultBean.getCount()+"},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring+="]";
        Log.i("TAG",substring);
        Map<String,String> map=new HashMap<>();
        map.put("data",substring);
        ipresenter.onPutStartRequest(Apis.SHOPCAR_ADD,map,ShopCarAddBean.class);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_detail;
    }

    @OnClick({R.id.detail_back,R.id.detail_add_shopcar,R.id.detail_buy})
    public void setOnbackClick(View v){
        switch (v.getId()){
            case R.id.detail_back:
                //点返回 销毁页面
                finish();
                break;
            case R.id.detail_add_shopcar:
                //先查询购物车 判断购物车里是否有要添加的商品 如果有 就数量加1  如果没有 再加入购物车
                //只能先查询购物车 如果先添加 会把购物车里 原有的数据替换掉
                selShopCar();
                break;
            case R.id.detail_buy:
                //点击购买
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof HomeDetailBean){
            HomeDetailBean homeDetailBean= (HomeDetailBean) data;
            HomeDetailBean.ResultBean result = homeDetailBean.getResult();
            detail_title.setText(result.getCommodityName());
            detail_price.setText("¥"+result.getPrice());
            detail_salenum.setText("已售"+result.getSaleNum()+"件");
            detail_weight.setText(result.getWeight()+"kg");
            String[] split = result.getPicture().split("\\,");
            List<String> list=new ArrayList<>();
            for (int i=0;i<split.length;i++){
                list.add(split[i]);
            }
            detail_banner.setImages(list);
            detail_banner.start();
            detail_webView.loadDataWithBaseURL(null, result.getDetails(), "text/html", "utf-8", null);
        }
        if(data instanceof ShopCarBean){
            ShopCarBean carBean= (ShopCarBean) data;
            if(carBean.getMessage().equals("查询成功")){
                //自己定义一个bean 存入 商品id 和数量count  ShopResultBean
                List<ShopResultBean> list=new ArrayList<>();
                //创建一个集合
                List<ShopCarBean.Result> result = carBean.getResult();
                for(ShopCarBean.Result results:result){
                    //把查询出来购物车的数据 循环添加进自己写的集合
                    list.add(new ShopResultBean(results.getCommodityId(),results.getCount()));
                }
                //把查到的数据 传给添加购物车方法 并判断
                addShopCar(list);
            }
        }

        if(data instanceof ShopCarAddBean){
            ShopCarAddBean addBean= (ShopCarAddBean) data;
            if(addBean.getMessage().equals("同步成功")){
                Toast.makeText(this, "已加入购物车,可点击查看", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onFail(String error) {
        Log.i("TAG",error);
    }

    /*public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
        if(detail_webView!=null) {
            detail_webView.setVisibility(View.GONE);
            detail_webView.removeAllViews();
            detail_webView.destroy();
            /*releaseAllWebViewCallback();*/
        }
    }
}
