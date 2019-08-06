package com.example.imagesearch.View;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imagesearch.R;

public class WebPageView extends AppCompatActivity {
    //웹페이지보여주는 뷰
    private WebView mWebView;

    private String docUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("박효완");

        Intent intent = getIntent();

       docUrl = intent.getExtras().getString("url");

        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(docUrl);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

    }
    //새창열기 없이 웹뷰 내에서 다시 열기
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
