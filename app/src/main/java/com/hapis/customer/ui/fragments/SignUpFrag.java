package com.hapis.customer.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.LoginActivity;
import com.hapis.customer.ui.SigupActivity;
import com.hapis.customer.ui.adapters.CountriesAdap;
import com.hapis.customer.ui.custom.AddEditAddressDialog;
import com.hapis.customer.ui.custom.InstantAutoCompleteTextView;
import com.hapis.customer.ui.custom.MaterialSpinner;
import com.hapis.customer.ui.custom.ShowHidePasswordEditText;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnBackPressListener;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.utils.EmailAccount;
import com.hapis.customer.ui.utils.GetDeviceEmailAccounts;
import com.hapis.customer.ui.utils.PermissionsUtils;
import com.hapis.customer.ui.utils.UserPermissionDeniedCallBack;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.SignUpFragmentView;
import com.hapis.customer.ui.view.SignUpFragmentViewModal;
import com.hapis.customer.utils.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpFrag extends BaseAbstractFragment<SignUpFragmentViewModal> implements
        GetDeviceEmailAccounts.FetchEmailAccounts, SignUpFragmentView {

    public static final String TAG = SignUpFrag.class.getSimpleName();

    private MaterialSpinner prefix_spinner,marital_status_spinner,nationality_spinner,religion_spinner;
    private MaterialEditText first_name_input_et,middle_name_input_et,last_name_input_et,mobile_et,dob_et,aadhaar_number_et;
    private RadioGroup gender_rg;
    private AppCompatRadioButton rb_male,rb_female;
    private TextInputEditText referral_code_et;


    private View v;
    private ShowHidePasswordEditText passwordET;
    private AppCompatButton signUpBttn;
    private List<String> countryCodesList;
    private CountriesAdap adapter;
    private AppCompatTextView countryFlagTv, countryISDCodeTv,profile_default_address_tv;
    private AppCompatImageView countryFlagIv,edit_profile_address_iv;
    private LinearLayout countryCodeLay;
    private AppCompatTextView termsOfUse;
    private TextView signInTv2;
    private TextInputLayout emailInputLay;
    private InstantAutoCompleteTextView emailEt;

    private List<String> emailAccountList;
//    private AppCompatTextView versionTv, copyrightTv;

    private String countryIsdCode = "";


    @Override
    public void onFetchingEmailAccounts(List<EmailAccount> emailAccounts) {
        if (emailAccountList == null) {
            emailAccountList = new ArrayList<>();
        }
        for (EmailAccount account : emailAccounts) {
            emailAccountList.add(account.getName());
        }
        final ArrayAdapter<String> emailAdapter = new ArrayAdapter(getActivity(), R.layout.row_spn, emailAccountList);
        emailEt.setAdapter(emailAdapter);
    }

    public SignUpFrag() {
        // Required empty public constructor
    }

    @Override
    protected Class getViewModalClass() {
        return SignUpFragmentViewModal.class;
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
        v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initViews();

        new TedPermission(getActivity())
                .setPermissionListener(new PermissionListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionGranted() {
                        new GetDeviceEmailAccounts(getActivity(), SignUpFrag.this, GetDeviceEmailAccounts.GMAIL_ACCOUNT);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PermissionsUtils.onPermissionDenied(new UserPermissionDeniedCallBack() {
                                @Override
                                public void takeActionOnOnlyPermissionDenied() {
                                    return;
                                }

                                @Override
                                public void takeActionOnCheckedDontAskAndPermissionDenied() {
                                    return;
                                }

                                @Override
                                public void noAction() {
                                    return;
                                }
                            }, getActivity(), deniedPermissions, null);
                        }
                    }

                })
                .setPermissions(
                        Manifest.permission.GET_ACCOUNTS)
                .check();

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
                mViewModal.validateRegistrationDetails(EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(last_name_input_et), gender_rg.getCheckedRadioButtonId(),
                        selectedMaritalStatus, selectedNationality, selectedReligion, EditTextUtils.getText(mobile_et), EditTextUtils.getText(emailEt), EditTextUtils.getText(dob_et),
                        EditTextUtils.getText(passwordET), EditTextUtils.getText(aadhaar_number_et), visibleCurrentLocation);
            }
        });

        passwordET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ((SigupActivity) getActivity()).hideSoftKeyPad();
                    mViewModal.validateRegistrationDetails(EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(last_name_input_et), gender_rg.getCheckedRadioButtonId(),
                            selectedMaritalStatus, selectedNationality, selectedReligion, EditTextUtils.getText(mobile_et), EditTextUtils.getText(emailEt), EditTextUtils.getText(dob_et),
                            EditTextUtils.getText(passwordET), EditTextUtils.getText(aadhaar_number_et), visibleCurrentLocation);
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

    private String selectedPrefix,selectedNationality,selectedReligion,selectedMaritalStatus;

    AdapterView.OnItemSelectedListener prefixItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedPrefix = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener nationalityItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedNationality = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener religionItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedReligion = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener maritalStatusItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedMaritalStatus = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @SuppressLint("RestrictedApi")
    private void initViews() {

        prefix_spinner = (MaterialSpinner)v.findViewById(R.id.prefix_spinner);

        ArrayAdapter<String> prefixAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.prefix_array));
        prefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefix_spinner.setAdapter(prefixAdapter);
        prefix_spinner.setOnItemSelectedListener(prefixItemSelectedListener);
        /*Spannable word = new SpannableString("Select Payment Mode ");
        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimary)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        prefix_spinner.setHint(word);
        Spannable wordTwo = new SpannableString("●");
        wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        prefix_spinner.setHintAppend(wordTwo);*/

        first_name_input_et = (MaterialEditText)v.findViewById(R.id.first_name_input_et);
        middle_name_input_et = (MaterialEditText)v.findViewById(R.id.middle_name_input_et);
        last_name_input_et = (MaterialEditText)v.findViewById(R.id.last_name_input_et);

        gender_rg = (RadioGroup)v.findViewById(R.id.gender_rg);
        rb_male = (AppCompatRadioButton)v.findViewById(R.id.rb_male);
        rb_female = (AppCompatRadioButton)v.findViewById(R.id.rb_female);
        gender_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male: {
                        rb_male.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        rb_female.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        break;
                    }
                    case R.id.rb_female: {
                        rb_male.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        rb_female.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        break;
                    }
                }
            }
        });

        marital_status_spinner = (MaterialSpinner)v.findViewById(R.id.marital_status_spinner);

        ArrayAdapter<String> maritalStatusAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.marital_status_array));
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_status_spinner.setAdapter(maritalStatusAdapter);
        marital_status_spinner.setOnItemSelectedListener(maritalStatusItemSelectedListener);

        nationality_spinner = (MaterialSpinner)v.findViewById(R.id.nationality_spinner);

        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.nationality_array));
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationality_spinner.setAdapter(nationalityAdapter);
        nationality_spinner.setOnItemSelectedListener(nationalityItemSelectedListener);

        religion_spinner = (MaterialSpinner)v.findViewById(R.id.religion_spinner);

        ArrayAdapter<String> religionAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.religion_array));
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religion_spinner.setAdapter(religionAdapter);
        religion_spinner.setOnItemSelectedListener(religionItemSelectedListener);

        dob_et = (MaterialEditText)v.findViewById(R.id.dob_et);
        dob_et.setLongClickable(false);
        final Drawable purchase_date_drawable = getResources().getDrawable(R.drawable.ic_calendar);
        final Drawable clear_purchase_date_drawable = getResources().getDrawable(R.drawable.ic_clear);

        dob_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (dob_et.getCompoundDrawables()[2] == null)
                        return false;

                    if (EditTextUtils.isEmpty(dob_et) && event.getX() > dob_et.getWidth() - dob_et.getPaddingRight() - purchase_date_drawable.getIntrinsicWidth()) {
                        ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();
                        ((BaseFragmentActivity) getActivity()).setDateResultTo(dob_et, null, false);
                        ((BaseFragmentActivity)getActivity()).showDialog(BaseFragmentActivity.DATE_PICKER_ID);
                    } else if (!EditTextUtils.isEmpty(dob_et) && event.getX() > dob_et.getWidth() - dob_et.getPaddingRight() - clear_purchase_date_drawable.getIntrinsicWidth()) {
                        dob_et.setText("");
                    }
                    return true;
                }
                else
                    return false;
            }
        });
        dob_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (EditTextUtils.getText(dob_et).length() == 0) {
                    dob_et.setCompoundDrawablesWithIntrinsicBounds(null,null,purchase_date_drawable,null);
                } else if (EditTextUtils.getText(dob_et).length() > 0) {
                    dob_et.setCompoundDrawablesWithIntrinsicBounds(null,null,clear_purchase_date_drawable,null);
                }
            }
        });

        referral_code_et = (TextInputEditText)v.findViewById(R.id.referral_code_et);
        aadhaar_number_et = (MaterialEditText)v.findViewById(R.id.aadhaar_number_et);
        edit_profile_address_iv = (AppCompatImageView)v.findViewById(R.id.edit_profile_address_iv);
        edit_profile_address_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditAddressDialog addEditAddressDialog = new AddEditAddressDialog(getActivity(), addressAddOrUpdateInputListener, visibleCurrentLocation);
                addEditAddressDialog.show();
            }
        });
        profile_default_address_tv = (AppCompatTextView)v.findViewById(R.id.profile_default_address_tv);

        emailEt = v.findViewById(R.id.email_et);
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

    AddEditAddressDialog.AddressAddOrUpdateInputListener addressAddOrUpdateInputListener = new AddEditAddressDialog.AddressAddOrUpdateInputListener() {
        @Override
        public void onAddressAddOrUpdateInputCompleted(AddressModel addressModel) {
            visibleCurrentLocation = addressModel;
            fillLocationDetails();
        }
    };

    private AddressModel visibleCurrentLocation;

    private void fillLocationDetails() {

        String addProfileDefaultAddressHint = getFormattedAddress();
        profile_default_address_tv.setText(addProfileDefaultAddressHint);
    }

    private String getFormattedAddress() {

        String completeAddress = null;

        if (visibleCurrentLocation != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (visibleCurrentLocation.getName() != null && visibleCurrentLocation.getName().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getName() + " ");
            if (visibleCurrentLocation.getAddressLine1() != null && visibleCurrentLocation.getAddressLine1().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getAddressLine1() + " ");
            if (visibleCurrentLocation.getAddressLine2() != null && visibleCurrentLocation.getAddressLine2().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getAddressLine2() + " ");
            if (visibleCurrentLocation.getCity() != null && visibleCurrentLocation.getCity().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getCity() + " ");
            if (visibleCurrentLocation.getDistrict() != null && visibleCurrentLocation.getDistrict().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getDistrict() + " ");
            if (visibleCurrentLocation.getStateCode() != null && visibleCurrentLocation.getStateCode().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getStateCode() + " ");
            if (visibleCurrentLocation.getCountry() != null && visibleCurrentLocation.getCountry().length() > 0)
                stringBuilder.append(visibleCurrentLocation.getCountry() + " ");
            if (visibleCurrentLocation.getPinCode() != null && visibleCurrentLocation.getPinCode().length() > 0)
                stringBuilder.append(" - " + visibleCurrentLocation.getPinCode());

            completeAddress = stringBuilder.toString();
        }

        return completeAddress;
    }

    @Override
    public void validateScreenFields(String errorMsg) {
        if(errorMsg == null || (errorMsg != null && errorMsg.length() == 0)){
            ((SigupActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait_progress_msg));
            mViewModal.doSignup(selectedPrefix, EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(middle_name_input_et),
                    EditTextUtils.getText(last_name_input_et), gender_rg.getCheckedRadioButtonId() == R.id.rb_male ? "MALE" : "FEMALE",
                    EditTextUtils.getText(aadhaar_number_et), selectedMaritalStatus, selectedNationality, selectedReligion, EditTextUtils.getText(mobile_et),
                    EditTextUtils.getText(emailEt), DateUtil.dateFormater(EditTextUtils.getText(dob_et) , DateUtil.DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS_Z , DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN),
                    EditTextUtils.getText(passwordET), EditTextUtils.getText(referral_code_et), visibleCurrentLocation);
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
