package com.example.refuseclassification.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.refuseclassification.R;
import androidx.appcompat.app.AppCompatActivity;
import com.example.refuseclassification.CommonExerciseActivity;
import com.example.refuseclassification.ErrorProneExerciseActivity;
import com.example.refuseclassification.ExerciseActivity;
import com.example.refuseclassification.SpecialExerciseActivity;
import com.example.refuseclassification.TestExerciseActivity;
/**
 * scc
 */
public class TestAllActivity extends AppCompatActivity {

    // 定义按钮变量
    private ImageButton testButton;
    private ImageButton exerciseButton;
    private ImageButton errorProneButton;
    private ImageButton commonButton;
    private ImageButton specialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件
        setContentView(R.layout.frag_test);

        // 绑定测试按钮并设置点击事件
        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 TestActivity
                Intent intent = new Intent(TestAllActivity.this, TestExerciseActivity.class);
                startActivity(intent);
            }
        });

        // 绑定练习按钮并设置点击事件
        exerciseButton = findViewById(R.id.exercise_button);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 ExerciseActivity
                Intent intent = new Intent(TestAllActivity.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });

        // 绑定易错题按钮并设置点击事件
        errorProneButton = findViewById(R.id.errorProne_button);
        errorProneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 ErrorProneActivity
                Intent intent = new Intent(TestAllActivity.this, ErrorProneExerciseActivity.class);
                startActivity(intent);
            }
        });

        // 绑定常见问题按钮并设置点击事件
        commonButton = findViewById(R.id.common_button);
        commonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 CommonActivity
                Intent intent = new Intent(TestAllActivity.this, CommonExerciseActivity.class);
                startActivity(intent);
            }
        });

        // 绑定专题按钮并设置点击事件
        specialButton = findViewById(R.id.special_button);
        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 SpecialActivity
                Intent intent = new Intent(TestAllActivity.this, SpecialExerciseActivity.class);
                startActivity(intent);
            }
        });
    }
}
