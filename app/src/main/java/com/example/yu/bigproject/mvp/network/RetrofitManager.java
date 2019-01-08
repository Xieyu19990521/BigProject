package com.example.yu.bigproject.mvp.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.yu.bigproject.base.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<T> {
    private final String BASE_URL = "http://mobile.bwstudent.com/small/";
    //private final String BASE_URL = "http://172.17.8.100/small/";
    private static RetrofitManager mRetrofitManager;
    private BaseApis mBaseApis;
    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                SharedPreferences preferences = MyApplication.getApplication().getSharedPreferences("User", Context.MODE_PRIVATE);
                String userId = preferences.getString("UserId", "");
                String sessionId = preferences.getString("SessionId", "");
                Request.Builder builder1 = request.newBuilder();
                builder1.method(request.method(),request.body());
                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                    builder1.addHeader("userId",userId);
                    builder1.addHeader("sessionId",sessionId);
                }
                Request build = builder1.build();
                return chain.proceed(build);
            }
        });
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }

    /**
     * get请求
     */
    public void get(String url, HttpListener listener) {

        mBaseApis.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));
    }

    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }
    /**
     * put请求
     */
    public void put(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * delete请求
     */
    public void delete(String url,HttpListener listener){
        mBaseApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    private Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    Log.i("TAG",data);
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }
        };

        return observer;
    }


    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }
}
