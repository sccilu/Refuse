package com.example.refuseclassification.mainfragment;
//解裔地

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refuseclassification.DryActivity;
import com.example.refuseclassification.HarmfulActivity;
import com.example.refuseclassification.R;
import com.example.refuseclassification.RecyclableActivity;
import com.example.refuseclassification.WetActivity;

public class KnowledgeActivity extends AppCompatActivity {
    // 定义四个ImageButton变量，分别对应四类垃圾的按钮
    private ImageButton recyclableButton;
    private ImageButton harmfulButton;
    private ImageButton wetButton;
    private ImageButton dryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件为frag_knowledge
        setContentView(R.layout.frag_knowledge);

        // 初始化recyclableButton并设置点击监听器
        recyclableButton = findViewById(R.id.recyclable_button);
        recyclableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent以启动RecyclableActivity
                Intent intent = new Intent(KnowledgeActivity.this, RecyclableActivity.class);
                // 启动RecyclableActivity
                startActivity(intent);
            }
        });

        // 初始化harmfulButton并设置点击监听器
        harmfulButton = findViewById(R.id.harmful_button);
        harmfulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent以启动HarmfulActivity
                Intent intent = new Intent(KnowledgeActivity.this, HarmfulActivity.class);
                // 启动HarmfulActivity
                startActivity(intent);
            }
        });

        // 初始化wetButton并设置点击监听器
        wetButton = findViewById(R.id.wet_button);
        wetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent以启动WetActivity
                Intent intent = new Intent(KnowledgeActivity.this, WetActivity.class);
                // 启动WetActivity
                startActivity(intent);
            }
        });

        // 初始化dryButton并设置点击监听器
        dryButton = findViewById(R.id.dry_button);
        dryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个Intent以启动DryActivity
                Intent intent = new Intent(KnowledgeActivity.this, DryActivity.class);
                // 启动DryActivity
                startActivity(intent);
            }
        });
    }
}
