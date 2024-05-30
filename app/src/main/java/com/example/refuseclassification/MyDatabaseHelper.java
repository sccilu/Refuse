package com.example.refuseclassification; // wsy

import android.content.Context; // 导入Android中的Context类
import android.database.sqlite.SQLiteDatabase; // 导入SQLite数据库相关类
import android.database.sqlite.SQLiteOpenHelper; // 导入SQLiteOpenHelper类，用于创建和管理数据库
import android.widget.Toast; // 导入Android中的Toast类，用于显示提示信息

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 创建表的SQL语句
    public static final String CREATE_ACCOUNT = "create table Account (" +
            "id integer primary key autoincrement, " +
            "account text, " +
            "password text)";

    private Context mContext; // 上下文对象，用于显示Toast提示信息

    // 构造方法
    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context; // 初始化上下文对象
    }

    // 创建数据库时调用的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 执行SQL语句创建表
        db.execSQL(CREATE_ACCOUNT);

        // 显示注册成功的提示信息
        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT); // 此处应调用show()方法显示Toast
    }

    // 数据库版本更新时调用的方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除旧表
        db.execSQL("drop table if exists Account");
        // 重新创建新表
        onCreate(db);
    }
}