package com.hapis.customer.ui.fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.hapis.customer.R;
import com.hapis.customer.database.tables.UserProfileTable;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.UserProfileActivity;
import com.hapis.customer.ui.adapters.CountriesAdap;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnBackPressListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.custom.observableview.ObservableScrollView;
import com.hapis.customer.ui.custom.observableview.ObservableScrollViewCallbacks;
import com.hapis.customer.ui.custom.observableview.ScrollState;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.utils.DeviceScreenResolutionUtil;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BaseViewModal;
import com.hapis.customer.ui.view.UserProfileFragmentView;
import com.hapis.customer.ui.view.UserProfileFragmentViewModal;
import com.hapis.customer.utils.Util;

import java.util.Arrays;
import java.util.List;

public class UserProfileFragment extends BaseAbstractFragment<UserProfileFragmentViewModal> implements UserProfileFragmentView, ObservableScrollViewCallbacks {

    public static final String TAG = UserProfileFragment.class.getName();

    private AppCompatImageView profileIV, flagIV;

    private TextInputLayout inputLayMobileNumber;
    private AppCompatEditText inputMobileNumber;

    private MaterialEditText nameEt, dobEt, emailEt;
    private AppCompatTextView countryCodeTv;
    private RadioGroup genderRG, customerTypeRG;
    private AppCompatRadioButton maleRB, femaleRB, individualRB, organisationRB;
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
    private CountriesAdap adapter;
    private List<String> countryCodesList;
    private static int DEFAULT_LOCATION_ID = 0;
    private LinearLayout profileDetailsLay2;

    private AppCompatTextView greeting_tv, consumer_name_tv;
    private AppCompatImageView mobileNumberEditIv;

    private LinearLayout profile_address_ll;
    private AppCompatTextView profile_address_label_tv, profile_default_address_tv;
    private AppCompatImageView edit_profile_address_iv;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_profile, container, false);

        profile_address_ll = v.findViewById(R.id.profile_address_ll);
        profile_address_label_tv = v.findViewById(R.id.profile_address_label_tv);
        profile_default_address_tv = v.findViewById(R.id.profile_default_address_tv);
        edit_profile_address_iv = v.findViewById(R.id.edit_profile_address_iv);

        edit_profile_address_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        greeting_tv = v.findViewById(R.id.greeting_tv);
        greeting_tv.setText(getUserGreetingMsg());
        consumer_name_tv = v.findViewById(R.id.consumer_name_tv);

        AppCompatTextView customer_type_tv = v.findViewById(R.id.customer_type_tv);
        AppCompatTextView gender_tv = v.findViewById(R.id.gender_tv);

        profileDetailsLay2 = v.findViewById(R.id.content_ll);

        profileIV = v.findViewById(R.id.profile_pic_iv);
        res = getResources();
        setDefaultProfilePic();
        countryCodesList = Arrays.asList(getResources().getStringArray(R.array.CountryCodes));
        editProfilePicIV = v.findViewById(R.id.edit_pic_iv);

