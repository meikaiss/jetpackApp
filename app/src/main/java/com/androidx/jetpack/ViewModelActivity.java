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

public class ViewModelActivity extends AppCompatActivity {

    private static final String TAG = "LiveData";

    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.btn_modify)
    Button btnModify;
    @BindView(R.id.btn_modify_delay)
    Button btnModifyDelay;

    private Handler handler = new Handler();

    private BusViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnModifyDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "3秒后设置");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.e(TAG, "开始延迟设置1");
                        myViewModel.name.setValue("Benz Bus");
                        Log.e(TAG, "开始延迟设置2");

                    }
                }, 3000);


            }
        });


        myViewModel = ViewModelProviders.of(this).get(BusViewModel.class);
        myViewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "监听到更新，s="+s);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
