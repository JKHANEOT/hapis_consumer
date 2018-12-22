package com.hapis.customer.ui.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hapis.customer.R;

/**
 * Created by Javeed on 12/20/2017.
 */

public class DeviceScreenResolutionUtil {

    public static final String TAG = DeviceScreenResolutionUtil.class.getName();

    public static final int TOTAL_DRAWABLE_AREA = 1;
    public static final int FLOATING_BUTTON_AREA = 2;
    public static final int AVAILABLE_RECYCLER_AREA = 3;
    public static final int FIRST_RECYCLER_AREA = 4;
    public static final int SECOND_RECYCLER_AREA = 5;
    public static final int FLOATING_BUTTON_MARGIN_AREA = 6;

    public static final int LABEL_TV_LEFT_MARGIN_AREA = 7;
    public static final int LABEL_TV_TOP_MARGIN_AREA = 8;
    public static final int SEPARATOR_LEFT_MARGIN_AREA = 9;
    public static final int SEPARATOR_RIGHT_MARGIN_AREA = 10;
    public static final int NO_DATA_TV_LEFT_MARGIN_AREA = 11;
    public static final int NO_DATA_TV_TOP_MARGIN_AREA = 12;
    public static final int RECYCLER_VIEW_TOP_BOTTOM_MARGIN_AREA = 13;
    public static final int RECYCLER_VIEW_RIGHT_LEFT_MARGIN_AREA = 14;

    public static final int RECYCLER_VIEW_ITEM_WIDTH_AREA = 15;
    public static final int RECYCLER_VIEW_ITEM_HEIGHT_AREA = 16;
    public static final int RECYCLER_VIEW_ITEM_SPACING_WIDTH_AREA = 17;

    public static final float total_screen_percentage = 100f;
    public static final float notification_bar_percentage = 2.5f;
    public static final float tool_bar_percentage = 9f;
    public static final float softkey_bar_percentage = 8.5f;
    public static final float total_drawable_area_percentage = 82f;
    public static final float floating_button_percentage = 11.25f;
    public static final float floating_button_margin_percentage = 2.5f;
//    public static final float total_multiple_recycler_view_area_percentage = 68.25f;
    public static final float total_multiple_recycler_view_area_percentage = 74f;
    //    public static final float total_individual_recycler_view_area_percentage = 34.125f;
    public static final float total_individual_recycler_view_area_percentage = 37f;

    public static final float label_tv_left_margin_area_percentage = 4.5f;
    public static final float no_data_tv_left_margin_area_percentage = 4.5f;
    public static final float separator_left_margin_area_percentage = 4.5f;
    public static final float separator_right_margin_area_percentage = 4.5f;
    public static final float label_tv_top_margin_area_percentage = 7.5f;
    public static final float no_data_tv_top_margin_area_percentage = 7.5f;

    public static final float recycler_view_left_right_margin_area_percentage = 4.5f;
    public static final float recycler_view_top_bottom_margin_area_percentage = 5f;

    public static final float recycler_view_item_width_area = 35f;
    public static final float recycler_view_item_spacing_width_area = 5.5f;
    public static final float recycler_view_item_height_area = 68.5f;

