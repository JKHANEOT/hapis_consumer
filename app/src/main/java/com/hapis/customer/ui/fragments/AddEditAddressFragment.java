package com.hapis.customer.ui.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.custom.dialogplus.OnClickListener;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.utils.AlertUtil;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.view.AddEditAddressFragmentView;
import com.hapis.customer.ui.view.AddEditAddressFragmentViewModal;
import com.hapis.customer.ui.view.BaseView;

/**
 * Created by JKHAN
 */
public class AddEditAddressFragment extends BaseAbstractFragment<AddEditAddressFragmentViewModal> implements AddEditAddressFragmentView {

    private static final String TAG = AddEditAddressFragment.class.getSimpleName();

    private Activity mActivity;
    private MaterialEditText pincode_edittext, city_edittext, state_edittext, country_edittext, address_edittext, landmark_edittext;
    private AppCompatTextView title_address_tv, cancel_address_tv, submit_address_tv;

    public AddEditAddressFragment() {
        // Required empty public constructor
    }

    /*View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
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

                                                                *//*if (addressHashMap.containsKey("administrative_area_level_2")) {//District
                                                                    districtEt.setText(addressHashMap.get("administrative_area_level_2"));
                                                                } else
                                                                    districtEt.setText("");*//*

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
    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_address_input_dialog, container, false);

        pincode_edittext = (MaterialEditText)view.findViewById(R.id.pincode_edittext);
        city_edittext = (MaterialEditText)view.findViewById(R.id.city_edittext);
        state_edittext = (MaterialEditText)view.findViewById(R.id.state_edittext);
        country_edittext = (MaterialEditText)view.findViewById(R.id.country_edittext);
        address_edittext = (MaterialEditText)view.findViewById(R.id.address_edittext);
        landmark_edittext = (MaterialEditText)view.findViewById(R.id.landmark_edittext);

        return view;
    }

    /**
     * This method is used to show error alert.
     */
    private boolean isAddressValid() {
        boolean isValid;

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

        } else if (country_edittext.getText().toString().trim().length() == 0) {
            country_edittext.setEnabled(true);
            country_edittext.setError(mActivity.getResources().getText(R.string.please_enter_country));
            isValid = false;

        } else if (address_edittext.getText().toString().trim().length() == 0) {
            address_edittext.setEnabled(true);
            address_edittext.setError(mActivity.getResources().getText(R.string.please_enter_valid_address));
            isValid = false;

        } else {
            isValid = true;
        }

        return isValid;
    }

    @Override
    protected Class getViewModalClass() {
        return AddEditAddressFragmentViewModal.class;
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
    public void showError(String errorMsg, OnClickListener onClickListener, String positiveLbl, String negativeLbl) {

    }
}
