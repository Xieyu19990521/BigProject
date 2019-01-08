package com.example.yu.bigproject.my.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseActivity;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.my.bean.MyPersonBean;
import com.example.yu.bigproject.my.bean.MyUpdataNameBean;
import com.example.yu.bigproject.my.bean.MyUpdataPwdBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonNewActivity extends BaseActivity implements Iview {

    IpresenterImpl ipresenter;
    @BindView(R.id.my_person_new_image)
    ImageView my_person_new_image;
    @BindView(R.id.my_pet_name_image)
    TextView my_pet_name_text;
    @BindView(R.id.my_pwd_image)
    TextView my_pwd_text;
    private AlertDialog.Builder namebuilder,pwdbuilder;
    private String new_name,new_pwd;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ipresenter=new IpresenterImpl(this);
        ipresenter.onGetStartRequest(Apis.USER_PERSON_NEW,MyPersonBean.class);
    }

    @OnClick({R.id.my_person_new_navator,R.id.my_pwd,R.id.my_pet_name})
    public void setOnClickListener(View v){
        switch (v.getId()){
            case R.id.my_pet_name:
                namebuilder = new AlertDialog.Builder(this);
                View updata_name = View.inflate(this, R.layout.my_alertdialog_view, null);
                final EditText updata_edit=updata_name.findViewById(R.id.updata_edix);
                namebuilder.setView(updata_name);
                namebuilder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                namebuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new_name = updata_edit.getText().toString().trim();
                        if (new_name.equals("")) {
                            Toast.makeText(PersonNewActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("nickName", new_name);
                            ipresenter.onPutStartRequest(Apis.USER_UPDATE_PETNAME, map, MyUpdataNameBean.class);
                        }
                    }
                });
                namebuilder.show();
                break;
            case R.id.my_person_new_navator:break;
            case R.id.my_pwd:
                pwdbuilder = new AlertDialog.Builder(this);
                View updata_pwd = View.inflate(this, R.layout.my_alertdialog_pwd, null);
                final EditText old_edit=updata_pwd.findViewById(R.id.old_edix);
                final EditText new_edit = updata_pwd.findViewById(R.id.new_edix);
                pwdbuilder.setView(updata_pwd);
                pwdbuilder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                pwdbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String old_pwd = old_edit.getText().toString().trim();
                        new_pwd = new_edit.getText().toString().trim();
                        if (old_pwd.equals("")&&new_pwd.equals("")) {
                            Toast.makeText(PersonNewActivity.this, "新旧密码不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, String> map = new HashMap<>();
                            map.put("oldPwd", old_pwd);
                            map.put("newPwd",new_pwd);
                            ipresenter.onPutStartRequest(Apis.USER_UPDATE_PWD, map, MyUpdataNameBean.class);
                        }
                    }
                });
                pwdbuilder.show();
                break;
            default:break;
        }
    }
    @Override
    protected int getResId() {
        return R.layout.activity_person_new;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof MyPersonBean){
            MyPersonBean personBean= (MyPersonBean) data;
            Glide.with(this).load(personBean.getResult().getHeadPic()).into(my_person_new_image);
            my_pet_name_text.setText(personBean.getResult().getNickName());
            my_pwd_text.setText(personBean.getResult().getPassword());
        }else if(data instanceof MyUpdataNameBean){
            MyUpdataNameBean nameBean= (MyUpdataNameBean) data;
            if(nameBean.getMessage().equals("修改成功")){
                Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();
                my_pet_name_text.setText(new_name);
            }else{
                Toast.makeText(this, nameBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if(data instanceof MyUpdataNameBean){
            MyUpdataPwdBean pwdBean= (MyUpdataPwdBean) data;
            if(pwdBean.getMessage().equals("修改成功")){
                if(pwdBean.getMessage().equals("修改成功")){
                    Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();
                    my_pwd_text.setText(new_pwd);
                }else{
                    Toast.makeText(this, pwdBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
