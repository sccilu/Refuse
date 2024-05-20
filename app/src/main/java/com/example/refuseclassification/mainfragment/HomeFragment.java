package com.example.refuseclassification.mainfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.example.refuseclassification.DryActivity;
import com.example.refuseclassification.HarmfulActivity;
import com.example.refuseclassification.KnowledgeDatabase;
import com.example.refuseclassification.R;
import com.example.refuseclassification.RecyclableActivity;
import com.example.refuseclassification.SearchActivity;
import com.example.refuseclassification.SpeechRecognitionActivity;
import com.example.refuseclassification.WetActivity;
import com.example.refuseclassification.setTitleCenter;

import java.util.ArrayList;



public class HomeFragment extends Fragment implements EventListener{

    private Toolbar toolbar;
    private ImageButton testAll_Button;
    private ImageButton Knowledge_Button;
    private EditText search;
    private ImageButton recordingButton;

    private EventManager asr;//语音识别核心库
    private String result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("首页");
        new setTitleCenter().setTitleCenter(toolbar);// 初始化ToolBar
        new KnowledgeDatabase().setKnowledgeDatabase();// 初始化数据库
        // 绑定按钮以及事件

        Knowledge_Button = (ImageButton) view.findViewById(R.id.knowledge_button);
        Knowledge_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start TestAllActivity when test_all_button is clicked
                Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
                startActivity(intent);
            }
        });

        testAll_Button = (ImageButton) view.findViewById(R.id.test_all_button);
        testAll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start TestAllActivity when test_all_button is clicked
                Intent intent = new Intent(getActivity(), TestAllActivity.class);
                startActivity(intent);
            }
        });

        recordingButton = view.findViewById(R.id.recording_button);
        recordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpeechRecognitionActivity.class);
                startActivity(intent);
            }
        });


        search = (EditText) view.findViewById(R.id.searchHome);
        search.setFocusable(false);//失去焦点
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), perm)) {
                toApplyList.add(perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), toApplyList.toArray(tmpList), 123);
        }
    }

    @Override
    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {

    }
}
