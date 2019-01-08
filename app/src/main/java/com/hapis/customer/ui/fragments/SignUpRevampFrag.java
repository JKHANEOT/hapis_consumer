package com.hapis.customer.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.LoginActivity;
import com.hapis.customer.ui.SigupActivity;
import com.hapis.customer.ui.adapters.CountriesAdap;
import com.hapis.customer.ui.custom.ShowHidePasswordEditText;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnBackPressListener;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.SignUpFragmentView;
import com.hapis.customer.ui.view.SignUpRevampFragmentViewModal;

import java.util.Arrays;
import java.util.List;

public class SignUpRevampFrag extends BaseAbstractFragment<SignUpRevampFragmentViewModal> implements
        SignUpFragmentView {

    public static final String TAG = SignUpRevampFrag.class.getSimpleName();

    private MaterialEditText first_name_input_et,last_name_input_et, mobile_et;
    private TextInputEditText referral_code_et;


    private View v;
    private ShowHidePasswordEditText passwordET;
    private AppCompatButton signUpBttn;
    private List<String> countryCodesList;
    private CountriesAdap adapter;
    private AppCompatTextView countryFlagTv, countryISDCodeTv;
    private AppCompatImageView countryFlagIv;
    private LinearLayout countryCodeLay;
    private AppCompatTextView termsOfUse;
    private TextView signInTv2;

    private String countryIsdCode = "";

    public SignUpRevampFrag() {
        // Required empty public constructor
    }

    @Override
    protected Class getViewModalClass() {
        return SignUpRevampFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sign_up_revamp, container, false);

        initViews();

        termsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), LoadWebPageActivity.class);
                intent.putExtra(LoadWebPageActivity.PAGE_TYPE, LoadWebPageActivity.TERMS_CONDITIONS);
                startActivity(intent);*/
            }
        });
        signInTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInClicked();
            }
        });
        signUpBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SigupActivity) getActivity()).hideSoftKeyPad();
                mViewModal.validateRegistrationDetails(EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(last_name_input_et),
                        EditTextUtils.getText(mobile_et), EditTextUtils.getText(passwordET));
            }
        });

        passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ((SigupActivity) getActivity()).hideSoftKeyPad();
                    mViewModal.validateRegistrationDetails(EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(last_name_input_et),
                            EditTextUtils.getText(mobile_et), EditTextUtils.getText(passwordET));
                    return true;
                }
                return false;
            }
        });

        countryCodeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SigupActivity) getActivity()).hideSoftKeyPad();
                final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                        .setExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.country_list_lay))
                        .setCancelable(true)
                        .setBackgroundColorResourceId(R.color.white)
                        .setOnBackPressListener(new OnBackPressListener() {
                            @Override
                            public void onBackPressed(DialogPlus dialog) {
                                dialog.dismiss();
                            }
                        })
                        .create(false);
                dialog.show();

                View dialogView = dialog.getHolderView();
                AppCompatEditText searchableET = dialogView.findViewById(R.id.searchable_et);
                AppCompatTextView noResultTv = dialogView.findViewById(R.id.no_result_tv);
//                TypefaceHelper.getInstance().setTypeface(searchableET, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
//                TypefaceHelper.getInstance().setTypeface(noResultTv, TypefaceHelper.getFont(TypefaceHelper.FONT.LIGHT));
                RecyclerView countryLv = dialogView.findViewById(R.id.country_lv);
                adapter = new CountriesAdap(getActivity(), countryCodesList, searchableET, noResultTv, dialog);
                countryLv.setAdapter(adapter);
                countryLv.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.setOnItemClickListener(new CountriesAdap.CountryClickListener() {
                    @Override
                    public void onCountryClick(View view, int position, String[] countryStrArray) {
                        AppCompatTextView flagTv = view.findViewById(R.id.flag_tv);
                        AppCompatTextView countryTv = view.findViewById(R.id.country_name_tv);
                        countryFlagTv.setText(flagTv.getText());
                        String countryISOAlpha2Name = countryStrArray[1].trim().toLowerCase();
//                        countryFlagIv.setImageResource(getActivity().getResources().getIdentifier("drawable/" + countryISOAlpha2Name, null, getActivity().getPackageName()));
                        Glide.with(getActivity())
                                .load(getActivity().getResources().getIdentifier("drawable/" + countryISOAlpha2Name, null, getActivity().getPackageName()))
                                .apply(RequestOptions
                                        .noTransformation()
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                        .centerInside()
                                        .circleCrop())
                                .into(countryFlagIv);
                        LOG.d("CountryISOCode", "" + countryStrArray[0]);
                        String ISOCode = countryStrArray[0];
                        countryISDCodeTv.setText("");
                        countryISDCodeTv.setText(countryISOAlpha2Name);
                        countryIsdCode = ISOCode;
                        countryISDCodeTv.invalidate();
                        ((SigupActivity) getActivity()).hideSoftKeyPad();
                        dialog.dismiss();
                    }
                });
            }
        });

