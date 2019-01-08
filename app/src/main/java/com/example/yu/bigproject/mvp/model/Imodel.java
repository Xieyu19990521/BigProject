package com.example.yu.bigproject.mvp.model;

import com.example.yu.bigproject.mvp.callback.MyCallBack;

import java.util.Map;

public interface Imodel {
    void onPostRequestStart(String s, Map<String,String> map, Class clas,MyCallBack myCallBack);
    void onGetRequestStart(String s,Class clas,MyCallBack myCallBack);
    void onDeleteRequestStart(String s,Class clas,MyCallBack myCallBack);
    void onPutRequestStart(String s, Map<String,String> map, Class clas,MyCallBack myCallBack);
}
