package com.example.refuseclassification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SpeechRecognitionActivity extends AppCompatActivity implements EventListener {

    private static final int PERMISSION_REQUEST_CODE = 123; // 权限请求码

    private EditText dialogEtInput; // 输入文本框
    private EventManager asr; // 语音识别事件管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);

        initPermission(); // 初始化权限

        asr = EventManagerFactory.create(this, "asr"); // 创建语音识别事件管理器
        asr.registerListener(this); // 注册事件监听器

        dialogEtInput = findViewById(R.id.dialog_et_input); // 绑定输入文本框
        final Button holdToSpeakBtn = findViewById(R.id.btn_hold_to_speak); // 绑定按住说话按钮
        Button confirmBtn = findViewById(R.id.btn_confirm); // 绑定确认按钮

        // 设置按住说话按钮的触摸监听器
        holdToSpeakBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下事件
                        asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0); // 开始语音识别
                        Toast.makeText(SpeechRecognitionActivity.this, "开始识别", Toast.LENGTH_SHORT).show();
                        holdToSpeakBtn.setBackgroundColor(ContextCompat.getColor(SpeechRecognitionActivity.this, R.color.pressed_button)); // 改变按钮颜色
                        return true;
                    case MotionEvent.ACTION_UP: // 抬起事件
                        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0); // 停止语音识别
                        Toast.makeText(SpeechRecognitionActivity.this, "停止识别", Toast.LENGTH_SHORT).show();
                        holdToSpeakBtn.setBackgroundColor(ContextCompat.getColor(SpeechRecognitionActivity.this, R.color.default_button)); // 恢复按钮颜色
                        return true;
                }
                return false;
            }
        });

        // 设置确认按钮的点击监听器
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将识别结果传递到SearchActivity
                String resultText = dialogEtInput.getText().toString();
                Intent intent = new Intent(SpeechRecognitionActivity.this, SearchActivity.class);
                intent.putExtra("record", resultText);
                startActivity(intent); // 启动新的活动
            }
        });
    }

    // 初始化权限
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO, // 录音权限
                Manifest.permission.ACCESS_NETWORK_STATE, // 访问网络状态权限
                Manifest.permission.INTERNET, // 互联网权限
                Manifest.permission.WRITE_EXTERNAL_STORAGE, // 写外部存储权限
                Manifest.permission.READ_EXTERNAL_STORAGE // 读外部存储权限
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        // 检查每个权限是否已授予
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                toApplyList.add(perm);
            }
        }

        // 请求未授予的权限
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限被拒绝，无法进行语音识别", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            // 权限被授予，继续执行后续操作
        }
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) { // 处理语音识别部分结果事件
            if (params != null && !params.isEmpty()) {
                Log.i("asr.event", params); // 打印日志

                Gson gson = new Gson();
                ASRResponse asrResponse = gson.fromJson(params, ASRResponse.class); // 解析JSON响应

                if (asrResponse != null) {
                    String result = asrResponse.getBestResult();
                    if (result != null) {
                        result = result.replace('，', ' ').replace('。', ' ').trim(); // 替换中文逗号、句号并去除多余空格
                        dialogEtInput.setText(result); // 将结果显示在文本框中
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0); // 取消语音识别
        asr.unregisterListener(this); // 注销事件监听器
    }

    // 语音识别响应类
    class ASRResponse {
        private String best_result;

        public String getBestResult() {
            return best_result;
        }

        public void setBestResult(String best_result) {
            this.best_result = best_result;
        }
    }
}
