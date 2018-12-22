package com.hapis.customer.ui.utils;

import com.hapis.customer.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JKHAN
 */

public enum DialogIconCodes {

    DIALOG_NETWORK_ERROR("DIALOG_NETWORK_ERROR", R.drawable.ic_dialog_network_error),
    DIALOG_SERVER_ERROR("DIALOG_SERVER_ERROR", R.drawable.ic_dialog_server_error),
    DIALOG_DELETE("DIALOG_DELETE", R.drawable.ic_dialog_delete),
    DIALOG_DK_UPDATE("DIALOG_DK_UPDATE", R.drawable.ic_dialog_dk_update),
    DIALOG_SUCCESS("DIALOG_SUCCESS", R.drawable.ic_dialog_tick_mark),
    DIALOG_FAILED("DIALOG_FAILED", R.drawable.ic_dialog_failed_action),
    DIALOG_WANT_2_GO_BACK("DIALOG_WANT_2_GO_BACK", R.drawable.ic_dialog_prevent_back_action),
    DIALOG_NOT_AVAILABLE("DIALOG_NOT_AVAILABLE", R.drawable.ic_dialog_no_data_to_show),
    DIALOG_LOGOUT("DIALOG_LOGOUT", R.drawable.ic_logout);

    private final String iconCode;
    private final int icon;
    private static Map<String, Integer> mMap = Collections.unmodifiableMap(initializeMapping());

    public String getIconCode() {
        return iconCode;
    }

    public int getIcon() {
        return icon;
    }

    DialogIconCodes(String iconCode, int icon) {
        this.iconCode = iconCode;
        this.icon = icon;
    }

    public static int getIconByCode(String iconCode) {
        if (mMap == null) {
            initializeMapping();
        }
        if (mMap.containsKey(iconCode)) {
            return mMap.get(iconCode);
        }
        return 0;
    }

    private static Map<String, Integer> initializeMapping() {
        Map<String, Integer> mMap = new HashMap<String, Integer>();
        for (DialogIconCodes s : DialogIconCodes.values()) {
            mMap.put(s.iconCode, s.icon);
        }

        return mMap;
    }
}

