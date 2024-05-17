package com.example.refuseclassification.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.refuseclassification.ExerciseActivity;
import com.example.refuseclassification.CommonActivity;
import com.example.refuseclassification.SpecialActivity;
import com.example.refuseclassification.TestActivity;
import com.example.refuseclassification.ErrorProneActivity;
import com.example.refuseclassification.R;

public class TestFragment extends Fragment {

    private ImageButton testButton;
    private ImageButton exerciseButton;
    private ImageButton errorProneButton;
    private ImageButton commonButton;
    private ImageButton specialButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_test, container, false);

        // 初始化按钮并设置点击监听器
        testButton = view.findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });

        exerciseButton = view.findViewById(R.id.exercise_button);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);
                startActivity(intent);
            }
        });

        errorProneButton = view.findViewById(R.id.errorProne_button);
        errorProneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ErrorProneActivity.class);
                startActivity(intent);
            }
        });

        commonButton = view.findViewById(R.id.common_button);
        commonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                startActivity(intent);
            }
        });

        specialButton = view.findViewById(R.id.special_button);
        specialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpecialActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}


