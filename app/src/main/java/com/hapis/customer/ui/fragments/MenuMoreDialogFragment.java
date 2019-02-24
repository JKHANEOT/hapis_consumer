package com.hapis.customer.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.LoadWebPageActivity;
import com.hapis.customer.ui.adapters.NavMenuDrawerAdapter;
import com.hapis.customer.ui.custom.DividerItemDecorationMenuMore;
import com.hapis.customer.ui.custom.RecyclerTouchListener;
import com.hapis.customer.ui.models.NavDrawerItem;
import com.hapis.customer.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MenuMoreDialogFragment extends DialogFragment {

    public static final String TAG = MenuMoreDialogFragment.class.getName();

//    https://github.com/codepath/android_guides/wiki/Using-DialogFragment

    private RecyclerView recyclerView;
    private NavMenuDrawerAdapter adapter;
    private static String[] titles = null;
    private static int[] icons = {
            R.drawable.ic_my_profile,
            R.drawable.ic_my_address,
            R.drawable.ic_my_orders,
            R.drawable.ic_payments_icon,
            /*R.drawable.ic_funding,*/
            R.drawable.ic_feedback,
            R.drawable.ic_help_support,
            R.drawable.ic_change_password,
            R.drawable.ic_change_language_wrapped,
            R.drawable.ic_logout
    };
    private AppCompatTextView terms_conditions_tv, privacy_policy_tv, version_no_tv;

    public MenuMoreDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public void setMenuMoreOnCallBackListener(MenuMoreOnCallBackListener menuMoreOnCallBackListener) {
        this.menuMoreOnCallBackListener = menuMoreOnCallBackListener;
        if (menuMoreOnCallBackListener != null && menuMoreOnCallBackListener instanceof BaseFragmentActivity)
            baseFragmentActivity = (BaseFragmentActivity) menuMoreOnCallBackListener;
    }

    private MenuMoreOnCallBackListener menuMoreOnCallBackListener;
    private BaseFragmentActivity baseFragmentActivity;

    public static MenuMoreDialogFragment newInstance(MenuMoreOnCallBackListener menuMoreOnCallBackListener) {
        MenuMoreDialogFragment frag = new MenuMoreDialogFragment();
        frag.setMenuMoreOnCallBackListener(menuMoreOnCallBackListener);
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return frag;
    }

    public interface MenuMoreOnCallBackListener {

        void onOptionSelected(final Dialog dialog, final int selectedIndex);

        void termsOrPoliciesToShow(final Dialog dialog, final String type);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_menu_more, container);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titles = getResources().getStringArray(R.array.menu_more_labels);

        recyclerView = view.findViewById(R.id.drawerList);

        adapter = new NavMenuDrawerAdapter(baseFragmentActivity, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(baseFragmentActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecorationMenuMore(baseFragmentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(baseFragmentActivity, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(menuMoreOnCallBackListener != null) {
                    menuMoreOnCallBackListener.onOptionSelected(getDialog(), position);
                    menuMoreOnCallBackListener = null;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        terms_conditions_tv = view.findViewById(R.id.terms_conditions_tv);
        terms_conditions_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuMoreOnCallBackListener.termsOrPoliciesToShow(getDialog(), LoadWebPageActivity.TERMS_CONDITIONS);
            }
        });
        privacy_policy_tv = view.findViewById(R.id.privacy_policy_tv);
        privacy_policy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuMoreOnCallBackListener.termsOrPoliciesToShow(getDialog(), LoadWebPageActivity.PRIVACY_POLICIES);
            }
        });
        version_no_tv = view.findViewById(R.id.version_no_tv);
        version_no_tv.setText("V " + Util.getAppVersionFor(baseFragmentActivity));
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setIcon(icons[i]);
            navItem.setShowNotify(false);
            data.add(navItem);
        }

        return data;
    }
}
