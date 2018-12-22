package com.hapis.customer.ui.custom;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.hapis.customer.R;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.custom.dialogplus.DialogPlus;
import com.hapis.customer.ui.custom.dialogplus.OnCancelListener;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.dialogplus.ViewHolder;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.models.AddressModel;
import com.hapis.customer.ui.models.enums.AddressType;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Javeed Khan H on 14/03/18.
 */
public class AddEditAddressDialog {

    private static final String TAG = AddEditAddressDialog.class.getSimpleName();

    private AddressAddOrUpdateInputListener addressAddOrUpdateInputListener;
    private Activity mActivity;
    private MaterialEditText pincode_edittext, city_edittext, state_edittext, country_edittext, address_edittext, landmark_edittext;
    /*private TextInputLayout input_layout_pincode_edittext, input_layout_city_edittext, input_layout_state_edittext,
            input_layout_country_edittext, input_layout_address_edittext, input_layout_landmark_edittext;*/
    DialogPlus dialog;
    private AppCompatTextView title_address_tv, cancel_address_tv, submit_address_tv;
    private AddressModel mAddressModel;

    public AddEditAddressDialog(Activity mActivity, AddressAddOrUpdateInputListener addressAddOrUpdateInputListener, AddressModel addressModel) {
        this.mActivity = mActivity;
        this.addressAddOrUpdateInputListener = addressAddOrUpdateInputListener;
        mAddressModel = addressModel;
    }