//        todosTv = (AppCompatTextView) fragmentView.findViewById(R.id.to_dos_count_tv);
        nameEt = v.findViewById(R.id.name_et);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{getResources().getColor(R.color.radio_button_not_selected)
                        , getResources().getColor(R.color.radio_button_selected),
                }
        );

        customerTypeRG = v.findViewById(R.id.customer_type_rg);

        individualRB = v.findViewById(R.id.rb_individual);
        organisationRB = v.findViewById(R.id.rb_organisation);
        individualRB.setSupportButtonTintList(colorStateList);
        organisationRB.setSupportButtonTintList(colorStateList);

        customerTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_individual: {
                        individualRB.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        organisationRB.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        break;
                    }
                    case R.id.rb_organisation: {
                        individualRB.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        organisationRB.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        break;
                    }
                }
            }
        });

        genderRG = v.findViewById(R.id.gender_rg);
        maleRB = v.findViewById(R.id.rb_male);
        femaleRB = v.findViewById(R.id.rb_female);
        maleRB.setSupportButtonTintList(colorStateList);
        femaleRB.setSupportButtonTintList(colorStateList);

        genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male: {
                        maleRB.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        femaleRB.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        break;
                    }
                    case R.id.rb_female: {
                        maleRB.setTextColor(getResources().getColor(R.color.radio_button_not_selected));
                        femaleRB.setTextColor(getResources().getColor(R.color.radio_button_selected));
                        break;
                    }
                }
            }
        });

        dobEt = v.findViewById(R.id.dob_et);
        emailEt = v.findViewById(R.id.email_et);
        inputLayMobileNumber = v.findViewById(R.id.input_lay_mobile_no);
        inputMobileNumber = v.findViewById(R.id.input_mobile_no);
        prefixLL = v.findViewById(R.id.country_lay);
        flagIV = v.findViewById(R.id.flag_iv);
        countryCodeTv = v.findViewById(R.id.country_mobile_code_tv);

        inputMobileNumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        inputMobileNumber.setClickable(false);
        inputMobileNumber.setLongClickable(false);
        inputMobileNumber.setFocusable(false);
        inputMobileNumber.setClickable(false);
        inputMobileNumber.setLongClickable(false);
        inputMobileNumber.setFocusable(false);

        mobileNumberEditIv = v.findViewById(R.id.mobile_edit_iv);
        mobileNumberEditIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        countryCodeTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        String countryID = ((UserProfileActivity) getActivity()).getCountryID();
        flagIV.setImageResource(getActivity().getResources().getIdentifier("drawable/"
                + countryID, null, getActivity().getPackageName()));
        countryCodeTv.setText("+" + ((UserProfileActivity) getActivity()).getCountryDialCode());

        countryCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserProfileActivity) getActivity()).hideSoftKeyPad();
                final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                        .setExpanded(false)
                        .setGravity(Gravity.TOP)
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
                        String pngName = countryStrArray[1].trim().toLowerCase();
                        flagIV.setImageResource(getActivity().getResources().getIdentifier("drawable/" + pngName, null, getActivity().getPackageName()));

                        LOG.d("CountryISOCode", "" + countryStrArray[0]);
                        String ISOCode = countryStrArray[0];
                        countryCodeTv.setText("");
                        countryCodeTv.setText("+" + ISOCode);
                        countryCodeTv.invalidate();
                        ((UserProfileActivity) getActivity()).hideSoftKeyPad();
                        dialog.dismiss();
                    }
                });
            }
        });

        createProfileBttn = v.findViewById(R.id.edit_profile_bttn);

        profileIV.setOnClickListener(viewProfileOnClickListener);
        editProfilePicIV.setOnClickListener(editProfileOnClickListener);

        dobEt.setKeyListener(null);
        dobEt.setClickable(true);
        final Drawable purchase_date_drawable = getResources().getDrawable(R.drawable.ic_calendar);
        final Drawable clear_purchase_date_drawable = getResources().getDrawable(R.drawable.ic_clear);

        dobEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (dobEt.getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                        return false;

                    if (EditTextUtils.isEmpty(dobEt) && event.getRawX() >= (dobEt.getRight() - dobEt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();
                        ((UserProfileActivity) getActivity()).setDateResultTo(dobEt, null, false);
                        getActivity().showDialog(BaseFragmentActivity.DATE_PICKER_ID);
                        return true;
                    } else if (!EditTextUtils.isEmpty(dobEt) && event.getRawX() >= (dobEt.getRight() - dobEt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        dobEt.setText("");
                        return true;
                    } else {
                        if (EditTextUtils.isEmpty(dobEt)) {
                            ((BaseFragmentActivity) getActivity()).hideSoftKeyPad();
                            ((UserProfileActivity) getActivity()).setDateResultTo(dobEt, null, false);
                            getActivity().showDialog(BaseFragmentActivity.DATE_PICKER_ID);
                        }
                        return true;
                    }
                } else
                    return false;
            }
        });

        dobEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (EditTextUtils.getText(dobEt).length() == 0) {
                    dobEt.setCompoundDrawablesWithIntrinsicBounds(null, null, purchase_date_drawable, null);
                } else if (EditTextUtils.getText(dobEt).length() > 0) {
                    dobEt.setCompoundDrawablesWithIntrinsicBounds(null, null, clear_purchase_date_drawable, null);
                }
            }
        });

        createProfileBttn.setOnClickListener(updateProfileOnClickListener);

        mScrollView = v.findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        dobEt.setOnFocusChangeListener((view, hasFocus) -> {
                    if (hasFocus) {
                        ((BaseFragmentActivity<BaseViewModal>) getActivity()).hideSoftKeyPad();
                    }
                }
        );

        return v;
    }

    View.OnClickListener updateProfileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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

    }

    @Override
    public void updateUserProfile(String msg) {

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
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (customerProfile.getFirstName() != null) {
                            nameEt.setText(customerProfile.getFirstName());
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
                                    countryCodeTv.setText("");
                                    countryCodeTv.setText("+" + ISOCode);
                                    countryCodeTv.invalidate();
                                    countryCodeTv.setClickable(false);
                                    countryCodeTv.setLongClickable(false);
                                    countryCodeTv.setFocusable(false);
                                    prefixLL.setClickable(false);
                                    prefixLL.setLongClickable(false);
                                    prefixLL.setFocusable(false);
                                    break;
                                }
                            }
                        }

                        if (customerProfile.getGender() != null) {
                            if (customerProfile.getGender().startsWith("M")) {
                                genderRG.check(R.id.rb_male);
                            } else if (customerProfile.getGender().startsWith("F")) {
                                genderRG.check(R.id.rb_female);
                            }
                        }

                        if (customerProfile.getDateOfBirth() > 0) {
                            String formattedDob = Util.getDateString(customerProfile.getDateOfBirth());
                            dobEt.setText(formattedDob);
                        }
                        if (customerProfile.getEmail() != null) {
                            emailEt.setText(customerProfile.getEmail());
                        }
                        if (customerProfile.getMobileNumber() != null) {
                            inputMobileNumber.setText(customerProfile.getMobileNumber());
                        }

                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }
            });
        }
    }
}
