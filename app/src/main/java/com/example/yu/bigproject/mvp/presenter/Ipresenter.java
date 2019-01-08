package com.example.yu.bigproject.mvp.presenter;

import java.util.Map;

public interface Ipresenter {
    void onPostStartRequest(String s, Map<String,String> map,Class clas);
    void onGetStartRequest(String s,Class clas);
    void onDeleteStartRequest(String s,Class clas);
    void onPutStartRequest(String s, Map<String,String> map,Class clas);
}
