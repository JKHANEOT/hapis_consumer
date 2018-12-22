
package com.hapis.customer.ui.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.Holder;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.custom.nhancetoast.StyleableToast;

/**
 * @author JKHAN
 */

public class AlertUtil {

    public static final long DIALOG_FADE_AWAY_TIME = 2000;
    private static Handler handler;
    private static DialogPlus dialog;

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     */
    public static void showAlert(Context context, int titleId, int messageId, String status) {

        OnClickListener onClickListener = new OnClickListener() {
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

        showAlertDialogFragment(context, context.getResources().getString(titleId), context.getResources().getString(messageId), null, null, onClickListener, status);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param message
     */
    public static void showAlert(Context context, int titleId, String message, String status) {

        OnClickListener onClickListener = new OnClickListener() {
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

        showAlertDialogFragment(context, context.getResources().getString(titleId), message, null, null, onClickListener, status);
    }

    public static void showAlert(Context context, String title, String message, String status) {
        if (context != null) {
            OnClickListener onClickListener = new OnClickListener() {
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

            showAlertDialogFragment(context, title, message, null, null, onClickListener, status);
        }
    }

    public static void showAlert(Context context, String title, String message, String status, OnClickListener onClickListener) {

        showAlertDialogFragment(context, title, message, null, null, onClickListener, status);
    }

    public static void showAlert(Context context, String title, String message, String status, String positiveString, OnClickListener onClickListener) {

        showAlertDialogFragment(context, title, message, null, null, onClickListener, status);
    }

    public static void showAlert(Context context, String title, String message, String status, boolean shouldFadeAway) {
        if (context != null) {
            OnClickListener onClickListener = new OnClickListener() {
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

            showAlertDialogFragment(context, title, message, null, null, onClickListener, status, shouldFadeAway);
        }
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, int titleId, int messageId, String positiveButtontxt, OnClickListener onClickListener, String status) {

        showAlertDialogFragment(context, context.getResources().getString(titleId), context.getResources().getString(messageId),
                positiveButtontxt.toString(), null, onClickListener, status);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, String title, String message,
                                 String positiveButtontxt, OnClickListener onClickListener, String status
    ) {
        showAlertDialogFragment(context, title, message, positiveButtontxt.toString(), null, onClickListener, status);
    }

    /**
     * Fade Away after few secs options
     * Show Alert Dialog
     *
     * @param context
     * @param title
     * @param message
     * @param positiveButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, String title, String message,
                                 String positiveButtontxt, OnClickListener onClickListener, String status, boolean shouldFadeAway) {
        showAlertDialogFragment(context, title, message, positiveButtontxt.toString(), null, onClickListener, status, shouldFadeAway);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param negativeButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, int titleId, int messageId,
                                 String positiveButtontxt, String negativeButtontxt,
                                 OnClickListener onClickListener, String status) {
        showAlertDialogFragment(context, context.getResources().getString(titleId), context.getResources().getString(messageId),
                positiveButtontxt.toString(), negativeButtontxt.toString(), onClickListener, status);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param messageId
     * @param positiveButtontxt
     * @param negativeButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, String titleId, String messageId,
                                 String positiveButtontxt, String negativeButtontxt,
                                 OnClickListener onClickListener, String status) {
        showAlertDialogFragment(context, titleId, messageId, positiveButtontxt,
                negativeButtontxt, onClickListener, status);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param message
     * @param positiveButtontxt
     * @param negativeButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, int titleId, String message,
                                 String positiveButtontxt,
                                 String negativeButtontxt, OnClickListener onClickListener, String status) {
        showAlertDialogFragment(context, context.getResources().getString(titleId), message, positiveButtontxt, negativeButtontxt, onClickListener, status);
    }

    /**
     * Show Alert Dialog
     *
     * @param context
     * @param titleId
     * @param message
     * @param positiveButtontxt
     * @param onClickListener
     */
    public static void showAlert(Context context, int titleId, String message,
                                 String positiveButtontxt, OnClickListener onClickListener, String status) {
        showAlertDialogFragment(context, context.getResources().getString(titleId), message,
                positiveButtontxt, null, onClickListener, status);
    }

    public static void showAlert(Context context, int titleId, String message,
                                 String positiveButtontxt, OnClickListener onClickListener,
                                 String status, boolean shouldFadeAway) {
        showAlertDialogFragment(context, context.getResources().getString(titleId), message,
                positiveButtontxt, null, onClickListener, status, shouldFadeAway);
    }

    private static void showAlertDialogFragment(final Context context, final String title, final String message,
                                                final String positiveString, final String negativeString,
                                                final OnClickListener clickListener, final String dialogType) {
        if (handler == null)
            handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            public void run() {

                View view = null;

                String selectedLocaleCode = AccessPreferences.get(context, ApplicationConstants.SELECTED_LOCALE_CODE, ApplicationConstants.DEFAULT_LOCALE_CODE);
                if(selectedLocaleCode != null){
                    LocaleUtils.updateLocaleConfiguration(context, LocaleUtils.transform(selectedLocaleCode));
                }

                if (negativeString == null)
                    view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_fragment, null);
                else
                    view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_fragment_two_buttons, null);

//                TypefaceHelper.getInstance().setTypeface(view, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
                if (dialogType != null && dialogType.length() > 0)
                    ((ImageView) view.findViewById(R.id.image_view_status)).setImageDrawable(context.getResources().getDrawable(DialogIconCodes.getIconByCode(dialogType)));
                /*if (status) {
                    if (positiveString != null && positiveString.equalsIgnoreCase("Logout"))
                        ((ImageView) view.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.logout_alert_dialog_icon);
                    else
                        ((ImageView) view.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.alert_correct);
                } else
                    ((ImageView) view.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.alert_wrong);*/

                ((TextView) view.findViewById(R.id.alert_title)).setText(title);
                ((TextView) view.findViewById(R.id.alert_message)).setText(message);
                if (positiveString != null)
                    ((Button) view.findViewById(R.id.positive_btn)).setText(positiveString);
                else
                    ((Button) view.findViewById(R.id.positive_btn)).setText(context.getResources().getString(R.string.ok));

                if (negativeString != null)
                    ((Button) view.findViewById(R.id.negative_btn)).setText(negativeString);

                Holder viewHolder = new ViewHolder(view);
                dialog = DialogPlus.newDialog(context)
                        .setContentHolder(viewHolder)
                        .setOnClickListener(clickListener)
                        .setGravity(Gravity.CENTER)
                        .setBackgroundColorResourceId(android.R.color.transparent)
                        .setCancelable(false)
                        .create(false);
                dialog.show();
            }
        });
    }

    private static void showAlertDialogFragment(final Context context, final String title, final String message,
                                                final String positiveString, final String negativeString,
                                                final OnClickListener clickListener, final String dialogType,
                                                final boolean shouldFadeAway) {
        if (context != null) {
            if (handler == null)
                handler = new Handler(Looper.getMainLooper());

            handler.post(new Runnable() {

                public void run() {

                    View view = null;

                    if (negativeString == null)
                        view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_fragment, null);
                    else
                        view = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_fragment_two_buttons, null);

//                TypefaceHelper.getInstance().setTypeface(view, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
                    ((ImageView) view.findViewById(R.id.image_view_status)).setImageDrawable(context.getResources().getDrawable(DialogIconCodes.getIconByCode(dialogType)));

                /*if (status)
                    ((ImageView) view.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.alert_correct);
                else
                    ((ImageView) view.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.alert_wrong);*/

                    ((AppCompatTextView) view.findViewById(R.id.alert_title)).setText(title);
                    ((AppCompatTextView) view.findViewById(R.id.alert_message)).setText(message);
                    if (positiveString != null)
                        ((Button) view.findViewById(R.id.positive_btn)).setText(positiveString);
                    else
                        ((Button) view.findViewById(R.id.positive_btn)).setText(context.getResources().getString(R.string.ok));

                    if (negativeString != null)
                        ((Button) view.findViewById(R.id.negative_btn)).setText(negativeString);

                    Holder viewHolder = new ViewHolder(view);
                    dialog = DialogPlus.newDialog(context)
                            .setContentHolder(viewHolder)
                            .setOnClickListener(clickListener)
                            .setGravity(Gravity.CENTER)
                            .setInAnimation(R.anim.fade_in_center)
                            .setOutAnimation(R.anim.fade_out_center)
                            .setBackgroundColorResourceId(android.R.color.transparent)
                            .setCancelable(false)
                            .create(false);
                    dialog.show();

                    if (shouldFadeAway) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, DIALOG_FADE_AWAY_TIME);
                    }
                }
            });
        }
    }

    public static void showToast(String TAG, String message, Context context) {
        Toast.makeText(context, TAG + " " + message, Toast.LENGTH_SHORT).show();
    }

    public static void dismissCurrentVisibleDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showCustomToast(Context mContext, String msg, int bgColor) {
        StyleableToast styleableToast = new StyleableToast
                .Builder(mContext)
                .text(msg)
                .duration(Toast.LENGTH_LONG)
                .textColor(Color.WHITE)
                .typeface(ResourcesCompat.getFont(mContext,R.font.opensans_regular))
                .backgroundColor(bgColor)
                .build();

        if (styleableToast != null) {
            styleableToast.show();
            styleableToast = null;
        }
    }
}

