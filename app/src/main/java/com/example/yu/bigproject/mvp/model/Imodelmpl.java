package com.example.yu.bigproject.mvp.model;

import com.example.yu.bigproject.mvp.callback.MyCallBack;
import com.example.yu.bigproject.mvp.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class Imodelmpl implements Imodel{
    @Override
    public void onPostRequestStart(String s, Map<String, String> map, final Class clas, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().post(s, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clas);
                myCallBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.onFail(error);
            }
        });
    }

    @Override
    public void onGetRequestStart(String s, final Class clas, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(s, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clas);
                myCallBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.onFail(error);
            }
        });
    }

    @Override
    public void onDeleteRequestStart(String s, final Class clas, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().delete(s, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clas);
                myCallBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.onFail(error);
            }
        });
    }

    @Override
    public void onPutRequestStart(String s, Map<String, String> map, final Class clas, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().put(s, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clas);
                myCallBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.onFail(error);
            }
        });
    }
}
