package com.example.refuseclassification.mainfragment;

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
    private ImageButton recyclableButton;
    private ImageButton harmfulButton;
    private ImageButton wetButton;
    private ImageButton dryButton;
    private ImageButton konwledgeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_knowledge);

        recyclableButton =findViewById(R.id.recyclable_button);
        recyclableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KnowledgeActivity.this,RecyclableActivity.class);
                startActivity(intent);
            }
        });

        harmfulButton = findViewById(R.id.harmful_button);
        harmfulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KnowledgeActivity.this,HarmfulActivity.class);
                startActivity(intent);
            }
        });
        wetButton = findViewById(R.id.wet_button);
        wetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KnowledgeActivity.this, WetActivity.class);
                startActivity(intent);
            }
        });
        dryButton = findViewById(R.id.dry_button);
        dryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KnowledgeActivity.this, DryActivity.class);
                startActivity(intent);
            }
        });

    }
}
