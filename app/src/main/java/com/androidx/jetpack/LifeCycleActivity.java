package com.androidx.jetpack;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by meikai on 2019/11/11.
 */
public class LifeCycleActivity extends AppCompatActivity {

    private static final String TAG = "LifeCircle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_circle);

        Log.e(TAG, "onCreate");

        /**
         * 分析：
         * 1、定义一个观察者，它来观察一个具有生命周期的物体
         */
        LifecycleObserver observer = new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                Log.e(TAG, "source" + source + ", event=" + event.name());
            }
        };

        /**
         * 分析：
         * 1、getLifecycle() 获取具有生命周期的物体。在sdk中返回的是 LifecycleRegistry，它是继承Lifecycle的，
         *    这里是装饰模式，LifecycleRegistry装饰了Lifecycle，增加了mObserverMap，以此实现可以注册多个观察者
         * 2、sdk的Activity在相应时机回调LifecycleRegistry，执行注册的所有observer
         */
        getLifecycle().addObserver(observer);


        Log.e(TAG, "onCreate2");

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}
