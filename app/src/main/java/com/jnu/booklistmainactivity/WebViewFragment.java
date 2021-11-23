package com.jnu.booklistmainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {


    public WebViewFragment() {
        // Required empty public constructor
    }


    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView webView=rootView.findViewById(R.id.webview_content);
        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("file://android_asset/baidu.html");
        //加载手机本地的html页面
        webView.loadUrl("content://com.android.htmlfileprovider/sdcard/baidu.html");
        //加载 HTML 页面的一小段内容。参数1：需要截取展示的内容、参数2：展示内容的类型、参数3：字节码
        webView.loadData("html", "text/html; charset=UTF-8", null);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        return rootView;
    }
}
