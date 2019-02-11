package com.hapis.customer.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.custom.BottomNavigationViewHelper;
import com.hapis.customer.ui.custom.badges.Badge;
import com.hapis.customer.ui.custom.badges.BadgeView;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.fragments.ExpiredSchedulesSchedulesFrag;
import com.hapis.customer.ui.fragments.MenuMoreDialogFragment;
import com.hapis.customer.ui.fragments.UpComingSchedulesFrag;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.DashboardView;
import com.hapis.customer.ui.view.DashboardViewModal;
import com.hapis.customer.utils.Util;

public class DashboardActivity extends BaseFragmentActivity<DashboardViewModal> implements DashboardView, MenuMoreDialogFragment.MenuMoreOnCallBackListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setUpNavigationDrawer(getResources().getString(R.string.dashboard), null, true, null);
        initViews();
    }

    private Menu bottomMenu;
    private MenuItem navHome, navDk, navInbox, navMoreSettings;

    private void initViews() {
        /***************************** Bottom Navigation Bar initialization *****************************/
        bottomNavigationView = findViewById(R.id.bottom_navigation_container);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomMenu = bottomNavigationView.getMenu();
        navHome = bottomMenu.getItem(0);
        navDk = bottomMenu.getItem(1);
        navInbox = bottomMenu.getItem(2);
        navMoreSettings = bottomMenu.getItem(3);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomNavigationView.setSelectedItemId(navDk.getItemId());
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < bottomNavigationMenuView.getChildCount(); i++) {
            final View iconView = bottomNavigationMenuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);

            if (i == 2) {
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, displayMetrics);
            }
            iconView.setLayoutParams(layoutParams);
        }

        loadFragment(new UpComingSchedulesFrag());
    }

    Animation bellAnimation;

    private void setInboxBadgeCount(int badgeCount) {
        if (bottomNavigationMenuView != null) {
            View v = bottomNavigationMenuView.getChildAt(2); // number of menu from left
            Badge badgeView = new BadgeView(this).bindTarget(v);
            badgeView.hide(true);
            badgeView.hide(false);
            badgeView.setBadgeNumber(badgeCount);
            badgeView.setBadgeTextSize(10, true);
            badgeView.setGravityOffset(25, 0, true);
            if (badgeCount > 0) {
                final View iconView = bottomNavigationMenuView.getChildAt(2).findViewById(android.support.design.R.id.icon);
                bellAnimation = AnimationUtils.loadAnimation(this, R.anim.bell_animation);
                iconView.startAnimation(bellAnimation);
                bellAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iconView.startAnimation(bellAnimation);
                            }
                        }, 1500);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                if (bellAnimation != null) {
                    bellAnimation.cancel();
                }
            }
        }
    }

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationMenuView bottomNavigationMenuView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_upcoming: {
                    loadFragment(new UpComingSchedulesFrag());
                    return true;
                }
                case R.id.navigation_history: {
                    loadFragment(new ExpiredSchedulesSchedulesFrag());
                    return true;
                }
                case R.id.navigation_inbox: {
                    /*Intent intentInbox = new Intent(DashBoardActivity.this, PushNotificationsActivity.class);
                    intentInbox.putExtra(PushNotificationsActivity.TAB_TO_OPEN, PushNotificationsActivity.NOTIFICATIONS_TAB);
                    startActivity(intentInbox);*/
                    return true;
                }
                case R.id.navigation_more_settings: {
                    FragmentManager fm = getSupportFragmentManager();
                    MenuMoreDialogFragment alertDialog = MenuMoreDialogFragment.newInstance(DashboardActivity.this);
                    alertDialog.show(fm, "fragment_alert");
                    return true;
                }
            }
            return false;
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.exit_toast_txt), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onOptionSelected(Dialog dialog, int selectedIndex) {
        dialog.dismiss();
        if(selectedIndex == 0){
            Intent intent = new Intent(DashboardActivity.this, UserProfileActivity.class);
            startActivity(intent);
        }else if(selectedIndex == 8){
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:{
                            dialog.dismiss();
                            mViewModal.proceedWithLogout();
                            break;
                        }
                        case R.id.negative_btn:{
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            };
            showError(getResources().getString(R.string.do_you_wish_to_logout), onClickListener, getResources().getString(R.string.logout), getResources().getString(R.string.cancel), DialogIconCodes.DIALOG_LOGOUT.getIconCode());
        }
    }

    @Override
    public void termsOrPoliciesToShow(Dialog dialog, String type) {

    }

    private Menu menuThis;
    private MenuItem refreshAction;
    private int messageCenterBadgeCount = 0;

    @Override
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl, String status) {
        AlertUtil.showAlert(DashboardActivity.this, getResources().getString(R.string.app_name), errorMsg, positiveLbl, negativeLbl, onClickListener, status);
    }

    @Override
    public void logoutStatus(boolean isLoggedOut) {
        if(isLoggedOut){
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:{
                            dialog.dismiss();
                            showLoginScreen();
                            break;
                        }
                    }
                }
            };
            showError(getResources().getString(R.string.user_logout_message), onClickListener, getResources().getString(R.string.logged_out), null, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
        }else{
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:{
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            };
            showError(getResources().getString(R.string.user_logout_not_possible_please_try_later), onClickListener, getResources().getString(R.string.ok_label), null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    private void showLoginScreen() {

        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class getViewModalClass() {
        return DashboardViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    /*private void updateNotificationBadge() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    messageCenterBadgeCount = 0*//*new NotificationAction(null).getUnreadNotificationsCount()*//*;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (menuThis != null) {
                    if (messageCenterBadgeCount == 0) {
                        ActionItemBadge.update(menuThis.findItem(R.id.action_notifications), null);
                    } else {
                        changeActionItemBadge(ActionItemBadge.BadgeStyles.RED, messageCenterBadgeCount);
                    }
                }
                // BottomNavigationView - Set inbox Item Badge count
                //  if (messageCenterBadgeCount > 0)
                setInboxBadgeCount(messageCenterBadgeCount);
            }
        });

    }

    private void changeActionItemBadge(final ActionItemBadge.BadgeStyles styles, final int badgeCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActionItemBadge.update(DashboardActivity.this, menuThis.findItem(R.id.action_notifications),
                        UIUtil.getCompatDrawable(DashboardActivity.this, R.mipmap.ic_action_notification_icon),
                        styles, NumberUtils.formatNumber(badgeCount));
            }
        });
    }*/

    private AppCompatTextView wallet_account_balance_tv;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        final MenuItem alertMenuItem = menu.findItem(R.id.action_wallet);
        RelativeLayout rootView = (RelativeLayout) alertMenuItem.getActionView();
        wallet_account_balance_tv = (AppCompatTextView) rootView.findViewById(R.id.wallet_account_balance_tv);

        wallet_account_balance_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0.0));
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToUserWallet();
            }
        });

        return true;
    }

    private void moveToUserWallet(){
        Intent intent = new Intent(DashboardActivity.this, UserWalletActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_wallet: {
                moveToUserWallet();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateAvailableBalance(Double balance) {
        if(balance != null && balance.doubleValue() > 0){
            wallet_account_balance_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(balance.doubleValue()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mViewModal.getAvailableWalletBalance();
    }
}

