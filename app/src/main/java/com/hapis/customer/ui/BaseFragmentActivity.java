/*
 * Copyright © EasOfTech 2015. All rights reserved.
 *
 * This software is the confidential and proprietary information
 *  of EasOfTech. You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms and
 *  conditions entered into with EasOfTech.
 *
 */
package com.hapis.customer.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.ApplicationConstants;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.LocaleUtils;
import com.hapis.customer.ui.utils.MobileDeviceInfo;
import com.hapis.customer.ui.utils.PermissionsUtils;
import com.hapis.customer.ui.utils.UserPermissionDeniedCallBack;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BaseViewModal;
import com.hapis.customer.utils.Application;
import com.hapis.customer.utils.DateUtil;
import com.hapis.customer.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by JKHAN.
 */

public abstract class BaseFragmentActivity<T extends BaseViewModal> extends AppCompatActivity  implements ApplicationConstants{

    public static final String TAG = BaseFragmentActivity.class.getName();

    protected abstract Class getViewModalClass();

    protected T mViewModal;

    protected abstract BaseView getViewImpl();

    private ProgressDialog progressDialog = null;
    private Handler handler;
    private String progressMessage = null;

    private static final String ARG_SECTION_NUMBER = "section_number";

    // private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in .
     */
    private CharSequence mTitle;

    private Toolbar toolbar;

    /**
     * dateEditText is used to set and retrieve date.
     */
    private EditText dateEditText;

    /**
     * dateTextInputLayout is used to disable error.
     */
    private TextInputLayout dateTextInputLayout;

    /**
     * mCurrentyear, mSelectedYear is used to set the current and selected year.
     */
    private int mCurrentyear, mSelectedYear;
    /**
     * mCurrentmonth, mSelectedMonth is used to set the current and selected month.
     */
    private int mCurrentmonth, mSelectedMonth;
    /**
     * mCurrentday, mSelectedDay is used to set the current and selected day.
     */
    private int mCurrentday, mSelectedDay;

    /**
     * DATE_PICKER_ID is used to define constant for popup dialog.
     */
    public static final int DATE_PICKER_ID = 1111;
    public static final int DATE_PICKER_ID1 = 2222;

    public ConnectivityManager mSystemService;

