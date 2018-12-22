package com.hapis.customer.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by JKHAN
 */
public class CountriesAdap extends RecyclerView.Adapter<CountriesAdap.MyViewHolder> {

    private Context mContext;
    private List<String> countriesList;
    private AppCompatTextView noResultTv;
    private AppCompatEditText mEdtSearch;
    private DialogPlus mDialog;

    private List<String> filteredCountries;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView countryFlagTv;
        public AppCompatTextView countryNameTv;
        public AppCompatImageView countryFlagIv;
        public LinearLayout itemLay;

        public MyViewHolder(View view) {
            super(view);
            countryFlagTv = view.findViewById(R.id.flag_tv);
            countryNameTv = view.findViewById(R.id.country_name_tv);
            countryFlagIv = view.findViewById(R.id.flag_iv);
            itemLay = view.findViewById(R.id.item_lay);
        }
    }

    public CountriesAdap(Context context, List<String> countriesList) {
        this.countriesList = countriesList;
        this.mContext = context;
    }

    public CountriesAdap(Context context, List<String> countriesList, AppCompatEditText searchableET, AppCompatTextView noResultTv, DialogPlus dialog) {
        this.countriesList = countriesList;
        this.mContext = context;
        this.mEdtSearch = searchableET;
        this.noResultTv = noResultTv;
        this.mDialog = dialog;
        this.filteredCountries = getFilteredCountries();
        setTextWatcher();
    }

    /**
     * add textChangeListener, to apply new query each time editText get text changed.
     */
    private void setTextWatcher() {
        if (mEdtSearch != null) {
            mEdtSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    applyQuery(s.toString());
                }
            });
        }
    }

    /**
     * Filter country list for given keyWord / query.
     * Lists all countries that contains @param query in country's name, name code or phone code.
     *
     * @param query : text to match against country name, name code or phone code
     */
    private void applyQuery(String query) {
        noResultTv.setVisibility(View.GONE);
        query = query.toLowerCase();

        //if query started from "+" ignore it
        if (query.length() > 0 && query.charAt(0) == '+') {
            query = query.substring(1);
        }

        filteredCountries = getFilteredCountries(query);

        if (filteredCountries.size() == 0) {
            noResultTv.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    private List<String> getFilteredCountries() {
        return getFilteredCountries("");
    }

    private List<String> getFilteredCountries(String query) {
        List<String> tempCountries = new ArrayList<>();

        for (String country : countriesList) {
            if (isEligibleForQuery(getCountryName(country), query)) {
                tempCountries.add(country);
            }
        }
        return tempCountries;
    }

    private boolean isEligibleForQuery(String country, String query) {
        boolean isEligible = false;
        if (country.toLowerCase().contains(query)) {
            isEligible = true;
        }
        return isEligible;
    }

    private String getCountryName(String country) {
        Locale.setDefault(Locale.US);
        String[] g = country.split(",");
        String ssid = g[1].trim();
        Locale loc = new Locale("", ssid);
        return loc.getDisplayCountry().trim();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final String[] g = filteredCountries.get(position).split(",");

        holder.countryNameTv.setText(GetDisplayCountryName(g[1]).trim());
        holder.countryFlagTv.setText(localeToEmoji(g[1]).trim());
        String pngName = g[1].trim().toLowerCase();
        if(holder.countryNameTv.getText().toString().contains("Cocos")){
            LOG.d("Country Name: ", holder.countryNameTv.getText().toString());
            LOG.d("Country Code: ", pngName);
        }
        holder.countryFlagIv.setImageResource(mContext.getResources().getIdentifier("drawable/" + pngName, null, mContext.getPackageName()));

        holder.itemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCountryClickListener != null) {
                    mCountryClickListener.onCountryClick(v, position, g);
                }
            }
        });

       /* holder.itemLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mCountryClickListener != null) {
                    mCountryClickListener.onCountryLongClick(v, position, dk);
                }

                return false;
            }
        });*/
    }

    private String GetDisplayCountryName(String ssid) {
        Locale loc = new Locale("", ssid);
        return loc.getDisplayCountry().trim();
    }

    private String localeToEmoji(String countryCode) {
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    @Override
    public int getItemCount() {
        return filteredCountries.size();
    }

    private static CountryClickListener mCountryClickListener;

    public interface CountryClickListener {
        void onCountryClick(View view, int position, String[] countryStrArray);

       // void onCountryLongClick(View v, int position, String[] countryStrArray);
    }

    public void setOnItemClickListener(final CountryClickListener countryClickListener) {
        CountriesAdap.mCountryClickListener = countryClickListener;
    }
}
