package com.example.refuseclassification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;  // 用于保存用户偏好设置的对象
    private SharedPreferences.Editor editor;  // 用于编辑 SharedPreferences 的对象
    private EditText accountEdit;  //   用户名输入框
    private EditText passwordEdit;  // 密码输入框
    private Button login;  // 登录按钮
    private Button register;  // 注册按钮
    private CheckBox rememberPass;  // 记住密码复选框
    private CheckBox showPassword;  // 显示密码复选框
    private Toolbar toolbar;  // 工具栏
    private MyDatabaseHelper dbhelper;  // 数据库帮助类实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_login);  // 设置布局文件

        // 初始化 Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(""); // 设置标题为空

        // 设置 Toolbar 标题居中
        new setTitleCenter().setTitleCenter(toolbar);

        // 初始化 SharedPreferences 和界面控件
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPass = findViewById(R.id.remember_pass);
        showPassword = findViewById(R.id.show_password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        dbhelper = new MyDatabaseHelper(this, "Account password", null, 2);

        // 加载保存的偏好设置
        loadSavedPreferences();

        // 设置登录按钮的点击事件监听器
        login.setOnClickListener(v -> handleLogin());

        // 设置注册按钮的点击事件监听器
        register.setOnClickListener(v -> handleRegister());

        // 设置显示密码复选框的选中状态改变事件监听器
        showPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // 显示密码
                passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // 隐藏密码
                passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            // 光标移动到末尾
            passwordEdit.setSelection(passwordEdit.getText().length());
        });
    }

    // 加载保存的偏好设置
    private void loadSavedPreferences() {
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
    }

    // 处理登录逻辑
    private void handleLogin() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query("Account", null, null, null, null, null, null);
        boolean loginSuccess = false;
        if (cursor.moveToFirst()) {
            do {
                String hadaccount = cursor.getString(cursor.getColumnIndex("account"));
                String hadpassword = cursor.getString(cursor.getColumnIndex("password"));
                if (account.equals(hadaccount) && password.equals(hadpassword)) {
                    // 如果账号密码匹配，保存偏好设置并跳转到主界面
                    savePreferences(account, password);
                    navigateToMainActivity(account);
                    loginSuccess = true;
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (!loginSuccess) {
            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    // 处理注册逻辑
    private void handleRegister() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (account.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "账户和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("password", password);
        db.insert("Account", null, values);
        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    // 保存偏好设置
    private void savePreferences(String account, String password) {
        editor = pref.edit();
        if (rememberPass.isChecked()) {
            editor.putBoolean("remember_password", true);
            editor.putString("account", account);
            editor.putString("password", password);
        } else {
            editor.clear();
        }
        editor.apply();
    }

    // 跳转到主界面
    private void navigateToMainActivity(String account) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
        finish();
    }
}