    /**
     * This is used to allow date picker future date or not.
     */
    private boolean allowFutureDate;
    private AppCompatTextView toolbarTitle;
    private AppCompatTextView toolbarSubtitle;
    private AppCompatImageView mToolbarLogo;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        mSystemService = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        handler = new Handler();
        mViewModal = (T) ViewModelProviders.of(this , new BaseViewModal.Factory(this)).get(getViewModalClass());
        mViewModal.registerView(getViewImpl());
    }

    public void setUpToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressSpinner = toolbar.findViewById(R.id.progress_spinner);
    }

    private ProgressBar progressSpinner;

    public void setUpNavigationDrawer(String screenTitle, View container) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarSubtitle = toolbar.findViewById(R.id.toolbar_subtitle);
        mToolbarLogo = toolbar.findViewById(R.id.prod_icon_toolbar);
        mToolbarLogo.setVisibility(View.GONE);
       /* mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);*/
        mTitle = screenTitle;
        toolbarTitle.setText(mTitle);

        //mNavigationDrawerFragment.setContainer(container);

        // Set up the drawer.
       /* mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                findViewById(R.id.drawer_layout), toolbar, screenTitle);*/

        progressSpinner = toolbar.findViewById(R.id.progress_spinner);
    }

    public ProgressBar getProgressSpinner() {
        return progressSpinner;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbarBackground(int backgroundColor) {
        if (toolbar != null)
            toolbar.setBackgroundColor(backgroundColor);
    }

    public void setToolbarTitleColor(int titleTextColor) {
        if (toolbar != null)
            if (toolbarTitle != null)
                toolbarTitle.setTextColor(titleTextColor);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpNavigationDrawer(String screenTitle, String subTitle, boolean isBackEnabled, String productDisplayImageUrl) {

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarSubtitle = toolbar.findViewById(R.id.toolbar_subtitle);
        mToolbarLogo = toolbar.findViewById(R.id.prod_icon_toolbar);

        if (productDisplayImageUrl != null && productDisplayImageUrl.length() > 0) {
            if (mToolbarLogo != null) {
                mToolbarLogo.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .asBitmap()
                        .load(productDisplayImageUrl)
                        .apply(RequestOptions
                                .noTransformation()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .fallback(R.drawable.prod_default_image)
                                .placeholder(R.drawable.prod_default_image))
                        .into(mToolbarLogo);
            }
        }

        progressSpinner = toolbar.findViewById(R.id.progress_spinner);

        setSupportActionBar(toolbar);

        if (isBackEnabled) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>" + screenTitle + " </font>"));
            if (subTitle != null)
                actionBar.setSubtitle(Html.fromHtml("<font color='#ffffff'> <small>" + subTitle + " <small> </font>"));

            setToolbarTitle(screenTitle);
            if (subTitle == null || (subTitle != null && subTitle.equals(""))) {
                toolbarSubtitle.setVisibility(View.GONE);
            } else {
                toolbarSubtitle.setVisibility(View.VISIBLE);
                setToolbarSubtitle(subTitle);
            }
        }
    }

    public void onDBChange(int dbOperation, String tableName, String result) {

    }

    public void setToolbarTitle(String title) {
//        toolbarTitle.setText(Html.fromHtml("<font color='#ffffff'>" + title + " </font>"));
        toolbarTitle.setText(title);
    }

    public void setToolbarSubtitle(String subTitle) {
        toolbarSubtitle.setText(Html.fromHtml("<font color='#8f8f8f'> <small>" + subTitle + " <small> </font>"));
    }

    /*@Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(BaseFragmentActivity.this, MenuMoreDialogFragment.class);
                    startActivity(intent);
                }
            }, 200);

        } else if (position == 1) {
            Intent intent = new Intent(BaseFragmentActivity.this, UserDkDetailsActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            *//*Intent addAnotherNumberIntent = new Intent(BaseFragmentActivity.this, AddAnotherNumberActivity.class);
            startActivity(addAnotherNumberIntent);*//*
            // Payment History
            Intent intent = new Intent(BaseFragmentActivity.this, PaymentActivity.class);
            startActivity(intent);
        } else if (position == 3) { // item for user documents
            Intent intent = new Intent(BaseFragmentActivity.this, UserDocumentsActivity.class);
            startActivity(intent);

        } else if (position == 4) {
            sendEmail("Feedback - Consumer Mobile No. ");
        } else if (position == 5) {
            termsOrPolicies(LoadWebPageActivity.TERMS_CONDITIONS);
            //  mNavigationDrawerFragment.closeNavigationDrawerLayout();
        } else if (position == 6) {
            termsOrPolicies(LoadWebPageActivity.PRIVACY_POLICIES);
            //  mNavigationDrawerFragment.closeNavigationDrawerLayout();
        } else if (position == 7) {
            logout();
            //logoutWithDeRegisteringDevice(BaseFragmentActivity.this);
            //mNavigationDrawerFragment.closeNavigationDrawerLayout();
        }
    }

    public void termsOrPolicies(final String type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseFragmentActivity.this, LoadWebPageActivity.class);
                intent.putExtra(LoadWebPageActivity.PAGE_TYPE, type);
                startActivity(intent);
            }
        }, 200);
    }*/

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    public void openWebPage(String url) {
        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    protected void sendEmail(String mailSubject) {
        LOG.i("Send email", "");
        String[] TO = {"support@nhancenow.com"};
        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + TO[0]));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mailSubject + Application.getInstance().getMobileNumber());
        /*emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);*/

        new TedPermission(BaseFragmentActivity.this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        MobileDeviceInfo mobileDeviceInfo = MobileDeviceInfo.getMobileDeviceInfo();
                        List<String> deviceIdList = mobileDeviceInfo.getDeviceId();

                        String deviceModelName = mobileDeviceInfo.getDeviceName();

                        String deviceVersion = mobileDeviceInfo.getAndroidVersion();

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("\n\n\n");
                        if (deviceIdList != null && deviceIdList.size() > 0) {

                            String deviceID = null;

                            if (deviceIdList.size() > 1) {
                                StringBuilder sb = new StringBuilder();

                                for (int i = 0; i < deviceIdList.size(); i++) {
                                    sb.append(deviceIdList.get(i));
                                    if (i <= deviceIdList.size() - 2)
                                        sb.append(" , ");
                                }
                                deviceID = sb.toString();
                            } else {
                                deviceID = deviceIdList.get(0);
                            }

                            stringBuilder.append("Device ID : " + deviceID + "\n");
                        }
                        if (deviceModelName != null)
                            stringBuilder.append("Device/Model Name : " + deviceModelName + "\n");
                        if (deviceVersion != null)
                            stringBuilder.append("Operating System version : " + deviceVersion + "\n");

                        emailIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            LOG.i("email sent...", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BaseFragmentActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PermissionsUtils.onPermissionDenied(new UserPermissionDeniedCallBack() {
                                @Override
                                public void takeActionOnOnlyPermissionDenied() {
                                    MobileDeviceInfo mobileDeviceInfo = MobileDeviceInfo.getMobileDeviceInfo();
                                    String deviceModelName = mobileDeviceInfo.getDeviceName();

                                    String deviceVersion = mobileDeviceInfo.getAndroidVersion();

                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("\n\n\n");
                                    if (deviceModelName != null)
                                        stringBuilder.append("Device/Model Name : " + deviceModelName + "\n");
                                    if (deviceVersion != null)
                                        stringBuilder.append("Operating System version : " + deviceVersion + "\n");

                                    emailIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                                    try {
                                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                        LOG.i("email sent...", "");
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(BaseFragmentActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void takeActionOnCheckedDontAskAndPermissionDenied() {
                                    MobileDeviceInfo mobileDeviceInfo = MobileDeviceInfo.getMobileDeviceInfo();
                                    String deviceModelName = mobileDeviceInfo.getDeviceName();

                                    String deviceVersion = mobileDeviceInfo.getAndroidVersion();

                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("\n\n\n");
                                    if (deviceModelName != null)
                                        stringBuilder.append("Device/Model Name : " + deviceModelName + "\n");
                                    if (deviceVersion != null)
                                        stringBuilder.append("Operating System version : " + deviceVersion + "\n");

                                    emailIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                                    try {
                                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                        LOG.i("email sent...", "");
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(BaseFragmentActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void noAction() {
                                    return;
                                }
                            }, BaseFragmentActivity.this, deniedPermissions, null);
                        }
                    }
                })
                .setPermissions(
                        Manifest.permission.READ_PHONE_STATE
                )
                .check();


    }

    /*@Override
    public void onNavigationDrawerProfileClicked() {
        if (!(this instanceof ProfileManagementActivity)) {
            Intent intent = new Intent(this, ProfileManagementActivity.class);
            intent.putExtra(ApplicationConstants.IS_EXISTING_USER, true);
            startActivity(intent);
            //mNavigationDrawerFragment.closeNavigationDrawerLayout();
        }
    }*/

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void logout() {

        try {
            OnClickListener clickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {

                    switch (view.getId()) {
                        case R.id.positive_btn: {
                            dialog.dismiss();

//                            logoutWithDeRegisteringDevice(BaseFragmentActivity.this);
                            break;
                        }
                        case R.id.negative_btn: {
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            };

            AlertUtil.showAlert(this, getResources().getString(R.string.user_logout_message), getResources().getString(R.string.are_you_sure_to_logout), getResources().getString(R.string.logout), getResources().getString(R.string.cancel), clickListener, DialogIconCodes.DIALOG_LOGOUT.getIconCode());

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert(this, R.string.app_name, e.getMessage(), DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

   /* public void logoutWithDeRegisteringDevice(final Context context) {
        showProgressDialog(BaseFragmentActivity.this, "");

        UserAuthModel login = new UserAuthModel();
        login.setUserId(Application.getInstance().getUserProfileUserIdOrGuid());

        login.setRequestType(RequestTypes.LOGOUT_REQ);
        HapisApplication.setModelToTakeAction(login);

        RestCall restCall = new RestCall();
        restCall.setOnRestCallListener(new RestCall.RestCallListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                dismissProgressDialog();
                e.printStackTrace();
                AlertUtil.showAlert(BaseFragmentActivity.this,
                        getResources().getString(R.string.logout), e.getMessage(),
                        DialogIconCodes.DIALOG_FAILED.getIconCode(), false);
            }

            @Override
            public void onResponse(RestCall.Result result, String response, List<ErrorMessage> errorMessages, String msg) {
                if (result == RestCall.Result.FAILED || result == RestCall.Result.EXCEPTION) {
                    dismissProgressDialog();
                    AlertUtil.showAlert(BaseFragmentActivity.this,
                            getResources().getString(R.string.logout), errorMessages.get(0).getMessageDescription(),
                            DialogIconCodes.DIALOG_FAILED.getIconCode(), false);
                } else {
                    try {
                        String resStr = response;
                        LOG.d(TAG, "" + resStr);

                        proceedToLogout(context, false);

                    } catch (Exception e) {
                        dismissProgressDialog();
                        e.printStackTrace();
                        AlertUtil.showAlert(BaseFragmentActivity.this,
                                getResources().getString(R.string.logout), e.getMessage(),
                                DialogIconCodes.DIALOG_FAILED.getIconCode(), false);
                    }
                }
            }
        });
        try {
            new UserManagementAction().addCommonRequestParameters(login);
            restCall.get(BaseFragmentActivity.this, true, "Loading items",
                    HapisApplication.getApplication().getBackendUrl() + RestConstants.LOGOUT_URL + "/" + Application.getInstance().getUserProfileUserIdOrGuid());
        } catch (IOException e) {
            dismissProgressDialog();
            e.printStackTrace();
            AlertUtil.showAlert(BaseFragmentActivity.this,
                    getResources().getString(R.string.logout), e.getMessage(),
                    DialogIconCodes.DIALOG_FAILED.getIconCode(), false);
        } catch (Exception e) {
            dismissProgressDialog();
            e.printStackTrace();
            AlertUtil.showAlert(BaseFragmentActivity.this,
                    getResources().getString(R.string.logout), e.getMessage(),
                    DialogIconCodes.DIALOG_FAILED.getIconCode(), false);
        }
    }


    public void proceedToLogout(final Context context, boolean dueToLoginInDifferentDevice) {
        // Stopping DownloadDkService if running
        if (DownloadDksService.isDkDownloadServiceRunning) {
            stopService(new Intent(context, DownloadDksService.class));
        }
        // Stopping IntervalSyncService if running
        if (IntervalSyncService.IS_INTERVAL_SYNC_RUNNING)
            stopService(new Intent(context, IntervalSyncService.class));

        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.SENT_TOKEN_TO_SERVER, false);
        // Clear all event alerts
        InAppNotificationScheduleService.removeAllDkAlerts();
        // Clearing all notifications on logout
        InAppNotificationScheduleService.clearNotifications(context);
//        new RegistrationIntentService().unregisterGCMToken();
        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.LOGGED_IN_USER_GUID, "");
        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_LOGGED_OUT);
        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.DEFAULT_LOCATION_ID, 0);
        Application.getInstance().setLoggedInUserName("");
        Application.getInstance().setLoggedInUserProfilePicPath("");
        Application.getInstance().setUserProfileUserIdOrGuid(null);
        Application.getInstance().setEmailId(null);
        Application.getInstance().setMobileNumber(null);
        String selectedLocaleCode = AccessPreferences.get(BaseFragmentActivity.this, ApplicationConstants.SELECTED_LOCALE_CODE, ApplicationConstants.DEFAULT_LOCALE_CODE);

        boolean isCustomServerUrl = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.IS_CUSTOM_SERVER_URL, false);
        String backendUrl = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.CUSTOM_SERVER_URL, "");

        AccessPreferences.clear(context);

        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.IS_CUSTOM_SERVER_URL, isCustomServerUrl);
        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.CUSTOM_SERVER_URL, backendUrl);

        if (selectedLocaleCode != null) {
            LocaleUtils.updateLocaleConfiguration(BaseFragmentActivity.this, LocaleUtils.transform(selectedLocaleCode));
        }
        //  new UserClientService(context).logout();
        HapisApplication.tempAddedAddress = null;
        if (isAppOpened()) {
            if (!dueToLoginInDifferentDevice) {
                showLoginScreen(context);
            } else {
                final Activity currentActivity = getForegroundActivity();
                showLoginScreen(context);
                OnClickListener clickListener = new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {

                        switch (view.getId()) {
                            case R.id.positive_btn: {
                                dialog.dismiss();
                                break;
                            }
                        }
                    }
                };
                if (currentActivity != null) {
                    AlertUtil.showAlert(currentActivity, R.string.app_name, context.getResources().getString(R.string.user_logout_message_due_to_other_login), context.getResources().getString(R.string.ok), clickListener, DialogIconCodes.DIALOG_LOGOUT.getIconCode());
                }
            }
        }
    }*/

    public static Activity getForegroundActivity() {
        Activity activity = null;
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);

            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return activity;
    }

    /*private void showLoginScreen(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        try {
            ((AppCompatActivity) context).finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Stopping DownloadDkService if running
        if (DownloadDksService.isDkDownloadServiceRunning) {
            stopService(new Intent(context, DownloadDksService.class));
        }
        try {
            HapisApplication.applicationDataBase.resetAllTables();
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }*/

    public boolean isAppOpened() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND || appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
            return true;
        }

        try {
            final Activity currenctActivity = getForegroundActivity();
            if (currenctActivity != null) {
                KeyguardManager km = (KeyguardManager) currenctActivity.getSystemService(Context.KEYGUARD_SERVICE);
                return km.inKeyguardRestrictedInputMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /*public void restoreActionBar() {
        mNavigationDrawerFragment.initActionBar();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* if (mNavigationDrawerFragment != null && !mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.general_actionbar_screen, menu);
            restoreActionBar();
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            /*Intent intent = new Intent(this, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();*/
        }
        return true;
    }

    public void showProgressDialog(final Context context, final String message) {
        handler.post(new Runnable() {

            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context, R.style.MyTheme);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);

                    if (message.equals(getResources().getString(R.string.fetching_digital_kits))) {
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.softwhite_transparent)));
                    }
                    if (!(((Activity) context).isFinishing()))
                        progressDialog.show();
                } else {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    if (message.equals(getResources().getString(R.string.fetching_digital_kits))) {
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.softwhite_transparent)));
                    }
                    if (!(((Activity) context).isFinishing()))
                        progressDialog.show();
                }
                // TypefaceHelper.getInstance().setTypeface(progressDialog, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
            }
        });
    }

    public void showProgressDialog(final Context context, final String message, final boolean isCancelable, final DialogInterface.OnCancelListener cancelListener) {
        handler.post(new Runnable() {

            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context, R.style.MyTheme);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(isCancelable);

                    progressDialog.setOnCancelListener(cancelListener);

                    if (message.equals(getResources().getString(R.string.fetching_digital_kits))) {
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.softwhite_transparent)));
                    }
                    if (!(((Activity) context).isFinishing()))
                        progressDialog.show();
                } else {
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    if (message.equals(getResources().getString(R.string.fetching_digital_kits))) {
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.softwhite_transparent)));
                    }
                    if (!(((Activity) context).isFinishing()))
                        progressDialog.show();
                }
                // TypefaceHelper.getInstance().setTypeface(progressDialog, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
            }
        });
    }

    public void dismissProgressDialog() {
        try {
            handler.post(new Runnable() {
                public void run() {
                    if (progressDialog == null) {
                        return;
                    }
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*if (mNavigationDrawerFragment != null && mNavigationDrawerFragment.isDrawerOpen()) {
                mNavigationDrawerFragment.closeNavigationDrawerLayout();
                return true;
            } else*/
            return super.onKeyDown(keyCode, event);
        } else return super.onKeyDown(keyCode, event);
    }

    /**
     * Is used to set EditText, TextInputLayout, and boolean to allow future date or not.
     *
     * @param dateEditText
     */
    public void setDateResultTo(EditText dateEditText, TextInputLayout textInputLayout, boolean allowFutureDate) {
        this.allowFutureDate = allowFutureDate;
        final Calendar c = Calendar.getInstance();
        mCurrentyear = c.get(Calendar.YEAR);
        mCurrentmonth = c.get(Calendar.MONTH);
        mCurrentday = c.get(Calendar.DAY_OF_MONTH);

        this.dateEditText = dateEditText;
        this.dateTextInputLayout = textInputLayout;
        String previousDate = this.dateEditText.getText().toString().trim();
        if (previousDate != null && previousDate.length() >= getResources().getInteger(R.integer.DOB_LENGTH)) {
            String[] splitedDate = previousDate.split("-");
            mSelectedDay = Integer.parseInt(splitedDate[0]);
            mSelectedMonth = Integer.parseInt(splitedDate[1]);

            mSelectedMonth--;

            if (mSelectedMonth < 0)
                mSelectedMonth = 0;

            mSelectedYear = Integer.parseInt(splitedDate[2]);
        } else {
            mSelectedYear = 0;
            mSelectedMonth = 0;
            mSelectedDay = 0;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID: {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepicker, pickerListener, mSelectedYear == 0 ?
                        mCurrentyear : mSelectedYear, mSelectedMonth == 0 ? mCurrentmonth : mSelectedMonth, mSelectedDay == 0 ? mCurrentday : mSelectedDay);

                if (!allowFutureDate) {
//                    http://stackoverflow.com/questions/33105398/last-day-of-datepickerdialog-is-not-selectable-after-setting-max-date
                    Calendar maxDate = Calendar.getInstance();
                    maxDate.set(Calendar.HOUR_OF_DAY, 23);
                    maxDate.set(Calendar.MINUTE, 59);
                    maxDate.set(Calendar.SECOND, 59);

                    datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis()/*new Date().getTime() + (1000 * 60 * 60 * 24)*/);
                } else {
                    datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                }
                return datePickerDialog;
            }
            case DATE_PICKER_ID1: {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepicker, pickerListener, mSelectedYear == 0 ?
                        mCurrentyear : mSelectedYear, mSelectedMonth == 0 ? mCurrentmonth : mSelectedMonth, mSelectedDay == 0 ? mCurrentday : mSelectedDay);

                if (!allowFutureDate) {
//                    http://stackoverflow.com/questions/33105398/last-day-of-datepickerdialog-is-not-selectable-after-setting-max-date
                    Calendar maxDate = Calendar.getInstance();
                    maxDate.set(Calendar.HOUR_OF_DAY, 23);
                    maxDate.set(Calendar.MINUTE, 59);
                    maxDate.set(Calendar.SECOND, 59);

                    datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis()/*new Date().getTime() + (1000 * 60 * 60 * 24)*/);
                } else {
                    datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                }
                return datePickerDialog;
            }
        }
        return null;
    }

    /**
     * pickerListener is used as date picker listner.
     */
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            if (!allowFutureDate) {
                String selectedDate = new StringBuilder().append((selectedDay < 10 ? ("0" + selectedDay) : selectedDay)).append("-")
                        .append(((selectedMonth + 1) < 10 ? ("0" + (selectedMonth + 1)) : (selectedMonth + 1))).append("-").append(selectedYear)
                        .append(" ").toString();

                if (!DateUtil.isDateAfter(Util.getDateString(new Date().getTime()), selectedDate)) {
                    dateEditText.setText(selectedDate);
                    if (dateTextInputLayout != null)
                        dateTextInputLayout.setError(null);
                } else {
                    AlertUtil.showAlert(BaseFragmentActivity.this, getResources().getString(R.string.date_selection), getResources().getString(R.string.future_date_can_not_be_selected), DialogIconCodes.DIALOG_FAILED.getIconCode());
                }
            } else {
                String selectedDate = new StringBuilder().append((selectedDay < 10 ? ("0" + selectedDay) : selectedDay)).append("-")
                        .append(((selectedMonth + 1) < 10 ? ("0" + (selectedMonth + 1)) : (selectedMonth + 1))).append("-").append(selectedYear)
                        .append(" ").toString();

                dateEditText.setText(selectedDate);
                if (dateTextInputLayout != null)
                    dateTextInputLayout.setError(null);
            }
        }
    };



    public long hoursSinceEpoch() {
        final Date now = new Date();
        final long nowMillis = now.getTime();
        return nowMillis / 1000 * 60 * 60;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void hideSoftKeyPad() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            if (!(e.getMessage() == null)) {

            }
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (mNavigationDrawerFragment != null) {
            mNavigationDrawerFragment.updateProfilePic();
            if (mNavigationDrawerFragment.isDrawerOpen()) {
                mNavigationDrawerFragment.closeDrawer(Gravity.START);
            }
        }*/

        String selectedLocaleCode = AccessPreferences.get(BaseFragmentActivity.this, ApplicationConstants.SELECTED_LOCALE_CODE, ApplicationConstants.DEFAULT_LOCALE_CODE);
        if(selectedLocaleCode != null){
            LocaleUtils.updateLocaleConfiguration(BaseFragmentActivity.this, LocaleUtils.transform(selectedLocaleCode));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        /*if (mNavigationDrawerFragment != null) {
            boolean drawerOpen = mNavigationDrawerFragment.isDrawerOpen();
            hideMenuItems(menu, !drawerOpen);
        }*/
        return super.onPrepareOptionsMenu(menu);
    }

    private void hideMenuItems(Menu menu, boolean visible) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(visible);
        }
    }

    public String getCountryDialCode() {
        String countryID;
        String countryDialCode = "";
        TelephonyManager telephonyMngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        countryID = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrCountryCode = getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < arrCountryCode.length; i++) {
            String[] arrDial = arrCountryCode[i].split(",");
            if (arrDial[1].trim().equals(countryID.trim())) {
                countryDialCode = arrDial[0];
                break;
            }
        }

        if (countryDialCode == null || countryDialCode.length() < 1) {
            countryDialCode = "91";
        }

        return countryDialCode;
    }

    public String getCountryID() {
        String countryID;
        TelephonyManager telephonyMngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        countryID = telephonyMngr.getSimCountryIso().toUpperCase();
        String[] arrCountryCode = getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < arrCountryCode.length; i++) {
            String[] arrDial = arrCountryCode[i].split(",");
            if (arrDial[1].trim().equals(countryID.trim())) {
                countryID = arrDial[1].trim().toLowerCase();
                break;
            }
        }
        if (countryID == null || countryID.length() < 1) {
            countryID = "in";
        }
        return countryID;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String selectedLocaleCode = AccessPreferences.get(BaseFragmentActivity.this, ApplicationConstants.SELECTED_LOCALE_CODE, ApplicationConstants.DEFAULT_LOCALE_CODE);
        if(selectedLocaleCode != null){
            LocaleUtils.updateLocaleConfiguration(BaseFragmentActivity.this, LocaleUtils.transform(selectedLocaleCode));
        }
    }

}
