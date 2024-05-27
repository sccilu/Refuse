package com.example.refuseclassification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedbackContent;
    private EditText contactInfo;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);

        feedbackContent = findViewById(R.id.feedback_content);
        contactInfo = findViewById(R.id.contact_info);
        email = findViewById(R.id.email);
        Button sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {
        String feedback = feedbackContent.getText().toString().trim();
        String contact = contactInfo.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();

        if (feedback.isEmpty() || contact.isEmpty() || emailAddress.isEmpty()) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        // 此处可以添加发送反馈到服务器或邮箱的逻辑
        // ...

        // 显示反馈成功的通知
        Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
        feedbackContent.setText("");
        contactInfo.setText("");
        email.setText("");
    }
}
