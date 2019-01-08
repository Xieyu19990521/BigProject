package com.example.yu.bigproject.mvp.view;

public interface Iview<T> {
    void onSuccess(T data);
    void onFail(String error);
}