    public static int getAssignedHeightInDp(int areaToCalculate, Context context){
        int assignedHeightInDp = 0;
        float assignedHeightInPixels = 0;
        float percentage = 0;

        int[] screenResolutionInPixels = getScreenResolutionInPixels(context);
        int screenWidthInPixels = screenResolutionInPixels[0];
//        LOG.d(TAG, "screenWidthInPixels: "+screenWidthInPixels);
        int screenHeightInPixels = screenResolutionInPixels[1];
//        LOG.d(TAG, "screenHeightInPixels: "+screenHeightInPixels);

        float screenDensity = getScreenDensity(context);
//        LOG.d(TAG, "screenDensity: "+screenDensity);
        String screenDensityName = getDensityNameByDensity(context, screenDensity);
//        LOG.d(TAG, "screenDensityName: "+screenDensityName);

        switch (areaToCalculate){
            case TOTAL_DRAWABLE_AREA:{
                percentage = total_drawable_area_percentage;
//                LOG.d(TAG, "TOTAL_DRAWABLE_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_AREA:{
                percentage = floating_button_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_MARGIN_AREA:{
                percentage = floating_button_margin_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case AVAILABLE_RECYCLER_AREA:{
                percentage = total_multiple_recycler_view_area_percentage;
//                LOG.d(TAG, "AVAILABLE_RECYCLER_AREA percentage: "+percentage);
                break;
            }
            case FIRST_RECYCLER_AREA:
            case SECOND_RECYCLER_AREA:{
                percentage = total_individual_recycler_view_area_percentage;
//                LOG.d(TAG, "FIRST_RECYCLER_AREA|SECOND_RECYCLER_AREA percentage: "+percentage);
                break;
            }
        }

        assignedHeightInPixels = ((screenHeightInPixels/100)*percentage);
//        LOG.d(TAG, "assignedHeightInPixels: "+assignedHeightInPixels);
        assignedHeightInDp = Math.round(assignedHeightInPixels/screenDensity);
//        LOG.d(TAG, "assignedHeightInDp: "+assignedHeightInDp);

        return assignedHeightInDp;
    }

    public static int getAssignedHeightInPixels(int areaToCalculate, Context context){
        int assignedHeightInPixels = 0;
        float percentage = 0;

        int[] screenResolutionInPixels = getScreenResolutionInPixels(context);
        int screenWidthInPixels = screenResolutionInPixels[0];
//        LOG.d(TAG, "screenWidthInPixels: "+screenWidthInPixels);
        int screenHeightInPixels = screenResolutionInPixels[1];
//        LOG.d(TAG, "screenHeightInPixels: "+screenHeightInPixels);

        float screenDensity = getScreenDensity(context);
//        LOG.d(TAG, "screenDensity: "+screenDensity);
        String screenDensityName = getDensityNameByDensity(context, screenDensity);
//        LOG.d(TAG, "screenDensityName: "+screenDensityName);

        switch (areaToCalculate){
            case TOTAL_DRAWABLE_AREA:{
                percentage = total_drawable_area_percentage;
//                LOG.d(TAG, "TOTAL_DRAWABLE_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_AREA:{
                percentage = floating_button_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_MARGIN_AREA:{
                percentage = floating_button_margin_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case AVAILABLE_RECYCLER_AREA:{
                percentage = total_multiple_recycler_view_area_percentage;
//                LOG.d(TAG, "AVAILABLE_RECYCLER_AREA percentage: "+percentage);
                break;
            }
            case FIRST_RECYCLER_AREA:
            case SECOND_RECYCLER_AREA:{
                percentage = total_individual_recycler_view_area_percentage;
//                LOG.d(TAG, "FIRST_RECYCLER_AREA|SECOND_RECYCLER_AREA percentage: "+percentage);
                break;
            }
            case LABEL_TV_TOP_MARGIN_AREA:{
                percentage = label_tv_top_margin_area_percentage;
//                LOG.d(TAG, "LABEL_TV_TOP_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case NO_DATA_TV_TOP_MARGIN_AREA:{
                percentage = no_data_tv_top_margin_area_percentage;
//                LOG.d(TAG, "NO_DATA_TV_TOP_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case RECYCLER_VIEW_TOP_BOTTOM_MARGIN_AREA:{
                percentage = recycler_view_top_bottom_margin_area_percentage;
//                LOG.d(TAG, "RECYCLER_VIEW_TOP_BOTTOM_MARGIN_AREA percentage: "+percentage);
                break;
            }
        }

        float floatVal = screenHeightInPixels/100;

        assignedHeightInPixels = Math.round(floatVal*percentage);
//        LOG.d(TAG, "assignedHeightInPixels: "+assignedHeightInPixels);

        return assignedHeightInPixels;
    }

    public static int getAssignedHeightInPixels(int areaToCalculate, Context context, float availableHeight){
        int assignedHeightInPixels = 0;
        float percentage = 0;

//        LOG.d(TAG, "availableHeight: "+availableHeight);

        float screenDensity = getScreenDensity(context);
//        LOG.d(TAG, "screenDensity: "+screenDensity);
        String screenDensityName = getDensityNameByDensity(context, screenDensity);
//        LOG.d(TAG, "screenDensityName: "+screenDensityName);

        switch (areaToCalculate){
            case TOTAL_DRAWABLE_AREA:{
                percentage = total_drawable_area_percentage;
//                LOG.d(TAG, "TOTAL_DRAWABLE_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_AREA:{
                percentage = floating_button_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_AREA percentage: "+percentage);
                break;
            }
            case FLOATING_BUTTON_MARGIN_AREA:{
                percentage = floating_button_margin_percentage;
//                LOG.d(TAG, "FLOATING_BUTTON_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case AVAILABLE_RECYCLER_AREA:{
                percentage = total_multiple_recycler_view_area_percentage;
//                LOG.d(TAG, "AVAILABLE_RECYCLER_AREA percentage: "+percentage);
                break;
            }
            case FIRST_RECYCLER_AREA:
            case SECOND_RECYCLER_AREA:{
                percentage = total_individual_recycler_view_area_percentage;
//                LOG.d(TAG, "FIRST_RECYCLER_AREA|SECOND_RECYCLER_AREA percentage: "+percentage);
                break;
            }
            case LABEL_TV_TOP_MARGIN_AREA:{
                percentage = label_tv_top_margin_area_percentage;
//                LOG.d(TAG, "LABEL_TV_TOP_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case NO_DATA_TV_TOP_MARGIN_AREA:{
                percentage = no_data_tv_top_margin_area_percentage;
//                LOG.d(TAG, "NO_DATA_TV_TOP_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case RECYCLER_VIEW_TOP_BOTTOM_MARGIN_AREA:{
                percentage = recycler_view_top_bottom_margin_area_percentage;
//                LOG.d(TAG, "RECYCLER_VIEW_TOP_BOTTOM_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case RECYCLER_VIEW_ITEM_HEIGHT_AREA:{
                percentage = recycler_view_item_height_area;
//                LOG.d(TAG, "RECYCLER_VIEW_ITEM_HEIGHT_AREA percentage: "+percentage);
                break;
            }
        }

        float floatVal = availableHeight/100;

        assignedHeightInPixels = Math.round(floatVal*percentage);
//        LOG.d(TAG, "assignedHeightInPixels: "+assignedHeightInPixels);

        return assignedHeightInPixels;
    }

    public static int getAssignedWidthInPixels(int areaToCalculate, Context context){
        int assignedWidthInPixels = 0;
        float percentage = 0;

        int[] screenResolutionInPixels = getScreenResolutionInPixels(context);
        int screenWidthInPixels = screenResolutionInPixels[0];
//        LOG.d(TAG, "screenWidthInPixels: "+screenWidthInPixels);
        int screenHeightInPixels = screenResolutionInPixels[1];
//        LOG.d(TAG, "screenHeightInPixels: "+screenHeightInPixels);

        float screenDensity = getScreenDensity(context);
//        LOG.d(TAG, "screenDensity: "+screenDensity);
        String screenDensityName = getDensityNameByDensity(context, screenDensity);
//        LOG.d(TAG, "screenDensityName: "+screenDensityName);

        switch (areaToCalculate){
            case LABEL_TV_LEFT_MARGIN_AREA:{
                percentage = label_tv_left_margin_area_percentage;
//                LOG.d(TAG, "LABEL_TV_LEFT_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case NO_DATA_TV_LEFT_MARGIN_AREA:{
                percentage = no_data_tv_left_margin_area_percentage;
//                LOG.d(TAG, "NO_DATA_TV_LEFT_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case SEPARATOR_LEFT_MARGIN_AREA:{
                percentage = separator_left_margin_area_percentage;
//                LOG.d(TAG, "SEPARATOR_LEFT_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case SEPARATOR_RIGHT_MARGIN_AREA:{
                percentage = separator_right_margin_area_percentage;
//                LOG.d(TAG, "SEPARATOR_RIGHT_MARGIN_AREA percentage: "+percentage);
                break;
            }
            case RECYCLER_VIEW_RIGHT_LEFT_MARGIN_AREA:{
                percentage = recycler_view_left_right_margin_area_percentage;
//                LOG.d(TAG, "RECYCLER_VIEW_RIGHT_LEFT_MARGIN_AREA percentage: "+percentage);
                break;
            }
        }

        float floatVal = screenWidthInPixels/100;

        assignedWidthInPixels = Math.round(floatVal*percentage);
//        LOG.d(TAG, "assignedWidthInPixels: "+assignedWidthInPixels);

        return assignedWidthInPixels;
    }

    public static int getAssignedWidthInPixels(int areaToCalculate, Context context, float availableWidth){
        int assignedWidthInPixels = 0;
        float percentage = 0;

//        LOG.d(TAG, "availableWidth: "+availableWidth);

        float screenDensity = getScreenDensity(context);
//        LOG.d(TAG, "screenDensity: "+screenDensity);
        String screenDensityName = getDensityNameByDensity(context, screenDensity);
//        LOG.d(TAG, "screenDensityName: "+screenDensityName);

        switch (areaToCalculate){
            case RECYCLER_VIEW_ITEM_WIDTH_AREA:{
                percentage = recycler_view_item_width_area;
//                LOG.d(TAG, "RECYCLER_VIEW_ITEM_WIDTH_AREA percentage: "+percentage);
                break;
            }
            case RECYCLER_VIEW_ITEM_SPACING_WIDTH_AREA:{
                percentage = recycler_view_item_spacing_width_area;
//                LOG.d(TAG, "RECYCLER_VIEW_ITEM_SPACING_WIDTH_AREA percentage: "+percentage);
                break;
            }
        }

        float floatVal = availableWidth/100;

        assignedWidthInPixels = Math.round(floatVal*percentage);
//        LOG.d(TAG, "assignedWidthInPixels: "+assignedWidthInPixels);

        return assignedWidthInPixels;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int[] getScreenResolutionInPixels(Context context){
        int[] screenResolutionInPixels = new int[2];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        screenResolutionInPixels[0] = displayMetrics.widthPixels;
        screenResolutionInPixels[1] = displayMetrics.heightPixels;

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.widthPixels: "+displayMetrics.widthPixels );
//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.widthPixels: "+displayMetrics.heightPixels );

        return screenResolutionInPixels;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int[] getScreenResolutionInDp(Context context){
        int[] screenResolutionInDp = new int[2];

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        screenResolutionInDp[0] = Math.round(displayMetrics.widthPixels/displayMetrics.density);
        screenResolutionInDp[1] = Math.round(displayMetrics.heightPixels/displayMetrics.density);

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.width DP: "+screenResolutionInDp[0]);
//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.height DP: "+screenResolutionInDp[1]);

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.density: "+displayMetrics.density);
//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.densityDpi: "+displayMetrics.densityDpi);
//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.scaledDensity: "+displayMetrics.scaledDensity);

        return screenResolutionInDp;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenDensityInDpi(Context context){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.densityDpi: "+displayMetrics.densityDpi);

        return displayMetrics.densityDpi;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static float getScreenDensity(Context context){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "displayMetrics.density: "+displayMetrics.density);

        return displayMetrics.density;
    }

    public static String getDensityNameByDensity(Context context) {
        String densityName = "ldpi";
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            densityName = "xxxhdpi";
        }
        if (density >= 3.0) {
            densityName = "xxhdpi";
        }
        if (density >= 2.0) {
            densityName = "xhdpi";
        }
        if (density >= 1.5) {
            densityName = "hdpi";
        }
        if (density >= 1.0) {
            densityName = "mdpi";
        }

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "densityName by density: "+densityName);

        return densityName;
    }

    public static String getDensityNameByDensity(Context context, float density) {
        String densityName = "ldpi";
        if (density == 4.0) {
            densityName = "xxxhdpi";
        }
        if (density == 3.0) {
            densityName = "xxhdpi";
        }
        if (density == 2.0) {
            densityName = "xhdpi";
        }
        if (density == 1.5) {
            densityName = "hdpi";
        }
        if (density == 1.0) {
            densityName = "mdpi";
        }

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "densityName by density: "+densityName);

        return densityName;
    }

    public static String getDensityNameByDensityDpi(Context context) {
        String densityName = "ND";
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        if (densityDpi > 480 || densityDpi <= 640) {
            densityName = "xxxhdpi";
        }
        if (densityDpi > 320 || densityDpi <= 480) {
            densityName = "xxhdpi";
        }
        if (densityDpi > 240 || densityDpi <= 320) {
            densityName = "xhdpi";
        }
        if (densityDpi > 213 || densityDpi <= 240) {
            densityName = "hdpi";
        }
        if (densityDpi > 160 || densityDpi <= 213) {
            densityName = "tvdpi";
        }
        if (densityDpi > 120 || densityDpi <= 160) {
            densityName = "mdpi";
        }
        if (densityDpi > 0 || densityDpi <= 120) {
            densityName = "ldpi";
        }

//        LOG.d(DeviceScreenResolutionUtil.class.getName(), "densityName by densityDpi: "+densityName);

        return densityName;
    }

    public static int getValueInDp(int pixels, Context context){
        int calulatedDp = 0;

        float density = getScreenDensity(context);
        calulatedDp = Math.round(pixels/density);

//        LOG.d(TAG, "calulatedDp: "+calulatedDp);

        return calulatedDp;
    }

    public static int getValueInPx(double dpi, Context context){
        long calulatedDp = 0;

        float density = getScreenDensity(context);
        calulatedDp = Math.round(dpi*density);

//        LOG.d(TAG, "calulatedDp: "+calulatedDp);

        return (int)calulatedDp;
    }

    public static int getStatusBarHeight(Context context) {
        int height;

        Resources myResources = context.getResources();
        int idStatusBarHeight = myResources.getIdentifier(
                "status_bar_height", "dimen", "android");
        if (idStatusBarHeight > 0) {
            height = context.getResources().getDimensionPixelSize(idStatusBarHeight);
//            LOG.d(TAG, "Status Bar Height = : "+height);
        }else{
            height = 0;
//            LOG.d(TAG, "Resources NOT found"+height);
        }

        return height;
    }

    @SuppressLint("NewApi")
    public static int getSoftButtonsBarHeight(AppCompatActivity context) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
//            LOG.d(TAG, "usableHeight: "+usableHeight);
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
//            LOG.d(TAG, "realHeight: "+realHeight);
            if (realHeight > usableHeight) {
                int softButtonsBarHeight = realHeight - usableHeight;
//                LOG.d(TAG, "softButtonsBarHeight: "+softButtonsBarHeight);
                return softButtonsBarHeight;
            }
            else {
//                LOG.d(TAG, "No softButtonsBar");
                return 0;
            }
        }
        return 0;
    }

    public static int getToolBarHeight(Context context) {
        int[] attrs = new int[] {R.attr.actionBarSize};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();

//        LOG.d(TAG, "toolBarHeight: "+toolBarHeight);

        return toolBarHeight;
    }
}