    private void setValuesToViews() {
        if (mAddressModel != null) {
            if (mAddressModel.getGuid() != null && mAddressModel.getGuid().length() > 0) {
                title_address_tv.setText(mActivity.getResources().getString(R.string.update_address));
                submit_address_tv.setText(mActivity.getResources().getString(R.string.update));
            }
            /*if (mAddressModel.getName() != null)
                nameEt.setText(mAddressModel.getName());
            if (mAddressModel.getMobileNumber() != null)
                mobileEt.setText(mAddressModel.getMobileNumber());*/
            if (mAddressModel.getPinCode() != null)
                pincode_edittext.setText(mAddressModel.getPinCode());
            if (mAddressModel.getCity() != null)
                city_edittext.setText(mAddressModel.getCity());
            /*if (mAddressModel.getDistrict() != null)
                districtEt.setText(mAddressModel.getDistrict());*/
            if (mAddressModel.getStateCode() != null)
                state_edittext.setText(mAddressModel.getStateCode());
            if (mAddressModel.getCountry() != null)
                country_edittext.setText(mAddressModel.getCountry());
            if (mAddressModel.getAddressLine1() != null)
                address_edittext.setText(mAddressModel.getAddressLine1());
            if (mAddressModel.getAddressLine2() != null)
                landmark_edittext.setText(mAddressModel.getAddressLine2());
        }
    }

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (pincode_edittext.getText().toString().trim().length() > 0) {

                if (((BaseFragmentActivity) mActivity).mSystemService.getActiveNetworkInfo() == null) {
                    return;
                }

                String pinCode = EditTextUtils.getText(pincode_edittext);
                if (pinCode != null && pinCode.length() > 0) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
                    builder.append(pinCode);
                    builder.append("&sensor=true");
                    builder.append("&key=" + mActivity.getResources().getString(R.string.google_api_key));

                    final String urlString = builder.toString();
                    LOG.d(TAG, urlString);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                                http://stackoverflow.com/questions/12870332/how-can-i-iterate-jsonobject-to-get-individual-items
                            InputStream iStream = null;
                            HttpURLConnection urlConnection = null;
                            try {
                                URL url = new URL(urlString);
                                // Creating an http connection to communicate with url
                                urlConnection = (HttpURLConnection) url.openConnection();
                                // Connecting to url
                                urlConnection.connect();
                                // Reading data from url
                                iStream = urlConnection.getInputStream();

                                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                                StringBuffer sb = new StringBuffer();

                                String line = "";
                                while ((line = br.readLine()) != null) {
                                    sb.append(line);
                                }

                                final String jsonResponse = sb.toString();
                                if (jsonResponse != null) {
                                    LOG.d(TAG, jsonResponse);
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(jsonResponse);
                                                if (jsonObject != null) {
                                                    JSONArray resultsJsonArray = jsonObject.getJSONArray("results");

                                                    if (resultsJsonArray != null && resultsJsonArray.length() > 0) {
                                                        JSONArray address_componentsJsonArray = resultsJsonArray.getJSONObject(0).getJSONArray("address_components");
                                                        if (address_componentsJsonArray != null) {
                                                            HashMap<String, String> addressHashMap = new HashMap<String, String>();
                                                            for (int i = 0; i < address_componentsJsonArray.length(); i++) {
                                                                JSONObject address = address_componentsJsonArray.getJSONObject(i);
                                                                String type = null;
                                                                if (address != null) {
                                                                    JSONArray typesJsonArray = address.getJSONArray("types");
                                                                    type = typesJsonArray.getString(0);
                                                                }

                                                                if (type != null && !type.equalsIgnoreCase("postal_code")) {
                                                                    addressHashMap.put(type, address.getString("long_name"));
                                                                }
                                                            }

                                                            if (addressHashMap != null && addressHashMap.size() > 0) {
                                                                if (addressHashMap.containsKey("country")) {
                                                                    country_edittext.setText(addressHashMap.get("country"));
                                                                } else {
                                                                    country_edittext.setText("");
                                                                }
                                                                if (addressHashMap.containsKey("administrative_area_level_1")) {//State
                                                                    state_edittext.setText(addressHashMap.get("administrative_area_level_1"));
                                                                } else
                                                                    state_edittext.setText("");

                                                                /*if (addressHashMap.containsKey("administrative_area_level_2")) {//District
                                                                    districtEt.setText(addressHashMap.get("administrative_area_level_2"));
                                                                } else
                                                                    districtEt.setText("");*/

                                                                if (addressHashMap.containsKey("locality")) {//city or town
                                                                    city_edittext.setText(addressHashMap.get("locality"));
                                                                } else {
                                                                    city_edittext.setText("");
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        resetAddress();
                                                        LOG.d(TAG, "Json Data not available.");
                                                    }
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                resetAddress();
                                            }
                                        }
                                    });
                                } else {
                                    resetAddress();
                                    LOG.d(TAG, "Json Data not available.");
                                }

                                br.close();

                            } catch (Exception e) {
                                resetAddress();
                                LOG.i("Excpt in downloading", e.toString());
                            } finally {
                                try {
                                    if (iStream != null)
                                        iStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (urlConnection != null)
                                    urlConnection.disconnect();
                            }
                        }
                    }).start();
                }
            }
        }
    };

    private void resetAddress() {
        city_edittext.setText("");
        state_edittext.setText("");
        country_edittext.setText("");
    }

    public void show() {
        if (dialog == null) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.add_edit_address_input_dialog, null);

            title_address_tv = view.findViewById(R.id.title_address_tv);
            cancel_address_tv = view.findViewById(R.id.cancel_address_tv);
            submit_address_tv = view.findViewById(R.id.submit_address_tv);

            pincode_edittext = view.findViewById(R.id.pincode_edittext);
            pincode_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!EditTextUtils.isEmpty(pincode_edittext)) {
                        pincode_edittext.setError(null);
                    }
                }
            });
            pincode_edittext.setOnFocusChangeListener(focusChangeListener);

            city_edittext = view.findViewById(R.id.city_edittext);

            state_edittext = view.findViewById(R.id.state_edittext);

            country_edittext = view.findViewById(R.id.country_edittext);

            address_edittext = view.findViewById(R.id.address_edittext);

            landmark_edittext = view.findViewById(R.id.landmark_edittext);

            setValuesToViews();

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(DialogPlus dialog, View view) {

                    if (view.getId() == R.id.submit_address_tv) {
                        try {
                            if (!isAddressValid()) {
                                LOG.d(TAG, "User data not valid so going to return from ValidateAndDoBuyWarranty");
                                return;
                            }
                            if (addressAddOrUpdateInputListener != null) {
                                ((BaseFragmentActivity) mActivity).hideSoftKeyPad();

                                if (mAddressModel == null) {
                                    mAddressModel = new AddressModel();
                                    mAddressModel.setAddressType(AddressType.DEFAULT.getCode());
                                }

//                                mAddressModel.setName(UserProfileTable.getStringColumnValue(UserProfileTable.COLUMN_FIRST_NAME, UserProfileTable.COLUMN_USER_ID_OR_GUID, Application.getInstance().getUserProfileUserIdOrGuid()));
                                mAddressModel.setPinCode(pincode_edittext.getText().toString().trim());
                                mAddressModel.setCity(city_edittext.getText().toString().trim());
                                mAddressModel.setStateCode(state_edittext.getText().toString().trim());
                                mAddressModel.setCountry(country_edittext.getText().toString().trim());
                                mAddressModel.setAddressLine1(address_edittext.getText().toString().trim());

                                String landMark = landmark_edittext.getText().toString().trim();
                                if (landMark != null && landMark.length() > 0)
                                    mAddressModel.setAddressLine2(landMark);

                                addressAddOrUpdateInputListener.onAddressAddOrUpdateInputCompleted(mAddressModel);
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            AlertUtil.showAlert(mActivity, R.string.my_address, e.getMessage(), DialogIconCodes.DIALOG_FAILED.getIconCode());
                        }

                        dialog.dismiss();
                    } else if (view.getId() == R.id.cancel_address_tv) {
//                        dialog.dismiss();
                        showConfirmCancelDialog();
                    }
                }
            };

            OnCancelListener onCancelListener = new OnCancelListener() {
                @Override
                public void onCancel(final DialogPlus dialog) {
                    showConfirmCancelDialog();
                }
            };
            dialog = DialogPlus.newDialog(mActivity)
                    .setContentHolder(new ViewHolder(view))
                    .setGravity(Gravity.BOTTOM)
                    .setOnCancelListener(onCancelListener)
                    .setOnClickListener(onClickListener)
                    .setCancelable(false)
                    .setExpanded(false)
                    .create(false);
            dialog.show();

        } else {
            dialog.show();
        }
    }

    public void showConfirmCancelDialog() {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            public void run() {
                final Dialog dlg = new Dialog(mActivity);
                dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dlg.setContentView(R.layout.custom_alert_dialog_fragment_two_buttons);
                dlg.setCancelable(false);
                dlg.show();

                ((ImageView) dlg.findViewById(R.id.image_view_status)).setImageResource(R.mipmap.alert_wrong);
                AppCompatTextView tv_message = dlg.findViewById(R.id.alert_message);

                AppCompatTextView titleTv = dlg.findViewById(R.id.alert_title);

                titleTv.setText("");

                if (mAddressModel != null && mAddressModel.getGuid() != null)
                    tv_message.setText(mActivity.getResources().getString(R.string.do_you_wish_to_cancel_update_address));
                else
                    tv_message.setText(mActivity.getResources().getString(R.string.do_you_wish_to_cancel_add_address));

                Button bt_yes = dlg.findViewById(R.id.positive_btn);
                bt_yes.setText(mActivity.getResources().getString(R.string.yes_button));
                Button bt_no = dlg.findViewById(R.id.negative_btn);
                bt_no.setText(mActivity.getResources().getString(R.string.no_button));
                bt_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                        dialog.dismiss();
                    }
                });
                bt_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();
                        dialog.show();
                    }
                });
            }
        });
    }

    public boolean isShowing() {
        return (dialog != null) && dialog.isShowing();
    }


    /**
     * This method is used to show error alert.
     */
    private boolean isAddressValid() {
        boolean isValid;

        /*if (nameEt.getText().toString().trim().length() == 0) {
            nameTextLay.setEnabled(true);
            nameTextLay.setError("Please enter name");
            isValid = false;

        } else if (!Validator.isMobileNumValid(EditTextUtils.getText(mobileEt), mobileEt, getActivity())) {
            mobileTextLay.setEnabled(true);
            mobileTextLay.setError("Please enter a valid mobile number");
            isValid = false;

        } else*/
        if (pincode_edittext.getText().toString().trim().length() == 0) {
            pincode_edittext.setEnabled(true);
            pincode_edittext.setError(mActivity.getResources().getText(R.string.please_enter_pin_code));
            isValid = false;

        } else if (city_edittext.getText().toString().trim().length() == 0) {
            city_edittext.setEnabled(true);
            city_edittext.setError(mActivity.getResources().getText(R.string.please_enter_city_name));
            isValid = false;

        } else if (state_edittext.getText().toString().trim().length() == 0) {
            state_edittext.setEnabled(true);
            state_edittext.setError(mActivity.getResources().getText(R.string.please_enter_state_province));
            isValid = false;

        } /*else if (country_edittext.getText().toString().trim().length() == 0) {
            country_edittext.setEnabled(true);
            country_edittext.setError(mActivity.getResources().getText(R.string.please_enter_country));
            isValid = false;

        }*/ else if (address_edittext.getText().toString().trim().length() == 0) {
            address_edittext.setEnabled(true);
            address_edittext.setError(mActivity.getResources().getText(R.string.please_enter_valid_address));
            isValid = false;

        } else {
            isValid = true;
        }

        return isValid;
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public interface AddressAddOrUpdateInputListener {
        void onAddressAddOrUpdateInputCompleted(AddressModel addressModel);
    }
}
