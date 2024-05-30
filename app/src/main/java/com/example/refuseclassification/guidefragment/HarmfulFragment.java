package com.example.refuseclassification.guidefragment;//wsy

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.refuseclassification.R;

public class HarmfulFragment extends Fragment {

    private WebView webView; // WebView用于显示有害垃圾相关信息的网页内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局文件frag_harmful.xml
        View view = inflater.inflate(R.layout.frag_harmful, container, false);
        // 获取WebView实例
        webView = (WebView) view.findViewById(R.id.harmful_web);
        // 设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置WebView的客户端，用于处理网页加载过程中的事件
        webView.setWebViewClient(new WebViewClient());
        // 加载百度百科中有害垃圾的相关信息
        webView.loadUrl("https://baike.baidu.com/item/%E6%9C%89%E5%AE%B3%E5%9E%83%E5%9C%BE");
        return view;
    }
}
