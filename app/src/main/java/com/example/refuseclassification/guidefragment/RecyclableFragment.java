package com.example.refuseclassification.guidefragment;//wsy

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.refuseclassification.R;

public class RecyclableFragment extends Fragment {

    private WebView webView; // WebView用于显示可回收垃圾相关信息的网页内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局文件frag_recyclable.xml
        View view = inflater.inflate(R.layout.frag_recyclable, container, false);
        // 获取WebView实例
        webView = (WebView) view.findViewById(R.id.recyclable_web);
        // 设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置WebView的客户端，用于处理网页加载过程中的事件
        webView.setWebViewClient(new WebViewClient());
        // 加载百度百科中可回收垃圾的相关信息
        webView.loadUrl("https://baike.baidu.com/item/%E5%8F%AF%E5%9B%9E%E6%94%B6%E7%89%A9");
        return view;
    }
}