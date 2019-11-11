package com.androidx.jetpack.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidx.jetpack.R;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 复习知识点：
 * 1、A启动B时，A.onPause失去焦点，B.create、B.start、B.resume，最后才是A.onStop不再显示，以此保证界面上有东西可操作
 * 2、B.finish后，B.pause，A.restart、A.start、A.resume，B.stop、B.destroyed
 * 3、destroy后的Activity及内部View对象并不会立即回收内存，而是在下一次gc时回收
 * Created by meikai on 2019/11/11.
 */
public class ATestActivity extends AppCompatActivity {

    private static final String TAG = "ATest";

    public static PhantomReference<BTestActivity> ref; // 虚引用 不能 获取 对象的引用
    public static ReferenceQueue<BTestActivity> queue = new ReferenceQueue<>();

    public static WeakReference<BTestActivity> weakRef;
    public static WeakReference<View> weakRefView;

    @BindView(R.id.tv_check_result)
    TextView tvCheckResult;
    @BindView(R.id.tv_check_result_2)
    TextView tvCheckResult2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_activity);

        ButterKnife.bind(this);

        Log.e(TAG, "onCreate");


        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weakRef == null) {
                    tvCheckResult.setText("没有创建弱引用");
                } else {

                    BTestActivity bTestActivity = weakRef.get();
                    if (bTestActivity == null) {
                        tvCheckResult.setText("弱引用的对象不存在");
                    } else {
                        tvCheckResult.setText("弱引用的对象存在=" + bTestActivity
                                + ", destroyed=" + (bTestActivity.isDestroyed() ? "true" : "false"));
                    }
                }

                if (weakRefView == null) {
                    tvCheckResult2.setText("没有创建弱引用");
                } else {

                    View view = weakRefView.get();
                    if (view == null) {
                        tvCheckResult2.setText("弱引用的对象不存在");
                    } else {
                        tvCheckResult2.setText("弱引用的对象存在=" + view
                                + ", isAttachedToWindow=" + (view.isAttachedToWindow() ? "true" : "false"));
                    }
                }

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
