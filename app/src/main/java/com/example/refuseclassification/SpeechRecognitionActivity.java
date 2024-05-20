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

    private static final int PERMISSION_REQUEST_CODE = 123;

    private EditText dialogEtInput;
    private EventManager asr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);

        initPermission();

        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this);

        dialogEtInput = findViewById(R.id.dialog_et_input);
        final Button holdToSpeakBtn = findViewById(R.id.btn_hold_to_speak);
        Button confirmBtn = findViewById(R.id.btn_confirm);

        holdToSpeakBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0);
                        Toast.makeText(SpeechRecognitionActivity.this, "开始识别", Toast.LENGTH_SHORT).show();
                        holdToSpeakBtn.setBackgroundColor(ContextCompat.getColor(SpeechRecognitionActivity.this, R.color.pressed_button));
                        return true;
                    case MotionEvent.ACTION_UP:
                        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
                        Toast.makeText(SpeechRecognitionActivity.this, "停止识别", Toast.LENGTH_SHORT).show();
                        holdToSpeakBtn.setBackgroundColor(ContextCompat.getColor(SpeechRecognitionActivity.this, R.color.default_button));
                        return true;
                }
                return false;
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将识别结果传递到SearchActivity
                String resultText = dialogEtInput.getText().toString();
                Intent intent = new Intent(SpeechRecognitionActivity.this, SearchActivity.class);
                intent.putExtra("record", resultText);
                startActivity(intent);
            }
        });
    }

    private void initPermission() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                toApplyList.add(perm);
            }
        }

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
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            if (params != null && !params.isEmpty()) {
                Log.i("asr.event", params);

                Gson gson = new Gson();
                ASRResponse asrResponse = gson.fromJson(params, ASRResponse.class);

                if (asrResponse != null) {
                    String result = asrResponse.getBestResult();
                    if (result != null) {
                        result = result.replace('，', ' ').trim();
                        dialogEtInput.setText(result);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        asr.unregisterListener(this);
    }

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
