package com.hapis.customer.ui.utils;

import android.app.Activity;
import android.view.View;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;

/**
 * Created by Javeed on 13/08/17.
 */
public class ApplicationRuntimePermissions {
    private Activity activity;
    private AppRuntimePermissionCallBack mAppRuntimePermissionCallBack;

    public ApplicationRuntimePermissions(Activity activity, AppRuntimePermissionCallBack appRuntimePermissionCallBack) {
        this.activity = activity;
        mAppRuntimePermissionCallBack = appRuntimePermissionCallBack;
    }

    public void checkRuntimePermissionForPhotoState() {
        if (ApplicationPermissionsUtils.checkSelfForPhoneStatePermission(activity)) {
            requestPhoneStatePermissions();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_PHONE_STATE);
    }

    public void checkRuntimePermissionForSMS() {
        if (ApplicationPermissionsUtils.checkSelfForSMSPermission(activity)) {
            requestSMSPermissions();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_SMS_RECEIVE_READ);
    }

    public void checkRuntimePermissionForStorage() {
        if (ApplicationPermissionsUtils.checkSelfForStoragePermission(activity)) {
            requestStoragePermissions();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_STORAGE);
    }

    public void checkRuntimePermissionForReadContacts() {
        if (ApplicationPermissionsUtils.checkSelfForContactPermission(activity)) {
            requestContactPermission();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_CONTACT);
    }

    public void checkRuntimePermissionForPhotosMediaFiles() {
        if (ApplicationPermissionsUtils.checkSelfForMediaFilesPickerPermission(activity)) {
            requestCameraPermissionForProfilePhoto();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_CAMERA_FOR_PROFILE_PHOTO);
    }

    public void checkRuntimePermissionForVideoCapture() {
        if (ApplicationPermissionsUtils.checkSelfForVideoCapture(activity)) {
            requestCameraPermissionForProfilePhoto();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_CAMERA_FOR_VIDEO_RECORDING);
    }

    public void checkRuntimePermissionForPhoneCalls() {
        if (ApplicationPermissionsUtils.checkSelfForCallPermission(activity)) {
            requestCallPermission();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_CALL_PHONE);
    }

    public void checkRuntimePermissionForCamera() {
        if (ApplicationPermissionsUtils.checkSelfForCameraPermission(activity)) {
            requestCameraPermission();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_CAMERA);
    }

    public void checkRuntimePermissionForLocation() {
        if (ApplicationPermissionsUtils.checkSelfPermissionForLocation(activity)) {
            requestLocationPermissions();
        } else
            mAppRuntimePermissionCallBack.processNext(ApplicationPermissionsUtils.REQUEST_LOCATION);
    }

