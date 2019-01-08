package com.example.yu.bigproject;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.circle.fragment.CircleFragment;
import com.example.yu.bigproject.home.fragment.HomeFragment;
import com.example.yu.bigproject.list.fragment.ListFragment;
import com.example.yu.bigproject.my.fragment.MyFragment;
import com.example.yu.bigproject.shopcar.fragment.ShopCartFragment;
import com.example.yu.bigproject.views.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YU
 * 展示信息页面
 */
public class ShowActivity extends BaseActivity {

    private final int PAGER_LENGHT=5;
    @BindView(R.id.show_viewpager)
    CustomViewPager show_viewPager;
    @BindView(R.id.show_group)
    RadioGroup show_group;
    HomeFragment homeFragment=new HomeFragment();
    @Override
    protected void initData() {
        show_viewPager.setOffscreenPageLimit(1);
        show_viewPager.setCurrentItem(0,false);
        show_group.check(R.id.show_home);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        show_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        return homeFragment;
                    case 1:
                        return new CircleFragment();
                    case 2:
                        return new ShopCartFragment();
                    case 3:
                        return new ListFragment();
                    case 4:
                        return new MyFragment();
                    default:return new HomeFragment();
                }
            }

            @Override
            public int getCount() {
                return PAGER_LENGHT;
            }
        });

        show_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        show_group.check(R.id.show_home);
                        break;
                    case 1:
                        show_group.check(R.id.show_circle);
                        break;
                    case 2:
                        show_group.check(R.id.show_shopping_cart);
                        break;
                    case 3:
                        show_group.check(R.id.show_list);
                        break;
                    case 4:
                        show_group.check(R.id.show_my);
                        break;
                    default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_show;
    }

    //点击下面的图标 显示不同的页面
    @OnClick({R.id.show_home,R.id.show_circle,R.id.show_shopping_cart,R.id.show_list,R.id.show_my})
    public void setShowViewpager(View v){
        switch (v.getId()){
            case R.id.show_home:
                show_viewPager.setCurrentItem(0);
                break;
            case R.id.show_circle:
                show_viewPager.setCurrentItem(1);
                break;
            case R.id.show_shopping_cart:
                show_viewPager.setCurrentItem(2);
                break;
            case R.id.show_list:
                show_viewPager.setCurrentItem(3);
                break;
            case R.id.show_my:
                show_viewPager.setCurrentItem(4);
                break;
            default:break;
        }
    }

    private long exitTime = 0;

    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            int currentItem = show_viewPager.getCurrentItem();
            if(currentItem==0){
                homeFragment.getBackData(true);
            }
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
