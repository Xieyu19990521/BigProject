package com.example.yu.bigproject.my.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yu.bigproject.Apis;
import com.example.yu.bigproject.R;
import com.example.yu.bigproject.base.BaseFragment;
import com.example.yu.bigproject.mvp.presenter.IpresenterImpl;
import com.example.yu.bigproject.mvp.view.Iview;
import com.example.yu.bigproject.my.activity.PersonNewActivity;
import com.example.yu.bigproject.my.activity.UserAddressActivity;
import com.example.yu.bigproject.my.activity.UserCircleActivity;
import com.example.yu.bigproject.my.activity.UserFootActivity;
import com.example.yu.bigproject.my.activity.UserWalletActivity;
import com.example.yu.bigproject.my.bean.MyPersonBean;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YU
 */
public class MyFragment extends BaseFragment implements Iview {

    private IpresenterImpl ipresenter;
    private String path=Environment.getExternalStorageDirectory()+"/image.png";

    @BindView(R.id.my_avatar)
    ImageView my_avatar;
    @BindView(R.id.my_pet_name)
    TextView my_pet_name;
    private PopupWindow popupWindow;

    @Override
    protected void initDate() {

    }

    @OnClick({R.id.my_avatar,R.id.my_person_new,R.id.my_circle,R.id.my_foot,R.id.my_wallet,R.id.my_address})
    public void setOnMyClick(View v){
        switch (v.getId()){
            case R.id.my_avatar:
                //有问题
                getPopupWindow();
                break;
            case R.id.my_person_new:
                Intent intent=new Intent(getContext(),PersonNewActivity.class);
                startActivity(intent);
                break;
            case R.id.my_circle:
                Intent intent1=new Intent(getContext(),UserCircleActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_foot:
                Intent intent2=new Intent(getContext(),UserFootActivity.class);
                startActivity(intent2);
                break;
            case R.id.my_wallet:
                Intent intent3=new Intent(getContext(),UserWalletActivity.class);
                startActivity(intent3);
                break;
            case R.id.my_address:
                Intent intent4=new Intent(getContext(),UserAddressActivity.class);
                startActivity(intent4);
                break;
            default:break;
        }
    }
    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
        ipresenter=new IpresenterImpl(this);
        getUserData();

    }

    private void getUserData(){
        ipresenter.onGetStartRequest(Apis.USER_PERSON_NEW,MyPersonBean.class);
    }
    private void getPopupWindow() {
        View view =View.inflate(getActivity(),R.layout.my_fragment_popwindow,null);
        TextView pick=view.findViewById(R.id.pick);
        TextView camera=view.findViewById(R.id.camera);
        TextView cancel=view.findViewById(R.id.cancel);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置焦点
        popupWindow.setFocusable(true);
        //设置可触摸
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, 400);
                popupWindow.dismiss();
            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 300);
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }
    @Override
    protected int getResId() {
        return R.layout.fragment_show_my;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof MyPersonBean){
            MyPersonBean personBean= (MyPersonBean) data;
            Glide.with(getContext()).load(personBean.getResult().getHeadPic()).into(my_avatar);
            my_pet_name.setText(personBean.getResult().getNickName());
            Log.i("TAG",personBean.getResult().getPassword());
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