    public void requestStoragePermissions() {
        if (ApplicationPermissionsUtils.shouldShowRequestForStoragePermission(activity)) {
            showAlertToRequestPermission(R.string.storage_permission, ApplicationPermissionsUtils.PERMISSIONS_STORAGE, ApplicationPermissionsUtils.REQUEST_STORAGE);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSIONS_STORAGE, ApplicationPermissionsUtils.REQUEST_STORAGE);
        }
    }

    public void requestPhoneStatePermissions() {
        if (ApplicationPermissionsUtils.shouldShowRequestForPhoneStatePermission(activity)) {
            showAlertToRequestPermission(R.string.phone_state_permisssion, ApplicationPermissionsUtils.PERMISSIONS_PHONE_STATE, ApplicationPermissionsUtils.REQUEST_PHONE_STATE);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSIONS_PHONE_STATE, ApplicationPermissionsUtils.REQUEST_PHONE_STATE);
        }
    }

    public void requestSMSPermissions() {
        if (ApplicationPermissionsUtils.shouldShowRequestForSMSPermission(activity)) {
            showAlertToRequestPermission(R.string.read_sms_permission, ApplicationPermissionsUtils.PERMISSION_SMS_RECEIVE_READ, ApplicationPermissionsUtils.REQUEST_SMS_RECEIVE_READ);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSION_SMS_RECEIVE_READ, ApplicationPermissionsUtils.REQUEST_SMS_RECEIVE_READ);
        }
    }

    public void requestLocationPermissions() {
        if (ApplicationPermissionsUtils.shouldShowRequestForLocationPermission(activity)) {
            showAlertToRequestPermission(R.string.location_permission, ApplicationPermissionsUtils.PERMISSIONS_LOCATION, ApplicationPermissionsUtils.REQUEST_LOCATION);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSIONS_LOCATION, ApplicationPermissionsUtils.REQUEST_LOCATION);
        }
    }

    public void requestAudio() {
        if (ApplicationPermissionsUtils.shouldShowRequestForLocationPermission(activity)) {
            showAlertToRequestPermission(R.string.record_audio, ApplicationPermissionsUtils.PERMISSIONS_LOCATION, ApplicationPermissionsUtils.REQUEST_AUDIO_RECORD);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSIONS_RECORD_AUDIO, ApplicationPermissionsUtils.REQUEST_AUDIO_RECORD);
        }
    }

    public void requestCallPermission() {
        if (ApplicationPermissionsUtils.shouldShowRequestForCallPermission(activity)) {
            showAlertToRequestPermission(R.string.phone_call_permission, ApplicationPermissionsUtils.PERMISSION_CALL, ApplicationPermissionsUtils.REQUEST_CALL_PHONE);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSION_CALL, ApplicationPermissionsUtils.REQUEST_CALL_PHONE);
        }
    }

    public void requestCameraPermission() {
        if (ApplicationPermissionsUtils.shouldShowRequestForCameraPermission(activity)) {
            showAlertToRequestPermission(R.string.phone_camera_permission, ApplicationPermissionsUtils.PERMISSION_CAMERA, ApplicationPermissionsUtils.REQUEST_CAMERA);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSION_CAMERA, ApplicationPermissionsUtils.REQUEST_CAMERA);
        }
    }

    public void requestContactPermission() {
        if (ApplicationPermissionsUtils.shouldShowRequestForContactPermission(activity)) {
            showAlertToRequestPermission(R.string.contact_permission, ApplicationPermissionsUtils.PERMISSION_CONTACT, ApplicationPermissionsUtils.REQUEST_CONTACT);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSION_CONTACT, ApplicationPermissionsUtils.REQUEST_CONTACT);
        }
    }

    public void requestCameraPermissionForProfilePhoto() {
        if (ApplicationPermissionsUtils.shouldShowRequestForCameraPermission(activity)) {
            showAlertToRequestPermission(R.string.phone_file_picker_permission,
                    ApplicationPermissionsUtils.PERMISSION_CAMERA, ApplicationPermissionsUtils.REQUEST_CAMERA_FOR_PROFILE_PHOTO);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity,
                    ApplicationPermissionsUtils.PERMISSION_CAMERA, ApplicationPermissionsUtils.REQUEST_CAMERA_FOR_PROFILE_PHOTO);
        }
    }

    public void requestStoragePermissionsForProfilePhoto() {
        if (ApplicationPermissionsUtils.shouldShowRequestForStoragePermission(activity)) {
            showAlertToRequestPermission(R.string.storage_permission, ApplicationPermissionsUtils.PERMISSIONS_STORAGE, ApplicationPermissionsUtils.REQUEST_STORAGE_FOR_PROFILE_PHOTO);
        } else {
            ApplicationPermissionsUtils.requestPermissions(activity, ApplicationPermissionsUtils.PERMISSIONS_STORAGE, ApplicationPermissionsUtils.REQUEST_STORAGE_FOR_PROFILE_PHOTO);
        }
    }

    public void showAlertToRequestPermission(int messageId, final String[] permissions, final int requestCode) {

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.positive_btn: {
                        dialog.dismiss();
                        ApplicationPermissionsUtils.requestPermissions(activity, permissions, requestCode);
                        break;
                    }
                }
            }
        };

        AlertUtil.showAlert(activity, R.string.app_name,
                activity.getResources().getString(messageId),
                null, null, onClickListener,
                DialogIconCodes.DIALOG_SUCCESS.getIconCode() );
    }

    public interface AppRuntimePermissionCallBack {
        void processNext(int requestCode);
    }

}
