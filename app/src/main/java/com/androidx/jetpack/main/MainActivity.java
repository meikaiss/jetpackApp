package com.androidx.jetpack.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.jetpack.LifeCycleActivity;
import com.androidx.jetpack.LiveDataActivity;
import com.androidx.jetpack.R;
import com.androidx.jetpack.test.ATestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by meikai on 2019/11/11.
 */
public class MainActivity extends AppCompatActivity {

    private static final TestCase[] actArr = new TestCase[]{
            new TestCase(ATestActivity.class, "模拟现有框架的问题"),
            new TestCase(LifeCycleActivity.class, "演示LifeCycle基本用法"),
            new TestCase(LiveDataActivity.class, "演示LifeData基本用法"),
    };

    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;

    private MainAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

    }


    private static class MainAdapter extends RecyclerView.Adapter<MainVH> {

        @NonNull
        @Override
        public MainVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(14);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.normal_item_selector);

            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, Resources.getSystem().getDisplayMetrics())
            );
            textView.setLayoutParams(lp);

            MainVH vh = new MainVH(textView);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final MainVH holder, final int position) {

            holder.textView.setText(actArr[position].activityClass.getSimpleName()
                    + "\n" + actArr[position].desc);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.textView.getContext(), actArr[position].activityClass);
                    holder.textView.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return actArr.length;
        }
    }

    private static class MainVH extends RecyclerView.ViewHolder {

        TextView textView;

        public MainVH(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }


}
