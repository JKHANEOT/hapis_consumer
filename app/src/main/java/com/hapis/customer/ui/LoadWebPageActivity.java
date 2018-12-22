package com.hapis.customer.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.TermsAndPoliciesFrag;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.LoadWebPageView;
import com.hapis.customer.ui.view.LoadWebPageViewModal;

import org.apache.commons.io.FilenameUtils;

public class LoadWebPageActivity extends BaseFragmentActivity<LoadWebPageViewModal> implements LoadWebPageView {

    public static final String PAGE_TYPE = "page_type";
    public static final String PAGE_URL = "page_url";
    public static final String PRIVACY_POLICIES = "privacy_policies";
    public static final String TERMS_CONDITIONS = "terms_conditions";
    public static final String SOME_PAGE = "some_page";
    public static final String PAGE_TITLE = "page_title";
    private String title = "";
    TermsAndPoliciesFrag frag = null;
    public static final String URL = "url";

    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;

    @Override
    protected Class getViewModalClass() {
        return LoadWebPageViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pageType = getIntent().getStringExtra(PAGE_TYPE);
        if (pageType != null && pageType.equals(SOME_PAGE)) {
            setContentView(R.layout.fragment_load_web_page);
            title = getIntent().getStringExtra(PAGE_TITLE);
            String pageUrl = getIntent().getStringExtra(PAGE_URL);

            customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
            webView = (WebView) findViewById(R.id.webView);

            mWebViewClient = new myWebViewClient();
            webView.setWebViewClient(mWebViewClient);

            mWebChromeClient = new myWebChromeClient();
            webView.setWebChromeClient(mWebChromeClient);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSaveFormData(true);
            webView.loadUrl(pageUrl);
        }else{
            setContentView(R.layout.activity_terms_and_policies);

            Bundle args = new Bundle();
            args.putString(PAGE_TYPE, pageType);
            if (pageType != null && pageType.equals(TERMS_CONDITIONS)) {
                title = getResources().getString(R.string.terms_conditions_title_label);
                args.putString(URL, "http://nhancenow.com/mob_TermsOfUse.html");

            } else if (pageType != null && pageType.equals(PRIVACY_POLICIES)) {
                title = getResources().getString(R.string.privacy_policies_title_label);
                args.putString(URL, "http://nhancenow.com/mob_PrivacyPolicy.html");

            } else if (pageType != null && pageType.equals(SOME_PAGE)) {
                title = getIntent().getStringExtra(PAGE_TITLE);
                String pageUrl = getIntent().getStringExtra(PAGE_URL);
                args.putString(URL, pageUrl);

            }else if (pageType == null) {
                String pageUrl = getIntent().getStringExtra(PAGE_URL);
                String documentName = "";
                if (pageUrl != null && pageUrl.length() > 0) {
                    documentName = FilenameUtils.getBaseName(pageUrl);
                }
                title = documentName;
                args.putString(URL, pageUrl);
            }
            frag = new TermsAndPoliciesFrag();
            frag.setArguments(args);

            setUpNavigationDrawer(title, null, true, null);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.terms_policies_container, frag)
                    .commit();
        }
//        TypefaceHelper.getInstance().setTypeface(this, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
    }

    @Override
    public void onBackPressed() {
        String pageType = getIntent().getStringExtra(PAGE_TYPE);
        if (pageType != null && !pageType.equals(SOME_PAGE)) {
            if (frag.getWebView().canGoBack()) {
                frag.getWebView().goBack();
            } else {
                super.onBackPressed();
            }
        }else
            super.onBackPressed();
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        if(mWebChromeClient != null)
            mWebChromeClient.onHideCustomView();
    }

    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        if(webView != null)
            webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        if(webView != null)
            webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl) {

    }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(LoadWebPageActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
