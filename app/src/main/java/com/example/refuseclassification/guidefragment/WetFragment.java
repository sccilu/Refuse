package com.example.refuseclassification.guidefragment;//wsy

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.refuseclassification.R;

public class WetFragment extends Fragment {

    private WebView webView; // WebView用于显示湿垃圾相关信息的网页内容

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载布局文件frag_wet.xml
        View view = inflater.inflate(R.layout.frag_wet, container, false);
        // 获取WebView实例
        webView = (WebView) view.findViewById(R.id.wet_web);
        // 设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置WebView的客户端，用于处理网页加载过程中的事件
        webView.setWebViewClient(new WebViewClient());
        // 加载百度百科中湿垃圾的相关信息
        webView.loadUrl("https://baike.baidu.com/item/%E6%B9%BF%E5%9E%83%E5%9C%BE");
        return view;
    }
}
