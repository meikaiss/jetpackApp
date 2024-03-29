package com.androidx.jetpack;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveDataActivity extends AppCompatActivity {

    private static final String TAG = "LiveData";

    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.btn_modify)
    Button btnModify;
    @BindView(R.id.btn_modify_delay)
    Button btnModifyDelay;

    private MutableLiveData<String> mutableLiveData;

    private Handler handler = new Handler();

    private BusViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mutableLiveData = new MutableLiveData<>();
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "监听到值的改变，s=" + s);
            }
        });


        Log.e(TAG, "开始设置1");
        mutableLiveData.setValue("123abc");
        Log.e(TAG, "开始设置2");

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "开始设置1");
                mutableLiveData.setValue("123abc");
                Log.e(TAG, "开始设置2");

            }
        });
        btnModifyDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "3秒后设置");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.e(TAG, "开始延迟设置1");
                        mutableLiveData.setValue("123abcefg");
                        Log.e(TAG, "开始延迟设置2");

                    }
                }, 3000);

            }
        });


        myViewModel = ViewModelProviders.of(this).get(BusViewModel.class);


        /**
         * 分析：
         * 1、入参1，LifecycleOwner实际就是 sdk 的Activity、Fragment，这个接口内部只有一个getLifecycle()方法
         * 2、通过入参1提供的 具有生命周期的物体，向其注册一个观察者ObserverWrapper包装器，在收到生命周期变化时ObserverWrapper包装器再来触发LiveDat的Observer
         */
        myViewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }

}
