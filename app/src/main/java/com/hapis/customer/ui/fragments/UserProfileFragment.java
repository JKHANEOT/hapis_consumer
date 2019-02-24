package com.hapis.customer.ui.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hapis.customer.R;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.UserProfileActivity;
import com.hapis.customer.ui.adapters.CountriesAdap;
import com.hapis.customer.ui.custom.AddEditAddressDialog;
import com.hapis.customer.ui.custom.InstantAutoCompleteTextView;
import com.hapis.customer.ui.custom.MaterialSpinner;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.custom.observableview.ObservableScrollView;
import com.hapis.customer.ui.custom.observableview.ObservableScrollViewCallbacks;
import com.hapis.customer.ui.custom.observableview.ScrollState;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.utils.DeviceScreenResolutionUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.utils.EmailAccount;
import com.hapis.customer.ui.utils.GetDeviceEmailAccounts;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.UserProfileFragmentView;
import com.hapis.customer.ui.view.UserProfileFragmentViewModal;
import com.hapis.customer.utils.DateUtil;
import com.hapis.customer.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserProfileFragment extends BaseAbstractFragment<UserProfileFragmentViewModal> implements UserProfileFragmentView, ObservableScrollViewCallbacks, GetDeviceEmailAccounts.FetchEmailAccounts {

    public static final String TAG = UserProfileFragment.class.getName();

    private AppCompatImageView profileIV, flagIV;


    private AppCompatButton createProfileBttn;
    private LinearLayout prefixLL;

    private ObservableScrollView mScrollView;
    /**
     * CustomerProfile holds user profile data.
     */
    private UserProfileTable customerProfile;
    private AddressModel visibleCurrentLocation;
    private ImageView editProfilePicIV;
    private Resources res;
    private Bitmap defaultProfilePic;

    private MaterialSpinner prefix_spinner,marital_status_spinner,nationality_spinner,religion_spinner;
    private MaterialEditText first_name_input_et,middle_name_input_et,last_name_input_et,mobile_et,dob_et,aadhaar_number_et;
    private RadioGroup gender_rg;
    private AppCompatRadioButton rb_male,rb_female;


    private List<String> countryCodesList;
    private CountriesAdap adapter;
    private AppCompatTextView countryFlagTv, countryISDCodeTv,profile_default_address_tv;
    private AppCompatImageView countryFlagIv,edit_profile_address_iv;
    private LinearLayout countryCodeLay;
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

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        initViews(v);

        return v;
    }

    private String selectedPrefix,selectedNationality,selectedReligion,selectedMaritalStatus;

    AdapterView.OnItemSelectedListener prefixItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != -1)
                selectedPrefix = parent.getItemAtPosition(position).toString();
            else
                selectedPrefix = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener nationalityItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != -1)
                selectedNationality = parent.getItemAtPosition(position).toString();
            else
                selectedNationality = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener religionItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != -1)
                selectedReligion = parent.getItemAtPosition(position).toString();
            else
                selectedReligion = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener maritalStatusItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position != -1)
                selectedMaritalStatus = parent.getItemAtPosition(position).toString();
            else
                selectedMaritalStatus = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void validateScreenFields(String errorMsg) {
        if(errorMsg == null || (errorMsg != null && errorMsg.length() == 0)){
            ((UserProfileActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait_progress_msg));
            mViewModal.updateUserProfile(selectedPrefix, EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(middle_name_input_et),
                    EditTextUtils.getText(last_name_input_et), gender_rg.getCheckedRadioButtonId() == R.id.rb_male ? "MALE" : "FEMALE",
                    EditTextUtils.getText(aadhaar_number_et), selectedMaritalStatus, selectedNationality, selectedReligion, EditTextUtils.getText(mobile_et),
                    EditTextUtils.getText(emailEt), DateUtil.dateFormater(EditTextUtils.getText(dob_et) ,
                            DateUtil.DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS_Z , DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN),visibleCurrentLocation);
        }else{
            ((UserProfileActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @SuppressLint("RestrictedApi")
    private void initViews(View v) {

        profileIV = v.findViewById(R.id.profile_pic_iv);
        res = getResources();
        setDefaultProfilePic();
        editProfilePicIV = v.findViewById(R.id.edit_pic_iv);

        createProfileBttn = v.findViewById(R.id.edit_profile_bttn);

        profileIV.setOnClickListener(viewProfileOnClickListener);
        editProfilePicIV.setOnClickListener(editProfileOnClickListener);

        createProfileBttn.setOnClickListener(updateProfileOnClickListener);

        mScrollView = v.findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        prefix_spinner = (MaterialSpinner)v.findViewById(R.id.prefix_spinner);

        ArrayAdapter<String> prefixAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.prefix_array));
        prefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefix_spinner.setAdapter(prefixAdapter);
        prefix_spinner.setOnItemSelectedListener(prefixItemSelectedListener);

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

        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));

        countryCodeLay = v.findViewById(R.id.country_lay);
        countryFlagTv = v.findViewById(R.id.flag_tv);
        countryFlagIv = v.findViewById(R.id.flag_iv);
        countryISDCodeTv = v.findViewById(R.id.country_mobile_code_tv);
        //countryFlagTv.setText(localeToEmoji("IN"));
        String countryID = ((UserProfileActivity) getActivity()).getCountryID();
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
        countryISDCodeTv.setText(countryID/*"+" + ((UserProfileActivity) getActivity()).getCountryDialCode()*/);
        countryIsdCode = ((UserProfileActivity) getActivity()).getCountryDialCode();
    }

    AddEditAddressDialog.AddressAddOrUpdateInputListener addressAddOrUpdateInputListener = new AddEditAddressDialog.AddressAddOrUpdateInputListener() {
        @Override
        public void onAddressAddOrUpdateInputCompleted(AddressModel addressModel) {
            visibleCurrentLocation = addressModel;
            fillLocationDetails();
        }
    };

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

    View.OnClickListener updateProfileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mViewModal.validateRegistrationDetails(EditTextUtils.getText(first_name_input_et), EditTextUtils.getText(last_name_input_et), gender_rg.getCheckedRadioButtonId(),
                    selectedMaritalStatus, selectedNationality, selectedReligion, EditTextUtils.getText(mobile_et), EditTextUtils.getText(emailEt), EditTextUtils.getText(dob_et),
                    EditTextUtils.getText(aadhaar_number_et), visibleCurrentLocation);
        }
    };

    View.OnClickListener viewProfileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener editProfileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void setDefaultProfilePic() {
        defaultProfilePic = BitmapFactory.decodeResource(res, R.mipmap.default_profile_pic);
        Glide.with(getActivity())
                .load(R.mipmap.default_profile_pic)
                .apply(RequestOptions
                        .noTransformation()
                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                        .skipMemoryCache(true)
                        .circleCrop()
                        .override(DeviceScreenResolutionUtil.getValueInPx(100, getActivity()), DeviceScreenResolutionUtil.getValueInPx(100, getActivity())))
                .into(profileIV);
    }

    private String getUserGreetingMsg() {
        String alertMsg = getActivity().getResources().getString(R.string.login_success);
        if (Util.greetingsBasedOnDeviceTime() != null && Util.greetingsBasedOnDeviceTime().length() > 0) {
            alertMsg = Util.greetingsBasedOnDeviceTime();
        }
        return alertMsg;
    }

    @Override
    protected Class getViewModalClass() {
        return UserProfileFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModal.loadUserProfileDetails();
    }

    @Override
    public void failedToProcess(String errorMsg) {
        ((UserProfileActivity)getActivity()).dismissProgressDialog();
        if(errorMsg != null && errorMsg.length() > 0){

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:
                        {
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            };

            ((UserProfileActivity)getActivity()).showError(errorMsg, onClickListener, getResources().getString(R.string.ok), null, DialogIconCodes.DIALOG_FAILED.getIconCode());
        }
    }

    @Override
    public void updateUserProfile(String msg) {
        ((UserProfileActivity)getActivity()).dismissProgressDialog();
        if(msg != null && msg.length() > 0){

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {
                    switch (view.getId()){
                        case R.id.positive_btn:
                        {
                            dialog.dismiss();
                            getActivity().finish();
                            break;
                        }
                    }
                }
            };

            ((UserProfileActivity)getActivity()).showError(msg, onClickListener, getResources().getString(R.string.ok), null, DialogIconCodes.DIALOG_SUCCESS.getIconCode());
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void loadUserProfileDetails(final UserProfileTable userProfileTable){
        customerProfile = userProfileTable;
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{

                        if(customerProfile.getTitle() != null){
                            selectedPrefix = customerProfile.getTitle();
                            String[] titleArray = getResources().getStringArray(R.array.prefix_array);
                            if(titleArray != null && titleArray.length > 0){
                                for(int i = 0; i < titleArray.length; i++){
                                    if(selectedPrefix.equalsIgnoreCase(titleArray[i])){
                                        prefix_spinner.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                        }

                        if (customerProfile.getFirstName() != null) {
                            first_name_input_et.setText(customerProfile.getFirstName());
                        }
                        if (customerProfile.getMiddleName()!= null) {
                            middle_name_input_et.setText(customerProfile.getMiddleName());
                        }
                        if (customerProfile.getLastName() != null) {
                            last_name_input_et.setText(customerProfile.getLastName());
                        }

                        if(customerProfile.getGender() != null){
                            if (customerProfile.getGender().startsWith("M")) {
                                gender_rg.check(R.id.rb_male);
                            } else if (customerProfile.getGender().startsWith("F")) {
                                gender_rg.check(R.id.rb_female);
                            }
                        }

                        if(customerProfile.getMaritalStatus() != null){
                            selectedMaritalStatus = customerProfile.getMaritalStatus();
                            String[] maritalStatusArray = getResources().getStringArray(R.array.marital_status_array);
                            if(maritalStatusArray != null && maritalStatusArray.length > 0){
                                for(int i = 0; i < maritalStatusArray.length; i++){
                                    if(selectedMaritalStatus.equalsIgnoreCase(maritalStatusArray[i])){
                                        marital_status_spinner.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                        }

                        if(customerProfile.getNationality() != null){
                            selectedNationality = customerProfile.getNationality();
                            String[] nationalityArray = getResources().getStringArray(R.array.nationality_array);
                            if(nationalityArray != null && nationalityArray.length > 0){
                                for(int i = 0; i < nationalityArray.length; i++){
                                    if(selectedNationality.equalsIgnoreCase(nationalityArray[i])){
                                        nationality_spinner.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                        }

                        if(customerProfile.getReligion() != null){
                            selectedReligion = customerProfile.getReligion();
                            String[] religionArray = getResources().getStringArray(R.array.religion_array);
                            if(religionArray != null && religionArray.length > 0){
                                for(int i = 0; i < religionArray.length; i++){
                                    if(selectedReligion.equalsIgnoreCase(religionArray[i])){
                                        religion_spinner.setSelection(i+1);
                                        break;
                                    }
                                }
                            }
                        }

                        if (customerProfile.getMobileNumber() != null) {
                            mobile_et.setText(customerProfile.getMobileNumber());
                        }

                        if (customerProfile.getIsdCode() != null) {
                            String countryisdCode = String.valueOf(customerProfile.getIsdCode());
                            for (String countryCode : countryCodesList) {
                                String[] g = countryCode.split(",");
                                if (g[0].equalsIgnoreCase(countryisdCode)) {
                                    String pngName = g[1].trim().toLowerCase();
                                    flagIV.setImageResource(getActivity().getResources().getIdentifier("drawable/" + pngName, null, getActivity().getPackageName()));

                                    LOG.d("CountryISOCode", "" + g[0]);
                                    String ISOCode = g[0];
                                    countryISDCodeTv.setText("");
                                    countryISDCodeTv.setText("+" + ISOCode);
                                    countryISDCodeTv.invalidate();
                                    countryISDCodeTv.setClickable(false);
                                    countryISDCodeTv.setLongClickable(false);
                                    countryISDCodeTv.setFocusable(false);
                                    prefixLL.setClickable(false);
                                    prefixLL.setLongClickable(false);
                                    prefixLL.setFocusable(false);
                                    break;
                                }
                            }
                        }

                        if (customerProfile.getEmail() != null) {
                            emailEt.setText(customerProfile.getEmail());
                        }

                        if (customerProfile.getDateOfBirth() > 0) {
                            String formattedDob = Util.getDateString(customerProfile.getDateOfBirth());
                            dob_et.setText(formattedDob);
                        }

                        if(customerProfile.getAadhaarNumber() != null){
                            aadhaar_number_et.setText(customerProfile.getAadhaarNumber());
                        }

                        mViewModal.loadUserAddress(customerProfile.getUniqueId());

                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }
            });
        }
    }

    @Override
    public void loadUserProfilAddressDetails(AddressModel addressModel) {
        visibleCurrentLocation = addressModel;
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fillLocationDetails();
                }
            });
        }
    }
}
