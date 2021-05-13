package com.jina.wishbook.Camera;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jina.wishbook.R;

import java.util.zip.Inflater;

public class BottomSheet extends BottomSheetDialogFragment {
    private WebView webView;
    private WebSettings webSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);
        View v = inflater.inflate(R.layout.fragment_dialog,container,false);

        webView = v.findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient());
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.naver.com/");

        return v;
    }

    public BottomSheet() {
        super();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
