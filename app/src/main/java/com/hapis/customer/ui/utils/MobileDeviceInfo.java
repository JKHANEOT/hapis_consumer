package com.hapis.customer.ui.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javeed on 6/17/2016.
 */
public class MobileDeviceInfo {

//    http://stackoverflow.com/questions/3596722/how-to-get-the-android-phone-model-version-sdk-details

    private static Context mContext;

    private static MobileDeviceInfo mobileDeviceInfo;

    public static void MobileDeviceInfoInit(Context context) {
        if(mobileDeviceInfo == null)
        {
            mobileDeviceInfo = new MobileDeviceInfo(context);
        }
    }

    private MobileDeviceInfo(Context context){
        mContext = context;
    }

    public static MobileDeviceInfo getMobileDeviceInfo(){

        return mobileDeviceInfo;
    }

    public String getDeviceName() {

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public List<String> getDeviceId() {
        List<String> deviceIdList = new ArrayList<>();
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(mContext);
        deviceIdList.add(telephonyInfo.getImsiSIM1());
        if(telephonyInfo.isDualSIM() && telephonyInfo.getImsiSIM2() != null && telephonyInfo.getImsiSIM2().length() > 0)
            deviceIdList.add(telephonyInfo.getImsiSIM2());

        /*final TelephonyManager mTelephony = (TelephonyManager) (mContext.getSystemService(Context.TELEPHONY_SERVICE));
        if (mTelephony.getDeviceId() != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int totalSIMSlots = mTelephony.getPhoneCount();

                if(totalSIMSlots > 0)
                {
                    for (int i = 0; i < totalSIMSlots; i++)
                    {
                        deviceIdList.add(mTelephony.getDeviceId(i));
                    }
                }
            }
            else
            {
                String deviceId = mTelephony.getDeviceId();
                deviceIdList.add(deviceId);
            }
        } else {
            String deviceId = Settings.Secure.getString(mContext
                    .getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceIdList.add(deviceId);
        }*/
        return deviceIdList;
    }

    public static String getMacAddr() {
        /*try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";*/
        String deviceUniqueId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        return deviceUniqueId;
    }
}
