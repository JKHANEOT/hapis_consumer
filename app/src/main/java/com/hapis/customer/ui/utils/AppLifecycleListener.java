package com.hapis.customer.ui.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.hapis.customer.logger.LOG;

public class AppLifecycleListener implements LifecycleObserver {
    NhanceLifecycleListener appListener;

    public AppLifecycleListener(NhanceLifecycleListener listener) {
        this.appListener = listener;
    }

    public AppLifecycleListener() {
    }

    public static final String TAG = AppLifecycleListener.class.getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        LOG.d(TAG, "onMoveToForeground");
        if (appListener != null) {
            appListener.isNhanceInForeground(true);
            appListener.onNhanceForeground();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        LOG.d(TAG, "onMoveToBackground");
        if (appListener != null) {
            appListener.isNhanceInForeground(false);
            appListener.onNhanceBackground();
        }
    }
}