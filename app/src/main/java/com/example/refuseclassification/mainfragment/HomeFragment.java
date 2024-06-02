package com.example.refuseclassification.mainfragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.refuseclassification.ApiServer.AdvancedGeneral;
import com.example.refuseclassification.Database.KnowledgeDatabase;
import com.example.refuseclassification.R;
import com.example.refuseclassification.SearchActivity;
import com.example.refuseclassification.SpeechRecognitionActivity;
import com.example.refuseclassification.setTitleCenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    // 定义常量，用于请求图片捕捉和相机权限
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private String currentPhotoPath;

    private Toolbar toolbar;
    private ImageButton testAll_Button;
    private ImageButton Knowledge_Button;
    private EditText search;

    private ImageButton photo_Button;

    //录音功能
    private ImageButton recording_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载并返回布局文件
        View view = inflater.inflate(R.layout.frag_home, container, false);
        toolbar = view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("首页");
        new setTitleCenter().setTitleCenter(toolbar); // 初始化ToolBar
        new KnowledgeDatabase().setKnowledgeDatabase(); // 初始化数据库

        // 绑定知识按钮并设置点击事件
        Knowledge_Button = view.findViewById(R.id.knowledge_button);
        Knowledge_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 KnowledgeActivity
                Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
                startActivity(intent);
            }
        });

        // 绑定测试按钮并设置点击事件
        testAll_Button = view.findViewById(R.id.test_all_button);
        testAll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 TestAllActivity
                Intent intent = new Intent(getActivity(), TestAllActivity.class);
                startActivity(intent);
            }
        });
        /**
         * scc
         */
        // 绑定拍照按钮并设置点击事件
        photo_Button = view.findViewById(R.id.photograph_button);
        photo_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查相机权限
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    dispatchTakePictureIntent();
                }
            }
        });

        // 绑定录音按钮并设置点击事件
        recording_Button = view.findViewById(R.id.recording_button);
        recording_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 SpeechRecognitionActivity
                Intent intent = new Intent(getActivity(), SpeechRecognitionActivity.class);
                startActivity(intent);
            }
        });

        // 绑定搜索框并设置点击事件
        search = view.findViewById(R.id.searchHome);
        search.setFocusable(false); // 失去焦点
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 SearchActivity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // 分发拍照意图
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.refuseclassification.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // 创建图片文件
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // 文件前缀
                ".jpg",   // 文件后缀
                storageDir // 存储目录
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // 将拍照的结果传递给图像识别
            new Thread(() -> {
                try {
                    String result = AdvancedGeneral.recognizeImage(currentPhotoPath);
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("record", result);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getContext(), "需要相机权限才能使用此功能", Toast.LENGTH_SHORT).show();
            }
        }
    }
}