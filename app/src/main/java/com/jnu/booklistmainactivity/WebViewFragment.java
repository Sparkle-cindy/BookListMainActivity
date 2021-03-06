package com.jnu.booklistmainactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
        WebSettings webSettings=webView.getSettings();
        webView.loadUrl("http://news.sina.cn");
        webSettings.setJavaScriptEnabled(true);
        return rootView;
    }
}
