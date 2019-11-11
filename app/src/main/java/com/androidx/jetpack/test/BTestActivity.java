package com.androidx.jetpack.test;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.jetpack.R;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 结论
 * 1、activity在执行onDestroyed后面，若仍然持有其内部的View，仍然是可以操作view，如TextView.setText、setVisibility、removeView；并不会崩溃
 * 2、先dialog.show，再activity.finish，最后dialog.dismiss时会报错not attached to window manager
 * Created by meikai on 2019/11/11.
 */
public class BTestActivity extends AppCompatActivity {

    private static final String TAG = "BTest";

    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_test_2)
    TextView tv_test_2;

    Handler handler = new Handler();
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_test_activity);

        Log.e(TAG, "onCreate");

        ATestActivity.ref = new PhantomReference<BTestActivity>(this, ATestActivity.queue);
        ATestActivity.weakRef = new WeakReference<>(this);
        ATestActivity.weakRefView = new WeakReference<>(getWindow().getDecorView());

        ButterKnife.bind(this);

        TextView textView = new TextView(this);
        textView.setText("这是一个dialog");
        dialog = new Dialog(this);
        dialog.setContentView(textView);

        findViewById(R.id.btn_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "5秒后修改view");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.e(TAG, "5秒到了");

                        /**
                         * 若在5秒等待期间，用户返回了，则
                         * activity.isDestroyed = true;
                         * tvTest.isAttachedToWindow() = false;
                         * tvTest.isActivated() = false;
                         */
                        tvTest.setText("这是修改后的内容");

                        tv_test_2.setVisibility(View.GONE);
                        ((ViewGroup) tv_test_2.getParent()).removeView(tv_test_2);

                    }
                }, 5000l);
            }
        });

        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "show dialog");
                dialog.show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "finish activity");
                        finish();
                    }
                }, 1000l);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "dismiss dialog");
                        dialog.dismiss();
                    }
                }, 5000l);
            }
        });

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
