package com.hapis.customer.ui.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;

import java.util.ArrayList;

/**
 * Created by Rahul on 3/22/18.
 */
public class PermissionsUtils {

    public static final int REQUEST_STORAGE = 0;
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_PHONE_STATE = 2;
    public static final int REQUEST_AUDIO_RECORD = 3;
    public static final int REQUEST_CALL_PHONE = 4;
    public static final int REQUEST_CAMERA = 5;
    public static final int REQUEST_CONTACT = 6;
    public static final int REQUEST_CAMERA_FOR_PROFILE_PHOTO = 7;
    public static final int REQUEST_STORAGE_FOR_PROFILE_PHOTO = 8;
    public static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    public static String[] PERMISSION_CALL = {Manifest.permission.CALL_PHONE};
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String[] PERMISSIONS_RECORD_AUDIO = {Manifest.permission.RECORD_AUDIO};
    public static String[] PERMISSION_CAMERA = {Manifest.permission.CAMERA};
    public static String[] PERMISSION_CONTACT = {Manifest.permission.READ_CONTACTS};

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRequestForLocationPermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static boolean shouldShowRequestForAudioPermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.RECORD_AUDIO));
    }


    public static boolean shouldShowRequestForStoragePermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE));
    }

    public static boolean shouldShowRequestForCallPermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CALL_PHONE));
    }

    public static boolean shouldShowRequestForCameraPermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA));
    }

    public static boolean shouldShowRequestForContactPermission(Activity activity) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_CONTACTS));
    }

    public static boolean checkSelfForStoragePermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED);
    }


    public static boolean checkSelfPermissionForLocation(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkSelfPermissionForAudioRecording(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkSelfForCallPermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkSelfForCameraPermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED);
    }

    public static boolean checkSelfForContactPermission(Activity activity) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static boolean isAudioRecordingPermissionGranted(Context context) {
        String permission = "android.permission.RECORD_AUDIO";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isCameraPermissionGranted(Context context) {
        int res = context.checkCallingOrSelfPermission(Manifest.permission.CAMERA);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isCallPermissionGranted(Context context) {
        int res = context.checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void onPermissionDenied(UserPermissionDeniedCallBack permissionDeniedCallBack, Activity activity, ArrayList<String> deniedPermissions, String message) {

        final UserPermissionDeniedCallBack userPermissionDeniedCallBack = permissionDeniedCallBack;

        if(deniedPermissions != null && deniedPermissions.size() > 0){
            StringBuilder showRationaleStringBuilder = new StringBuilder();
            StringBuilder notRationaleStringBuilder = new StringBuilder();
            for(String deniedPermission : deniedPermissions){
                // user rejected the permission
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean showRationale = activity.shouldShowRequestPermissionRationale( deniedPermission );

                    if (! showRationale) {
                        showRationaleStringBuilder = null;
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting

                        notRationaleStringBuilder.append(" "+getPermissionUserFriendlyMsg(deniedPermission, activity)+",");
                    } else {
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                        showRationaleStringBuilder.append(" "+getPermissionUserFriendlyMsg(deniedPermission, activity)+",");
                    }
                }
            }

            if(notRationaleStringBuilder != null && notRationaleStringBuilder.length() > 0){

                OnClickListener onClickListener = new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()){
                            case R.id.positive_btn:
                            {
                                dialog.dismiss();
                                userPermissionDeniedCallBack.takeActionOnCheckedDontAskAndPermissionDenied();
                                break;
                            }
                        }
                    }
                };

                if(message == null || (message != null && message.length() == 0)){
                    AlertUtil.showAlert(activity, R.string.user_permission_denied_alert_title, activity.getResources().getString(R.string.user_permission_checked_dont_ask_denied_alert_msg1)+notRationaleStringBuilder.toString().substring(0, notRationaleStringBuilder.toString().length()-1)+" "+activity.getResources().getString(R.string.user_permission_checked_dont_ask_denied_alert_msg2), activity.getResources().getString(R.string.ok), onClickListener,DialogIconCodes.DIALOG_FAILED.getIconCode());
                }else{
                    AlertUtil.showAlert(activity, R.string.user_permission_denied_alert_title, message+activity.getResources().getString(R.string.user_permission_msg_set_manually), activity.getResources().getString(R.string.ok), onClickListener,DialogIconCodes.DIALOG_FAILED.getIconCode());
                }
            }else if(showRationaleStringBuilder != null && showRationaleStringBuilder.length() > 0){
                OnClickListener onClickListener = new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()){
                            case R.id.positive_btn:
                            {
                                dialog.dismiss();
                                userPermissionDeniedCallBack.takeActionOnOnlyPermissionDenied();
                                break;
                            }
                        }
                    }
                };
                if(message == null || (message != null && message.length() == 0)){
                    AlertUtil.showAlert(activity, R.string.user_permission_denied_alert_title, activity.getResources().getString(R.string.user_permission_denied_alert_msg1)+showRationaleStringBuilder.toString().substring(0, showRationaleStringBuilder.toString().length()-1)+" "+activity.getResources().getString(R.string.user_permission_denied_alert_msg2), activity.getResources().getString(R.string.ok), onClickListener,DialogIconCodes.DIALOG_FAILED.getIconCode());
                }else{
                    AlertUtil.showAlert(activity, R.string.user_permission_denied_alert_title, message, activity.getResources().getString(R.string.ok), onClickListener,DialogIconCodes.DIALOG_FAILED.getIconCode());
                }
            }else
                userPermissionDeniedCallBack.noAction();
        }
    }

    public static String getPermissionUserFriendlyMsg(String permission, Activity activity){
        String message = null;

        if(Manifest.permission.CAMERA.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.camera_operation);
        }else if(Manifest.permission.WRITE_EXTERNAL_STORAGE.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.data_storage);
        }else if(Manifest.permission.CALL_PHONE.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.call_phone);
        }else if(Manifest.permission.ACCESS_FINE_LOCATION.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.network_or_gps_provider_location_access);
        }else if(Manifest.permission.ACCESS_COARSE_LOCATION.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.location_access);
        }else if(Manifest.permission.READ_EXTERNAL_STORAGE.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.read_access);
        }else if("android.permission.RECEIVE_SMS".toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.receive_SMS);
        }else if("android.permission.READ_SMS".toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.read_SMS);
        }else if(Manifest.permission.READ_PHONE_STATE.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.read_phone_state);
        }else if(Manifest.permission.GET_ACCOUNTS.toLowerCase().equalsIgnoreCase(permission)){
            message = activity.getResources().getString(R.string.get_account);
        }



        return message;
    }
}