//        AccessPreferences.put(getActivity(), ApplicationConstants.IS_USER_LOGGED_IN, ApplicationConstants.USER_NEW);

        return v;
    }

    @SuppressLint("RestrictedApi")
    private void initViews() {

        first_name_input_et = (MaterialEditText)v.findViewById(R.id.first_name_input_et);
        last_name_input_et = (MaterialEditText)v.findViewById(R.id.last_name_input_et);

        referral_code_et = (TextInputEditText)v.findViewById(R.id.referral_code_et);

        mobile_et = v.findViewById(R.id.mobile_et);
        passwordET = v.findViewById(R.id.pswd_et);
        passwordET.setFilters(EditTextUtils.getSpaceFilterWithMaxLength(getResources().getInteger(R.integer.PASSWORD_LENGTH_MAX)));

        termsOfUse = v.findViewById(R.id.terms_of_use_tv);
        signInTv2 = v.findViewById(R.id.sign_in_tv2);
        signUpBttn = v.findViewById(R.id.sign_up_bttn);

        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));

        countryCodeLay = v.findViewById(R.id.country_lay);
        countryFlagTv = v.findViewById(R.id.flag_tv);
        countryFlagIv = v.findViewById(R.id.flag_iv);
        countryISDCodeTv = v.findViewById(R.id.country_mobile_code_tv);
        //countryFlagTv.setText(localeToEmoji("IN"));
        String countryID = ((SigupActivity) getActivity()).getCountryID();
        /*countryFlagIv.setImageResource(getActivity().getResources().getIdentifier("drawable/"
                + countryID, null, getActivity().getPackageName()));*/
        Glide.with(getActivity())
                .load(getActivity().getResources().getIdentifier("drawable/" + countryID, null, getActivity().getPackageName()))
                .apply(RequestOptions
                        .noTransformation()
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop())
                .into(countryFlagIv);
        countryISDCodeTv.setText(countryID/*"+" + ((SigupActivity) getActivity()).getCountryDialCode()*/);
        countryIsdCode = ((SigupActivity) getActivity()).getCountryDialCode();
    }

    @Override
    public void validateScreenFields(String errorMsg) {
        if(errorMsg == null || (errorMsg != null && errorMsg.length() == 0)){
            ((SigupActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait_progress_msg));
            mViewModal.doSignup(EditTextUtils.getText(first_name_input_et),
                    EditTextUtils.getText(last_name_input_et), EditTextUtils.getText(mobile_et),
                    EditTextUtils.getText(passwordET), EditTextUtils.getText(referral_code_et));
        }else{
            ((SigupActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void signInClicked() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            getActivity().finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SignupRequestFailed(String errorMsg) {
        if(errorMsg != null && errorMsg.length() > 0){
            ((SigupActivity)getActivity()).dismissProgressDialog();
            ((SigupActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void SignupRequestSuccess(String msg) {
        ((SigupActivity)getActivity()).dismissProgressDialog();
        if(msg != null && msg.length() > 0){

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:
                        {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            try {
                                getActivity().finishAffinity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            };

            ((SigupActivity)getActivity()).showError(msg, onClickListener, getResources().getString(R.string.ok), null, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
        }
    }
}
