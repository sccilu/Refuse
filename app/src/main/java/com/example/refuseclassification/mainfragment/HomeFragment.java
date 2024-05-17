package com.example.refuseclassification.mainfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.EventManager;
import com.baidu.speech.asr.SpeechConstant;
import com.example.refuseclassification.ASRresponse;
import com.example.refuseclassification.CommonActivity;
import com.example.refuseclassification.DryActivity;
import com.example.refuseclassification.ErrorProneActivity;
import com.example.refuseclassification.ExerciseActivity;
import com.example.refuseclassification.HarmfulActivity;
import com.example.refuseclassification.KnowledgeDatabase;
import com.example.refuseclassification.R;
import com.example.refuseclassification.RecyclableActivity;
import com.example.refuseclassification.SearchActivity;
import com.example.refuseclassification.SpecialActivity;
import com.example.refuseclassification.TestActivity;
import com.example.refuseclassification.TestAllActivity;
import com.example.refuseclassification.WetActivity;
import com.example.refuseclassification.setTitleCenter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeFragment extends Fragment implements EventListener{

    private Toolbar toolbar;
    private ImageButton recyclable_button;
    private ImageButton harmful_button;
    private ImageButton wet_button;
    private ImageButton dry_button;
    private ImageButton testAll_Button;
    private EditText search;
    private ImageButton recording_button;
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
        recyclable_button = (ImageButton) view.findViewById(R.id.recyclable_button);
        ImageButton testAllButton = view.findViewById(R.id.test_all_button);
        testAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestAllActivity.class);
                startActivity(intent);
            }
        });
        recyclable_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecyclableActivity.class);
                startActivity(intent);
            }
        });
        harmful_button = (ImageButton) view.findViewById(R.id.harmful_button);
        harmful_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HarmfulActivity.class);
                startActivity(intent);
            }
        });
        wet_button = (ImageButton) view.findViewById(R.id.wet_button);
        wet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WetActivity.class);
                startActivity(intent);
            }
        });
        dry_button = (ImageButton) view.findViewById(R.id.dry_button);
        dry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DryActivity.class);
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

        search = (EditText) view.findViewById(R.id.searchHome);
        search.setFocusable(false);//失去焦点
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        // 初始化权限
        initPermission();
        recording_button = (ImageButton) view.findViewById(R.id.recording_button);


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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        //退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener(this);
    }

    public void setResult(String result) {
        this.result = result;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
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
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {

    }
}
