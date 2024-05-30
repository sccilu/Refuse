package com.example.refuseclassification;//wsy

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private TextView skip;  // 跳过按钮
    private int TIME = 3;   // 倒计时时间（以秒为单位）
    private Handler handler;  // 用于处理延迟任务的处理器
    private Runnable runnable;  // 用于处理延迟任务的Runnable
    Timer timer = new Timer();  // 定时器

    // 定时任务，每秒更新一次倒计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TIME--;  // 每秒减少1秒
                    skip.setText("跳过 " + "(" + TIME + "s)");  // 更新跳过按钮文本
                    if (TIME < 0) {
                        // 当时间小于0时，取消定时器任务并隐藏跳过按钮
                        timer.cancel();
                        skip.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉app标题栏，使应用全屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);  // 设置布局文件

        // 初始化跳过按钮，并设置点击事件监听器
        skip = findViewById(R.id.skip);
        skip.setOnClickListener(this);

        // 开始定时任务，每秒更新一次倒计时
        timer.schedule(task, 1000, 1000);

        // 设置不点击跳过时的延迟跳转任务
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                // 从闪屏界面跳转到登录界面
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // 结束当前活动
            }
        }, 5000);  // 延迟5秒后执行任务
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                // 点击跳过按钮时立即跳转到登录页面
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // 结束当前活动
                if (runnable != null) {
                    // 如果延迟任务还未执行，则取消它
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}
