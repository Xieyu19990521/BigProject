package com.example.yu.bigproject.sign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.login.LoginActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.sign.bean.SignBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author YU
 * 注册页面
 */

public class SignActivity extends BaseActivity implements Iview {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private IpresenterImpl ipresenter;
    @BindView(R.id.sign_but)
    Button sign_but;
    @BindView(R.id.sign_tels)
    EditText sign_tels;
    @BindView(R.id.sign_password)
    EditText sign_password;
    @BindView(R.id.fast_login)
    TextView fast_login;
    @BindView(R.id.sign_eye)
    ImageView sign_eye;
    private String sign_tel;
    private String sign_pwd;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        sharedPreferences=this.getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    protected int getResId() {
        return R.layout.activity_sign;
    }

    @OnClick(R.id.sign_but)
    public void SignClick(){
        sign_tel = sign_tels.getText().toString();
        sign_pwd = sign_password.getText().toString();
        if(sign_pwd.length()>6){
            getData(sign_tel,sign_pwd);
        }else{
            Toast.makeText(this, "密码不能为空且长度必须大于6", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.fast_login)
    public void fastLoginClick(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @OnTouch(R.id.sign_eye)
    public boolean SignEyeClick(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            sign_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            sign_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return true;
    }

    public void getData(String sign_tel, String sign_pwd){
        Map<String,String> map=new HashMap<>();
        map.put(Apis.SIGN_PHONE, sign_tel);
        map.put(Apis.SIGN_PWD, sign_pwd);
        ipresenter.onPostStartRequest(Apis.USER_SIGN,map,SignBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof SignBean){
            SignBean signBean= (SignBean) data;
            if(signBean.getMessage().equals("注册成功")){
                Toast.makeText(this, signBean.getMessage(), Toast.LENGTH_SHORT).show();
                editor.putBoolean("isSuc_Sign",true);
                editor.putString("sign_pwd",sign_pwd);
                editor.putString("sign_tel",sign_tel);
                editor.commit();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, signBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
