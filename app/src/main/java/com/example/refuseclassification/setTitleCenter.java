package com.example.refuseclassification;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class setTitleCenter {
    public void setTitleCenter(Toolbar toolbar) {
        if (toolbar == null) return;


        toolbar.post(() -> {
            for (int i = 0; i < toolbar.getChildCount(); i++) {
                View view = toolbar.getChildAt(i);
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    if (toolbar.getTitle().equals(textView.getText().toString())) {
                        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                                Toolbar.LayoutParams.WRAP_CONTENT,
                                Toolbar.LayoutParams.MATCH_PARENT);
                        params.gravity = Gravity.CENTER;
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                        break;
                    }
                }
            }
        });
    }
}
