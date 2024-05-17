package com.example.refuseclassification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TestAllActivity extends AppCompatActivity {

    private ImageButton recyclableButton;
    private ImageButton harmfulButton;
    private ImageButton wetButton;
    private ImageButton dryButton;
    private ImageButton testButton;
    private ImageButton exerciseButton;
    private ImageButton errorProneButton;
    private ImageButton commonButton;
    private ImageButton specialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_test);


        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAllActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        exerciseButton = findViewById(R.id.exercise_button);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAllActivity.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });

        errorProneButton = findViewById(R.id.errorProne_button);
        errorProneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAllActivity.this, ErrorProneActivity.class);
                startActivity(intent);
            }
        });

        commonButton = findViewById(R.id.common_button);
        commonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAllActivity.this, CommonActivity.class);
                startActivity(intent);
            }
        });

        specialButton = findViewById(R.id.special_button);
        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestAllActivity.this, SpecialActivity.class);
                startActivity(intent);
            }
        });
    }
}
