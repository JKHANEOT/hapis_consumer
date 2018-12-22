package com.hapis.customer.ui.utils;

public interface NhanceLifecycleListener {

    void isNhanceInForeground(boolean isInForeground);

    void onNhanceForeground();

    void onNhanceBackground();
}
