package com.example.yu.bigproject.mvp.presenter;

import com.example.yu.bigproject.mvp.callback.MyCallBack;
import com.example.yu.bigproject.mvp.model.Imodelmpl;
import com.example.yu.bigproject.mvp.view.Iview;

import java.util.Map;

public class IpresenterImpl implements Ipresenter{
    Iview iview;
    Imodelmpl imodelmpl;

    public IpresenterImpl(Iview iview) {
        this.iview = iview;
        imodelmpl=new Imodelmpl();
    }

    @Override
    public void onPostStartRequest(String s, Map<String, String> map, Class clas) {
        imodelmpl.onPostRequestStart(s, map, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iview.onFail(error);
            }
        });
    }

    @Override
    public void onGetStartRequest(String s, Class clas) {
        imodelmpl.onGetRequestStart(s, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iview.onFail(error);
            }
        });
    }

    @Override
    public void onDeleteStartRequest(String s, Class clas) {
        imodelmpl.onDeleteRequestStart(s, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iview.onFail(error);
            }
        });
    }

    @Override
    public void onPutStartRequest(String s, Map<String, String> map, Class clas) {
        imodelmpl.onPutRequestStart(s, map, clas, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iview.onFail(error);
            }
        });
    }

    public void onDetach(){
        if(iview!=null){
            iview=null;
        }
        if(imodelmpl!=null){
            imodelmpl=null;
        }
    }

}
