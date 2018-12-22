package com.hapis.customer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hapis.customer.BuildConfig;
import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.DashboardActivity;
import com.hapis.customer.ui.LoginActivity;
import com.hapis.customer.ui.SigupActivity;
import com.hapis.customer.ui.adapters.CountriesAdap;
import com.hapis.customer.ui.custom.ShowHidePasswordEditText;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnBackPressListener;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.LoginFragmentView;
import com.hapis.customer.ui.view.LoginFragmentViewModal;

import java.util.Arrays;
import java.util.List;

public class LoginFrag extends BaseAbstractFragment<LoginFragmentViewModal> implements LoginFragmentView {

    public static final String TAG = LoginFrag.class.getName();

    private View v;
    private TextView frgtPswdTv;
    private AppCompatButton loginButton;

    private TextInputLayout inputLayEmailIdOrMobileNumber, inputLayPswd;
    private AppCompatEditText inputEmailIdOrMobileNumber;
    private ShowHidePasswordEditText passwordEditText;
    private TextView signUpTv1, signUpTv2;
//    private AppCompatTextView versionTv, copyrightTv;

    //    private SwitchCompat loginVariableChangerSwitch;
    private RelativeLayout mainLayout;
    private String usernameErrorStr;
    private String emailStr = "";
    private String mobileStr = "";

    private List<String> countryCodesList;
    private LinearLayout countryCodeLay, mobileLay;
    private AppCompatTextView countryFlagTv, countryISDCodeTv;
    private AppCompatImageView countryFlagIv;
    private CountriesAdap adapter;

    private int etPadding;

    private String countryIsdCode = "";
    private ImageView appLogoIv;

    public LoginFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();

        usernameErrorStr = getActivity().getResources().getString(R.string.mobile_number_blank_error);


        loginButton.setOnClickListener(new AppCompatButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModal.validateLoginDetails(EditTextUtils.getText(inputEmailIdOrMobileNumber), EditTextUtils.getText(passwordEditText));
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mViewModal.validateLoginDetails(EditTextUtils.getText(inputEmailIdOrMobileNumber), EditTextUtils.getText(passwordEditText));
                    return true;
                }
                return false;
            }
        });

        frgtPswdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordClicked();
            }
        });

        signUpTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });
        signUpTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });

        countryCodeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).hideSoftKeyPad();
                final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                        .setExpanded(false)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.country_list_lay))
                        .setCancelable(true)
                        .setBackgroundColorResourceId(R.color.transparent)
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
                        countryFlagIv.setImageResource(getActivity().getResources()
                                .getIdentifier("drawable/" + countryISOAlpha2Name,
                                        null, getActivity().getPackageName()));
                        Glide.with(getActivity())
                                .load(getActivity().getResources().getIdentifier("drawable/" + countryISOAlpha2Name, null, getActivity().getPackageName()))
                                .apply(RequestOptions
                                        .noTransformation()
                                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                                        .skipMemoryCache(true)
                                        .dontTransform()
                                        .optionalCenterCrop()
                                        .circleCrop())
                                .into(countryFlagIv);
                        LOG.d("CountryISOCode", "" + countryStrArray[0]);
                        String ISOCode = countryStrArray[0];
                        countryISDCodeTv.setText("");
                        countryISDCodeTv.setText(countryISOAlpha2Name);
                        countryIsdCode = ISOCode;
                        countryISDCodeTv.invalidate();
                        ((LoginActivity) getActivity()).hideSoftKeyPad();
                        dialog.dismiss();
                    }
                });
            }
        });

        return v;
    }

    private void initViews() {
        mainLayout = v.findViewById(R.id.main_lay); // You must use the layout root
        appLogoIv = v.findViewById(R.id.logo_iv);
        appLogoIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (BuildConfig.DEBUG) {
//                    Intent in = new Intent(getActivity(), SwitchServerActivity.class);
//                    startActivity(in);
                }
                return false;
            }
        });
        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));
        countryCodeLay = v.findViewById(R.id.country_lay);
        mobileLay = v.findViewById(R.id.mobile_lay);
        countryFlagTv = v.findViewById(R.id.flag_tv);
        countryFlagIv = v.findViewById(R.id.flag_iv);
        countryISDCodeTv = v.findViewById(R.id.country_mobile_code_tv);
        String countryID = ((LoginActivity) getActivity()).getCountryID();
        /*countryFlagIv.setImageResource(getActivity().getResources().getIdentifier("drawable/"
                + countryID, null, getActivity().getPackageName()));*/

        Glide.with(getActivity())
                .load(getActivity().getResources().getIdentifier("drawable/" + countryID, null, getActivity().getPackageName()))
                .apply(RequestOptions
                        .noTransformation()
                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                        .skipMemoryCache(true)
                        .dontTransform()
                        .optionalCenterCrop()
                        .circleCrop())
                .into(countryFlagIv);
        countryISDCodeTv.setText(countryID/*"+" + ((LoginActivity) getActivity()).getCountryDialCode()*/);
        countryIsdCode = ((LoginActivity) getActivity()).getCountryDialCode();

        inputLayEmailIdOrMobileNumber = v.findViewById(R.id.input_lay_email_id_or_mobile_no);
        inputLayPswd = v.findViewById(R.id.input_lay_pswd_et);
        inputEmailIdOrMobileNumber = v.findViewById(R.id.input_email_id_or_mobile_no);
        passwordEditText = v.findViewById(R.id.pswd_et);
//        loginVariableChangerSwitch = v.findViewById(R.id.login_variable_changer_switch);

        loginButton = v.findViewById(R.id.login_bttn);
        frgtPswdTv = v.findViewById(R.id.frgt_pswd_tv);
        signUpTv1 = v.findViewById(R.id.sign_up_tv1);
        signUpTv2 = v.findViewById(R.id.sign_up_tv2);
//        copyrightTv = v.findViewById(R.id.copyright_tv);
//        versionTv = v.findViewById(R.id.version_no_tv);
        etPadding = getResources().getDimensionPixelOffset(R.dimen.et_mobile_padding);
        inputEmailIdOrMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayEmailIdOrMobileNumber.setError(null);
                inputLayEmailIdOrMobileNumber.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayPswd.setError(null);
                inputLayPswd.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected Class getViewModalClass() {
        return LoginFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void validateScreenFields(String errorMsg) {
        if(errorMsg == null || (errorMsg != null && errorMsg.length() == 0)){
            ((LoginActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait_progress_msg));
            mViewModal.doLogin(EditTextUtils.getText(inputEmailIdOrMobileNumber), EditTextUtils.getText(passwordEditText));
        }else{
            ((LoginActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void forgotPasswordClicked() {

    }

    @Override
    public void signUpClicked() {
        Intent intent = new Intent(getActivity(), SigupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            getActivity().finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SigninRequestSuccess(String msg) {
        ((LoginActivity)getActivity()).dismissProgressDialog();
        if(msg != null && msg.length() > 0){

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:
                        {
                            Intent intent = new Intent(getActivity(), DashboardActivity.class);
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

            ((LoginActivity)getActivity()).showError(msg, onClickListener, getResources().getString(R.string.ok), null, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
        }
    }

    @Override
    public void SigninRequestFailed(String errorMsg) {
        if(errorMsg != null && errorMsg.length() > 0){
            ((LoginActivity)getActivity()).dismissProgressDialog();
            ((LoginActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }
}
