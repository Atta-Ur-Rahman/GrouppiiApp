package com.techease.groupiiapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techease.groupiiapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {


    String url;
    private static final String HTML = "<!DOCTYPE html><html><body><a href='tel:867-5309'>Click here to call!</a></body></html>";
    private static final String TEL_PREFIX = "tel:";

    @BindView(R.id.webView)
    WebView webView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_back_no_internet_connection)
    TextView tvNoInternetConnection;
    @BindView(R.id.pb_webview)
    ProgressBar progressBar;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.setWebViewClient(new HelloWebViewClient());
        progressBar.setVisibility(View.VISIBLE);
        webView.loadData(HTML, "text/html", "utf-8");
        webView.loadUrl(url);


        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {

                        onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView webView1, int newProgress) {

                Log.d("progress", String.valueOf(newProgress));

                progressBar.setProgress(newProgress);


                if (newProgress == 100) {
                    progressBar.setProgress(0);
                }
            }
        });


    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setProgress(10);
            Log.d("zma url",url);
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            if (url.startsWith(TEL_PREFIX)) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);


            if (url.endsWith(".mp4")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            progressBar.setProgress(0);

            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                error) {
            super.onReceivedError(view, request, error);

            progressBar.setProgress(0);
        }


    }
}