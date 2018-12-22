package com.hapis.customer.ui.fragments.search.enterprise.enterprises;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.adapters.VerticalSpaceItemDecoration;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;

import java.lang.reflect.Field;
import java.util.List;

public class EnterpriseSearchDialogFragment extends DialogFragment {

    public static final String TAG = EnterpriseSearchDialogFragment.class.getName();

    public EnterpriseSearchDialogFragment() {
        // Required empty public constructor
    }

    private EnterpriseSearchCallBack mEnterpriseSearchCallBack;

    private BaseFragmentActivity baseFragmentActivity;

    private List<EnterpriseRequest> mSearchData;

    public void setScreenTitle(String screenTitle) {
        mScreenTitle = screenTitle;
    }

    private String mScreenTitle;

    public static EnterpriseSearchDialogFragment newInstance(BaseFragmentActivity baseFragmentActivity, EnterpriseSearchCallBack enterpriseSearchCallBack, List<EnterpriseRequest> searchData, String screenTitle) {
        EnterpriseSearchDialogFragment f = new EnterpriseSearchDialogFragment();
        f.setOnCallBackListener(baseFragmentActivity, enterpriseSearchCallBack);
        f.setSearchData(searchData);
        f.setScreenTitle(screenTitle);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return f;
    }

    public void setSearchData(List<EnterpriseRequest> searchData) {
        mSearchData = searchData;
    }

    public void setOnCallBackListener(BaseFragmentActivity mBaseFragmentActivity, EnterpriseSearchCallBack enterpriseSearchCallBack) {
        mEnterpriseSearchCallBack = enterpriseSearchCallBack;
        if (mBaseFragmentActivity != null)
            baseFragmentActivity = mBaseFragmentActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_common_search, container);
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

    private AppCompatTextView select_search_title_tv;
    private SearchView mSearch;
    private RecyclerView myRecycler;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        LinearLayout close_dialog_ll = v.findViewById(R.id.close_search_dialog_ll);
        close_dialog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        select_search_title_tv = v.findViewById(R.id.select_search_title_tv);
        select_search_title_tv.setText(mScreenTitle);
        mSearch = v.findViewById(R.id.mSearch);
        EditText searchEditText = (EditText)mSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.app_dark_color));
        searchEditText.setHintTextColor(getResources().getColor(R.color.textview_hint_color));
        myRecycler = v.findViewById(R.id.myRecycler);

        myRecycler.setLayoutManager(new LinearLayoutManager(baseFragmentActivity));
        myRecycler.setItemAnimator(new DefaultItemAnimator());
        myRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));

        //ADAPTER
        final EnterpriseSearchAdapter adapter = new EnterpriseSearchAdapter(baseFragmentActivity, mSearchData, new EnterpriseSearchAdapter.EnterpriseSelectedCallBack() {
            @Override
            public void onValueSelected(EnterpriseRequest selectedEnterpriseRequest) {
                mEnterpriseSearchCallBack.updateSelectedValue(selectedEnterpriseRequest);
                dismiss();
            }
        });
        myRecycler.setAdapter(adapter);

        //SEARCH
        mSearch.setIconifiedByDefault(true);
        mSearch.setIconified(false);
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query.trim());
                return false;
            }
        });

        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView mSearchCloseButton = (ImageView) searchField.get(mSearch);
            if (mSearchCloseButton != null) {
                mSearchCloseButton.setEnabled(false);
                mSearchCloseButton.setImageDrawable(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
