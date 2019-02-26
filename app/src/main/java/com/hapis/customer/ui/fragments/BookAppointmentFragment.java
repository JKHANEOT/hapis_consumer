package com.hapis.customer.ui.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.ui.BaseFragmentActivity;
import com.hapis.customer.ui.BookAppointmentActivity;
import com.hapis.customer.ui.DashboardActivity;
import com.hapis.customer.ui.callback.BookRideDialogCallBack;
import com.hapis.customer.ui.callback.BookRideInitDialogCallBack;
import com.hapis.customer.ui.callback.CommonSearchCallBack;
import com.hapis.customer.ui.callback.SelectDateAndTimeSlotCallBack;
import com.hapis.customer.ui.callback.SelectPreferredLocationCallBack;
import com.hapis.customer.ui.custom.materialedittext.MaterialEditText;
import com.hapis.customer.ui.fragments.search.enterprise.doctor.DoctorSearchByEnterpriseCallBack;
import com.hapis.customer.ui.fragments.search.enterprise.doctor.DoctorSearchByEnterpriseDialogFragment;
import com.hapis.customer.ui.fragments.search.enterprise.enterprises.EnterpriseSearchCallBack;
import com.hapis.customer.ui.fragments.search.enterprise.enterprises.EnterpriseSearchDialogFragment;
import com.hapis.customer.ui.fragments.timeslot.TimeSlotDialogFragment;
import com.hapis.customer.ui.models.appointments.AppointmentRequest;
import com.hapis.customer.ui.models.appointments.DoctorDetails;
import com.hapis.customer.ui.models.enterprise.EnterpriseAddressRequest;
import com.hapis.customer.ui.models.enterprise.EnterpriseRequest;
import com.hapis.customer.ui.models.enums.EnterpriseTypeEnum;
import com.hapis.customer.ui.models.enums.MasterDataUtils;
import com.hapis.customer.ui.models.enums.PaymentMode;
import com.hapis.customer.ui.models.enums.PaymentStatus;
import com.hapis.customer.ui.models.users.UserRequest;
import com.hapis.customer.ui.utils.DialogIconCodes;
import com.hapis.customer.ui.utils.EditTextUtils;
import com.hapis.customer.ui.utils.HapisSlotUtils;
import com.hapis.customer.ui.view.BaseView;
import com.hapis.customer.ui.view.BaseViewModal;
import com.hapis.customer.ui.view.BookAppointmentFragmentView;
import com.hapis.customer.ui.view.BookAppointmentFragmentViewModal;
import com.hapis.customer.utils.DateUtil;
import com.hapis.customer.utils.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAppointmentFragment extends BaseAbstractFragment<BookAppointmentFragmentViewModal> implements BookAppointmentFragmentView, SelectPreferredLocationCallBack {

    public static final String TAG = BookAppointmentFragment.class.getName();

    private View v;
    private AppCompatTextView listEmptyTv;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    private RelativeLayout select_preferred_location_rl, select_enterprise_rl, select_specialization_rl, select_doctor_rl, select_date_rl, select_time_slot_rl, appointment_you_are_following_up_rl;
    private MaterialEditText select_preferred_location_edittext, select_enterprise_edittext, select_specialization_edittext, select_doctor_edittext, select_date_edittext, select_time_slot_edittext, appointment_you_are_following_up_edittext;

    private AppCompatButton reset_button, book_button, new_appointment_type_button, followup_appointment_type_button;
    private LinearLayout new_appointment_type_ll,followup_appointment_type_ll, bottom_button_ll, wallet_account_balance_ll;

    private TextInputEditText input_sr_message;

    private RadioGroup payment_mode_rg;
    private RadioButton payment_mode_coc_rb, payment_mode_online_rb, payment_mode_wallet_account_rb;

    private AppCompatTextView consultation_fee__value_tv, wallet_account_balance_value_tv;

    private int appointmentTypeId;

    private void updateAppointmentType(int clickedAppointmentTypeId){
        switch (clickedAppointmentTypeId){
            case R.id.new_appointment_type_button:{
                if(appointmentTypeId == clickedAppointmentTypeId){//Deselect
                    appointmentTypeId = 0;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    //TODO:Refresh UI.
                }else{//Select
                    appointmentTypeId = clickedAppointmentTypeId;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_green));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.md_green_500));
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.GONE);
                    //TODO:Refresh UI.
                }
                break;
            }
            case R.id.followup_appointment_type_button:{
                if(appointmentTypeId == clickedAppointmentTypeId){//Deselect
                    appointmentTypeId = 0;
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.GONE);
                    //TODO:Refresh UI.
                }else{//Select
                    appointmentTypeId = clickedAppointmentTypeId;
                    new_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_grey));
                    new_appointment_type_button.setTextColor(getResources().getColor(R.color.gray));
                    followup_appointment_type_ll.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button_green));
                    followup_appointment_type_button.setTextColor(getResources().getColor(R.color.md_green_500));

                    appointment_you_are_following_up_edittext.setText("");

                    appointment_you_are_following_up_rl.setVisibility(View.VISIBLE);
                    //TODO:Refresh UI.
                }
                break;
            }
        }
    }

    View.OnClickListener bookButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(appointmentTypeId == 0){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_type), Toast.LENGTH_SHORT).show();
            }else if(mSelectedLocation == null){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_preferred_location), Toast.LENGTH_SHORT).show();
            }else if(selectedEnterpriseRequest == null){
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
            }else if(EditTextUtils.isEmpty(select_specialization_edittext))
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_specialization), Toast.LENGTH_SHORT).show();
            else if(selectedDoctorDetails == null)
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_doctor), Toast.LENGTH_SHORT).show();
            else if(EditTextUtils.isEmpty(select_date_edittext))
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_date), Toast.LENGTH_SHORT).show();
            else if(mSelectedTimeSlot != null && mSelectedTimeSlot.size() == 0)
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_a_time_slot_to_continue), Toast.LENGTH_SHORT).show();
            else if(!((payment_mode_rg.getCheckedRadioButtonId() == R.id.payment_mode_wallet_account_rb) || (payment_mode_rg.getCheckedRadioButtonId() == R.id.payment_mode_coc_rb)))
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_payment_mode), Toast.LENGTH_SHORT).show();
            else if(payment_mode_rg.getCheckedRadioButtonId() == R.id.payment_mode_wallet_account_rb && walletBalance < consultationFee)
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.insufficient_wallet_balance_plz_recharge_to_continue), Toast.LENGTH_SHORT).show();
            else{
                String appointmentDate = EditTextUtils.getText(select_date_edittext);
                String doctorCode = selectedDoctorDetails.getDoctorCode();
                String hospitalCode = selectedEnterpriseRequest.getEnterpriseCode();
                int slotBooked = selectedIndex;
                String notes = EditTextUtils.getText(input_sr_message);

                Integer paymentMode = (payment_mode_rg.getCheckedRadioButtonId() == R.id.payment_mode_wallet_account_rb) ? PaymentMode.WALLET.code() : null;
                Integer paymentStatus = PaymentStatus.PENDING.code();

                if(paymentMode != null)
                    paymentStatus = PaymentStatus.PAID.code();

                ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));

                mViewModal.createAppointment(appointmentDate, doctorCode, hospitalCode, slotBooked, paymentMode, consultationFee, paymentStatus, notes);
            }
        }
    };

    View.OnClickListener resetButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reset();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        appointment_you_are_following_up_rl = v.findViewById(R.id.appointment_you_are_following_up_rl);
        appointment_you_are_following_up_edittext = v.findViewById(R.id.appointment_you_are_following_up_edittext);

        reset_button = v.findViewById(R.id.reset_button);
        reset_button.setOnClickListener(resetButtonOnClickListener);

        book_button = v.findViewById(R.id.book_button);
        book_button.setOnClickListener(bookButtonOnClickListener);

        new_appointment_type_button = v.findViewById(R.id.new_appointment_type_button);
        followup_appointment_type_button = v.findViewById(R.id.followup_appointment_type_button);

        new_appointment_type_ll = v.findViewById(R.id.new_appointment_type_ll);
        new_appointment_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentType(R.id.new_appointment_type_button);
            }
        });
        followup_appointment_type_ll = v.findViewById(R.id.followup_appointment_type_ll);
        followup_appointment_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentType(R.id.followup_appointment_type_button);
            }
        });

        select_preferred_location_rl = v.findViewById(R.id.select_preferred_location_rl);
        select_preferred_location_edittext = v.findViewById(R.id.select_preferred_location_edittext);

        select_preferred_location_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectLocationFragment();
            }
        });

        select_enterprise_rl = v.findViewById(R.id.select_enterprise_rl);
        select_enterprise_edittext = v.findViewById(R.id.select_enterprise_edittext);

        select_enterprise_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedLocation != null && mSelectedLocation.size() > 0 && mSelectedLocation.size() == 3) {
                    ((BookAppointmentActivity) getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
                    mViewModal.getEnterprisesBy(EnterpriseTypeEnum.HOSPITAL.code(), mSelectedLocation.get(2));
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_preferred_location), Toast.LENGTH_SHORT).show();
                }
            }
        });

        select_specialization_rl = v.findViewById(R.id.select_specialization_rl);
        select_specialization_edittext = v.findViewById(R.id.select_specialization_edittext);

        select_specialization_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonSearchDialogFragment dialog =
                        CommonSearchDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new CommonSearchCallBack() {
                            @Override
                            public void updateSelectedValue(String selectedValue) {
                                select_specialization_edittext.setText(selectedValue);
                            }
                        }, MasterDataUtils.getSpecialisation(), "Specialisation");
                dialog.setCancelable(false);
                dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);
            }
        });

        select_doctor_rl = v.findViewById(R.id.select_doctor_rl);
        select_doctor_edittext = v.findViewById(R.id.select_doctor_edittext);

        select_doctor_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextUtils.isEmpty(select_specialization_edittext))
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_specialization), Toast.LENGTH_SHORT).show();
                else if(selectedEnterpriseRequest == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
                else{
                    ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.please_wait));
                    mViewModal.getDoctorsByEnterprise(selectedEnterpriseRequest.getEnterpriseCode(), EditTextUtils.getText(select_specialization_edittext));
                }
            }
        });

        select_date_rl = v.findViewById(R.id.select_date_rl);
        select_date_edittext = v.findViewById(R.id.select_date_edittext);

        select_date_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseFragmentActivity<BaseViewModal>) getActivity()).hideSoftKeyPad();
                ((BaseFragmentActivity<BaseViewModal>) getActivity()).setDateResultTo(select_date_edittext, null, true);
                getActivity().showDialog(BaseFragmentActivity.DATE_PICKER_ID1);
            }
        });

        select_time_slot_rl = v.findViewById(R.id.select_time_slot_rl);
        select_time_slot_edittext = v.findViewById(R.id.select_time_slot_edittext);

        select_time_slot_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDoctorDetails == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_doctor), Toast.LENGTH_SHORT).show();
                else if(EditTextUtils.isEmpty(select_date_edittext))
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_appointment_date), Toast.LENGTH_SHORT).show();
                else if(selectedEnterpriseRequest == null)
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.please_select_enterprise), Toast.LENGTH_SHORT).show();
                else{
                    String selectedDateForAppointment = EditTextUtils.getText(select_date_edittext);
                    selectedDateForAppointment = DateUtil.changeDateFormat(selectedDateForAppointment, DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN, DateUtil.DATE_FORMAT_yyyy_MM_dd_SEP_HIPHEN);

                    mViewModal.getTimeSlotByDoctorEnterpriseAndDate(selectedDoctorDetails.getDoctorCode(), selectedDateForAppointment, selectedEnterpriseRequest.getEnterpriseCode());
                }
            }
        });

        consultation_fee__value_tv = v.findViewById(R.id.consultation_fee__value_tv);
        consultation_fee__value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0));

        wallet_account_balance_ll = v.findViewById(R.id.wallet_account_balance_ll);
        wallet_account_balance_ll.setVisibility(View.GONE);

        wallet_account_balance_value_tv = v.findViewById(R.id.wallet_account_balance_value_tv);

        payment_mode_rg = v.findViewById(R.id.payment_mode_rg);
        payment_mode_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateConsultationFee(checkedId);
            }
        });
        payment_mode_coc_rb = v.findViewById(R.id.payment_mode_coc_rb);
        payment_mode_online_rb = v.findViewById(R.id.payment_mode_online_rb);
        payment_mode_wallet_account_rb = v.findViewById(R.id.payment_mode_wallet_account_rb);

        input_sr_message = v.findViewById(R.id.input_sr_message);

        bottom_button_ll = v.findViewById(R.id.bottom_button_ll);

        updateAppointmentType(R.id.new_appointment_type_button);

        if(((BookAppointmentActivity)getActivity()).getAppointmentRequest() != null){
            new LoadAppointmentDetails().execute(((BookAppointmentActivity)getActivity()).getAppointmentRequest());
        }

        return v;
    }

    private void updateConsultationFee(int checkedId){
        switch (checkedId){
            case R.id.payment_mode_online_rb:
            {
                wallet_account_balance_ll.setVisibility(View.GONE);
                wallet_account_balance_value_tv.setText(Util.getFormattedAmount(0));
                break;
            }
            case R.id.payment_mode_wallet_account_rb:
            {
                wallet_account_balance_ll.setVisibility(View.VISIBLE);
                wallet_account_balance_value_tv.setText(Util.getFormattedAmount(0));
                mViewModal.getAvailableWalletBalance();
                break;
            }
            case R.id.payment_mode_coc_rb:
            {
                wallet_account_balance_ll.setVisibility(View.GONE);
                wallet_account_balance_value_tv.setText(Util.getFormattedAmount(0));
                break;
            }
        }
    }

    private EnterpriseRequest selectedEnterpriseRequest;

    @Override
    protected Class getViewModalClass() {
        return BookAppointmentFragmentViewModal.class;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void updateEnterpriseByTypeAndCity(List<EnterpriseRequest> enterpriseRequestList) {

        EnterpriseSearchDialogFragment dialog =
                EnterpriseSearchDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new EnterpriseSearchCallBack() {
                    @Override
                    public void updateSelectedValue(EnterpriseRequest enterpriseRequest) {
                        selectedEnterpriseRequest = enterpriseRequest;
                        select_enterprise_edittext.setText(selectedEnterpriseRequest.getEnterpriseName());
                    }
                }, enterpriseRequestList, "Hospital");
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);

        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
    }

    private DoctorDetails selectedDoctorDetails;

    @Override
    public void failedToProcess(String errorMsg) {
        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
        ((BookAppointmentActivity)getActivity()).showError(errorMsg, null, null, null, DialogIconCodes.DIALOG_FAILED.getIconCode());
    }

    @Override
    public void updateDoctorsByEnterpriseAndSpecialization(List<DoctorDetails> doctorDetailsList) {
        DoctorSearchByEnterpriseDialogFragment dialog =
                DoctorSearchByEnterpriseDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new DoctorSearchByEnterpriseCallBack() {
                    @Override
                    public void updateSelectedValue(DoctorDetails doctorDetails) {
                        selectedDoctorDetails = doctorDetails;
                        select_doctor_edittext.setText(selectedDoctorDetails.getFullName());
                    }
                }, doctorDetailsList, "Doctors");
        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);

        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
    }

    private List<String> mSelectedTimeSlot;
    private int selectedIndex;
    private double consultationFee;

    @Override
    public void updateDoctorAvailableTimeSlot(List<String> availableTimeSlot, Double fee) {
        if(fee != null && fee.doubleValue() > 0){
            consultationFee = fee.doubleValue();
            consultation_fee__value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(consultationFee));
        }else{
            consultationFee = 0.0;
            consultation_fee__value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(consultationFee));
        }
        TimeSlotDialogFragment dialog = TimeSlotDialogFragment.newInstance((BaseFragmentActivity)getActivity(), new SelectDateAndTimeSlotCallBack() {
            @Override
            public void updateSelectedDate(List<String> selectedDate) {

            }

            @Override
            public void updateSelectedTime(List<String> selectedTime) {

            }

            @Override
            public void updateSelectedTime(List<String> selectedTime, int index) {
                mSelectedTimeSlot = selectedTime;
                select_time_slot_edittext.setText(mSelectedTimeSlot.get(0));
                selectedIndex = index;
            }
        }, availableTimeSlot, mSelectedTimeSlot);
        dialog.show(getActivity().getSupportFragmentManager(), SelectBookingTimeSlotDialogFragment.TAG);
    }

    @Override
    public void createAppointment(String msg) {
        ((BookAppointmentActivity)getActivity()).dismissProgressDialog();
        AppointmentBookingDetailsDialogFrag dialog = AppointmentBookingDetailsDialogFrag.newInstance(bookRideInitDialogCallBack, selectedDoctorDetails, selectedEnterpriseRequest, EditTextUtils.getText(select_time_slot_edittext), EditTextUtils.getText(select_date_edittext));
        dialog.show(getActivity().getSupportFragmentManager(), AppointmentBookingDetailsDialogFrag.TAG);

    }

    BookRideDialogCallBack bookRideDialogCallBack = new BookRideDialogCallBack() {
        @Override
        public void bookRideDone() {
            moveToDashboard();
        }

        @Override
        public void bookRideWindowClosed() {
            moveToDashboard();
        }
    };

    BookRideInitDialogCallBack bookRideInitDialogCallBack = new BookRideInitDialogCallBack() {
        @Override
        public void bookRide(int rideIcon) {
            BookRideDialogFragment bookRideDialogFragment = BookRideDialogFragment.newInstance(bookRideDialogCallBack, rideIcon);
            bookRideDialogFragment.show(getActivity().getSupportFragmentManager(), BookRideDialogFragment.TAG);
        }

        @Override
        public void bookRideSkip() {
            moveToDashboard();
        }
    };

    private void moveToDashboard(){
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        try {
            getActivity().finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double walletBalance = 0.0;

    @Override
    public void updateAvailableBalance(Double balance) {
        if(balance != null && balance.doubleValue() > 0){
            walletBalance = balance.doubleValue();
            wallet_account_balance_value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(walletBalance));
        }else
            walletBalance = 0.0;
    }

    private List<String> mSelectedLocation;

    public void showSelectLocationFragment() {

        new LoadLocationDetailsFromFile().execute();
    }

    @Override
    public void updateSelectedLocation(List<String> selectedLocation) {
        mSelectedLocation = selectedLocation;
        if(mSelectedLocation != null && mSelectedLocation.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(mSelectedLocation.get(2)+"-");
            stringBuilder.append(mSelectedLocation.get(1)+"-");
            stringBuilder.append(mSelectedLocation.get(0));

            select_preferred_location_edittext.setText(stringBuilder.toString());
        }
    }

    class LoadLocationDetailsFromFile extends AsyncTask<Void, Void, Map<String, Map<String, List<String>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ((BookAppointmentActivity)getActivity()).showProgressDialog(getActivity(), "Loading location please wait..");
        }

        @Override
        protected Map<String, Map<String, List<String>>> doInBackground(Void... voids) {

            Map<String, Map<String, List<String>>> locationList = new HashMap<>();

            List<String> city = new ArrayList<>();
            city.add("Bangalore");
            city.add("Mysore");

            HashMap<String, List<String>> state = new HashMap<>();
            state.put("Karnataka", city);

            locationList.put("India", state);

            /*String countryJson = getAssetJsonData("countries.json");
            List<CountryModel> countryJsonModels = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<CountryModel>>() {
            }.getType());
            String stateJson = getAssetJsonData("cities.json");
            List<StateModel> stateJsonModels  = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<StateModel>>() {
            }.getType());
            String cityJson = getAssetJsonData("states.json");
            List<CityModel> cityJsonModels = JSONAdaptor.getGson().fromJson(countryJson, new TypeToken<List<CityModel>>() {
            }.getType());*/

            return locationList;
        }

        @Override
        protected void onPostExecute(Map<String, Map<String, List<String>>> locationList) {
            super.onPostExecute(locationList);

            ((BookAppointmentActivity)getActivity()).dismissProgressDialog();

            SelectPreferredLocationDialogFragment dialog =
                    SelectPreferredLocationDialogFragment.newInstance(BookAppointmentFragment.this, locationList, mSelectedLocation);
            dialog.setCancelable(false);
            dialog.show(getActivity().getSupportFragmentManager(), SelectPreferredLocationDialogFragment.TAG);
        }
    }

    private String getAssetJsonData(final String josnFileName) {
        String json = null;
        try {
            InputStream is = HapisApplication.getApplication().getAssets().open(josnFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }

    class LoadAppointmentDetails extends AsyncTask<AppointmentRequest, Void, Void> {

        @Override
        protected Void doInBackground(AppointmentRequest... appointmentRequests) {
            updateData(appointmentRequests[0]);
            return null;
        }
    }

    private void updateData(final AppointmentRequest appointmentRequest){
        if(getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(appointmentRequest != null){
                        EnterpriseRequest selectedEnterpriseRequest = appointmentRequest.getEnterpriseRequest();
                        if(selectedEnterpriseRequest != null){
                            if(selectedEnterpriseRequest.getAddresses() != null && selectedEnterpriseRequest.getAddresses().size() > 0){
                                EnterpriseAddressRequest enterpriseAddressRequest = (EnterpriseAddressRequest)selectedEnterpriseRequest.getAddresses().toArray()[0];
                                if(enterpriseAddressRequest != null){
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(enterpriseAddressRequest.getCity()+"-");
                                    stringBuilder.append(enterpriseAddressRequest.getStateCode()+"-");
                                    stringBuilder.append(enterpriseAddressRequest.getCountryCode());

                                    select_preferred_location_edittext.setText(stringBuilder.toString());
                                    select_preferred_location_edittext.setEnabled(false);
                                }
                            }

                            select_enterprise_edittext.setText(selectedEnterpriseRequest.getEnterpriseName());
                            select_enterprise_edittext.setEnabled(false);

                        }
                        UserRequest selectedDoctorDetails = appointmentRequest.getDoctorDetails();
                        if(selectedDoctorDetails != null){
                            select_specialization_edittext.setText(selectedDoctorDetails.getRoles().toLowerCase());
                            select_specialization_edittext.setEnabled(false);

                            StringBuilder stringBuilder = new StringBuilder();

                            if(selectedDoctorDetails.getFirstName() != null)
                                stringBuilder.append(selectedDoctorDetails.getFirstName()+" ");
                            if(selectedDoctorDetails.getMiddleName() != null)
                                stringBuilder.append(selectedDoctorDetails.getMiddleName()+" ");
                            if(selectedDoctorDetails.getLastName() != null)
                                stringBuilder.append(selectedDoctorDetails.getLastName());

                            select_doctor_edittext.setText(stringBuilder.toString());
                            select_doctor_edittext.setEnabled(false);
                        }

                        if(appointmentRequest.getAppointmentDate() != null) {
                            select_date_edittext.setText(appointmentRequest.getAppointmentDate());
                            select_date_edittext.setEnabled(false);
                        }
                        if(appointmentRequest.getSlotBooked() != null){
                            select_time_slot_edittext.setText(HapisSlotUtils.getSlotName(appointmentRequest.getSlotBooked()));
                            select_time_slot_edittext.setEnabled(false);
                        }
                        if(appointmentRequest.getPaymentMode() != null && appointmentRequest.getPaymentMode().intValue() > 0){
                            payment_mode_coc_rb.setEnabled(false);
                            payment_mode_online_rb.setEnabled(false);
                            payment_mode_wallet_account_rb.setEnabled(false);

                            switch (appointmentRequest.getPaymentMode().intValue()){
                                case 1:{
                                    payment_mode_rg.check(R.id.payment_mode_coc_rb);
                                    break;
                                }
                                case 4:
                                case 2:{
                                    payment_mode_rg.check(R.id.payment_mode_online_rb);
                                    break;
                                }
                                case 3:{
                                    payment_mode_rg.check(R.id.payment_mode_wallet_account_rb);
                                    break;
                                }
                            }
                        }
                        if(appointmentRequest.getFee() != null && appointmentRequest.getFee().doubleValue() > 0){
                            consultation_fee__value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(appointmentRequest.getFee().doubleValue()));
                        }else{
                            consultation_fee__value_tv.setText(getResources().getString(R.string.rs_currency)+" "+Util.getFormattedAmount(0.0));
                        }
                        input_sr_message.setEnabled(false);

                        bottom_button_ll.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void reset(){
        select_preferred_location_edittext.setText("");
        selectedEnterpriseRequest = null;
        select_enterprise_edittext.setText("");
        selectedDoctorDetails = null;
        select_specialization_edittext.setText("");
        select_doctor_edittext.setText("");
        select_date_edittext.setText("");
        select_time_slot_edittext.setText("");
        input_sr_message.setText("");
    }
}
