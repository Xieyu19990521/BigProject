package com.example.yu.bigproject.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.ShowActivity;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.sign.activity.SignActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author YU
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements Iview {

    private IpresenterImpl ipresenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.fast_sign)
    TextView fastsign;
    @BindView(R.id.login_eye)
    ImageButton login_eye;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.login_tels)
    EditText login_tels;
    @BindView(R.id.login_but)
    Button login_btn;
    @BindView(R.id.login_rem)
    CheckBox login_rem;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        //判断上一次登录是否是记住密码
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        boolean rem_isCheck = sharedPreferences.getBoolean("rem_isCheck", false);
        boolean isSuc_sign = sharedPreferences.getBoolean("isSuc_Sign", false);
        if(rem_isCheck){
            String login_tel = sharedPreferences.getString("login_tel", null);
            String login_pwd = sharedPreferences.getString("login_pwd", null);
            login_rem.setChecked(rem_isCheck);
            login_tels.setText(login_tel);
            login_password.setText(login_pwd);
        }
        if(isSuc_sign){
            String sign_tel = sharedPreferences.getString("sign_tel", null);
            String sign_pwd = sharedPreferences.getString("sign_pwd", null);
            login_tels.setText(sign_tel);
            login_password.setText(sign_pwd);
            login_rem.setChecked(true);
        }

    }

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    //点击登录按钮
    @OnClick(R.id.login_but)
    public void loginBtnClick(){
        String tels = login_tels.getText().toString();
        String password = login_password.getText().toString();
        if(login_rem.isChecked()){
            editor.putBoolean("rem_isCheck",login_rem.isChecked());
            editor.putString("login_tel",tels);
            editor.putString("login_pwd",password);
            editor.commit();
        }else{
            editor.clear();
            editor.commit();
        }
        getData(tels,password);
    }

    public void getData(String tels, String password){
        Map<String,String> map=new HashMap<>();
        map.put(Apis.LOGIN_PHONE,tels);
        map.put(Apis.LOGIN_PWD,password);
        ipresenter.onPostStartRequest(Apis.USER_LOGIN,map,LoginBean.class);
    }

    //点击快速注册
    @OnClick(R.id.fast_sign)
    public void fastSignClick(){
        Intent intent=new Intent(LoginActivity.this,SignActivity.class);
        startActivity(intent);
        finish();
    }

    //是否显示密码
    @OnTouch(R.id.login_eye)
    public boolean loginEyeClick(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return false;
    }

    //请求成功
    @Override
    public void onSuccess(Object data) {
        if(data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if(loginBean.getMessage().equals("登录成功")){
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,ShowActivity.class);
                editor.putString("SessionId",loginBean.getResult().getSessionId());
                editor.putString("UserId",loginBean.getResult().getUserId()+"");
                editor.commit();
                startActivity(intent);
                finish();
            }else{
                editor.remove("SessionId");
                editor.remove("UserId");
                editor.commit();
                Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //请求失败
    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
