package com.hapis.customer.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hapis.customer.R;
import com.hapis.customer.ui.LoadWebPageActivity;
import com.hapis.customer.ui.custom.NhanceWebView;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.utils.Util;

public class TermsAndPoliciesFrag extends Fragment implements NhanceWebView.Listener {

    private View v;
    //    private String pageType = "";
    private String URL = "";
    private ProgressBar myProgress;
    public NhanceWebView mWebView;

    public WebView getWebView() {
        return mWebView;
    }

    public TermsAndPoliciesFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_terms_and_policies, container, false);

        URL = getArguments().getString(LoadWebPageActivity.URL);

        initWidgets(v);
        return v;
    }

    private void initWidgets(View v) {
        myProgress = v.findViewById(R.id.progress_bar);
        myProgress.setVisibility(View.GONE);
        mWebView = v.findViewById(R.id.webView);
        mWebView.setListener(getActivity(), this);
        if (Util.isNetworkAvailable(getActivity())) {
            getDataFromServer();
        } else {
            AlertUtil.showAlert(getActivity(), R.string.no_network_title, R.string.no_network_message, DialogIconCodes.DIALOG_NETWORK_ERROR.getIconCode());
            return;
        }
    }

    private void getDataFromServer() {
        mWebView.loadUrl(URL);
    }

    @Override
    public void onDestroy() {
        if(myProgress.isShown()){

        }
        mWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        myProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(String url) {
        myProgress.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        myProgress.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
