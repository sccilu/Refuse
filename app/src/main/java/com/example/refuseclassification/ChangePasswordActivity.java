package com.example.refuseclassification;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;


import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnChangePassword;
    private MyDatabaseHelper dbHelper;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // 获取当前登录用户的账号
        account = getIntent().getStringExtra("account");

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);
        dbHelper = new MyDatabaseHelper(this, "Account password", null, 2);

        btnChangePassword.setOnClickListener(v -> {
            String oldPassword = etOldPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ChangePasswordActivity.this, "新密码和确认密码不匹配", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // 验证旧密码
            Cursor cursor = db.query("Account", null, "account=?", new String[]{account}, null, null, null);
            if (cursor.moveToFirst()) {
                String currentPassword = cursor.getString(cursor.getColumnIndex("password"));
                if (!oldPassword.equals(currentPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "旧密码错误", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    return;
                }
            }
            cursor.close();

            // 更新密码
            ContentValues values = new ContentValues();
            values.put("password", newPassword);
            db.update("Account", values, "account=?", new String[]{account});
            Toast.makeText(ChangePasswordActivity.this, "密码已修改", Toast.LENGTH_SHORT).show();

            // 返回到主界面
            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

