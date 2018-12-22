package com.hapis.customer.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.adapters.LocationSearchAdapter;
import com.hapis.customer.ui.adapters.VerticalSpaceItemDecoration;
import com.hapis.customer.ui.callback.SelectPreferredLocationCallBack;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectPreferredLocationDialogFragment extends DialogFragment {

    public static final String TAG = SelectPreferredLocationDialogFragment.class.getName();

    public SelectPreferredLocationDialogFragment() {
        // Required empty public constructor
    }

    private SelectPreferredLocationCallBack mPreferredLocationCallBack;

    private BaseFragmentActivity baseFragmentActivity;

    private Map<String, Map<String, List<String>>> mLocations;

    public static SelectPreferredLocationDialogFragment newInstance(SelectPreferredLocationCallBack selectDateAndTimeSlotCallBack, Map<String, Map<String, List<String>>> location, List<String> selectedPreferredLocation) {
        SelectPreferredLocationDialogFragment f = new SelectPreferredLocationDialogFragment();
        f.setOnCallBackListener(selectDateAndTimeSlotCallBack);
        f.setSelectedPreferredLocation(selectedPreferredLocation);
        f.setLocations(location);
        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return f;
    }

    public void setLocations(Map<String, Map<String, List<String>>> mLocations) {
        this.mLocations = mLocations;
    }

    public void setOnCallBackListener(SelectPreferredLocationCallBack preferredLocationCallBack) {
        mPreferredLocationCallBack = preferredLocationCallBack;
        if (mPreferredLocationCallBack != null && mPreferredLocationCallBack instanceof BaseAbstractFragment)
            baseFragmentActivity = (BaseFragmentActivity) ((BaseAbstractFragment) mPreferredLocationCallBack).getActivity();
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
        return inflater.inflate(R.layout.fragment_select_preferred_location, container);
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

    private RelativeLayout title_bar_rl,search_main_ll, select_country_rl, select_state_rl, select_city_rl;
    private CardView booking_details_card_view;
    private MaterialEditText select_country_edittext, select_state_edittext, select_city_edittext;
    private AppCompatTextView select_search_location_tv;
    private SearchView mSearch;
    private RecyclerView myRecycler;

    private String selectedCountry, selectedState, selectedCity;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        title_bar_rl = v.findViewById(R.id.title_bar_rl);
        search_main_ll = v.findViewById(R.id.search_main_ll);
        booking_details_card_view = v.findViewById(R.id.booking_details_card_view);

        select_country_edittext = v.findViewById(R.id.select_country_edittext);
        select_country_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedCountry = select_country_edittext.getText().toString().trim();
                search_main_ll.setVisibility(View.GONE);
                booking_details_card_view.setVisibility(View.VISIBLE);
                title_bar_rl.setVisibility(View.VISIBLE);
            }
        });
        select_state_edittext = v.findViewById(R.id.select_state_edittext);
        select_state_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedState = select_state_edittext.getText().toString().trim();
                search_main_ll.setVisibility(View.GONE);
                booking_details_card_view.setVisibility(View.VISIBLE);
                title_bar_rl.setVisibility(View.VISIBLE);
            }
        });
        select_city_edittext = v.findViewById(R.id.select_city_edittext);
        select_city_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedCity = select_city_edittext.getText().toString().trim();
                search_main_ll.setVisibility(View.GONE);
                booking_details_card_view.setVisibility(View.VISIBLE);
                title_bar_rl.setVisibility(View.VISIBLE);
            }
        });

        select_country_rl = v.findViewById(R.id.select_country_rl);

        select_country_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedState = null;
                selectedCity = null;
                loadSearchView(baseFragmentActivity.getResources().getString(R.string.select_country), new ArrayList<>(mLocations.keySet()), select_country_edittext);
            }
        });

        select_state_rl = v.findViewById(R.id.select_state_rl);
        select_state_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCity = null;
                loadSearchView(baseFragmentActivity.getResources().getString(R.string.select_state), new ArrayList<>(mLocations.get(selectedCountry).keySet()), select_state_edittext);
            }
        });

        select_city_rl = v.findViewById(R.id.select_city_rl);
        select_city_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSearchView(baseFragmentActivity.getResources().getString(R.string.select_city), new ArrayList<>(mLocations.get(selectedCountry).get(selectedState)), select_city_edittext);
            }
        });

        LinearLayout close_dialog_ll = v.findViewById(R.id.close_dialog_ll);
        close_dialog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AppCompatButton select_preferred_location_bttn = v.findViewById(R.id.select_preferred_location_bttn);
        select_preferred_location_bttn.setOnClickListener(select_timings_bttn_OnClickListener);

        LinearLayout close_search_dialog_ll = v.findViewById(R.id.close_search_dialog_ll);
        close_search_dialog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_main_ll.setVisibility(View.GONE);
                booking_details_card_view.setVisibility(View.VISIBLE);
                title_bar_rl.setVisibility(View.VISIBLE);
            }
        });

        select_search_location_tv = v.findViewById(R.id.select_search_location_tv);
        mSearch = v.findViewById(R.id.mSearch);
        EditText searchEditText = (EditText)mSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.app_dark_color));
        searchEditText.setHintTextColor(getResources().getColor(R.color.textview_hint_color));
        myRecycler = v.findViewById(R.id.myRecycler);

        if(selectedCountry != null){
            select_country_edittext.setText(selectedCountry);
        }
        if(selectedState != null){
            select_state_edittext.setText(selectedState);
        }
        if(selectedCity != null){
            select_city_edittext.setText(selectedCity);
        }
    }

    private void loadSearchView(final String searchTitle, final ArrayList<String> searchList, final MaterialEditText materialEditTextToSetVal){
        select_search_location_tv.setText(searchTitle);
        search_main_ll.setVisibility(View.VISIBLE);
        booking_details_card_view.setVisibility(View.GONE);
        title_bar_rl.setVisibility(View.GONE);

        myRecycler.setLayoutManager(new LinearLayoutManager(baseFragmentActivity));
        myRecycler.setItemAnimator(new DefaultItemAnimator());
        myRecycler.addItemDecoration(new VerticalSpaceItemDecoration(10));

        //ADAPTER
        final LocationSearchAdapter adapter = new LocationSearchAdapter(baseFragmentActivity, searchList, new LocationSearchAdapter.LocationSelectedCallBack() {
            @Override
            public void onLocationSelected(String location) {
                materialEditTextToSetVal.setText(location);
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

    View.OnClickListener select_timings_bttn_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(selectedCountry == null){
                Toast.makeText(baseFragmentActivity, baseFragmentActivity.getResources().getString(R.string.please_select_country), Toast.LENGTH_SHORT).show();
            }else if(selectedState == null){
                Toast.makeText(baseFragmentActivity, baseFragmentActivity.getResources().getString(R.string.please_select_state), Toast.LENGTH_SHORT).show();
            }else if(selectedCity == null){
                Toast.makeText(baseFragmentActivity, baseFragmentActivity.getResources().getString(R.string.please_select_city), Toast.LENGTH_SHORT).show();
            }else{
                if(mSelectedPreferredLocation == null)
                    mSelectedPreferredLocation = new ArrayList<>();

                mSelectedPreferredLocation.add(selectedCountry);
                mSelectedPreferredLocation.add(selectedState);
                mSelectedPreferredLocation.add(selectedCity);

                mPreferredLocationCallBack.updateSelectedLocation(mSelectedPreferredLocation);
                dismiss();
            }
        }
    };

    private List<String> mSelectedPreferredLocation;

    public void setSelectedPreferredLocation(List<String> selectedPreferredLocation) {

        if(selectedPreferredLocation != null){

            selectedCountry = selectedPreferredLocation.get(0);
            selectedState = selectedPreferredLocation.get(1);
            selectedCity = selectedPreferredLocation.get(2);
        }
    }
}
